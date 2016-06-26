package com.kyuricard.blocks;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;
import com.kyuricard.items.KItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RepeatBlock extends Block implements KRecipable {
	public static final String name = "repeatBlock";
	public boolean powerState = false;
	
	public RepeatBlock() {
		super(Material.redstoneLight);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHarvestLevel("pickaxe", 2);
		setHardness(5);
		setCreativeTab(ModBase.blocksTab);	
	}
		
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return powerState ? 15 : 0;
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 10;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(this), "SSS", "SRS", "SSS", 'S', new ItemStack(KItems.magicShard), 'R', new ItemStack(Blocks.redstone_block));
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
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		// TODO: change this, may cause crash and bugs
		if (worldIn.isBlockIndirectlyGettingPowered(pos) > 0)
		{
			powerState = true;
		}
		else
		{
			powerState = false;
		}
	}
}
