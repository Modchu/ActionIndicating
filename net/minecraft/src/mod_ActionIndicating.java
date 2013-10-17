package net.minecraft.src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class mod_ActionIndicating extends BaseMod {

	public static int actionIndicatingRodID = 17661;
	public static int actionIndicatingWhistleID = 17662;
	public static boolean useAddChatMessage = true;
	public static boolean usePlaySound = true;

	public static Item actionIndicatingRod;
	public static Item actionIndicatingWhistle;
	private static boolean DebugMessage = true;
	private static mod_ActionIndicating mod_actionIndicating;
	private static final File cfgdir = new File(Minecraft.getMinecraft().mcDataDir, "/config/");
	private static File mainCfgfile = new File(cfgdir, ("ActionIndicating.cfg"));
	public static String actionIndicatingRodItemName;
	public static String actionIndicatingWhistleItemName;
	private String packageName;

	public static void Debug(String s)
	{
		if (DebugMessage)
		{
			System.out.println((new StringBuilder()).append("actionIndicating-").append(s).toString());
		}
	}

	public static void mDebug(String s)
	{
		if (DebugMessage
				&& !mod_actionIndicating.isRelease())
		{
			System.out.println((new StringBuilder()).append("actionIndicating_").append(s).toString());
		}
	}

	public boolean isRelease() {
		return getPackage() == null;
	}

	public String getVersion() {
		return "1.6.2-1";
	}

	public void load() {
		mod_actionIndicating = this;
		loadcfg();
		if (actionIndicatingRodID > 0) {
			actionIndicatingRodItemName = "ActionIndicatingRod";
			actionIndicatingRod = new ItemActionIndicatingRod(
					actionIndicatingRodID - 256).setUnlocalizedName(
							actionIndicatingRodItemName).setCreativeTab(CreativeTabs.tabMaterials);
			ModLoader.addName(actionIndicatingRod, actionIndicatingRodItemName);
			ModLoader.addRecipe(new ItemStack(actionIndicatingRod, 1),
					new Object[] { " Y", "X ", Character.valueOf('X'),
					Item.fishingRod, Character.valueOf('Y'), Item.sugar });
		}
		if (actionIndicatingWhistleID > 0) {
			actionIndicatingWhistleItemName = "ActionIndicatingWhistle";
			actionIndicatingWhistle = new ItemActionIndicatingWhistle(
					actionIndicatingWhistleID - 256).setUnlocalizedName(
							actionIndicatingWhistleItemName).setCreativeTab(CreativeTabs.tabMaterials);
			ModLoader.addName(actionIndicatingWhistle, actionIndicatingWhistleItemName);
			ModLoader.addRecipe(new ItemStack(actionIndicatingWhistle, 1),
					new Object[] { "XY", "YY", Character.valueOf('X'),
					Item.stick, Character.valueOf('Y'), Item.sugar });
		}
	}

	public static void writerList(String[] s, File file, List<String> list) {
		//Listファイル書き込み
		try {
			BufferedWriter bwriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < s.length ; i++)
			{
				//mDebug("s[i]="+s[i]);
				if (s[i] != null) {
					bwriter.write(s[i]);
					list.add(s[i]);
					bwriter.newLine();
				}
			}
			bwriter.close();
			Debug("file new file create.");
		} catch (Exception e) {
			Debug("file writer fail.");
			e.printStackTrace();
			Debug(" ");
		}
	}

	public static void loadcfg() {
		// cfg読み込み
		if (cfgdir.exists()) {
			if (!mainCfgfile.exists()) {
				// cfgファイルが無い = 新規作成
				String s[] = {
						"actionIndicatingRodID=17661", "actionIndicatingWhistleID=17662", "useAddChatMessage=true", "usePlaySound=true"
				};
				ActionIndicatingConfig.writerConfig(mainCfgfile, s);
			} else {
				// cfgファイルがある
				actionIndicatingRodID = Integer.valueOf((ActionIndicatingConfig.loadConfig(mainCfgfile, "actionIndicatingRodID", actionIndicatingRodID)).toString());
				actionIndicatingWhistleID = Integer.valueOf((ActionIndicatingConfig.loadConfig(mainCfgfile, "actionIndicatingWhistleID", actionIndicatingWhistleID)).toString());
				useAddChatMessage = Boolean.valueOf((ActionIndicatingConfig.loadConfig(mainCfgfile, "useAddChatMessage", useAddChatMessage)).toString());
				usePlaySound = Boolean.valueOf((ActionIndicatingConfig.loadConfig(mainCfgfile, "usePlaySound", usePlaySound)).toString());
				cfgMaxMinCheck();
				String k[] = {
						"actionIndicatingRodID", "actionIndicatingWhistleID", "useAddChatMessage", "usePlaySound"
				};
				String k1[] = {
						""+actionIndicatingRodID, ""+actionIndicatingWhistleID, ""+useAddChatMessage, ""+usePlaySound
				};
				ActionIndicatingConfig.writerSupplementConfig(mainCfgfile, k, k1);
			}
		}
	}

	public static void cfgMaxMinCheck() {
		if (actionIndicatingRodID > 32000) actionIndicatingRodID = 32000;
		if (actionIndicatingWhistleID > 32000) actionIndicatingWhistleID = 32000;
	}

	public String getPackage() {
		if (packageName != null) return packageName;
		try {
			String s = ""+Class.forName("net.minecraft.src.FMLRenderAccessLibrary");
			Class c = Class.forName("net.minecraft.src.mod_ActionIndicatingRod");
			if (c != null) return packageName = "net.minecraft.src";
			return packageName;
		} catch (Exception e) {
		}
		Package pac = getClass().getPackage();
		if (pac != null) {
			packageName = pac.getName();
			return packageName;
		}
		return packageName;
	}
}