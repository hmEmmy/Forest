package me.emmy.flowerhub.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Created by Emmy
 * Project: FlowerHub-main
 * Date: 17/03/2024 - 21:08
 * GitHub: https://github.com/Emmiesa
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