package com.DrShadow.TechXProject.power;

public interface IPower
{
	public void setPower(int power);
	public int addPower(int power);
	public void substractPower(int power);
	public int getPower();
	public void setMaxPower(int maxPower);
	public int getMaxPower();
	public void setTransfer(int transfer);
	public int getTransfer();
}
