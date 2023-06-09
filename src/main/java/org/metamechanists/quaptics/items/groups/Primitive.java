package org.metamechanists.quaptics.items.groups;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.quaptics.Quaptics;
import org.metamechanists.quaptics.implementation.blocks.concentrators.SolarConcentrator;
import org.metamechanists.quaptics.implementation.blocks.consumers.Charger;
import org.metamechanists.quaptics.implementation.blocks.consumers.DataStripper;
import org.metamechanists.quaptics.implementation.blocks.consumers.ItemProjector;
import org.metamechanists.quaptics.implementation.blocks.consumers.MultiblockClicker;
import org.metamechanists.quaptics.implementation.blocks.consumers.launchpad.Launchpad;
import org.metamechanists.quaptics.implementation.blocks.consumers.turrets.ModulatedTurret;
import org.metamechanists.quaptics.implementation.blocks.manipulators.Capacitor;
import org.metamechanists.quaptics.implementation.blocks.manipulators.Combiner;
import org.metamechanists.quaptics.implementation.blocks.manipulators.Lens;
import org.metamechanists.quaptics.implementation.blocks.manipulators.Splitter;
import org.metamechanists.quaptics.implementation.blocks.upgraders.DiffractionGrating;
import org.metamechanists.quaptics.implementation.blocks.upgraders.Interferometer;
import org.metamechanists.quaptics.implementation.blocks.upgraders.Polariser;
import org.metamechanists.quaptics.implementation.multiblocks.entangler.EntanglementContainer;
import org.metamechanists.quaptics.implementation.multiblocks.entangler.EntanglementMagnet;
import org.metamechanists.quaptics.implementation.multiblocks.infuser.InfusionContainer;
import org.metamechanists.quaptics.implementation.multiblocks.infuser.InfusionPillar;
import org.metamechanists.quaptics.implementation.multiblocks.reactor.ReactorController;
import org.metamechanists.quaptics.implementation.multiblocks.reactor.ReactorRing;
import org.metamechanists.quaptics.items.Groups;
import org.metamechanists.quaptics.items.RecipeTypes;

import static org.metamechanists.quaptics.implementation.blocks.concentrators.SolarConcentrator.SOLAR_CONCENTRATOR_1;
import static org.metamechanists.quaptics.implementation.blocks.concentrators.SolarConcentrator.SOLAR_CONCENTRATOR_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.Charger.CHARGER_1;
import static org.metamechanists.quaptics.implementation.blocks.consumers.Charger.CHARGER_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.DataStripper.DATA_STRIPPER_1;
import static org.metamechanists.quaptics.implementation.blocks.consumers.DataStripper.DATA_STRIPPER_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.ItemProjector.ITEM_PROJECTOR;
import static org.metamechanists.quaptics.implementation.blocks.consumers.ItemProjector.ITEM_PROJECTOR_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.MultiblockClicker.MULTIBLOCK_CLICKER_1;
import static org.metamechanists.quaptics.implementation.blocks.consumers.MultiblockClicker.MULTIBLOCK_CLICKER_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.launchpad.Launchpad.LAUNCHPAD;
import static org.metamechanists.quaptics.implementation.blocks.consumers.launchpad.Launchpad.LAUNCHPAD_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.turrets.ModulatedTurret.TURRET_1_HOSTILE;
import static org.metamechanists.quaptics.implementation.blocks.consumers.turrets.ModulatedTurret.TURRET_1_HOSTILE_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.consumers.turrets.ModulatedTurret.TURRET_1_PASSIVE;
import static org.metamechanists.quaptics.implementation.blocks.consumers.turrets.ModulatedTurret.TURRET_1_PASSIVE_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Capacitor.CAPACITOR_1;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Capacitor.CAPACITOR_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Combiner.COMBINER_1_2;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Combiner.COMBINER_1_2_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Lens.LENS_1;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Lens.LENS_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Splitter.SPLITTER_1_2;
import static org.metamechanists.quaptics.implementation.blocks.manipulators.Splitter.SPLITTER_1_2_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.upgraders.DiffractionGrating.DIFFRACTION_GRATING_1;
import static org.metamechanists.quaptics.implementation.blocks.upgraders.DiffractionGrating.DIFFRACTION_GRATING_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.upgraders.Interferometer.INTERFEROMETER_1;
import static org.metamechanists.quaptics.implementation.blocks.upgraders.Interferometer.INTERFEROMETER_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.blocks.upgraders.Polariser.POLARISER_1;
import static org.metamechanists.quaptics.implementation.blocks.upgraders.Polariser.POLARISER_1_SETTINGS;
import static org.metamechanists.quaptics.implementation.multiblocks.entangler.EntanglementContainer.ENTANGLEMENT_CONTAINER;
import static org.metamechanists.quaptics.implementation.multiblocks.entangler.EntanglementContainer.ENTANGLEMENT_CONTAINER_SETTINGS;
import static org.metamechanists.quaptics.implementation.multiblocks.entangler.EntanglementMagnet.ENTANGLEMENT_MAGNET;
import static org.metamechanists.quaptics.implementation.multiblocks.entangler.EntanglementMagnet.ENTANGLEMENT_MAGNET_SETTINGS;
import static org.metamechanists.quaptics.implementation.multiblocks.infuser.InfusionContainer.INFUSION_CONTAINER;
import static org.metamechanists.quaptics.implementation.multiblocks.infuser.InfusionContainer.INFUSION_CONTAINER_SETTINGS;
import static org.metamechanists.quaptics.implementation.multiblocks.infuser.InfusionPillar.INFUSION_PILLAR;
import static org.metamechanists.quaptics.implementation.multiblocks.infuser.InfusionPillar.INFUSION_PILLAR_SETTINGS;
import static org.metamechanists.quaptics.implementation.multiblocks.reactor.ReactorController.REACTOR_CONTROLLER;
import static org.metamechanists.quaptics.implementation.multiblocks.reactor.ReactorController.REACTOR_CONTROLLER_SETTINGS;
import static org.metamechanists.quaptics.implementation.multiblocks.reactor.ReactorRing.REACTOR_RING;
import static org.metamechanists.quaptics.implementation.multiblocks.reactor.ReactorRing.REACTOR_RING_SETTINGS;


@SuppressWarnings({"ZeroLengthArrayAllocation", "WeakerAccess"})
@UtilityClass
public class Primitive {
    public final SlimefunItemStack PHASE_CRYSTAL_1 = new SlimefunItemStack("QP_PHASE_CRYSTAL_1", getPhaseCrystal(), "&7Phase Crystal (&e1°&7)");
    public final SlimefunItemStack PHASE_CRYSTAL_5 = new SlimefunItemStack("QP_PHASE_CRYSTAL_5", getPhaseCrystal(), "&7Phase Crystal (&e5°&7)");
    public final SlimefunItemStack PHASE_CRYSTAL_15 = new SlimefunItemStack("QP_PHASE_CRYSTAL_15", getPhaseCrystal(), "&7Phase Crystal (&e15°&7)");
    public final SlimefunItemStack PHASE_CRYSTAL_45 = new SlimefunItemStack("QP_PHASE_CRYSTAL_45", getPhaseCrystal(), "&7Phase Crystal (&e45°&7)");
    public final SlimefunItemStack PHASE_CRYSTAL_90 = new SlimefunItemStack("QP_PHASE_CRYSTAL_90", getPhaseCrystal(), "&7Phase Crystal (&e90°&7)");
    public final SlimefunItemStack PHASE_CRYSTAL_180 = new SlimefunItemStack("QP_PHASE_CRYSTAL_180", getPhaseCrystal(), "&7Phase Crystal (&e180°&7)");
    public final SlimefunItemStack ENTANGLED_CORE = new SlimefunItemStack("QP_ENTANGLED_CORE", getEntangledCore(), "&dEntangled Core");

    private @NotNull ItemStack getPhaseCrystal() {
        final ItemStack itemStack = new ItemStack(Material.QUARTZ);
        itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return itemStack;
    }
    private @NotNull ItemStack getEntangledCore() {
        final ItemStack itemStack = new ItemStack(Material.HEART_OF_THE_SEA);
        itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return itemStack;
    }

    public void initialize() {
        final SlimefunAddon addon = Quaptics.getInstance();

        new SolarConcentrator(
                Groups.PRIMITIVE,
                SOLAR_CONCENTRATOR_1,
                RecipeType.NULL,
                new ItemStack[]{},
                SOLAR_CONCENTRATOR_1_SETTINGS).register(addon);

        new Lens(
                Groups.PRIMITIVE,
                LENS_1,
                RecipeType.NULL,
                new ItemStack[]{},
                LENS_1_SETTINGS).register(addon);

        new Combiner(
                Groups.PRIMITIVE,
                COMBINER_1_2,
                RecipeType.NULL,
                new ItemStack[]{},
                COMBINER_1_2_SETTINGS).register(addon);

        new Splitter(
                Groups.PRIMITIVE,
                SPLITTER_1_2,
                RecipeType.NULL,
                new ItemStack[]{},
                SPLITTER_1_2_SETTINGS).register(addon);

        new Charger(
                Groups.PRIMITIVE,
                CHARGER_1,
                RecipeType.NULL,
                new ItemStack[]{},
                CHARGER_1_SETTINGS).register(addon);

        new Capacitor(
                Groups.PRIMITIVE,
                CAPACITOR_1,
                RecipeType.NULL,
                new ItemStack[]{},
                CAPACITOR_1_SETTINGS).register(addon);

        new ModulatedTurret(
                Groups.PRIMITIVE,
                TURRET_1_HOSTILE,
                RecipeType.NULL,
                new ItemStack[]{},
                TURRET_1_HOSTILE_SETTINGS).register(addon);

        new ModulatedTurret(
                Groups.PRIMITIVE,
                TURRET_1_PASSIVE,
                RecipeType.NULL,
                new ItemStack[]{},
                TURRET_1_PASSIVE_SETTINGS).register(addon);

        new MultiblockClicker(
                Groups.PRIMITIVE,
                MULTIBLOCK_CLICKER_1,
                RecipeType.NULL,
                new ItemStack[]{},
                MULTIBLOCK_CLICKER_1_SETTINGS).register(addon);

        new DataStripper(
                Groups.PRIMITIVE,
                DATA_STRIPPER_1,
                RecipeType.NULL,
                new ItemStack[]{},
                DATA_STRIPPER_1_SETTINGS).register(addon);

        new Launchpad(
                Groups.PRIMITIVE,
                LAUNCHPAD,
                RecipeType.NULL,
                new ItemStack[]{},
                LAUNCHPAD_SETTINGS).register(addon);

        new ItemProjector(
                Groups.PRIMITIVE,
                ITEM_PROJECTOR,
                RecipeType.NULL,
                new ItemStack[]{},
                ITEM_PROJECTOR_SETTINGS).register(addon);

        new InfusionContainer(
                Groups.PRIMITIVE,
                INFUSION_CONTAINER,
                RecipeType.NULL,
                new ItemStack[]{},
                INFUSION_CONTAINER_SETTINGS).register(addon);

        new InfusionPillar(
                Groups.PRIMITIVE,
                INFUSION_PILLAR,
                RecipeType.NULL,
                new ItemStack[]{},
                INFUSION_PILLAR_SETTINGS).register(addon);

        new Polariser(
                Groups.PRIMITIVE,
                POLARISER_1,
                RecipeType.NULL,
                new ItemStack[]{},
                POLARISER_1_SETTINGS).register(addon);

        new Interferometer(
                Groups.PRIMITIVE,
                INTERFEROMETER_1,
                RecipeType.NULL,
                new ItemStack[]{},
                INTERFEROMETER_1_SETTINGS).register(addon);

        new DiffractionGrating(
                Groups.PRIMITIVE,
                DIFFRACTION_GRATING_1,
                RecipeType.NULL,
                new ItemStack[]{},
                DIFFRACTION_GRATING_1_SETTINGS).register(addon);

        new EntanglementMagnet(
                Groups.PRIMITIVE,
                ENTANGLEMENT_MAGNET,
                RecipeType.NULL,
                new ItemStack[]{},
                ENTANGLEMENT_MAGNET_SETTINGS).register(addon);

        new EntanglementContainer(
                Groups.PRIMITIVE,
                ENTANGLEMENT_CONTAINER,
                RecipeType.NULL,
                new ItemStack[]{},
                ENTANGLEMENT_CONTAINER_SETTINGS).register(addon);

        new ReactorController(
                Groups.PRIMITIVE,
                REACTOR_CONTROLLER,
                RecipeType.NULL,
                new ItemStack[]{},
                REACTOR_CONTROLLER_SETTINGS).register(addon);

        new ReactorRing(
                Groups.PRIMITIVE,
                REACTOR_RING,
                RecipeType.NULL,
                new ItemStack[]{},
                REACTOR_RING_SETTINGS).register(addon);

        new SlimefunItem(
                Groups.PRIMITIVE,
                PHASE_CRYSTAL_1,
                RecipeTypes.RECIPE_INFUSION,
                new ItemStack[]{
                        null, null, null,
                        null, new ItemStack(Material.QUARTZ), null,
                        null, null, null
                }).register(addon);

        new SlimefunItem(
                Groups.PRIMITIVE,
                PHASE_CRYSTAL_5,
                RecipeTypes.RECIPE_INFUSION,
                new ItemStack[]{
                        null, null, null,
                        null, PHASE_CRYSTAL_1, null,
                        null, null, null
                }).register(addon);

        new SlimefunItem(
                Groups.PRIMITIVE,
                PHASE_CRYSTAL_15,
                RecipeTypes.RECIPE_INFUSION,
                new ItemStack[]{
                        null, null, null,
                        null, PHASE_CRYSTAL_5, null,
                        null, null, null
                }).register(addon);

        new SlimefunItem(
                Groups.PRIMITIVE,
                PHASE_CRYSTAL_45,
                RecipeTypes.RECIPE_INFUSION,
                new ItemStack[]{
                        null, null, null,
                        null, PHASE_CRYSTAL_15, null,
                        null, null, null
                }).register(addon);

        new SlimefunItem(
                Groups.PRIMITIVE,
                PHASE_CRYSTAL_90,
                RecipeTypes.RECIPE_INFUSION,
                new ItemStack[]{
                        null, null, null,
                        null, PHASE_CRYSTAL_45, null,
                        null, null, null
                }).register(addon);

        new SlimefunItem(
                Groups.PRIMITIVE,
                ENTANGLED_CORE,
                RecipeTypes.RECIPE_ENTANGLEMENT,
                new ItemStack[]{
                        null, null, null,
                        null, new ItemStack(Material.DEAD_BUSH), null,
                        null, null, null
                }).register(addon);
    }
}
