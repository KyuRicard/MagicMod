package com.kyuricard.blocks;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;
import com.kyuricard.items.KItems;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBreaker extends BlockDispenser implements KRecipable {
	public static final String name = "blockBreaker";

	public BlockBreaker() {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModBase.blocksTab);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
			net.minecraft.entity.player.EntityPlayer playerIn, net.minecraft.util.EnumHand hand, ItemStack heldItem,
			net.minecraft.util.EnumFacing side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	protected void dispense(World worldIn, BlockPos pos) {
		BlockPos position = pos;
		switch (getFacing(getMetaFromState(worldIn.getBlockState(position)))) {
		case DOWN:
			position = position.add(0, -1, 0);
			break;
		case EAST:
			position = position.add(1, 0, 0);
			break;
		case NORTH:
			position = position.add(0, 0, -1);
			break;
		case SOUTH:
			position = position.add(0, 0, 1);
			break;
		case UP:
			position = position.add(0, 1, 0);
			break;
		case WEST:
			position = position.add(-1, 0, 0);
			break;
		default:
			break;
		}

		if (!worldIn.isAirBlock(position)) {
			worldIn.destroyBlock(position, true);
		}

	}

	@Override
	public void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(this), "SSS", "SRS", "SBS", 'S', KItems.magicShard, 'R', Items.redstone,
				'B', Items.iron_pickaxe);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void registerTextures() {
		for (int i = 0; i < 16; ++i)
		{
			ModBase.rend.register(Item.getItemFromBlock(this), i, new ModelResourceLocation(ModBase.ModID + ":" + getUnlocalizedName().substring(5), "inventory"));
		}
	}
}
