package me.emmy.hub.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * @author Emmy
 * @project Forest
 * @date 17/08/2024 - 19:24
 */
@UtilityClass
public class WeatherUtil {

    /**
     * Disable weather in a world
     *
     * @param world World
     */
    public void disableWeather(World world) {
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(0);
    }

    /**
     * Disable weather in all worlds
     */
    public void disableWeatherInAllWorlds() {
        for (World world : Bukkit.getWorlds()) {
            disableWeather(world);
        }
    }
}