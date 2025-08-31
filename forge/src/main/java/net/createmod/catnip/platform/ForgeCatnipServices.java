package net.createmod.catnip.platform;

import net.createmod.catnip.platform.services.ModFluidHelper;
import net.createmod.catnip.platform.services.RegisteredObjectsHelper;
import net.createmod.catnip.render.FluidRenderHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unchecked")
public class ForgeCatnipServices {
	public static final ModFluidHelper<FluidStack> FLUID_HELPER = (ModFluidHelper<FluidStack>) CatnipServices.FLUID_HELPER;
	public static final FluidRenderHelper<FluidStack> FLUID_RENDERER = (FluidRenderHelper<FluidStack>) CatnipServices.FLUID_RENDERER;
	public static final RegisteredObjectsHelper<IForgeRegistry<?>> REGISTRIES = (RegisteredObjectsHelper<IForgeRegistry<?>>) CatnipServices.REGISTRIES;
}
