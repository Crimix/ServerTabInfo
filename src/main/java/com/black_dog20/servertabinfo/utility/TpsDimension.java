package com.black_dog20.servertabinfo.utility;

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
}
