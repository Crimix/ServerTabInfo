package com.black_dog20.servertabinfo.common.datagen;

import com.black_dog20.servertabinfo.ServerTabInfo;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ServerTabInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (event.includeClient())
            registerClientProviders(event.getGenerator(), event);
    }

    private static void registerClientProviders(DataGenerator generator, GatherDataEvent event) {
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(new GeneratorLanguageEnglish(generator));
        generator.addProvider(new GeneratorLanguageDanish(generator));
        generator.addProvider(new GeneratorLanguageGerman(generator));
        generator.addProvider(new GeneratorLanguageRussian(generator));
        generator.addProvider(new GeneratorLanguageChinese(generator));
    }


}
