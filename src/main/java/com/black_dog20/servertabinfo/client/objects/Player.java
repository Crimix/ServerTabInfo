package com.black_dog20.servertabinfo.client.objects;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.WorldSettings.GameType;

public class Player implements IRenderable {
	
	private NetworkPlayerInfo networkInfo;
	private Minecraft mc;
	private int headWidth = 10;
	private int spacing = 2;
	private boolean flag = false;
	
	public Player(NetworkPlayerInfo info, Minecraft minecraft) {
		networkInfo = info;
		mc = minecraft;
		flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
	}

	@Override
	public int getWidth() {
		int width = 0;
		if(flag) {
			width += headWidth;
		}
		width += CompatibilityHelper.getStringWidth(mc, this.getPlayerName(networkInfo));
		width += spacing;
		width += CompatibilityHelper.getStringWidth(mc, getDim(getPlayerName(networkInfo)));
		width += spacing;
		width += CompatibilityHelper.getStringWidth(mc, getPing());
		return width;
	}

	@Override
	public void render(int x, int y) {
		
		GameProfile gameprofile = networkInfo.getGameProfile();
		EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());

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

        x += CompatibilityHelper.getStringWidth(mc, s4) + spacing;
        CompatibilityHelper.drawStringWithShadow(mc, getDim(s4), (float)x, (float)y, -1);
        x += CompatibilityHelper.getStringWidth(mc, getDim(s4))+spacing;
        CompatibilityHelper.drawStringWithShadow(mc, getPing(),(float)x, (float)y, -1);
	}
	
    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn)
    {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }
    
    private String getPing() {
		String pingValue = CompatibilityHelper.text(Integer.toString(networkInfo.getResponseTime()));
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
