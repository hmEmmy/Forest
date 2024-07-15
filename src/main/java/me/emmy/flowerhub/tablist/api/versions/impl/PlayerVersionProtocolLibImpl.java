package me.emmy.flowerhub.tablist.api.versions.impl;

import com.comphenix.protocol.ProtocolLibrary;
import me.emmy.flowerhub.tablist.api.versions.module.IPlayerVersion;
import me.emmy.flowerhub.tablist.api.versions.module.PlayerVersion;
import org.bukkit.entity.Player;

/**
 * Created By LeandroSSJ
 * Created on 22/09/2021
 */

public class PlayerVersionProtocolLibImpl implements IPlayerVersion
{
    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(ProtocolLibrary.getProtocolManager().getProtocolVersion(player));
    }
}
