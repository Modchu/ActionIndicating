package net.minecraft.src;

import java.util.ArrayList;

public class ItemActionIndicatingWhistle extends ItemActionIndicatingBase {

    private static ArrayList<EntityLivingBase> entityList = new ArrayList();

	public ItemActionIndicatingWhistle(int i) {
		super(i);
		setMaxDamage(256);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityPlayer, EntityLivingBase entity) {
		if(entity != null) ;else return true;
		if (entity.worldObj.isRemote) {
			//mod_ActionIndicating.mDebug("isRemote");
			return true;
		}
		if (entity.isDead) {
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingWhistle entity.isDead");
			return true;
		}
		//mod_ActionIndicating.mDebug("setEntity entity.getClass()="+entity.getClass());
		if (entityList.contains(entity)) {
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingWhistle List remove Entity.");
			entityList.remove(entity);
		} else {
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingWhistle List add Entity.");
			entityList.add(entity);
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		entityplayer.swingItem();
		if (entityplayer.worldObj.isRemote) {
			return itemstack;
		}
		if (GuiScreen.isCtrlKeyDown()) {
			actionCount++;
			if (actionCount > maxActionCount) actionCount = 0;
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingWhistle actionCount ="+actionCount);
			return itemstack;
		} else if (GuiScreen.isShiftKeyDown()) {
			actionCount--;
			if (actionCount < 0) actionCount = maxActionCount;
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingWhistle actionCount ="+actionCount);
			return itemstack;
		}
		if (actionCount == 0) return itemstack;
		if (entityList != null
				&& !entityList.isEmpty()) {
			for (int i1 = 0; i1 < entityList.size(); i1++) {
				actionSetting(entityList.get(i1), actionCount);
			}
		}
		itemstack.damageItem(1, entityplayer);
		if (mod_ActionIndicating.usePlaySound) entityplayer.worldObj.playSoundAtEntity(entityplayer, getSound(), entityplayer.getSoundVolume(), entityplayer.getSoundPitch());
		return itemstack;
	}

	private String getSound() {
		return "ActionIndicating.ActionIndicatingWhistle";
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i)
	{
		if (entityplayer.worldObj.isRemote
				| actionCount != 0) return;
		int var6 = getMaxItemUseDuration(itemstack) - i;
		float var7 = (float)var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
		//mod_ActionIndicating.mDebug("onPlayerStoppedUsing var7="+var7);
		if ((double)var7 < 0.1D)
		{
			return;
		}
		if (var7 > 1.0F)
		{
			var7 = 1.0F;
		}
		if (var7 == 1.0F)
		{
			if (mod_ActionIndicating.useAddChatMessage) minecraft.ingameGUI.getChatGUI().printChatMessage("ActionIndicatingWhistle entityList clear.");
			entityList.clear();
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(mod_ActionIndicating.actionIndicatingWhistleItemName);
	}
}