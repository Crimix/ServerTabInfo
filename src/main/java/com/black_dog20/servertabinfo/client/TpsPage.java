package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.client.objects.IRenderable;
import com.black_dog20.servertabinfo.config.ModConfig;
import com.black_dog20.servertabinfo.reference.Reference;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class TpsPage {
	
	private Minecraft mc;
	private int maxPages = 1;
	private int currentPage = 1;
	private int ticks = 0;
	private int changeTicks = 200;
	
	public TpsPage(Minecraft mc) {
		this.mc = mc;
	}

	public boolean render()
	{
		int startTop = 10;
		
		renderClientServerVersion();
		
		startTop = renderPing(startTop);
		
		renderTps(startTop);

		return true;
	}


	private int renderTps(int startTop) {
		ticks++;
		int hith = (int) (GuiTabPage.hight-startTop-(GuiTabPage.hight*0.15));
		
		int itemPerPage = (int) Math.floor(hith/this.mc.fontRendererObj.FONT_HEIGHT/2);
		List<IRenderable> Tlist = new ArrayList<>();

		if(GuiTabPage.dims==null || GuiTabPage.dims.isEmpty())
			return startTop;
	
		for(TpsDimension tpsInfo : GuiTabPage.dims) {
			Tlist.add(new TpsDimension(tpsInfo.name, tpsInfo.meanTickTime, tpsInfo.Id, GuiTabPage.responseVersion));
		}
		maxPages = (int)Math.ceil(Tlist.size() / (double)itemPerPage);
		
		if(ticks%changeTicks == 0) {
			ticks=0;
			changePage();
		}
		
		List<IRenderable> dimsT = RenderHelper.getPage(currentPage,itemPerPage,Tlist);
		int y = RenderHelper.RenderObjectList(dimsT, mc, startTop, GuiTabPage.width);
		String s = I18n.format("gui.servertabinfo.page")+ " " + Integer.toString(currentPage) +" of " + Integer.toString(maxPages);
		int x = GuiTabPage.width / 2;
		CompatibilityHelper.drawStringWithShadow(mc, s, (float) x+2, (float) y, -1);
		y += mc.fontRendererObj.FONT_HEIGHT;
		return y;
	}
	
	private void changePage() {
		if(currentPage == maxPages) {
			currentPage = 1;
		}
		else {
			currentPage++;
		}
	}

	private int renderPing(int startTop) {
		if(ModConfig.ping && GuiTabPage.responseVersion >= 2 && !ServerTabInfo.Proxy.isSinglePlayer()) {
			String pingText = CompatibilityHelper.translate("gui.servertabinfo.ping");
			String pingValue = CompatibilityHelper.text(Integer.toString(GuiTabPage.ping));
			String ms = CompatibilityHelper.translate("gui.servertabinfo.ms");
			String pingString = String.format("%s: %s%s", pingText, pingValue, ms);
			
			List<String> input = new ArrayList<String>();
			input.add(pingString);
			
			startTop = RenderHelper.RenderList(input, mc, startTop, GuiTabPage.width);
			startTop += 10;
		}
		return startTop;
	}


	private void renderClientServerVersion() {
		if(ModConfig.version) {
			int startTopp = 1;
			String cv = "C" + ": " + Reference.VERSION;
			String sv = "S" + ": " + (GuiTabPage.serverVersion != null ? GuiTabPage.serverVersion : "1.0.0");
			CompatibilityHelper.glPush();
			if(this.mc.gameSettings.guiScale!=1)
				CompatibilityHelper.glScale(0.5);
		
			List<String> list = new ArrayList<>();
			list.add(cv);
			if(!ServerTabInfo.Proxy.isSinglePlayer())
				list.add(sv);
			
			RenderHelper.RenderListAtStartPoint(list, mc, 0, startTopp);
			
			CompatibilityHelper.glPop();
		}
	}
}
