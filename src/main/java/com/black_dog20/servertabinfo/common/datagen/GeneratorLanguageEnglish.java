package com.black_dog20.servertabinfo.common.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.servertabinfo.ServerTabInfo;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.servertabinfo.common.utils.DimensionTranslations.*;
import static com.black_dog20.servertabinfo.common.utils.Translations.*;

public class GeneratorLanguageEnglish extends BaseLanguageProvider {

    public GeneratorLanguageEnglish(DataGenerator gen) {
        super(gen, ServerTabInfo.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Keys
        addPrefixed(CATEGORY, "Server Tab Info");
        addPrefixed(SHOW_KEY, "Show TPS list");

        // Gui
        addPrefixed(NOT_INSTALLED, "Server Tab Info not installed on server");
        addPrefixed(PAGE, "Page %d of %d");
        addPrefixed(MEAN,"mean");
        addPrefixed(MS, "ms");
        addPrefixed(TPS, "tps");
        addPrefixed(UNKOWN, "Unknown");

        // Dims
        addPrefixed(OVERALL, "Overall");
        addPrefixed(OVERWORLD, "Overworld");
        addPrefixed(THE_NETHER, "The Nether");
        addPrefixed(THE_END, "The End");

        addPrefixed(THE_LOST_CITIES, "The Lost Cities");
        addPrefixed(THE_TWILIGHT_FOREST, "The Twilight Forest");
    }
}
