package li.cil.scannable.common.item;

import com.example.examplemod.CapabilityProviderModuleCoord;
import li.cil.scannable.common.item.AbstractItemScannerModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemScannerModuleCoord extends AbstractItemScannerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, @Nullable final NBTTagCompound nbt) {
        return CapabilityProviderModuleCoord.INSTANCE;
    }
}
