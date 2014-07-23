package modchu.actionindicating;

import modchu.lib.Modchu_ItemBasis;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_Item;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataBase;
import modchu.model.ModchuModel_ModelDataMaster;

public abstract class ItemActionIndicatingBase extends Modchu_ItemBasis {
	protected boolean selectLock;
	protected int actionCount = 0;
	protected final int maxActionCount = 30;

	public ItemActionIndicatingBase(Modchu_Item modchu_Item, Object... o) {
		super(modchu_Item, (Object[]) o);
		setMaxStackSize(1);
	}

	public static void actionSetting(Object entityLivingBase, int i) {
		ModchuModel_Main.reverseActionRequest(entityLivingBase, i);
	}

	@Override
	public int getColorFromItemStack(Object itemstack, int i) {
		return 16777215;
	}

	@Override
	public Object onEaten(Object itemstack, Object world, Object entityplayer) {
		return itemstack;
	}

	@Override
	public int getMaxItemUseDuration(Object itemstack) {
		return 72000;
	}

	@Override
	public Object getItemUseAction(Object itemstack) {
		return Modchu_AS.getEnum(Modchu_AS.enumActionBow);
	}

	@Override
	public boolean hitEntity(Object itemStack, Object entityLivingBase, Object entityLivingBase2) {
		Modchu_AS.set(Modchu_AS.entityLivingBaseHeal, entityLivingBase, 1.0F);
		return Modchu_Reflect.loadClass("EntityPlayer").isInstance(entityLivingBase2) ? itemInteractionForEntity(itemStack, entityLivingBase2, entityLivingBase) : itemInteractionForEntity(itemStack, null, entityLivingBase);
	}
}