package com.black_dog20.servertabinfo.common.utils;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.servertabinfo.ServerTabInfo;

public enum DimensionTranslations implements ITranslation {
    OVERALL("servertabinfo:overall"),
    OVERWORLD("minecraft:overworld"),
    THE_NETHER("minecraft:the_nether"),
    THE_END("minecraft:the_end"),
    THE_LOST_CITIES("lostcities:lostcities"),
    THE_TWILIGHT_FOREST("twilightforest:twilight_forest");


    private final String modId;
    private final String key;

    private DimensionTranslations(String key) {
        this.modId = ServerTabInfo.MOD_ID;
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getModId() {
        return this.modId;
    }
}
