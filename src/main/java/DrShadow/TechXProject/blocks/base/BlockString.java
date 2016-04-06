package DrShadow.TechXProject.blocks.base;

import DrShadow.TechXProject.blocks.property.PropertyString;
import DrShadow.TechXProject.blocks.property.UnlistedPropertyString;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class BlockString extends BlockBase
{
	private final int maxMeta;
	private final List<String> values;
	private final PropertyString stringProp;
	private final IUnlistedProperty unlistedStringProp;
	private final BlockStateContainer realBlockState;

	public BlockString(Material material, String[] values, String propName)
	{
		super(material);

		this.maxMeta = values.length - 1;
		this.values = Arrays.asList(values);

		this.stringProp = PropertyString.create(propName, values);
		this.unlistedStringProp = new UnlistedPropertyString(values, propName);
		this.realBlockState = createRealBlockState();
		setupStates();
	}

	public BlockString(Material material, String[] values)
	{
		this(material, values, "type");
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getBlockState().getBaseState().withProperty(stringProp, values.get(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return values.indexOf(String.valueOf(state.getValue(stringProp)));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public BlockStateContainer getBlockState()
	{
		return this.realBlockState;
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return Blocks.air.getBlockState();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this, 1, this.getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list)
	{
		for (int i = 0; i < maxMeta + 1; i++)
			list.add(new ItemStack(this, 1, i));
	}

	private void setupStates()
	{
		this.setDefaultState(getExtendedBlockState().withProperty(unlistedStringProp, values.get(0)).withProperty(stringProp, values.get(0)));
	}

	public ExtendedBlockState getBaseExtendedState()
	{
		return (ExtendedBlockState) this.getBlockState();
	}

	public IExtendedBlockState getExtendedBlockState()
	{
		return (IExtendedBlockState) this.getBaseExtendedState().getBaseState();
	}

	private BlockStateContainer createRealBlockState()
	{
		return new ExtendedBlockState(this, new IProperty[]{stringProp}, new IUnlistedProperty[]{unlistedStringProp});
	}
}