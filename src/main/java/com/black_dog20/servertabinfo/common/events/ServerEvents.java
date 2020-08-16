package com.black_dog20.servertabinfo.common.events;

import com.black_dog20.servertabinfo.Config;
import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.common.network.PacketHandler;
import com.black_dog20.servertabinfo.common.network.packets.PacketDimensions;
import com.black_dog20.servertabinfo.common.network.packets.PacketPlayers;
import com.black_dog20.servertabinfo.common.utils.Dimension;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber( modid = ServerTabInfo.MOD_ID)
public class ServerEvents {

    private static List<Dimension> DIMENSIONS = new LinkedList<>();
    private static Map<UUID, ResourceLocation> PLAYER_DIMENSIONS = new HashMap<>();
    private static int ticks = 0;

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.END)
            return;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server.getPlayerList().getCurrentPlayerCount() == 0){
            ticks = 0;
            return;
        }

        if(ticks % Config.REFRESH_TICKS.get() == 0) {
            Pair<Integer, Double> overall = getTpsAndMean(server.tickTimeArray);
            DIMENSIONS.clear();
            DIMENSIONS.add(new Dimension(new ResourceLocation(ServerTabInfo.MOD_ID, "overall"), overall.getSecond(), overall.getFirst()));
            for (ServerWorld world : server.forgeGetWorldMap().values()) {
                ResourceLocation name = world.func_234923_W_().func_240901_a_();
                Pair<Integer, Double> tpsAndMean = getTpsAndMean(server.getTickTime(world.func_234923_W_()));
                DIMENSIONS.add(new Dimension(name, tpsAndMean.getSecond(), tpsAndMean.getFirst()));
		    }

            List<ServerPlayerEntity> playerList = server.getPlayerList().getPlayers();
            PLAYER_DIMENSIONS.clear();
            for (ServerPlayerEntity player : playerList) {
                ResourceLocation name = player.getEntityWorld().func_234923_W_().func_240901_a_();
                PLAYER_DIMENSIONS.put(player.getUniqueID(), name);
            }

            PacketHandler.sendToAll(new PacketDimensions(DIMENSIONS));
            PacketHandler.sendToAll(new PacketPlayers(PLAYER_DIMENSIONS));

            ticks = 1;
            return;
        }
        ticks++;
    }

    private static Pair<Integer, Double> getTpsAndMean(long[] tickArray) {

        long sum = 0L;
        if (tickArray == null) {
            return Pair.of(0, 0.0D);
        }
        for (long tickTime : tickArray) {
            sum += tickTime;
        }
        double mean = (sum / tickArray.length) * 1.0E-006D;
        int tps = (int) Math.min(1000.0D / mean, 20);

        return Pair.of(tps, mean);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().world.isRemote) {
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) event.getPlayer();
            ResourceLocation name = playerEntity.getEntityWorld().func_234923_W_().func_240901_a_();
            PLAYER_DIMENSIONS.put(playerEntity.getUniqueID(), name);

            PacketHandler.sendTo(new PacketDimensions(DIMENSIONS), playerEntity);
            PacketHandler.sendTo(new PacketPlayers(PLAYER_DIMENSIONS), playerEntity);
        }
    }
}
