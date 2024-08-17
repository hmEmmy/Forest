package me.emmy.hub.util;

import lombok.experimental.UtilityClass;
import me.emmy.hub.Forest;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Emmy
 * @project Forest
 * @date 17/08/2024 - 19:22
 */
@UtilityClass
public class TaskUtil {

    private final Forest plugin = Forest.getInstance();

    /**
     * Run a task asynchronously
     *
     * @param runnable Runnable
     */
    public void runTaskAsync(Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Run a task later
     *
     * @param runnable Runnable
     * @param delay    long
     */
    public void runTaskLater(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    /**
     * Run a task timer
     *
     * @param runnable Runnable
     * @param delay    long
     * @param timer    long
     */
    public void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(plugin, delay, timer);
    }

    /**
     * Run a task timer
     *
     * @param runnable Runnable
     * @param delay    long
     * @param timer    long
     */
    public void runTaskTimer(Runnable runnable, long delay, long timer) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, timer);
    }

    /**
     * Run a task on the main thread
     *
     * @param runnable Runnable
     */
    public void runTask(Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }
}
