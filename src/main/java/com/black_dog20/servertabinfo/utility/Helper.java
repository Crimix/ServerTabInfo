package com.black_dog20.servertabinfo.utility;

public class Helper {

	public static double mean(long[] tickArray) {

		long sum = 0L;
		if (tickArray == null) {
			return 0.0D;
		}
		for (long tickTime : tickArray) {
			sum += tickTime;
		}
		return sum / tickArray.length;
	}
}
