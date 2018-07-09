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
	
    public String getPlayerName(GuiPlayerInfo networkPlayerInfoIn)
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