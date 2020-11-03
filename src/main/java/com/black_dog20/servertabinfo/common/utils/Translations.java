package com.black_dog20.servertabinfo.common.utils;

import com.black_dog20.bml.utils.translate.ITranslation;
import com.black_dog20.servertabinfo.ServerTabInfo;

public enum Translations  implements ITranslation {
    CATEGORY("keys.category"),
    SHOW_KEY("keys.show"),
    NOT_INSTALLED("gui.not_installed"),
    PAGE("gui.page"),
    MEAN("gui.mean"),
    MS("gui.ms"),
    UNKOWN("gui.unknown"),
    TPS("gui.tps"),
    NOT_ALLOWED("msg.not_allowed");


    private final String modId;
    private final String key;

    private Translations(String key) {
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