package com.black_dog20.servertabinfo.client;

import java.util.ArrayList;
import java.util.List;

import com.black_dog20.servertabinfo.utility.CompatibilityHelper;
import com.black_dog20.servertabinfo.utility.RenderHelper;

import net.minecraft.client.Minecraft;

public class NotInstalledPage {

	private Minecraft mc;
	
	public NotInstalledPage(Minecraft mc) {
		this.mc = mc;
	}
	
	public boolean render() {
		String text = CompatibilityHelper.translate("gui.servertabinfo.notinstalled");
		int startTop = 10;
		List<String> temp = new ArrayList<String>();
		temp.add(text);
		RenderHelper.RenderList(temp, mc, startTop, GuiTabPage.width);
		return true;
	}
}
