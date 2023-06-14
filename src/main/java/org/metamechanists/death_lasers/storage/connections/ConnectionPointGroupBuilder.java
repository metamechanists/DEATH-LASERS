package org.metamechanists.death_lasers.storage.connections;

import org.bukkit.Location;
import org.metamechanists.death_lasers.implementation.ConnectionPoint;

import java.util.HashMap;
import java.util.Map;

public class ConnectionPointGroupBuilder {
    private final Map<Location, ConnectionPoint> points = new HashMap<>();

    public ConnectionPointGroupBuilder() {}

    public ConnectionPointGroupBuilder addConnectionPoint(ConnectionPoint point) {
        points.put(point.getLocation(), point);
        return this;
    }

    public ConnectionPointGroup build() {
        return new ConnectionPointGroup(points);
    }
}
