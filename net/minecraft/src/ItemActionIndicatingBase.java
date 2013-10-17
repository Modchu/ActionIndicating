package net.minecraft.src;

public abstract class ItemActionIndicatingBase extends Item {
	protected Minecraft minecraft = Minecraft.getMinecraft();
	protected boolean selectLock;
	protected int actionCount = 0;
	protected final int maxActionCount = 30;

	public ItemActionIndicatingBase(int i) {
		super(i);
		setMaxStackSize(1);
	}

	public static void actionSetting(EntityLivingBase entity, int i) {
		Modchu_ModelDataBase data = Modchu_ModelDataMaster.instance.getPlayerData(entity);
		if (data.getCapsValueBoolean(data.caps_shortcutKeysAction)) {
			data.setCapsValue(data.caps_shortcutKeysAction, false);
		} else {
			data.setCapsValue(data.caps_shortcutKeysAction, true);
			data.setCapsValue(data.caps_actionReleaseNumber, data.getCapsValueInt(data.caps_runActionNumber));
			data.setCapsValue(data.caps_runActionNumber, i);
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack itemstack, int i) {
		return 16777215;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		return itemstack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.bow;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entity, EntityLivingBase entity2)
	{
		entity.heal(1);
		return entity2 instanceof EntityPlayer ? itemInteractionForEntity(itemstack, (EntityPlayer) entity2, entity) : itemInteractionForEntity(itemstack, (EntityPlayer) null, entity);
	}
}