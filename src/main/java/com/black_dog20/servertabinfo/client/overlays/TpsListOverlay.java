package com.black_dog20.servertabinfo.client.overlays;

import com.black_dog20.bml.client.DrawingContext;
import com.black_dog20.bml.client.overlay.GameOverlay;
import com.black_dog20.bml.client.rows.Row;
import com.black_dog20.bml.client.rows.RowHelper;
import com.black_dog20.bml.client.rows.columns.BlankColumn;
import com.black_dog20.bml.client.rows.columns.Column;
import com.black_dog20.bml.client.rows.columns.ITextComponentColumn;
import com.black_dog20.bml.utils.dimension.DimensionUtil;
import com.black_dog20.bml.utils.text.TextComponentBuilder;
import com.black_dog20.servertabinfo.Config;
import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.ClientDataManager;
import com.black_dog20.servertabinfo.client.keybinds.Keybinds;
import com.black_dog20.servertabinfo.common.utils.Dimension;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.black_dog20.servertabinfo.common.utils.Translations.*;

@OnlyIn(Dist.CLIENT)
public class TpsListOverlay extends GameOverlay.PreLayer {

    private Minecraft minecraft;
    private Font fontRenderer;
    private ItemRenderer itemRenderer;
    private long lastRenderTime = Util.getMillis();
    private int ticks = 0;
    private int page = 1;
    private boolean hasBeenShown = false;

    public TpsListOverlay() {
        this.minecraft = Minecraft.getInstance();
        this.fontRenderer = minecraft.font;
        this.itemRenderer = minecraft.getItemRenderer();
    }

    @Override
    public void onRender(PoseStack matrixStack, int width, int height) {
        int y = 10;
        int z = 0;

        if (!ClientDataManager.modOnServer) {
            renderNotInstalled(matrixStack, width, height, z);
        } else {
            renderInstalled(matrixStack, width, height, y, z);
        }
    }

    private void renderInstalled(PoseStack matrixStack, int width, int height, int y, int z) {
        if (Util.getMillis() - 2000 > lastRenderTime) {
            page = 1;
            ticks = 1;
        }
        int itemsPerPage = (int) Math.floor((height - 7 * y) / fontRenderer.lineHeight);
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
        fontRenderer.drawShadow(matrixStack, PAGE.get(page, maxPages), width / 2 + 2, y + 2, -1);
        ticks++;
        lastRenderTime = Util.getMillis();
    }

    private void renderNotInstalled(PoseStack matrixStack, int width, int height, int z) {
        List<Row> rows = ImmutableList.of(new Row.RowBuilder()
                .withColumn(ITextComponentColumn.of("text", NOT_INSTALLED.get()))
                .build());

        int maxWidth = RowHelper.getMaxWidth(rows);
        int x = width / 2 - maxWidth / 2;

        DrawingContext drawingContext = new DrawingContext(matrixStack, width, height, x, 30, z, fontRenderer, itemRenderer);
        RowHelper.drawRowsWithBackground(drawingContext, rows);
    }

    @Override
    public boolean doRender(IIngameOverlay iIngameOverlay) {
        if (iIngameOverlay == ForgeIngameGui.CROSSHAIR_ELEMENT) {
            if (Keybinds.SHOW.isDown()) {
                boolean allowed = isAllowed();
                if (!hasBeenShown && !allowed) {
                    hasBeenShown = true;
                    Optional.ofNullable(minecraft.player)
                            .ifPresent(player -> player.displayClientMessage(NOT_ALLOWED.get(), true));
                }

                return allowed;
            } else {
                hasBeenShown = false;
            }
        }

        return false;
    }

    private boolean isAllowed() {
        return !Config.OP_ONLY_MODE.get() || minecraft.hasSingleplayerServer() || Optional.ofNullable(minecraft.player)
                .map(player -> player.hasPermissions(1))
                .orElse(false);
    }

    @Override
    public boolean doesCancelEvent() {
        return true;
    }

    private List<Row> getRows() {
        List<Row> rows = ClientDataManager.DIMENSIONS.stream()
                .filter(this::isNotBlocked)
                .map(this::buildRow)
                .collect(Collectors.toCollection(LinkedList::new));
        return rows;
    }

    private boolean isNotBlocked(Dimension dimension) {
        for (String blocked : Config.DIMENSION_BLOCK_LIST.get()) {
            if (dimension.name.toString().equalsIgnoreCase(blocked.trim())) {
                return false;
            } else if (dimension.name.getNamespace().equalsIgnoreCase(blocked.split(":")[0]) && "*".equalsIgnoreCase(blocked.split(":")[1])) {
                return false;
            }
        }

        return true;
    }

    private Row buildRow(Dimension dimension) {
        Row.RowBuilder builder = new Row.RowBuilder();

        return builder
                .withColumn(ITextComponentColumn.of("name", getDimensionName(dimension)))
                .withColumn(BlankColumn.of("nameSpace", 4))
                .withColumn(ITextComponentColumn.of("mean", MEAN.get()))
                .withColumn(BlankColumn.of("meanSpace", 2))
                .withColumn(ITextComponentColumn.of("meanNumber", getDimensionMean(dimension), Column.Alignment.RIGHT))
                .withColumn(BlankColumn.of("meanNumberSpace", 2))
                .withColumn(ITextComponentColumn.of("tps", getDimensionTps(dimension), Column.Alignment.RIGHT))
                .withColumn(BlankColumn.of("tpsSpace", 1))
                .build();
    }

    private List<Row> getPagedRows(List<Row> rows, int itemsPerPage) {
        rows = rows.stream()
                .skip((page-1)*itemsPerPage)
                .limit(itemsPerPage)
                .collect(Collectors.toCollection(LinkedList::new));
        return rows;
    }

    private BaseComponent getDimensionName(Dimension dimension) {
        ResourceLocation dimensionName = dimension.name;

        if(ClientDataManager.DIMENSION_NAME_CACHE.containsKey(dimensionName))
            return ClientDataManager.DIMENSION_NAME_CACHE.get(dimensionName);

        BaseComponent name = DimensionUtil.getFormattedDimensionName(dimensionName, ServerTabInfo.MOD_ID);
        ClientDataManager.DIMENSION_NAME_CACHE.put(dimensionName, name);
        return name;
    }

    private BaseComponent getDimensionMean(Dimension dimension) {
        double mean = dimension.meanTickTime;

        return TextComponentBuilder.of(String.format("%.2f", mean))
                .with(MS)
                .build();
    }

    private BaseComponent getDimensionTps(Dimension dimension) {
        int tps = dimension.tps;
        ChatFormatting color = ChatFormatting.GREEN;

        if (tps < 20)
			color = ChatFormatting.YELLOW;
		if (tps <= 10)
            color = ChatFormatting.RED;

		return TextComponentBuilder.of("(")
                .with(tps)
                .format(color)
                .space()
                .with(TPS)
                .with(")")
                .build();
    }
}
