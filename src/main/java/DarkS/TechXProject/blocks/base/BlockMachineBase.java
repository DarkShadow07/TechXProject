package DarkS.TechXProject.blocks.base;

import DarkS.TechXProject.api.IWrenchable;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Logger;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockMachineBase extends BlockRotatingBase implements IWrenchable, ITileEntityProvider
{
	public BlockMachineBase(Material material, float hardness, int harvestLevel, String tool)
	{
		super(material, hardness, harvestLevel, tool);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + getUnlocalizedName().substring(18), getPropertyString(state.getProperties()));
			}
		});

		for (int i = 0; i < EnumFacing.HORIZONTALS.length; i++)
		{
			Logger.info("Registered Custom Model Block " + getUnlocalizedName() + " with Variant " + i + "!");

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + getUnlocalizedName().substring(18), "inventory"));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return null;
	}
}
