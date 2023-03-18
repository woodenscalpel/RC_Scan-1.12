package com.example.examplemod.common.modules;

import ivorius.reccomplex.events.StructureGenerationEventLite;
import li.cil.scannable.common.capabilities.CapabilityScanResultProvider;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public enum CapabilityProviderModuleCoord implements ICapabilityProvider {
    INSTANCE;

    // --------------------------------------------------------------------- //
    // ICapabilityProvider

    @Override
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
        return capability == CapabilityScanResultProvider.SCAN_RESULT_PROVIDER_CAPABILITY;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
        if (capability == CapabilityScanResultProvider.SCAN_RESULT_PROVIDER_CAPABILITY) {
            return (T) ScanResultProviderPos.INSTANCE;
        }
        return null;
    }
}