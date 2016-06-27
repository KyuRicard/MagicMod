package com.kyuricard.items;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GoldenCoin extends Item implements KRecipable {
	private final String name = "coinGold";
	public GoldenCoin()
	{
		setUnlocalizedName(name);
		setCreativeTab(ModBase.itemsTab);
		setRegistryName(name);
	}
	
	@Override
	public void registerRecipes() {
		ItemStack that = GetIS();
		ItemStack nugget = new ItemStack(Items.gold_nugget);
		GameRegistry.addShapelessRecipe(that, nugget, nugget);		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_nugget, 2), that);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void registerTextures() {
		
	}

	@Override
	public ItemStack GetIS() {
		return new ItemStack(this);
	}
	
}
