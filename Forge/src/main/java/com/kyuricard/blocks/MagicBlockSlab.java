package com.kyuricard.blocks;

import com.kyuricard.ModBase;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;

public abstract class MagicBlockSlab extends BlockSlab {

	private final String name = "magicSlab";
	public MagicBlockSlab() {
		super(Material.iron);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(15);
		setLightLevel(8);
		setCreativeTab(ModBase.blocksTab);
		setHarvestLevel("pickaxe", 2);
		setLightOpacity(0);
	}
}
