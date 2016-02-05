package com.DrShadow.TechXProject.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * TeslaTowerModel - DarkShadow07
 * Created using Tabula 5.1.0
 */
public class TeslaTowerModel extends ModelBase
{
	public ModelRenderer LegBF;
	public ModelRenderer LegFL;
	public ModelRenderer LegBL;
	public ModelRenderer BaseTop;
	public ModelRenderer Coil1;
	public ModelRenderer Coil2;
	public ModelRenderer Coil3;
	public ModelRenderer Coil4;
	public ModelRenderer Dec0;
	public ModelRenderer Coil5;
	public ModelRenderer Base;
	public ModelRenderer BarL3;
	public ModelRenderer BarF2;
	public ModelRenderer BarR1;
	public ModelRenderer BarB3;
	public ModelRenderer BarB1;
	public ModelRenderer BarL1;
	public ModelRenderer BarF1;
	public ModelRenderer BarR3;
	public ModelRenderer BarF3;
	public ModelRenderer BarL2;
	public ModelRenderer BarB2;
	public ModelRenderer BarR2;
	public ModelRenderer DoorL;
	public ModelRenderer DoorR;
	public ModelRenderer Dec41;
	public ModelRenderer Dec11;
	public ModelRenderer Dec21;
	public ModelRenderer Dec31;
	public ModelRenderer Dec32;
	public ModelRenderer Dec42;
	public ModelRenderer Dec12;
	public ModelRenderer Dec22;
	public ModelRenderer LegFR;

	public TeslaTowerModel()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.BarF2 = new ModelRenderer(this, 0, 29);
		this.BarF2.setRotationPoint(-3.0F, 9.0F, -4.7F);
		this.BarF2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
		this.Coil2 = new ModelRenderer(this, 9, 0);
		this.Coil2.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.Coil2.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
		this.setRotateAngle(Coil2, 0.0F, 0.19198621771937624F, 0.0F);
		this.LegFL = new ModelRenderer(this, 0, 0);
		this.LegFL.setRotationPoint(4.04F, 24.0F, -4.04F);
		this.LegFL.addBox(-1.0F, -26.0F, -1.0F, 2, 26, 2, 0.0F);
		this.setRotateAngle(LegFL, -0.03839724354387525F, 0.0F, -0.03839724354387525F);
		this.BarB1 = new ModelRenderer(this, 0, 29);
		this.BarB1.setRotationPoint(-3.0F, 15.0F, 4.0F);
		this.BarB1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
		this.Dec41 = new ModelRenderer(this, 9, 10);
		this.Dec41.setRotationPoint(-2.0F, -4.5F, -2.0F);
		this.Dec41.addBox(0.0F, -0.5F, -0.5F, 7, 2, 1, 0.0F);
		this.setRotateAngle(Dec41, 0.0F, 2.356194490192345F, 0.0F);
		this.Coil1 = new ModelRenderer(this, 9, 0);
		this.Coil1.setRotationPoint(0.0F, -4.0F, 2.25F);
		this.Coil1.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
		this.setRotateAngle(Coil1, -0.6108652381980153F, 0.03490658503988659F, 0.0F);
		this.BarF3 = new ModelRenderer(this, 0, 29);
		this.BarF3.setRotationPoint(-3.0F, 2.0F, -4.4F);
		this.BarF3.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
		this.BarR1 = new ModelRenderer(this, 0, 33);
		this.BarR1.setRotationPoint(-5.0F, 15.0F, -3.0F);
		this.BarR1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
		this.Coil5 = new ModelRenderer(this, 9, 0);
		this.Coil5.setRotationPoint(0.0F, -4.0F, -2.25F);
		this.Coil5.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
		this.setRotateAngle(Coil5, 0.6108652381980153F, 0.017453292519943295F, 0.0F);
		this.BarL1 = new ModelRenderer(this, 0, 33);
		this.BarL1.setRotationPoint(4.0F, 15.0F, -3.0F);
		this.BarL1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
		this.Dec22 = new ModelRenderer(this, 9, 14);
		this.Dec22.setRotationPoint(6.5F, -2.0F, 6.5F);
		this.Dec22.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
		this.Dec32 = new ModelRenderer(this, 9, 14);
		this.Dec32.setRotationPoint(-6.5F, -2.0F, 6.5F);
		this.Dec32.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
		this.BarR3 = new ModelRenderer(this, 0, 33);
		this.BarR3.setRotationPoint(-4.4F, 2.0F, -3.0F);
		this.BarR3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
		this.Dec12 = new ModelRenderer(this, 9, 14);
		this.Dec12.setRotationPoint(6.5F, -2.0F, -6.5F);
		this.Dec12.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
		this.LegFR = new ModelRenderer(this, 0, 0);
		this.LegFR.setRotationPoint(-4.04F, 24.0F, -4.04F);
		this.LegFR.addBox(-1.0F, -26.0F, -1.0F, 2, 26, 2, 0.0F);
		this.setRotateAngle(LegFR, -0.03839724354387525F, 0.0F, 0.03839724354387525F);
		this.Dec31 = new ModelRenderer(this, 9, 10);
		this.Dec31.setRotationPoint(-2.0F, -4.5F, 2.0F);
		this.Dec31.addBox(0.0F, -0.5F, -0.5F, 7, 2, 1, 0.0F);
		this.setRotateAngle(Dec31, 0.0F, -2.356194490192345F, 0.0F);
		this.BarF1 = new ModelRenderer(this, 0, 29);
		this.BarF1.setRotationPoint(-3.0F, 15.0F, -5.0F);
		this.BarF1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
		this.LegBL = new ModelRenderer(this, 0, 0);
		this.LegBL.setRotationPoint(4.04F, 24.0F, 4.04F);
		this.LegBL.addBox(-1.0F, -26.0F, -1.0F, 2, 26, 2, 0.0F);
		this.setRotateAngle(LegBL, 0.03839724354387525F, 0.0F, -0.03839724354387525F);
		this.BarL2 = new ModelRenderer(this, 0, 33);
		this.BarL2.setRotationPoint(3.7F, 9.0F, -3.0F);
		this.BarL2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
		this.Dec42 = new ModelRenderer(this, 9, 14);
		this.Dec42.setRotationPoint(-6.5F, -2.0F, -6.5F);
		this.Dec42.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
		this.BarB3 = new ModelRenderer(this, 0, 29);
		this.BarB3.setRotationPoint(-3.0F, 2.0F, 3.4F);
		this.BarB3.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
		this.Dec11 = new ModelRenderer(this, 9, 10);
		this.Dec11.setRotationPoint(2.0F, -4.5F, -2.0F);
		this.Dec11.addBox(0.0F, -0.5F, -0.5F, 7, 2, 1, 0.0F);
		this.setRotateAngle(Dec11, 0.0F, 0.7853981633974483F, 0.0F);
		this.Dec0 = new ModelRenderer(this, 26, 0);
		this.Dec0.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.Dec0.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
		this.Coil3 = new ModelRenderer(this, 9, 0);
		this.Coil3.setRotationPoint(-2.25F, -4.0F, 0.0F);
		this.Coil3.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
		this.setRotateAngle(Coil3, 0.0F, -0.11868238913561441F, -0.6239552075879727F);
		this.DoorL = new ModelRenderer(this, 41, 48);
		this.DoorL.setRotationPoint(5.0F, 22.2F, 0.0F);
		this.DoorL.addBox(0.0F, -3.5F, -5.0F, 1, 5, 10, 0.0F);
		this.setRotateAngle(DoorL, 0.0F, 0.0F, 0.6108652381980153F);
		this.Base = new ModelRenderer(this, 0, 52);
		this.Base.setRotationPoint(0.0F, 23.0F, 0.0F);
		this.Base.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
		this.DoorR = new ModelRenderer(this, 41, 48);
		this.DoorR.setRotationPoint(-5.9F, 22.8F, 0.0F);
		this.DoorR.addBox(0.0F, -3.5F, -5.0F, 1, 5, 10, 0.0F);
		this.setRotateAngle(DoorR, 0.0F, 0.0F, -0.6108652381980153F);
		this.BarR2 = new ModelRenderer(this, 0, 33);
		this.BarR2.setRotationPoint(-4.7F, 9.0F, -3.0F);
		this.BarR2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
		this.BarL3 = new ModelRenderer(this, 0, 33);
		this.BarL3.setRotationPoint(3.4F, 2.0F, -3.0F);
		this.BarL3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 6, 0.0F);
		this.Dec21 = new ModelRenderer(this, 9, 10);
		this.Dec21.setRotationPoint(2.0F, -4.5F, 2.0F);
		this.Dec21.addBox(0.0F, -0.5F, -0.5F, 7, 2, 1, 0.0F);
		this.setRotateAngle(Dec21, 0.0F, -0.7853981633974483F, 0.0F);
		this.Coil4 = new ModelRenderer(this, 9, 0);
		this.Coil4.setRotationPoint(2.25F, -4.0F, 0.0F);
		this.Coil4.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
		this.setRotateAngle(Coil4, 0.0F, -0.08726646259971647F, 0.6108652381980153F);
		this.LegBF = new ModelRenderer(this, 0, 0);
		this.LegBF.setRotationPoint(-4.04F, 24.0F, 4.04F);
		this.LegBF.addBox(-1.0F, -26.0F, -1.0F, 2, 26, 2, 0.0F);
		this.setRotateAngle(LegBF, 0.03839724354387525F, 0.0F, 0.03839724354387525F);
		this.BaseTop = new ModelRenderer(this, 0, 42);
		this.BaseTop.setRotationPoint(0.0F, -2.95F, 0.0F);
		this.BaseTop.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F);
		this.BarB2 = new ModelRenderer(this, 0, 29);
		this.BarB2.setRotationPoint(-3.0F, 9.0F, 3.7F);
		this.BarB2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.BarF2.render(f5);
		this.Coil2.render(f5);
		this.LegFL.render(f5);
		this.BarB1.render(f5);
		this.Dec41.render(f5);
		this.Coil1.render(f5);
		this.BarF3.render(f5);
		this.BarR1.render(f5);
		this.Coil5.render(f5);
		this.BarL1.render(f5);
		this.Dec22.render(f5);
		this.Dec32.render(f5);
		this.BarR3.render(f5);
		this.Dec12.render(f5);
		this.LegFR.render(f5);
		this.Dec31.render(f5);
		this.BarF1.render(f5);
		this.LegBL.render(f5);
		this.BarL2.render(f5);
		this.Dec42.render(f5);
		this.BarB3.render(f5);
		this.Dec11.render(f5);
		this.Dec0.render(f5);
		this.Coil3.render(f5);
		this.DoorL.render(f5);
		this.Base.render(f5);
		this.DoorR.render(f5);
		this.BarR2.render(f5);
		this.BarL3.render(f5);
		this.Dec21.render(f5);
		this.Coil4.render(f5);
		this.LegBF.render(f5);
		this.BaseTop.render(f5);
		this.BarB2.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
