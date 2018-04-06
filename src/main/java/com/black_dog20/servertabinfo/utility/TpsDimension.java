package com.black_dog20.servertabinfo.utility;

public class TpsDimension {

	public String name;
	
	public Double meanTickTime;
	
	public int Id;
	
	public TpsDimension(String name, Double meanTickTime) {
		this.name = name;
		this.meanTickTime = meanTickTime;
	}
	
	
	public TpsDimension(String name, Double meanTickTiem, int id) {
		this.name = name;
		this.meanTickTime = meanTickTiem;
		this.Id = id;
	}
}
