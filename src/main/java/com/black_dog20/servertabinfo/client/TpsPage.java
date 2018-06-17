package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.ServerTabInfo;
import com.black_dog20.servertabinfo.config.ModConfig;
import com.black_dog20.servertabinfo.reference.Reference;
import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.RenderHelper;
import com.black_dog20.servertabinfo.utility.TpsDimension;

import net.minecraft.client.Minecraft;

public class TpsPage {
	
	private Minecraft mc;
	
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
		List<String> list = new ArrayList<>();

		if(GuiTabPage.dims==null || GuiTabPage.dims.isEmpty())
			return startTop;
		
		for(TpsDimension tpsInfo : GuiTabPage.dims) {
			list.add(tpsInfo.getDimString(GuiTabPage.responseVersion));
		}
		
		return RenderHelper.RenderList(list, mc, startTop, GuiTabPage.width);
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
