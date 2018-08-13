package com.black_dog20.servertabinfo.client.objects;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;

public class Player implements IRenderable {
	
	private GuiPlayerInfo networkInfo;
	private Minecraft mc;
	private int spacing = 2;
	
	public Player(GuiPlayerInfo info, Minecraft minecraft) {
		networkInfo = info;
		mc = minecraft;
	}

	@Override
	public int getWidth() {
		int width = 0;
		width += CompatibilityHelper.getStringWidth(mc, this.getPlayerName(networkInfo));
		width += (2*spacing);
		if(GuiTabPage.responseVersion >= 3 && (CustomPlayerList.playerDims.isEmpty()))
			width += CompatibilityHelper.getStringWidth(mc, "Analysing");
		else if(GuiTabPage.responseVersion < 3)
			width += CompatibilityHelper.getStringWidth(mc, "Unknown");
		else
			width += CompatibilityHelper.getStringWidth(mc, getDim(getPlayerName(networkInfo)));
		width += spacing;
		width += CompatibilityHelper.getStringWidth(mc, getPing());
		return width;
	}
	
	@Override
	public int getWidthOfElement(int n) {
		return getWidthArray()[n];
	}
	
	@Override
	public int[] getWidthArray() {
		int[] width = new int[3];
		int tempWidth = 0;
		if(flag) {
			tempWidth += headWidth;
		}
		tempWidth += CompatibilityHelper.getStringWidth(mc, this.getPlayerName(networkInfo));
		tempWidth += (2*spacing);
		width[0] = tempWidth;
		
		tempWidth = 0;
		
		if(GuiTabPage.responseVersion >= 3 && (CustomPlayerList.playerDims.isEmpty()))
			tempWidth += CompatibilityHelper.getStringWidth(mc, I18n.format("gui.servertabinfo.analysing"));
		else if(GuiTabPage.responseVersion < 3)
			tempWidth += CompatibilityHelper.getStringWidth(mc, I18n.format("gui.servertabinfo.unknown"));
		else
			tempWidth += CompatibilityHelper.getStringWidth(mc, getDim(getPlayerName(networkInfo)));
		tempWidth += spacing;
		width[1] = tempWidth;
		
		tempWidth = 0;
		
		tempWidth += CompatibilityHelper.getStringWidth(mc, getPing());
		width[2] = tempWidth;
		
		return width;
	}

	@Override
	public void render(int x, int y, int width) {
		int leftoverspacing = width - this.getWidth();

        String s4 = this.getPlayerName(networkInfo);
        CompatibilityHelper.drawStringWithShadow(mc, s4, (float)x, (float)y, -1);

        x += CompatibilityHelper.getStringWidth(mc, s4) + (2*spacing)+leftoverspacing;
        String dim = "Unknown";
		if(GuiTabPage.responseVersion >= 3 && (CustomPlayerList.playerDims.isEmpty()))
			dim ="Analysing";
		else if(GuiTabPage.responseVersion < 3)
			dim = "Unknown";
		else
			dim = getDim(getPlayerName(networkInfo));
        CompatibilityHelper.drawStringWithShadow(mc, dim, (float)x, (float)y, -1);
        x += CompatibilityHelper.getStringWidth(mc, dim)+spacing;
        CompatibilityHelper.drawStringWithShadow(mc, getPing(),(float)x, (float)y, -1);
	}
	
	private int calcLeftOverspace(int[] maxWidth, int n) {
		return maxWidth[n]-getWidthOfElement(n);
	}
	
	@Override
	public void render(int x, int y, int[] maxWidth) {
		GameProfile gameprofile = networkInfo.getGameProfile();
		EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());

        if (flag)
        {
            boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
            this.mc.getTextureManager().bindTexture(networkInfo.getLocationSkin());
            int l2 = 8 + (flag1 ? 8 : 0);
            int i3 = 8 * (flag1 ? -1 : 1);
            Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, (float)l2, 8, i3, 8, 8, 64.0F, 64.0F);

            if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT))
            {
                int j3 = 8 + (flag1 ? 8 : 0);
                int k3 = 8 * (flag1 ? -1 : 1);
                Gui.drawScaledCustomSizeModalRect(x, y, 40.0F, (float)j3, 8, k3, 8, 8, 64.0F, 64.0F);
            }

            x += headWidth;
        }

        String s4 = this.getPlayerName(networkInfo);

        if (networkInfo.getGameType() == GameType.SPECTATOR)
        {
        	CompatibilityHelper.drawStringWithShadowItalic(mc, s4, (float)x, (float)y, -1862270977);
        }
        else
        {
            CompatibilityHelper.drawStringWithShadow(mc, s4, (float)x, (float)y, -1);
        }

        x += CompatibilityHelper.getStringWidth(mc, s4) + (2*spacing)+calcLeftOverspace(maxWidth,0);
        String dim = "Unknown";
		if(GuiTabPage.responseVersion >= 3 && (CustomPlayerList.playerDims.isEmpty()))
			dim ="Analysing";
		else if(GuiTabPage.responseVersion < 3)
			dim = "Unknown";
		else
			dim = getDim(getPlayerName(networkInfo));
        CompatibilityHelper.drawStringWithShadow(mc, dim, (float)x, (float)y, -1);
        x += CompatibilityHelper.getStringWidth(mc, dim)+spacing+calcLeftOverspace(maxWidth,1);
        CompatibilityHelper.drawStringWithShadow(mc, getPing(),(float)x, (float)y, -1);
	}
	
    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn)
    {
        return networkPlayerInfoIn.name;
    }
    
    private String getPing() {
		String pingValue = CompatibilityHelper.text(Integer.toString(networkInfo.responseTime));
		String ms = CompatibilityHelper.translate("gui.servertabinfo.ms");
		String pingString = String.format("%s%s", pingValue, ms);
		return pingString;
    }
    
    private String getDim(String player) {
    	TpsDimension dim = CustomPlayerList.playerDims.get(player);
    	if(dim != null) {
    		return CustomPlayerList.playerDims.get(player).getShortDimString(2);
    	} else {
    		return "";
    	}
    	
    }
}