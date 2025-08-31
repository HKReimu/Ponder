package net.createmod.catnip.platform;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.jetbrains.annotations.Nullable;

import net.createmod.catnip.platform.services.ModFluidHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class ForgeFluidHelper implements ModFluidHelper<FluidStack> {
	@OnlyIn(Dist.CLIENT)
	@Override
	public int getColor(FluidStack stack, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos) {
		Fluid fluid = stack.getFluid();
		IClientFluidTypeExtensions extension = IClientFluidTypeExtensions.of(fluid);
		if (level == null || pos == null)
			return extension.getTintColor(stack);
		return extension.getTintColor(fluid.defaultFluidState(), level, pos);
	}

	@Override
	public int getLuminosity(FluidStack fluid) {
		return fluid.getFluid().getFluidType().getLightLevel();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	@Nullable
	public TextureAtlasSprite getStillTexture(FluidStack fluid) {
		ResourceLocation id = IClientFluidTypeExtensions.of(fluid.getFluid()).getStillTexture(fluid);
		return id == null ? null : Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(id);
	}

	@Override
	public boolean isLighterThanAir(FluidStack fluid) {
		return fluid.getFluid().getFluidType().isLighterThanAir();
	}

	@Override
	public FluidStack toStack(FluidState state) {
		return new FluidStack(state.getType(), 1000);
	}
}
