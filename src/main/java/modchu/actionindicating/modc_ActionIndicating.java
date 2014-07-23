package modchu.actionindicating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import modchu.lib.Modchu_Config;
import modchu.lib.Modchu_Main;
import modchu.lib.Modchu_Reflect;
import modchu.lib.characteristic.Modchu_AS;
import modchu.lib.characteristic.Modchu_Item;
import modchu.lib.characteristic.recompileonly.Modchu_CastHelper;
import modchu.ridingindicatingrod.ItemRidingIndicatingRod;

public class modc_ActionIndicating {

	public static int actionIndicatingRodID = 17661;
	public static int actionIndicatingWhistleID = 17662;
	public static boolean useAddChatMessage = true;
	public static boolean usePlaySound = true;

	public static Object actionIndicatingRod;
	public static Object actionIndicatingWhistle;
	private static boolean DebugMessage = true;
	private static modc_ActionIndicating modc_ActionIndicating;
	private static File cfgdir;
	private static File mainCfgfile;
	public static String actionIndicatingRodItemName;
	public static String actionIndicatingWhistleItemName;
	private String packageName;

	public String getVersion() {
		return "2";
	}

	public void load() {
		modc_ActionIndicating = this;
		cfgdir = new File(Modchu_AS.getFile(Modchu_AS.minecraftMcDataDir), "/config/");
		mainCfgfile = new File(cfgdir, ("ActionIndicating.cfg"));
		loadcfg();
		if (actionIndicatingRodID > 0) {
			actionIndicatingRodItemName = "ActionIndicatingRod";
			Object item = new Modchu_Item(ItemActionIndicatingRod.class, actionIndicatingRodID - 256);
			//Modchu_Debug.mDebug("modc_ActionIndicating load() item="+item);
			item = Modchu_AS.get(Modchu_AS.itemSetUnlocalizedName, item, actionIndicatingRodItemName);
			item = Modchu_AS.get(Modchu_AS.itemSetCreativeTab, item, Modchu_AS.get(Modchu_AS.creativeTabsTabMaterials));
			item = Modchu_AS.get(Modchu_AS.itemSetTextureName, item, actionIndicatingRodItemName);
			Modchu_Main.registerItem(item, actionIndicatingRodItemName);
			actionIndicatingRod = (Modchu_Item) item;
			//Modchu_Debug.mDebug("modc_ActionIndicating load() fishing_rod="+Modchu_AS.get(Modchu_AS.getItem, "fishing_rod"));
			//Modchu_Debug.mDebug("modc_ActionIndicating load() sugar="+Modchu_AS.get(Modchu_AS.getItem, "sugar"));
			//Modchu_Debug.mDebug("modc_ActionIndicating load() ItemStack="+Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item"), int.class }, new Object[]{ actionIndicatingRod, 1 }));
			Modchu_Main.addRecipe(Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item"), int.class }, new Object[]{ actionIndicatingRod, 1 }),
					new Object[] { " Y", "X ", Character.valueOf('X'),
				Modchu_AS.get(Modchu_AS.getItem, "fishing_rod"), Character.valueOf('Y'), Modchu_AS.get(Modchu_AS.getItem, "sugar") });
		}
		if (actionIndicatingWhistleID > 0) {
			actionIndicatingWhistleItemName = "ActionIndicatingWhistle";
			Object item = new Modchu_Item(ItemActionIndicatingWhistle.class, actionIndicatingWhistleID - 256);
			//Modchu_Debug.mDebug("modc_ActionIndicating load() item="+item);
			item = Modchu_AS.get(Modchu_AS.itemSetUnlocalizedName, item, actionIndicatingWhistleItemName);
			item = Modchu_AS.get(Modchu_AS.itemSetCreativeTab, item, Modchu_AS.get(Modchu_AS.creativeTabsTabMaterials));
			item = Modchu_AS.get(Modchu_AS.itemSetTextureName, item, actionIndicatingWhistleItemName);
			Modchu_Main.registerItem(item, actionIndicatingWhistleItemName);
			actionIndicatingWhistle = (Modchu_Item) item;
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

	public static void loadcfg() {
		// cfg読み込み
		if (cfgdir.exists()) {
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