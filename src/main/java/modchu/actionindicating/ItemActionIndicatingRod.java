package modchu.actionindicating;

import java.util.HashMap;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_Main;

public class ItemActionIndicatingRod extends ItemActionIndicatingBase {

	public ItemActionIndicatingRod(HashMap<String, Object> map) {
		super(map);
		setMaxDamage(512);
	}

	@Override
	public boolean itemInteractionForEntity(Object itemStack, Object entityPlayer, Object entityLivingBase) {
		if (Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, entityLivingBase)) {
			//Modchu_Debug.mDebug("setEntity isRemote entityLivingBase="+entityLivingBase);
			//if (Modchu_Main.getMinecraftVersion() > 169) actionSetting(entityLivingBase, actionCount);
			return true;
		}
		if (Modchu_AS.getBoolean(Modchu_AS.entityIsDead, entityLivingBase)) {
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingRod setEntity.isDead");
			return true;
		}
		//Modchu_Debug.mDebug("setEntity entityLivingBase="+entityLivingBase);
		actionSetting(entityLivingBase, actionCount);
		Modchu_AS.set(Modchu_AS.itemStackDamageItem, itemStack, 1, entityPlayer);
		return true;
	}

	@Override
	public Object onItemRightClick(Object itemStack, Object world, Object entityPlayer) {
		Modchu_AS.set(Modchu_AS.entityLivingBaseSwingItem, entityPlayer);
		if (Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, entityPlayer)) {
			return itemStack;
		}
		if (Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown)) {
			selectLock = !selectLock;
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingRod selectLock ="+selectLock);
		} else if (Modchu_AS.getBoolean(Modchu_AS.isShiftKeyDown)) {
			if (!selectLock) {
				actionCount--;
				if (actionCount < 0) actionCount = maxActionCount;
				if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingRod actionCount ="+actionCount);
			} else {
				if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingRod selectLock.");
			}
		} else {
			if (!selectLock) {
				actionCount++;
				if (actionCount > maxActionCount) actionCount = 0;
				if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingRod actionCount ="+actionCount);
			} else {
				if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingRod selectLock.");
			}
		}
		return itemStack;
	}

	@Override
	public void registerIcons(Object iIconRegister) {
		Modchu_AS.set(Modchu_AS.itemItemIcon, base, Modchu_AS.get(Modchu_AS.iIconRegisterRegisterIcon, iIconRegister, modc_ActionIndicating.actionIndicatingRodItemName));
	}
}