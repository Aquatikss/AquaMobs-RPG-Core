package com.aquaMobs.rpgCore.misc;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Location;

import java.util.Set;
import java.util.stream.Collectors;

public class RegionUtil {

    public static Set<String> getRegionsAt(Location loc) {
        // Get WorldGuard RegionContainer
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        // Get all regions at the location
        ApplicableRegionSet regions = query.getApplicableRegions(BukkitAdapter.adapt(loc));

        // Return a set of region IDs
        return regions.getRegions().stream()
                .map(region -> region.getId())
                .collect(Collectors.toSet());
    }

}
