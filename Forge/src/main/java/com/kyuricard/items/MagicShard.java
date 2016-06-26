package com.kyuricard.items;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;
import com.kyuricard.blocks.KBlocks;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MagicShard extends Item implements KRecipable {
	public static final String name = "magicShard";
	public MagicShard()
	{
		setUnlocalizedName(name);
		setCreativeTab(ModBase.itemsTab);
		setRegistryName(name);
	}
	
	public void registerRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4), new ItemStack(KBlocks.magicBlock));
	}

	@Override
	public String getName() {		
		return name;
	}

	@Override
	public void registerTextures() {
		ModBase.rend.register(this, 0, new ModelResourceLocation(ModBase.ModID + ":" + getUnlocalizedName().substring(5), "inventory"));
	}
}
