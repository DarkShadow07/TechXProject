package DarkS.TechXProject.blocks.node;

import DarkS.TechXProject.api.network.INetworkContainer;
import DarkS.TechXProject.blocks.base.BlockContainerBase;
import DarkS.TechXProject.blocks.base.IRenderer;
import DarkS.TechXProject.node.item.NodeUtil;
import DarkS.TechXProject.node.network.controller.TileNetworkController;
import DarkS.TechXProject.reference.Reference;
import DarkS.TechXProject.util.ChatUtil;
import DarkS.TechXProject.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockNetworkController extends BlockContainerBase implements IRenderer
{
	public static final PropertyBool active = PropertyBool.create("active");

	public BlockNetworkController()
	{
		super(Material.IRON, 3.0f, 2, "pickaxe");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileNetworkController tile = (TileNetworkController) worldIn.getTileEntity(pos);

		if (tile != null)
			ChatUtil.sendNoSpam(playerIn, NodeUtil.getNetworkInfo(tile.getNetwork()).toArray(new String[0]));

		return true;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		if (worldIn.getTileEntity(pos) != null)
		{
			return getDefaultState().withProperty(active, ((INetworkContainer) worldIn.getTileEntity(pos)).isActive());
		}

		return getDefaultState().withProperty(active, false);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, active);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(active, Util.intToBoolean(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return Util.booleanToInt(state.getValue(active));
	}

	@Override
	public void registerModel()
	{
		for (int i = 0; i < 2; i++)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(Reference.MOD_ID + ":" + getUnlocalizedName().substring(18), "inventory"));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileNetworkController();
	}
}
