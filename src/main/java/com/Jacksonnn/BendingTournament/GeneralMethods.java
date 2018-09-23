package com.Jacksonnn.BendingTournament;

import net.md_5.bungee.api.ChatColor;

public class GeneralMethods {
	public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "BendingTournament" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " ";
	public static String errorColor = prefix + ChatColor.DARK_RED + "Error! " + ChatColor.RED;
	public static String successColor = prefix + ChatColor.GREEN + "";
	public static String disableColor = prefix + ChatColor.RED + "";

	public enum Elements {
		AIR,
		WATER,
		EARTH,
		FIRE,
		CHI;
	}
}
