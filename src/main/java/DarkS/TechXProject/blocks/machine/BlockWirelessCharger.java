package DarkS.TechXProject.blocks.machine;

import DarkS.TechXProject.blocks.base.BlockContainerBase;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.machines.wirelessCharger.TileWirelessCharger;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.Logger;
import DarkS.TechXProject.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockWirelessCharger extends BlockContainerBase implements IRenderer
{
	public static PropertyBool working = PropertyBool.create("working");

	public BlockWirelessCharger()
	{
		super(Material.IRON, 3.5f, 2, "pickaxe");
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
	public int getMetaFromState(IBlockState state)
	{
		return Util.booleanToInt(state.getValue(working));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return blockState.getBaseState().withProperty(working, Util.intToBoolean(meta));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileWirelessCharger tile = (TileWirelessCharger) worldIn.getTileEntity(pos);

		return getDefaultState().withProperty(working, tile.working);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, working);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileWirelessCharger();
	}
}
