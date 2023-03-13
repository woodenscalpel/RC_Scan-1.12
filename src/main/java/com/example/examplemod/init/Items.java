package com.example.examplemod.init;

import li.cil.scannable.api.API;
import li.cil.scannable.common.config.Constants;
import li.cil.scannable.common.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;


@GameRegistry.ObjectHolder(API.MOD_ID)
public final class Items {

    @GameRegistry.ObjectHolder("TEST") //TODO FIX
    public static final Item moduleCoord= null;

    public static List<Item> getAllItems() {
        return Arrays.asList(
                moduleCoord
        );
    }

    // --------------------------------------------------------------------- //

    public static boolean isModuleCoord(final ItemStack stack) {
        return isItem(stack, moduleCoord);
    }

    // --------------------------------------------------------------------- //

    public static void register(final IForgeRegistry<Item> registry) {
        registerItem(registry, new ItemScannerModuleCoord(), "TEST");
    }

    // --------------------------------------------------------------------- //

    private static void registerItem(final IForgeRegistry<Item> registry, final Item item, final String name) {
        registry.register(item.
               // setTranslationKey(API.MOD_ID + "." + name).
                setCreativeTab(API.creativeTab).
                setRegistryName(name));
    }

    private static boolean isItem(final ItemStack stack, @Nullable final Item item) {
        return !stack.isEmpty() && stack.getItem() == item;
    }

    // --------------------------------------------------------------------- //

    private Items() {
    }
}
