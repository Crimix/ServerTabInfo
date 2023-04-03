package com.black_dog20.servertabinfo.common.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.servertabinfo.ServerTabInfo;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.servertabinfo.common.utils.DimensionTranslations.*;
import static com.black_dog20.servertabinfo.common.utils.Translations.*;

public class GeneratorLanguageChinese extends BaseLanguageProvider {

    public GeneratorLanguageChinese(DataGenerator gen) {
        super(gen.getPackOutput(), ServerTabInfo.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // Keys
        addPrefixed(CATEGORY, "服务器 Tab 信息");
        addPrefixed(SHOW_KEY, "显示 TPS");

        // Gui
        addPrefixed(NOT_INSTALLED, "Tab 信息暂未安装在此服务器内");
        addPrefixed(PAGE, "第 %d 页，共 %d 页");
        addPrefixed(MEAN,"平均延迟");
        addPrefixed(MS, "毫秒");
        //addPrefixed(TPS, "tps");
        addPrefixed(UNKOWN, "未知");

        // MSG
        //addPrefixed(NOT_ALLOWED, "You do not have permission to see tps information");

        // Dims
        addPrefixed(OVERALL, "全部世界");
        addPrefixed(OVERWORLD, "主世界");
        addPrefixed(THE_NETHER, "下界");
        addPrefixed(THE_END, "末地");

        addPrefixed(THE_LOST_CITIES, "失落的城市");
        addPrefixed(THE_TWILIGHT_FOREST, "暮色森林");
    }
}
