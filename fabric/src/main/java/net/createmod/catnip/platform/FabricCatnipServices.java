package net.createmod.catnip.platform;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.createmod.catnip.platform.services.ModFluidHelper;
import net.createmod.catnip.platform.services.RegisteredObjectsHelper;
import net.createmod.catnip.render.FluidRenderHelper;
import net.minecraft.core.Registry;

@SuppressWarnings("unchecked")
public class FabricCatnipServices {
	public static final ModFluidHelper<FluidStack> FLUID_HELPER = (ModFluidHelper<FluidStack>) CatnipServices.FLUID_HELPER;
	public static final FluidRenderHelper<FluidStack> FLUID_RENDERER = (FluidRenderHelper<FluidStack>) CatnipServices.FLUID_RENDERER;
	public static final RegisteredObjectsHelper<Registry<?>> REGISTRIES = (RegisteredObjectsHelper<Registry<?>>) CatnipServices.REGISTRIES;
}
