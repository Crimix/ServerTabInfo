package com.black_dog20.servertabinfo.client.objects;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;

public class Player implements IRenderable {
	
	private GuiPlayerInfo networkInfo;
	private Minecraft mc;
	private int spacing = 2;
	private boolean flag = false;
	
	public Player(GuiPlayerInfo info, Minecraft minecraft) {
		networkInfo = info;
		mc = minecraft;
		flag = this.mc.isIntegratedServerRunning();
	}

	@Override
	public int getWidth() {
		int width = 0;
		width += CompatibilityHelper.getStringWidth(mc, this.getPlayerName(networkInfo));
		width += spacing;
		width += CompatibilityHelper.getStringWidth(mc, getDim(getPlayerName(networkInfo)));
		width += spacing;
		width += CompatibilityHelper.getStringWidth(mc, getPing());
		return width;
	}

	@Override
	public void render(int x, int y) {
        String s4 = this.getPlayerName(networkInfo);
        CompatibilityHelper.drawStringWithShadow(mc, s4, (float)x, (float)y, -1);
        x += CompatibilityHelper.getStringWidth(mc, s4) + spacing;
        CompatibilityHelper.drawStringWithShadow(mc, getDim(s4), (float)x, (float)y, -1);
        x += CompatibilityHelper.getStringWidth(mc, getDim(s4))+spacing;
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
