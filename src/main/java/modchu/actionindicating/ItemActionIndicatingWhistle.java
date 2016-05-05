package modchu.actionindicating;

import java.util.ArrayList;
import java.util.HashMap;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.resurrectionfeather.modc_ResurrectionFeather;

public class ItemActionIndicatingWhistle extends ItemActionIndicatingBase {

    private static ArrayList entityList = new ArrayList();

	public ItemActionIndicatingWhistle(HashMap<String, Object> map) {
		super(map);
		setMaxDamage(256);
	}

	@Override
	public boolean itemInteractionForEntity(Object itemStack, Object entityPlayer, Object entityLivingBase, Object enumHand) {
		if(entityLivingBase != null); else return true;
		if (Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, entityLivingBase)) {
			//modc_ActionIndicating.mDebug("isRemote");
			return true;
		}
		if (Modchu_AS.getBoolean(Modchu_AS.entityIsDead, entityLivingBase)) {
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingWhistle entity.isDead");
			return true;
		}
		//modc_ActionIndicating.mDebug("setEntity entity.getClass()="+entity.getClass());
		if (entityList.contains(entityLivingBase)) {
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingWhistle List remove Entity.");
			entityList.remove(entityLivingBase);
		} else {
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingWhistle List add Entity.");
			entityList.add(entityLivingBase);
		}
		return true;
	}

	@Override
	public Object onItemRightClick(Object itemStack, Object world, Object entityPlayer) {
		return onItemRightClick(itemStack, world, entityPlayer, null);
	}

	@Override
	public Object onItemRightClick(Object itemStack, Object world, Object entityPlayer, Object enumHand) {
		Modchu_AS.set(Modchu_AS.entityPlayerSetItemInUse, entityPlayer, itemStack, getMaxItemUseDuration(itemStack));
		Modchu_AS.set(Modchu_AS.entityLivingBaseSwingItem, entityPlayer, enumHand);
		int version = Modchu_Main.getMinecraftVersion();
		boolean flag = version > 189;
		Object actionResult = flag ? Modchu_Reflect.newInstance("ActionResult", new Class[]{ Modchu_Reflect.loadClass("EnumActionResult"), Object.class }, new Object[]{ Modchu_AS.getEnum("EnumActionResult", "SUCCESS"), itemStack }) : itemStack;
		if (Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, entityPlayer)) {
			return actionResult;
		}
		if (Modchu_AS.getBoolean(Modchu_AS.isCtrlKeyDown)) {
			actionCount++;
			if (actionCount > maxActionCount) actionCount = 0;
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingWhistle actionCount ="+actionCount);
			return actionResult;
		} else if (Modchu_AS.getBoolean(Modchu_AS.isShiftKeyDown)) {
			actionCount--;
			if (actionCount < 0) actionCount = maxActionCount;
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingWhistle actionCount ="+actionCount);
			return actionResult;
		}
		if (actionCount == 0) return actionResult;
		if (entityList != null
				&& !entityList.isEmpty()) {
			for (Object o : entityList) {
				actionSetting(o, actionCount);
			}
		}
		Modchu_AS.set(Modchu_AS.itemStackDamageItem, itemStack, 1, entityPlayer);
		if (modc_ActionIndicating.usePlaySound) Modchu_AS.set(Modchu_AS.worldPlaySoundAtEntity, entityPlayer, getSound(), Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetSoundVolume, entityPlayer), Modchu_AS.getFloat(Modchu_AS.entityLivingBaseGetSoundPitch, entityPlayer));
		return actionResult;
	}

	private String getSound() {
		return "ActionIndicating.ActionIndicatingWhistle";
	}

	@Override
	public void onPlayerStoppedUsing(Object itemStack, Object world, Object entityPlayer, int i) {
		if (Modchu_AS.getBoolean(Modchu_AS.worldIsRemote, entityPlayer)
				| actionCount != 0) return;
		int var6 = getMaxItemUseDuration(itemStack) - i;
		float var7 = (float)var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
		//modc_ActionIndicating.mDebug("onPlayerStoppedUsing var7="+var7);
		if ((double)var7 < 0.1D) return;
		if (var7 > 1.0F) var7 = 1.0F;
		if (var7 == 1.0F) {
			if (modc_ActionIndicating.useAddChatMessage) Modchu_AS.set(Modchu_AS.printChatMessage, "ActionIndicatingWhistle entityList clear.");
			entityList.clear();
		}
	}

	@Override
	public void registerIcons(Object iIconRegister) {
		int version = Modchu_Main.getMinecraftVersion();
		Modchu_Main.registerIcons(base, iIconRegister,
						version < 160
						&& Modchu_Main.isForge ? "modchulib:"+modc_ActionIndicating.actionIndicatingWhistleItemName : modc_ActionIndicating.actionIndicatingWhistleItemName);
	}
}