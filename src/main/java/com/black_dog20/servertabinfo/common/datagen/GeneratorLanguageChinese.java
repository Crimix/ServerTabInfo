package com.black_dog20.servertabinfo.common.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.servertabinfo.ServerTabInfo;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.servertabinfo.common.utils.DimensionTranslations.*;
import static com.black_dog20.servertabinfo.common.utils.Translations.*;

public class GeneratorLanguageChinese extends BaseLanguageProvider {

    public GeneratorLanguageChinese(DataGenerator gen) {
        super(gen, ServerTabInfo.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // Keys
        addPrefixed(CATEGORY, "服务器Tab   信息");
        addPrefixed(SHOW_KEY, "显示 TPS");

        // Gui
        addPrefixed(NOT_INSTALLED, "服务器未安装服务器Tab信息");
        //addPrefixed(PAGE, "Page %d of %d");
        //addPrefixed(MEAN,"mean");
        //addPrefixed(MS, "ms");
        //addPrefixed(TPS, "tps");
        //addPrefixed(UNKOWN, "Unknown");

        // Dims
        //addPrefixed(OVERALL, "Overall");
        addPrefixed(OVERWORLD, "主世界");
        addPrefixed(THE_NETHER, "下界");
        addPrefixed(THE_END, "末地");

        addPrefixed(THE_LOST_CITIES, "失落的城市");
        addPrefixed(THE_TWILIGHT_FOREST, "暮色森林");
    }
}
