package com.black_dog20.servertabinfo.common.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.servertabinfo.ServerTabInfo;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.servertabinfo.common.utils.DimensionTranslations.*;
import static com.black_dog20.servertabinfo.common.utils.Translations.*;

public class GeneratorLanguageDanish extends BaseLanguageProvider {

    public GeneratorLanguageDanish(DataGenerator gen) {
        super(gen.getPackOutput(), ServerTabInfo.MOD_ID, "da_dk");
    }

    @Override
    protected void addTranslations() {
        // Keys
        //addPrefixed(CATEGORY, "Server Tab Info");
        //addPrefixed(SHOW_KEY, "Show TPS list");

        // Gui
        addPrefixed(NOT_INSTALLED, "Server Tab Info er ikke installeret på serveren");
        addPrefixed(PAGE, "Side %d af %d");
        addPrefixed(MEAN,"middel");
        //addPrefixed(MS, "ms");
        //addPrefixed(TPS, "tps");
        addPrefixed(UNKOWN, "Ukendt");

        // MSG
        addPrefixed(NOT_ALLOWED, "Du har ikke tilladelse til at se tps informationer");

        // Dims
        addPrefixed(OVERALL, "Overordnet");
        addPrefixed(OVERWORLD, "Oververden");
        addPrefixed(THE_NETHER, "Nether");
        addPrefixed(THE_END, "End");

        addPrefixed(THE_LOST_CITIES, "Lost Cities");
        addPrefixed(THE_TWILIGHT_FOREST, "Twilight Forest");
    }
}
