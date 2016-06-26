package com.kyuricard.blocks;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;
import com.kyuricard.items.KItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MagicBlock extends Block implements KRecipable {
	public static final String name = "magicBlock";
	public MagicBlock()
	{
		super(Material.iron);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(15);
		setLightLevel(8);
		setCreativeTab(ModBase.blocksTab);
		setHarvestLevel("pickaxe", 2);
		setLightOpacity(0);
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(this), "SS", "SS", 'S', new ItemStack(KItems.magicShard));		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void registerTextures() {
		ModBase.rend.register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(ModBase.ModID + ":" + getUnlocalizedName().substring(5), "inventory"));		
	}

}
