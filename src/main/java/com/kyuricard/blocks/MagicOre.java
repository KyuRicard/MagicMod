package com.kyuricard.blocks;

import java.util.Random;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;

import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MagicOre extends BlockOre implements KRecipable {
	public static final String name = "magicOre";
	public MagicOre()
	{
		setUnlocalizedName(name);
		setHardness(25f);
		setCreativeTab(ModBase.blocksTab);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(8);
		setRegistryName(name);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModBase.GetItem("magicShard");
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 1 + random.nextInt(1 + fortune * 2);
	}

	@Override
	public void registerRecipes() {	
		GameRegistry.addSmelting(this, ModBase.GetISFromItem("magicShard"), 25);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void registerTextures() {
		ModBase.rend.register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(ModBase.ModID + ":" + getUnlocalizedName().substring(5), "inventory"));
	}

	@Override
	public ItemStack GetIS() {
		return new ItemStack(this);
	}
}
