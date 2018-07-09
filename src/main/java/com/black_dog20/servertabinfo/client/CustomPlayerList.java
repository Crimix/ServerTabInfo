package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.black_dog20.servertabinfo.client.objects.IRenderable;
import com.black_dog20.servertabinfo.client.objects.Player;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;


public class CustomPlayerList
{
    private final Minecraft mc;
    public static HashMap<String, TpsDimension> playerDims = new HashMap<>();

    public CustomPlayerList(Minecraft mc)
    {
        this.mc = mc;
    }
    
    public boolean render(int width) {
    		int y = 10;
    		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
    		List<GuiPlayerInfo> list = nethandlerplayclient.playerInfoList;
    		List<IRenderable> playerList = new ArrayList<IRenderable>();
    		for (GuiPlayerInfo networkplayerinfo : list) {
    			playerList.add(new Player(networkplayerinfo,mc));
    		}
        
    		RenderHelper.RenderObjectList(playerList, mc, y, width);
    		return true;
    }  
}