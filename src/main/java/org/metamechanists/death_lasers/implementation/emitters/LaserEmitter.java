package org.metamechanists.death_lasers.implementation.emitters;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.death_lasers.connections.ConnectionPoint;
import org.metamechanists.death_lasers.connections.ConnectionType;
import org.metamechanists.death_lasers.lasers.BeamStorage;
import org.metamechanists.death_lasers.connections.ConnectionPointGroupBuilder;
import org.metamechanists.death_lasers.connections.ConnectionPointStorage;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class LaserEmitter extends SimpleSlimefunItem<BlockTicker> implements EnergyNetComponent {
    private static final Vector INPUT_VECTOR = new Vector(0.4F, 1.0F, 0.0F);
    private static final Vector OUTPUT_VECTOR = new Vector(0.4F, 1.0F, 0.8F);
    @Getter
    private final EnergyNetComponentType energyComponentType = EnergyNetComponentType.CONSUMER;
    @Getter
    private final int capacity;
    private final int consumption;

    public LaserEmitter(ItemGroup group, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int capacity, int consumption) {
        super(group, item, recipeType, recipe);
        this.consumption = consumption;
        this.capacity = capacity;
        addItemHandler(onBreak(), onPlace());
    }

    public abstract void updateBeamGroup(Location source, Location target);
    protected abstract void powerOn(Location source);
    protected void powerOff(Location source) {
        BeamStorage.setBeamGroupPowered(source, false);
    }

    @Nonnull
    private BlockPlaceHandler onPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@NotNull BlockPlaceEvent e) {
                final Location source = e.getBlock().getLocation();
                ConnectionPointStorage.addConnectionPointGroup(source,
                        new ConnectionPointGroupBuilder()
                                .addConnectionPoint(new ConnectionPoint(source.clone().add(INPUT_VECTOR), ConnectionType.INPUT))
                                .addConnectionPoint(new ConnectionPoint(source.clone().add(OUTPUT_VECTOR), ConnectionType.OUTPUT))
                                .build());
            }
        };
    }

    @Nonnull
    private BlockBreakHandler onBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@NotNull BlockBreakEvent e, @NotNull ItemStack item, @NotNull List<ItemStack> drops) {
                final Location source = e.getBlock().getLocation();
                ConnectionPointStorage.removeConnectionPointGroup(source);
                if (BeamStorage.hasBeamGroup(e.getBlock().getLocation())) {
                    BeamStorage.deprecateBeamGroup(e.getBlock().getLocation());
                }
            }
        };
    }

    @NotNull
    @Override
    public BlockTicker getItemHandler() {
        return new BlockTicker() {

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                final Location source = b.getLocation();

                // Check powered status
                int charge = getCharge(b.getLocation(), data);
                if (charge >= consumption) {
                    removeCharge(b.getLocation(), consumption);
                    if (BeamStorage.hasBeamGroup(source)) {
                        powerOn(source);
                    }
                } else {
                    if (BeamStorage.hasBeamGroup(source)) {
                        powerOff(source);
                    }
                }
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        };
    }
}
