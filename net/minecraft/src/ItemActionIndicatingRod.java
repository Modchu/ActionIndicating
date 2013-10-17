package net.minecraft.src;

public class ItemActionIndicatingRod extends ItemActionIndicatingBase {

	public ItemActionIndicatingRod(int par1) {
		super(par1);
		setMaxDamage(512);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityPlayer, EntityLivingBase entity) {
		if (entity.worldObj.isRemote) {
			return true;
		}
		if (entity.isDead) {
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingRod setEntity.isDead");
			return true;
		}
		//mod_ActionIndicating.mDebug("setEntity entity.getClass()="+entity.getClass());
		actionSetting(entity, actionCount);
		itemstack.damageItem(1, entity);
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.swingItem();
		if (par3EntityPlayer.worldObj.isRemote) {
			return par1ItemStack;
		}
		if (GuiScreen.isCtrlKeyDown()) {
			selectLock = !selectLock;
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingRod selectLock ="+selectLock);
		} else if (GuiScreen.isShiftKeyDown()) {
			if (!selectLock) {
				actionCount--;
				if (actionCount < 0) actionCount = maxActionCount;
				if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingRod actionCount ="+actionCount);
			} else {
				if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingRod selectLock.");
			}
		} else {
			if (!selectLock) {
				actionCount++;
				if (actionCount > maxActionCount) actionCount = 0;
				if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingRod actionCount ="+actionCount);
			} else {
				if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingRod selectLock.");
			}
		}
		return par1ItemStack;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(mod_ActionIndicating.actionIndicatingRodItemName);
	}
}