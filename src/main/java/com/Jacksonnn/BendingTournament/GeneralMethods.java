package com.Jacksonnn.BendingTournament;

import net.md_5.bungee.api.ChatColor;

public class GeneralMethods {
	public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "BendingTournament" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " ";
	public static String errorColor = prefix + ChatColor.DARK_RED + "Error! " + ChatColor.RED;
	public static String successColor = prefix + ChatColor.GREEN + "";
	public static String disableColor = prefix + ChatColor.RED + "";

	public enum Elements {

		Air("air", ChatColor.GRAY),
		WATER("water", ChatColor.AQUA),
		EARTH("earth", ChatColor.GREEN),
		FIRE("fire", ChatColor.RED),
		CHI("chi", ChatColor.GOLD);

		String alias;
		ChatColor color;

		Elements(String alias, ChatColor color) {
			this.alias = alias;
			this.color = color;
		}

		public ChatColor getColor(){
			return this.color;
		}

		public static Elements getByName(String alias) {
			for (Elements element : Elements.values()) {
				if (element.alias.equalsIgnoreCase(alias))
					return element;
			}
			return null;
		}
	}
}
