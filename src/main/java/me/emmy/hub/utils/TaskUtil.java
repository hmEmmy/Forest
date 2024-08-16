package me.emmy.hub.utils;

import me.emmy.hub.Forest;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Emmy
 * Project: Forest
 * Date: 27/03/2024 - 14:56
 */
public class TaskUtil {

    private static final Forest plugin = Forest.getInstance();

    public static void runTaskAsync(Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public static void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(plugin, delay, timer);
    }

    public static void runTaskTimer(Runnable runnable, long delay, long timer) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, timer);
    }

    public static void runTask(Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

}