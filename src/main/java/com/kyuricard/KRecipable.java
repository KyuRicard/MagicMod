package com.kyuricard;

import net.minecraft.item.ItemStack;

public interface KRecipable {
	public void registerRecipes();
	public String getName();
	public void registerTextures();
	public ItemStack GetIS();
}
