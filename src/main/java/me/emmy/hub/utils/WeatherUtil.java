package me.emmy.hub.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Created by Emmy
 * Project: Forest
 * Date: 17/03/2024 - 21:08
 */
public class WeatherUtil {
    public static void disableWeather(World world) {
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(0);
    }

    public static void disableWeatherInAllWorlds() {
        for (World world : Bukkit.getWorlds()) {
            disableWeather(world);
        }
    }
}