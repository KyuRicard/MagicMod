package com.kyuricard.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

public class MagicBlockHalfSlab extends MagicBlockSlab {

	public MagicBlockHalfSlab() {
		super();
	}
	
	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return null;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return null;
	}

}
