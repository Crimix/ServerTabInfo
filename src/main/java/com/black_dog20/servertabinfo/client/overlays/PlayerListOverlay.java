package com.black_dog20.servertabinfo.client.overlays;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.overlay.Overlay;
import com.black_dog20.bml.client.rows.Row;
import com.black_dog20.bml.client.rows.RowHelper;
import com.black_dog20.bml.client.rows.columns.BlankColumn;
import com.black_dog20.bml.client.rows.columns.Column;
import com.black_dog20.bml.client.rows.columns.HeadColumn;
import com.black_dog20.bml.client.rows.columns.ITextComponentColumn;
import com.black_dog20.bml.utils.dimension.DimensionUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.servertabinfo.Config;
import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.ClientDataManager;
import com.black_dog20.servertabinfo.client.keybinds.Keybinds;
import com.black_dog20.servertabinfo.common.utils.Dimension;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.black_dog20.servertabinfo.common.utils.Translations.*;

@OnlyIn(Dist.CLIENT)
public class PlayerListOverlay extends Overlay.Pre {

    private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator());
    private Minecraft minecraft;
    private FontRenderer fontRenderer;
    private ItemRenderer itemRenderer;
    private long lastRenderTime = Util.milliTime();
    private int ticks = 0;
    private int page = 1;

    public PlayerListOverlay() {
        this.minecraft = Minecraft.getInstance();
        this.fontRenderer = minecraft.fontRenderer;
        this.itemRenderer = minecraft.getItemRenderer();
    }

    @Override
    public void onRender(MatrixStack matrixStack, int width, int height) {
        if(Keybinds.SHOW.isKeyDown())
            return;
        int y = 10;
        int z = 0;
        if (Util.milliTime() - 2000 > lastRenderTime) {
            page = 1;
            ticks = 1;
        }
        int itemsPerPage = (int) Math.floor((height - 7 * y) / fontRenderer.FONT_HEIGHT);
        List<Row> rows = getRows();

        int maxPages = (int) Math.ceil(rows.size() / (double) itemsPerPage);

        if (ticks % 300 == 0) {
            if (page >= maxPages)
                page = 1;
            else
                page++;
            ticks = 1;
        }

        rows = getPagedRows(rows, itemsPerPage);
        int maxWidth = RowHelper.getMaxWidth(rows);
        int x = width / 2 - maxWidth / 2;

        DrawingContext drawingContext = new DrawingContext(matrixStack, width, height, x, y, z, fontRenderer, itemRenderer);
        y = RowHelper.drawRowsWithBackground(drawingContext, rows);
        fontRenderer.func_243246_a(matrixStack, PAGE.get(page, maxPages), width / 2 + 2, y + 2, -1);
        ticks++;
        lastRenderTime = Util.milliTime();
    }

    @Override
    public boolean doRender(RenderGameOverlayEvent.ElementType elementType) {
        if (elementType == RenderGameOverlayEvent.ElementType.PLAYER_LIST) {
            return Config.REPLACE_PLAYER_LIST.get();
        }

        return false;
    }

    @Override
    public boolean doesCancelEvent() {
        return true;
    }

    private List<Row> getRows() {
        ClientPlayNetHandler nethandlerplayclient = this.minecraft.player.connection;
        List<NetworkPlayerInfo> list = ENTRY_ORDERING.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());

        return list.stream()
                .map(this::buildRow)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private Row buildRow(NetworkPlayerInfo playerInfo) {
        Row.RowBuilder builder = new Row.RowBuilder();
        Scoreboard scoreboard = Optional.ofNullable(minecraft.world)
                .map(ClientWorld::getScoreboard)
                .orElse(null);

        return builder
                .withColumn(HeadColumn.of("head", playerInfo))
                .withColumn(ITextComponentColumn.of("name", getPlayerName(playerInfo)))
                .withColumn(BlankColumn.of("nameSpace", 6))
                .withColumn(ITextComponentColumn.of("dim", getPlayerDim(playerInfo)))
                .withColumn(BlankColumn.of("dimSpace", 3))
                .withColumn(ITextComponentColumn.of("score", getPlayerScore(playerInfo, scoreboard)), showScoreObjective(scoreboard))
                .withColumn(BlankColumn.of("scoreSpace", 3), showScoreObjective(scoreboard))
                .withColumn(ITextComponentColumn.of("ping", getPlayerPing(playerInfo), Column.Alignment.RIGHT))
                .withColumn(BlankColumn.of("pingSpace", 1))
                .build();
    }

    private ITextComponent getPlayerScore(NetworkPlayerInfo playerInfo, Scoreboard scoreboard) {
        ScoreObjective scoreObjective = Optional.ofNullable(scoreboard)
                .map(s -> s.getObjectiveInDisplaySlot(0))
                .orElse(null);

        return TextComponentBuilder.of("")
                .with(getScore(playerInfo, scoreboard, scoreObjective), showPlayerScore(playerInfo, scoreObjective))
                .format(TextFormatting.YELLOW)
                .build();
    }

    private Supplier<Boolean> showPlayerScore(NetworkPlayerInfo playerInfo, ScoreObjective scoreObjective) {
        return () -> scoreObjective != null && GameType.SPECTATOR != playerInfo.getGameType();
    }

    private int getScore(NetworkPlayerInfo playerInfo, Scoreboard scoreboard, ScoreObjective scoreObjective) {
        if (scoreObjective != null) {
            Score score = scoreboard.getOrCreateScore(playerInfo.getGameProfile().getName(), scoreObjective);
            return score.getScorePoints();
        } else {
            return 0;
        }
    }

    private Supplier<Boolean> showScoreObjective(Scoreboard scoreboard) {
        return () -> scoreboard != null && scoreboard.getObjectiveInDisplaySlot(0) != null;
    }

    private boolean isAllowed() {
        return !Config.OP_ONLY_MODE.get() || minecraft.isSingleplayer() || Optional.ofNullable(minecraft.player)
                .map(player -> player.hasPermissionLevel(1))
                .orElse(false);
    }

    private List<Row> getPagedRows(List<Row> rows, int itemsPerPage) {
        rows = rows.stream()
                .skip((page - 1) * itemsPerPage)
                .limit(itemsPerPage)
                .collect(Collectors.toCollection(LinkedList::new));
        return rows;
    }

    private ITextComponent getPlayerName(NetworkPlayerInfo playerInfo) {
        return Optional.ofNullable(playerInfo)
                .map(NetworkPlayerInfo::getDisplayName)
                .orElseGet(() -> ScorePlayerTeam.func_237500_a_(playerInfo.getPlayerTeam(), new StringTextComponent(playerInfo.getGameProfile().getName())));
    }

    private ITextComponent getPlayerPing(NetworkPlayerInfo playerInfo) {
        return TextComponentBuilder.of(playerInfo.getResponseTime())
                .with(MS)
                .build();
    }

    private ITextComponent getPlayerDim(NetworkPlayerInfo playerInfo) {
        ResourceLocation dimName = ClientDataManager.PLAYER_DIMENSIONS.getOrDefault(playerInfo.getGameProfile().getId(), null);
        return TextComponentBuilder.of(getDimensionName(dimName))
                .with(getDimensionTps(dimName), this::isAllowed)
                .build();

    }

    private TextComponent getDimensionName(ResourceLocation dimensionName) {
        if (dimensionName == null)
            return (TextComponent) UNKOWN.get();

        if (ClientDataManager.DIMENSION_NAME_CACHE.containsKey(dimensionName))
            return ClientDataManager.DIMENSION_NAME_CACHE.get(dimensionName);

        TextComponent name = DimensionUtil.getFormattedDimensionName(dimensionName, ServerTabInfo.MOD_ID);
        ClientDataManager.DIMENSION_NAME_CACHE.put(dimensionName, name);
        return name;
    }

    private TextComponent getDimensionTps(ResourceLocation dimensionName) {
        if (dimensionName == null)
            return new StringTextComponent("");
        Dimension dimension = ClientDataManager.DIMENSIONS.stream()
                .filter(d -> d.name.equals(dimensionName))
                .findFirst()
                .orElse(null);
        if (dimension == null)
            return new StringTextComponent("");

        int tps = dimension.tps;
        TextFormatting color = TextFormatting.GREEN;

        if (tps < 20)
            color = TextFormatting.YELLOW;
        if (tps <= 10)
            color = TextFormatting.RED;

        return TextComponentBuilder.of("(")
                .with(tps)
                .format(color)
                .with(")")
                .build();
    }

    @OnlyIn(Dist.CLIENT)
    static class PlayerComparator implements Comparator<NetworkPlayerInfo> {

        public int compare(NetworkPlayerInfo player1, NetworkPlayerInfo player2) {
            ScorePlayerTeam scoreplayerteam1 = player1.getPlayerTeam();
            ScorePlayerTeam scoreplayerteam2 = player2.getPlayerTeam();
            return ComparisonChain.start()
                    .compareTrueFirst(player1.getGameType() != GameType.SPECTATOR, player2.getGameType() != GameType.SPECTATOR)
                    .compare(getName(scoreplayerteam1), getName(scoreplayerteam2))
                    .compare(player1.getGameProfile().getName(), player2.getGameProfile().getName())
                    .result();
        }

        private String getName(ScorePlayerTeam scorePlayerTeam) {
            return Optional.ofNullable(scorePlayerTeam)
                    .map(ScorePlayerTeam::getName)
                    .orElse("");
        }
    }
}
