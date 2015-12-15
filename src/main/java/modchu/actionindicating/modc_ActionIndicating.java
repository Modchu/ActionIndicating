package modchu.actionindicating;

import java.io.File;
import java.util.ArrayList;

import modchu.lib.Modchu_AS;
import modchu.lib.Modchu_CastHelper;
import modchu.lib.Modchu_Config;
import modchu.lib.Modchu_Debug;
import modchu.lib.Modchu_EntityCapsHelper;
import modchu.lib.Modchu_GlStateManager;
import modchu.lib.Modchu_IItem;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.Modchu_RenderEngine;
import modchu.model.ModchuModel_IEntityCaps;
import modchu.model.ModchuModel_Main;
import modchu.model.ModchuModel_ModelDataMaster;
import modchu.model.ModchuModel_ModelRenderer;
import modchu.model.multimodel.base.MultiModelBaseBiped;

public class modc_ActionIndicating {

	public static int actionIndicatingRodID = 17661;
	public static int actionIndicatingWhistleID = 17662;
	public static boolean useAddChatMessage = true;
	public static boolean usePlaySound = true;

	public static final String versionString = "4a";
	public Modchu_IItem itemActionIndicatingRod;
	public Modchu_IItem itemActionIndicatingWhistle;
	public static Object actionIndicatingRod;
	public static Object actionIndicatingWhistle;
	private static boolean debugMessage = true;
	private static modc_ActionIndicating modc_ActionIndicating;
	private static File mainCfgfile;
	public static String actionIndicatingRodItemName;
	public static String actionIndicatingWhistleItemName;

	public String getVersion() {
		return versionString;
	}

	public void load() {
		modc_ActionIndicating = this;
		mainCfgfile = new File(Modchu_Main.cfgdir, ("ActionIndicating.cfg"));
		loadcfg();
		boolean flag = Modchu_Main.getMinecraftVersion() > 169;
		if (flag
				| actionIndicatingRodID > 0) {
			actionIndicatingRodItemName = "ActionIndicatingRod";
			itemActionIndicatingRod = (Modchu_IItem) Modchu_Main.newModchuCharacteristicObject("Modchu_Item", ItemActionIndicatingRod.class, actionIndicatingRodID - 256);
			//Modchu_Debug.mDebug("modc_ActionIndicating load() item="+item);
			itemActionIndicatingRod = (Modchu_IItem) Modchu_AS.get(Modchu_AS.itemSetUnlocalizedName, itemActionIndicatingRod, actionIndicatingRodItemName);
			itemActionIndicatingRod = (Modchu_IItem) Modchu_AS.get(Modchu_AS.itemSetCreativeTab, itemActionIndicatingRod, Modchu_AS.get(Modchu_AS.creativeTabsTabMaterials));
			itemActionIndicatingRod = (Modchu_IItem) Modchu_AS.get(Modchu_AS.itemSetTextureName, itemActionIndicatingRod, actionIndicatingRodItemName);
			Modchu_Main.languageRegistryAddName(itemActionIndicatingRod, actionIndicatingRodItemName.toLowerCase());
			Modchu_Main.registerItem(itemActionIndicatingRod, actionIndicatingRodItemName.toLowerCase());
			actionIndicatingRod = (Modchu_IItem) itemActionIndicatingRod;
			//Modchu_Debug.mDebug("modc_ActionIndicating load() fishing_rod="+Modchu_AS.get(Modchu_AS.getItem, "fishing_rod"));
			//Modchu_Debug.mDebug("modc_ActionIndicating load() sugar="+Modchu_AS.get(Modchu_AS.getItem, "sugar"));
			//Modchu_Debug.mDebug("modc_ActionIndicating load() ItemStack="+Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item"), int.class }, new Object[]{ actionIndicatingRod, 1 }));
			Modchu_Main.addRecipe(Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item"), int.class }, new Object[]{ actionIndicatingRod, 1 }),
					new Object[] { " Y", "X ", Character.valueOf('X'),
				Modchu_AS.get(Modchu_AS.getItem, "fishing_rod"), Character.valueOf('Y'), Modchu_AS.get(Modchu_AS.getItem, "sugar") });
		}
		if (flag
				| actionIndicatingWhistleID > 0) {
			actionIndicatingWhistleItemName = "ActionIndicatingWhistle";
			itemActionIndicatingWhistle = (Modchu_IItem) Modchu_Main.newModchuCharacteristicObject("Modchu_Item", ItemActionIndicatingWhistle.class, actionIndicatingWhistleID - 256);
			//Modchu_Debug.mDebug("modc_ActionIndicating load() item="+item);
			itemActionIndicatingWhistle = (Modchu_IItem) Modchu_AS.get(Modchu_AS.itemSetUnlocalizedName, itemActionIndicatingWhistle, actionIndicatingWhistleItemName);
			itemActionIndicatingWhistle = (Modchu_IItem) Modchu_AS.get(Modchu_AS.itemSetCreativeTab, itemActionIndicatingWhistle, Modchu_AS.get(Modchu_AS.creativeTabsTabMaterials));
			itemActionIndicatingWhistle = (Modchu_IItem) Modchu_AS.get(Modchu_AS.itemSetTextureName, itemActionIndicatingWhistle, actionIndicatingWhistleItemName);
			Modchu_Main.languageRegistryAddName(itemActionIndicatingWhistle, actionIndicatingWhistleItemName.toLowerCase());
			Modchu_Main.registerItem(itemActionIndicatingWhistle, actionIndicatingWhistleItemName.toLowerCase());
			actionIndicatingWhistle = (Modchu_IItem) itemActionIndicatingWhistle;
			//Modchu_Debug.mDebug("modc_ActionIndicating load() stick="+Modchu_AS.get(Modchu_AS.getItem, "stick"));
			//Modchu_Debug.mDebug("modc_ActionIndicating load() sugar="+Modchu_AS.get(Modchu_AS.getItem, "sugar"));
			//Modchu_Debug.mDebug("modc_ActionIndicating load() ItemStack="+Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item"), int.class }, new Object[]{ actionIndicatingWhistle, 1 }));
			Modchu_Main.addRecipe(Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item"), int.class }, new Object[]{ actionIndicatingWhistle, 1 }),
					new Object[] { "XY", "YY", Character.valueOf('X'),
				Modchu_AS.get(Modchu_AS.getItem, "stick"), Character.valueOf('Y'), Modchu_AS.get(Modchu_AS.getItem, "sugar") });
		}
		//Modchu_Debug.mDebug("modc_ActionIndicating load() end.");
	}

	public boolean modEnabled() {
		return true;
	}

	public void init(Object event) {
		//Modchu_Debug.lDebug("modc_ActionIndicating init");
		if (itemActionIndicatingRod != null) Modchu_Main.itemModelMesherRegister(itemActionIndicatingRod, 0, "modchulib:"+actionIndicatingRodItemName.toLowerCase(), "inventory");
		if (itemActionIndicatingWhistle != null) Modchu_Main.itemModelMesherRegister(itemActionIndicatingWhistle, 0, "modchulib:"+actionIndicatingWhistleItemName.toLowerCase(), "inventory");
		//Modchu_Debug.lDebug("modc_ActionIndicating init end.");
	}

	public static void loadcfg() {
		// cfg読み込み
		if (Modchu_Main.cfgdir.exists()) {
			ArrayList list = new ArrayList();
			if (!mainCfgfile.exists()) {
				// cfgファイルが無い = 新規作成
				String s[] = {
						"useAddChatMessage=true", "usePlaySound=true"
				};
				if (Modchu_Main.getMinecraftVersion() < 170) {
					list.add("actionIndicatingRodID=17661");
					list.add("actionIndicatingWhistleID=17662");
				}
				for(String s1 : s) {
					list.add(s1);
				}
				Modchu_Config.writerConfig(mainCfgfile, list);
			} else {
				// cfgファイルがある
				useAddChatMessage = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "useAddChatMessage", useAddChatMessage));
				usePlaySound = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "usePlaySound", usePlaySound));
				if (Modchu_Main.getMinecraftVersion() < 170) {
					actionIndicatingRodID = Modchu_CastHelper.Int(Modchu_Config.loadConfig(mainCfgfile, "actionIndicatingRodID", actionIndicatingRodID));
					actionIndicatingWhistleID = Modchu_CastHelper.Int(Modchu_Config.loadConfig(mainCfgfile, "actionIndicatingWhistleID", actionIndicatingWhistleID));
				}
				cfgMaxMinCheck();
				String k[] = {
						"useAddChatMessage", "usePlaySound"
				};
				String k1[] = {
						""+useAddChatMessage, ""+usePlaySound
				};
				ArrayList list2 = new ArrayList();
				if (Modchu_Main.getMinecraftVersion() < 170) {
					list.add("actionIndicatingRodID");
					list2.add(""+actionIndicatingRodID);
					list.add("actionIndicatingWhistleID");
					list2.add(""+actionIndicatingWhistleID);
				}
				for(String s1 : k) {
					list.add(s1);
				}
				for(String s1 : k1) {
					list2.add(s1);
				}
				Modchu_Config.writerSupplementConfig(mainCfgfile, list, list2);
			}
		}
	}

	public static void cfgMaxMinCheck() {
		if (actionIndicatingRodID > 32000) actionIndicatingRodID = 32000;
		if (actionIndicatingWhistleID > 32000) actionIndicatingWhistleID = 32000;
	}

}