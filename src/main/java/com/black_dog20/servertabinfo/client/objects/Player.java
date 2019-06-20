package com.black_dog20.servertabinfo.client.objects;

import java.util.ArrayList;

import com.black_dog20.servertabinfo.client.CustomPlayerList;
import com.black_dog20.servertabinfo.client.GuiTabPage;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;

public class Player implements IRenderable {
	
	private NetworkPlayerInfo networkInfo;
	private Minecraft mc;
	private int headWidth = 10;
	private int spacing = 3;
	private boolean flag = false;
	
	public Player(NetworkPlayerInfo info, Minecraft minecraft) {
		networkInfo = info;
		mc = minecraft;
		flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
	}
	
	@Override
	public int getWidthOfElement(int n) {
		return getWidthArrayPrivate()[n];
	}
	
	@Override
	public int[] getWidthArray() {
		return getWidtArrayHelper(false);
	}
	
	private int[] getWidthArrayPrivate() {
		return getWidtArrayHelper(true);
	}
	
	private int[] getWidtArrayHelper(boolean here) {
		int[] width = new int[3];
		int tempWidth = 0;
		if(flag) {
			tempWidth += headWidth;
		}
		if(!here) {
			ArrayList<String> playerList = new ArrayList<>();
			for(IRenderable p : CustomPlayerList.playerList) {
				if(p instanceof Player)
				playerList.add(((Player)p).getPlayerName());
			}
			tempWidth += RenderHelper.findMaxWidthString(playerList, mc);
		}else {
			tempWidth += CompatibilityHelper.getStringWidth(mc, getPlayerName());
		}
		tempWidth += (2*spacing);
		width[0] = tempWidth;
		
		tempWidth = 0;
		
		if(GuiTabPage.responseVersion >= 3 && (CustomPlayerList.playerDims.isEmpty()))
			tempWidth += CompatibilityHelper.getStringWidth(mc, I18n.format("gui.servertabinfo.analysing"));
		else if(GuiTabPage.responseVersion < 3)
			tempWidth += CompatibilityHelper.getStringWidth(mc, I18n.format("gui.servertabinfo.unknown"));
		else
			tempWidth += CompatibilityHelper.getStringWidth(mc, getDim(networkInfo));
		tempWidth += spacing;
		width[1] = tempWidth;
		
		tempWidth = 0;
		
		tempWidth += CompatibilityHelper.getStringWidth(mc, getPing());
		width[2] = tempWidth;
		
		return width;
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

        String s4 = this.getPlayerName();

        if (networkInfo.getGameType() == GameType.SPECTATOR)
        {
        	CompatibilityHelper.drawStringWithShadowItalic(mc, s4, (float)x, (float)y, -1862270977);
        }
        else
        {
            CompatibilityHelper.drawStringWithShadow(mc, s4, (float)x, (float)y, -1);
        }

        x += CompatibilityHelper.getStringWidth(mc, s4) + (2*spacing)+calcLeftOverspace(maxWidth,0);
        String dim = CompatibilityHelper.translate("gui.servertabinfo.unknown");
		if(GuiTabPage.responseVersion >= 3 && (CustomPlayerList.playerDims.isEmpty()))
			dim = CompatibilityHelper.translate("gui.servertabinfo.analysing");
		else if(GuiTabPage.responseVersion < 3)
			dim = CompatibilityHelper.translate("gui.servertabinfo.unknown");
		else
			dim = getDim(networkInfo);
        CompatibilityHelper.drawStringWithShadow(mc, dim, (float)x, (float)y, -1);
        x += CompatibilityHelper.getStringWidth(mc, dim)+spacing+calcLeftOverspace(maxWidth,1);
        CompatibilityHelper.drawStringWithShadow(mc, getPing(),(float)x, (float)y, -1);
	}
	
	
    public String getPlayerName()
    {
        return networkInfo.getDisplayName() != null ? networkInfo.getDisplayName().getFormattedText() : ScorePlayerTeam.formatMemberName(networkInfo.getPlayerTeam(), new TextComponentString(networkInfo.getGameProfile().getName())).getFormattedText();
    }
	
    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn)
    {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatMemberName(networkPlayerInfoIn.getPlayerTeam(), new TextComponentString(networkPlayerInfoIn.getGameProfile().getName())).getFormattedText();
    }
    
    private String getPing() {
		String pingValue = CompatibilityHelper.text(Integer.toString(networkInfo.getResponseTime()));
		String ms = CompatibilityHelper.translate("gui.servertabinfo.ms");
		String pingString = String.format("%s%s", pingValue, ms);
		return pingString;
    }
    
    private String getDim(NetworkPlayerInfo networkPlayerInfoIn) {
    	String player = networkPlayerInfoIn.getGameProfile().getName();
    	TpsDimension dim = CustomPlayerList.playerDims.get(player);
    	if(dim != null) {
    		return CustomPlayerList.playerDims.get(player).getShortDimString(2);
    	} else {
    		return "";
    	}
    	
    }

}
