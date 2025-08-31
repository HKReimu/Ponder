package net.createmod.catnip.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.jetbrains.annotations.Nullable;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.createmod.catnip.platform.services.ModFluidHelper;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;

public class FabricFluidHelper implements ModFluidHelper<FluidStack> {
	@Environment(EnvType.CLIENT)
	@Override
	public int getColor(FluidStack stack, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos) {
		return FluidVariantRendering.getColor(stack.getType(), level, pos);
	}

	@Override
	public int getLuminosity(FluidStack fluid) {
		return FluidVariantAttributes.getLuminance(fluid.getType());
	}

	@Environment(EnvType.CLIENT)
	@Override
	public TextureAtlasSprite getStillTexture(FluidStack fluid) {
		return FluidVariantRendering.getSprite(fluid.getType());
	}

	@Override
	public boolean isLighterThanAir(FluidStack fluid) {
		return FluidVariantAttributes.isLighterThanAir(fluid.getType());
	}

	@Override
	public FluidStack toStack(FluidState state) {
		return new FluidStack(state.getType(), FluidConstants.BUCKET);
	}
}
