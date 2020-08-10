package com.black_dog20.servertabinfo.common.utils;

import net.minecraft.util.ResourceLocation;

public class Dimension {

    public ResourceLocation name;

    public double meanTickTime;

    public int tps;

    public Dimension(ResourceLocation name, double meanTickTime, int tps) {
        this.name = name;
        this.meanTickTime = meanTickTime;
        this.tps = tps;
    }
}
