package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.black_dog20.servertabinfo.client.objects.IRenderable;
import com.black_dog20.servertabinfo.client.objects.Player;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomPlayerList
{
    private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new CustomPlayerList.PlayerComparator());
    private final Minecraft mc;
    public static HashMap<String, TpsDimension> playerDims = new HashMap<>();

    public CustomPlayerList(Minecraft mc)
    {
        this.mc = mc;
    }
    
    public boolean render(int width) {
    		int y = 10;
    		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
    		List<NetworkPlayerInfo> list = ENTRY_ORDERING.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());
    		List<IRenderable> playerList = new ArrayList<IRenderable>();
    		for (NetworkPlayerInfo networkplayerinfo : list) {
    			playerList.add(new Player(networkplayerinfo,mc));
    		}
        
    		RenderHelper.RenderObjectList(playerList, mc, y, width);
    		return true;
    }  

    @SideOnly(Side.CLIENT)
    static class PlayerComparator implements Comparator<NetworkPlayerInfo>
        {
            private PlayerComparator()
            {
            }

            public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_)
            {
                ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
                ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
                return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
            }
        }
}