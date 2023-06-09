package org.metamechanists.quaptics.panels.config;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.joml.Vector3d;
import org.metamechanists.quaptics.utils.id.complex.ConfigPanelAttributeId;
import org.metamechanists.quaptics.utils.id.complex.ConnectionGroupId;

import java.util.HashMap;
import java.util.Map;

public class ConfigPanelBuilder {
    private final ConnectionGroupId groupId;
    private final float size;
    private final Vector3d displayRotation;
    private final Vector attributeSpacing;
    private final Location location;
    private final Vector offset;
    private final Map<String, ConfigPanelAttributeId> attributes = new HashMap<>();

    public ConfigPanelBuilder(final ConnectionGroupId groupId, final Location location, final float size, final float rotationY) {
        this.groupId = groupId;
        this.size = size;
        this.displayRotation = new Vector3d(0, rotationY+Math.PI, 0);
        this.attributeSpacing = new Vector(0, size/3.5, 0);
        this.location = location;
        this.offset = new Vector();
    }

    public ConfigPanelBuilder addAttribute(final String name, final String key) {
        final ConfigPanelAttribute attribute = new ConfigPanelAttribute(groupId, name, key, location.clone(), offset.clone(), displayRotation, size);
        attributes.put(name, attribute.getId());
        offset.add(attributeSpacing);
        return this;
    }

    public ConfigPanelContainer build() {
        return new ConfigPanelContainer(location, attributes);
    }
}
