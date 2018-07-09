package com.black_dog20.servertabinfo.client;

import com.black_dog20.servertabinfo.client.objects.IRenderable;
import com.black_dog20.servertabinfo.client.objects.Player;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomPlayerList
{
    private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new CustomPlayerList.PlayerComparator());
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
    		NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
    		List<NetworkPlayerInfo> list = ENTRY_ORDERING.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());
    		List<IRenderable> playerList = new ArrayList<IRenderable>();
    		int hith = (int) (GuiTabPage.hight-y-(GuiTabPage.hight*0.15));
    		
    		int itemPerPage = (int) Math.floor(hith/this.mc.fontRendererObj.FONT_HEIGHT/2);
    		
    		for (NetworkPlayerInfo networkplayerinfo : list) {
    			playerList.add(new Player(networkplayerinfo,mc));
    		}
    		maxPages = (int)Math.ceil(playerList.size() / (double)itemPerPage);
    		if(ticks%changeTicks == 0) {
    			ticks=0;
    			changePage();
    		}
    		int yy = RenderHelper.RenderObjectList(RenderHelper.getPage(currentPage,itemPerPage,playerList), mc, y, width);
    		String s = "Page " + Integer.toString(currentPage) +" of " + Integer.toString(maxPages);
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
                return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != GameType.SPECTATOR, p_compare_2_.getGameType() != GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getTeamName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getTeamName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
            }
        }
}