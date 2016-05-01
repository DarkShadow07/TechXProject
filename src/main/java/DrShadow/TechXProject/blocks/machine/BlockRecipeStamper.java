package DrShadow.TechXProject.blocks.machine;

import DrShadow.TechXProject.TechXProject;
import DrShadow.TechXProject.blocks.base.BlockContainerBase;
import DrShadow.TechXProject.client.gui.GuiHandler;
import DrShadow.TechXProject.machines.recipeStamper.TileRecipeStamper;
import DrShadow.TechXProject.reference.Guis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRecipeStamper extends BlockContainerBase
{
	public BlockRecipeStamper()
	{
		super(Material.iron, 2.4f, 1, "pickaxe");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		GuiHandler.openGui(playerIn, TechXProject.instance, Guis.RECIPE_STAMPER, pos);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileRecipeStamper();
	}
}
