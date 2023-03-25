package com.black_dog20.servertabinfo.common.datagen;

import com.black_dog20.bml.datagen.BaseLanguageProvider;
import com.black_dog20.servertabinfo.ServerTabInfo;
import net.minecraft.data.DataGenerator;

import static com.black_dog20.servertabinfo.common.utils.DimensionTranslations.*;
import static com.black_dog20.servertabinfo.common.utils.Translations.MEAN;
import static com.black_dog20.servertabinfo.common.utils.Translations.NOT_INSTALLED;

public class GeneratorLanguageGerman extends BaseLanguageProvider {

    public GeneratorLanguageGerman(DataGenerator gen) {
        super(gen.getPackOutput(), ServerTabInfo.MOD_ID, "de_de");
    }

    @Override
    protected void addTranslations() {
        // Keys
        //addPrefixed(CATEGORY, "Server Tab Info");
        //addPrefixed(SHOW_KEY, "Show TPS list");

        // Gui
        addPrefixed(NOT_INSTALLED, "Server Tab Info ist nicht auf dem Server installiert");
        //addPrefixed(PAGE, "Page %d of %d");
        addPrefixed(MEAN,"durchschn");
        //addPrefixed(MS, "ms");
        //addPrefixed(TPS, "tps");
        //addPrefixed(UNKOWN, "Unknown");

        // MSG
        //addPrefixed(NOT_ALLOWED, "You do not have permission to see tps information");

        // Dims
        addPrefixed(OVERALL, "Insgesamt");
        addPrefixed(OVERWORLD, "Oberwelt");
        addPrefixed(THE_NETHER, "Nether");
        addPrefixed(THE_END, "Das Ende");

        //addPrefixed(THE_LOST_CITIES, "The Lost Cities");
        //addPrefixed(THE_TWILIGHT_FOREST, "The Twilight Forest");
    }
}
