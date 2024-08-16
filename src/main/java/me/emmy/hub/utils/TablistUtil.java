package me.emmy.hub.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistUtil {

    public static int getPing(Player player) {
        try {
            String a = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> b = Class.forName("org.bukkit.craftbukkit." + a + ".entity.CraftPlayer");
            Object c = b.getMethod("getHandle").invoke(player);
            return (int) c.getClass().getDeclaredField("ping").get(c);
        }
        catch (Exception ex) {
            return 0;
        }
    }

}