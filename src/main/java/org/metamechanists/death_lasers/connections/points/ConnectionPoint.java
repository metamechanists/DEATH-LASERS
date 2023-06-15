package org.metamechanists.death_lasers.connections.points;

import dev.sefiraat.sefilib.misc.TransformationBuilder;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.util.Vector;

public abstract class ConnectionPoint {
    @Getter
    protected final Location location;
    private final BlockData blockData;
    @Getter
    protected final Display.Brightness connectedBrightness;
    @Getter
    protected final Display.Brightness disconnectedBrightness;
    private final float scale;
    protected final BlockDisplay blockDisplay;
    protected final Interaction interaction;

    ConnectionPoint(Location location, BlockData blockData, Display.Brightness connectedBrightness, Display.Brightness disconnectedBrightness, float scale) {
        this.location = location;
        this.blockData = blockData;
        this.connectedBrightness = connectedBrightness;
        this.disconnectedBrightness = disconnectedBrightness;
        this.scale = scale;
        this.blockDisplay = buildBlockDisplay(location);
        this.interaction = buildInteraction(location);
    }

    private BlockDisplay buildBlockDisplay(Location location) {
        final Location locationAdjustedForBukkitWeirdness = location.clone().add(new Vector(-scale/2, 0, -scale/2));
        final BlockDisplay display = location.getWorld().spawn(locationAdjustedForBukkitWeirdness, BlockDisplay.class);
        display.setBlock(blockData);
        display.setBrightness(disconnectedBrightness);
        display.setTransformation(new TransformationBuilder().scale(scale, scale, scale).build());
        return display;
    }

    private Interaction buildInteraction(Location location) {
        final Interaction interaction = location.getWorld().spawn(location, Interaction.class);
        interaction.setInteractionWidth(scale);
        interaction.setInteractionHeight(scale);
        return interaction;
    }

    public abstract void tick();
    public abstract void remove();
    public abstract void kill();

    public void select() {
        blockDisplay.setGlowing(true);
        blockDisplay.setGlowColorOverride(Color.fromRGB(0, 255, 0));
    }

    public void deselect() {
        blockDisplay.setGlowing(false);
    }
}
