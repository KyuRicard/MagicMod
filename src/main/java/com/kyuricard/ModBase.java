package com.kyuricard;

import java.util.ArrayList;
import java.util.List;

import com.kyuricard.blocks.KBlocks;
import com.kyuricard.items.KItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ModBase.ModID, name = ModBase.ModName, version = ModBase.ModVersion)
public class ModBase {
	public final static String ModName = "KyuRicard Mod";
	public static final String ModID = "KMod";
	public static final String ModVersion = "1.0 Alpha";
	public static ItemModelMesher rend;
	
	public static List<Block> blocks = new ArrayList<Block>();
	public static List<Item> items = new ArrayList<Item>();
	public static List<KRecipable> recipes = new ArrayList<KRecipable>();
	
	@SidedProxy(clientSide = "com.kyuricard.ClientProxy", serverSide = "com.kyuricard.ServerProxy", modId = ModID)
	public static ServerProxy proxy;
	
	public static CreativeTabs blocksTab = new CreativeTabs("KBlocks") {		
		@Override
		public Item getTabIconItem() {			
			return Item.getItemFromBlock(KBlocks.magicBlock);
		}
	};
	
	public static CreativeTabs itemsTab = new CreativeTabs("KItems") {
		@Override
		public Item getTabIconItem() {
			return KItems.magicShard;
		}
	};
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		blocks.add(KBlocks.magicOre);
		blocks.add(KBlocks.magicBlock);
		blocks.add(KBlocks.blockPlacer);
		blocks.add(KBlocks.blockBreaker);
		blocks.add(KBlocks.repeatBlock);
		
		items.add(KItems.magicShard);
		
		for (Block b : blocks)
		{
			if (b instanceof KRecipable)
			{
				recipes.add((KRecipable)b);
			}
		}
		
		for (Item i : items)
		{
			if (i instanceof KRecipable)
			{
				recipes.add((KRecipable)i);
			}
		}
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		rend = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (Block b : blocks)
		{
			GameRegistry.register(b);
			GameRegistry.register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
		}
		for (Item i : items)
		{
			GameRegistry.register(i);
		}
		if (event.getSide() == Side.CLIENT)
		{
			for (KRecipable r : recipes)
			{
				r.registerTextures();
			}		
		}		
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		for (KRecipable r : recipes)
		{
			r.registerRecipes();
		}
	}
}
