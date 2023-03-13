package com.example.examplemod;

import li.cil.scannable.common.capabilities.CapabilityScanResultProvider;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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