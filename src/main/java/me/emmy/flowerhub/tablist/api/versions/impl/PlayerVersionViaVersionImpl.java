package me.emmy.flowerhub.tablist.api.versions.impl;

import me.emmy.flowerhub.tablist.api.versions.module.IPlayerVersion;
import me.emmy.flowerhub.tablist.api.versions.module.PlayerVersion;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */
public class PlayerVersionViaVersionImpl implements IPlayerVersion
{
    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(Via.getAPI().getPlayerVersion(player));
    }
}
