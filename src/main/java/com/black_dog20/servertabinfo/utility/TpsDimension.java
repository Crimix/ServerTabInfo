package com.black_dog20.servertabinfo.utility;

import com.black_dog20.servertabinfo.utility.ColorObject.Color;

public class TpsDimension {

	public String name;
	
	public int Id;
	
	public Double meanTickTime;
	
	public TpsDimension(String name, Double meanTickTime) {
		this.name = name;
		this.meanTickTime = meanTickTime;
	}
	
	public TpsDimension(String name, Double meanTickTime, int id ) {
		this.name = name;
		this.meanTickTime = meanTickTime;
		this.Id = id;
	}
	
	public String getDimString(int responseVersion) {
		ColorObject color = new ColorObject(Color.Green);
		int tps;
		if(responseVersion >= 2) {
			tps = (int) Math.min(1000.0D / (meanTickTime*1.0E-006D), 20);
		}
		else {
			tps = (int) Math.min(1000.0D / meanTickTime, 20);
		}

		if (tps < 20)
		{
			color = new ColorObject(Color.Yellow);
		}
		if (tps <= 10)
		{
			color = new ColorObject(Color.Red);
		}

		String tpsValue = CompatibilityHelper.text(Integer.toString(tps), color);
		String mean = CompatibilityHelper.translate("gui.servertabinfo.mean");
		String dim = CompatibilityHelper.translate("gui.servertabinfo.dim");
		String ms = CompatibilityHelper.translate("gui.servertabinfo.ms");
		String tpsText = CompatibilityHelper.translate("gui.servertabinfo.tps");
		String nameT = CompatibilityHelper.translate(dim + " " +Integer.toString(Id));
		if(!name.equals("")) {
			nameT = CompatibilityHelper.translate(name);

			if(nameT.equals(name+"§r")) {
				String nameC = CompatibilityHelper.translate("servertabinfo.dim." + name);
				if(!nameC.equals("servertabinfo.dim." + name+"§r")) {
					nameT = nameC;
				}
			}
		}
		if(responseVersion >= 2) {
			return String.format("%s: %s %.2f%s (%s %s)", nameT, mean, (meanTickTime*1.0E-006D), ms, tpsValue, tpsText);
		} else {
			return String.format("%s: %s %.2f%s (%s %s)", nameT, mean, meanTickTime, ms, tpsValue, tpsText);
		}
	}
	
	public String getShortDimString(int responseVersion) {
		ColorObject color = new ColorObject(Color.Green);
		int tps;
		if(responseVersion >= 2) {
			tps = (int) Math.min(1000.0D / (meanTickTime*1.0E-006D), 20);
		}
		else {
			tps = (int) Math.min(1000.0D / meanTickTime, 20);
		}

		if (tps < 20)
		{
			color = new ColorObject(Color.Yellow);
		}
		if (tps <= 10)
		{
			color = new ColorObject(Color.Red);
		}

		String tpsValue = CompatibilityHelper.text(Integer.toString(tps), color);
		String dim = CompatibilityHelper.translate("gui.servertabinfo.dim");
		String nameT = CompatibilityHelper.translate(dim + " " +Integer.toString(Id));
		if(!nameT.equals("")) {
			nameT = CompatibilityHelper.translate(name);

			if(nameT.equals(name+"§r")) {
				String nameC = CompatibilityHelper.translate("servertabinfo.dim." + name);
				if(!nameC.equals("servertabinfo.dim." + name+"§r")) {
					nameT = nameC;
				}
			}
		}
		return String.format("%s(%s)", nameT, tpsValue);
	}
	
}
