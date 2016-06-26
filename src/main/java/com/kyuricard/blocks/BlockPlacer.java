package com.kyuricard.blocks;

import com.kyuricard.KRecipable;
import com.kyuricard.ModBase;
import com.kyuricard.items.KItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockPlacer extends BlockDispenser implements KRecipable {
	public static final String name = "blockPlacer";
	public BlockPlacer()
	{
		super();
		setCreativeTab(ModBase.blocksTab);
		setRegistryName(name);
		setUnlocalizedName(name);
	}	
	
	@Override
	protected void dispense(World worldIn, BlockPos pos)
    {
        BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)blocksourceimpl.getBlockTileEntity();

        if (tileentitydispenser != null)
        {
            int i = tileentitydispenser.getDispenseSlot();

            if (i < 0)
            {
                worldIn.playAuxSFX(1001, pos, 0);
            }
            else
            {
                ItemStack itemstack = tileentitydispenser.getStackInSlot(i);   
                BlockPos position = pos;                
                switch (getFacing(getMetaFromState(worldIn.getBlockState(position))))
                {
				case DOWN:
					position = position.add(0, -1, 0);
					break;
				case EAST:
					position = position.add(-1, 0, 0);
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
					position = position.add(1, 0, 0);
					break;
				default:
					break;

                }
                
                if (worldIn.isAirBlock(position))
        		{                	
                	Block b = Block.getBlockFromItem(itemstack.getItem());
                	if (itemstack.getItem() == Items.bed)
                	{
                		super.dispense(worldIn, position);
                	}
                	else
                	{
                		if (b != null)
                		{
                			worldIn.setBlockState(position, b.getStateFromMeta(itemstack.getMetadata()));
                		}
                		else
                		{
                			BlockPos posLess = position.add(0, -1, 0);
                			itemstack.getItem().onItemUse(itemstack, Minecraft.getMinecraft().thePlayer, worldIn, posLess, EnumHand.MAIN_HAND, EnumFacing.UP, pos.getX(), pos.getY(), pos.getZ());
                		}
                    	itemstack.stackSize--;
                    	tileentitydispenser.setInventorySlotContents(i, itemstack.stackSize <= 0 ? null : itemstack);
                	}                	
        		}
            }
        }
    }
	
	@Override
	public void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(this), "SSS", "SRS", "SBS", 'S', KItems.magicShard, 'R', Items.redstone, 'B', Items.bow);		
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
