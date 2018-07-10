package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.black_dog20.servertabinfo.client.objects.IRenderable;
import com.black_dog20.servertabinfo.client.objects.Player;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;

public class CustomPlayerList
{
    private final Minecraft mc;
    public static HashMap<String, TpsDimension> playerDims = new HashMap<>();
	private int maxPages = 1;
	private int currentPage = 1;
	private int ticks = 0;
	private int changeTicks = 200;

    public CustomPlayerList(Minecraft mc)
    {
        this.mc = mc;
    }
    
    public boolean render(int width) {
    	ticks++;
    		int y = 10;
    		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
    		List<GuiPlayerInfo> list = nethandlerplayclient.playerInfoList;
    		List<IRenderable> playerList = new ArrayList<IRenderable>();
    		int hith = (int) (GuiTabPage.hight-y-(GuiTabPage.hight*0.15));
    		
    		int itemPerPage = (int) Math.floor(hith/this.mc.fontRendererObj.FONT_HEIGHT/2);
    		
    		for (GuiPlayerInfo networkplayerinfo : list) {
    			playerList.add(new Player(networkplayerinfo,mc));
    		}
    		maxPages = (int)Math.ceil(playerList.size() / (double)itemPerPage);
    		if(ticks%changeTicks == 0) {
    			ticks=0;
    			changePage();
    		}
    		int yy = RenderHelper.RenderObjectList(RenderHelper.getPage(currentPage,itemPerPage,playerList), mc, y, width);
    		String s = I18n.format("gui.servertabinfo.page")+ " " + Integer.toString(currentPage) +" of " + Integer.toString(maxPages);
    		int x = GuiTabPage.width / 2;
    		CompatibilityHelper.drawStringWithShadow(mc, s, (float) x+2, (float) yy, -1);
    		return true;
    }  
	
	private void changePage() {
		if(currentPage == maxPages) {
			currentPage = 1;
		}
		else {
			currentPage++;
		}
	}
}