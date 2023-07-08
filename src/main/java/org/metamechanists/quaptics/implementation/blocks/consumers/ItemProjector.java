package org.metamechanists.quaptics.implementation.blocks.consumers;

import dev.sefiraat.sefilib.entity.display.DisplayGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.metamechanists.quaptics.connections.ConnectionGroup;
import org.metamechanists.quaptics.connections.ConnectionPoint;
import org.metamechanists.quaptics.connections.ConnectionPointType;
import org.metamechanists.quaptics.connections.Link;
import org.metamechanists.quaptics.implementation.blocks.Settings;
import org.metamechanists.quaptics.implementation.blocks.attachments.ConfigPanelBlock;
import org.metamechanists.quaptics.implementation.blocks.attachments.ItemHolderBlock;
import org.metamechanists.quaptics.implementation.blocks.attachments.PowerAnimatedBlock;
import org.metamechanists.quaptics.implementation.blocks.base.ConnectedBlock;
import org.metamechanists.quaptics.panels.config.ConfigPanel;
import org.metamechanists.quaptics.panels.config.ConfigPanelContainer;
import org.metamechanists.quaptics.panels.config.implementation.ItemProjectorConfigPanel;
import org.metamechanists.quaptics.utils.BlockStorageAPI;
import org.metamechanists.quaptics.utils.Keys;
import org.metamechanists.quaptics.utils.Transformations;
import org.metamechanists.quaptics.utils.Utils;
import org.metamechanists.quaptics.utils.builders.BlockDisplayBuilder;
import org.metamechanists.quaptics.utils.builders.ItemDisplayBuilder;
import org.metamechanists.quaptics.utils.id.complex.ConfigPanelId;
import org.metamechanists.quaptics.utils.id.complex.ConnectionGroupId;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.Optional;

public class ItemProjector extends ConnectedBlock implements ItemHolderBlock, PowerAnimatedBlock, ConfigPanelBlock {
    private static final Vector RELATIVE_PANEL_LOCATION = new Vector(0, 0, -0.51);
    public static final double MAX_SIZE = 20;
    public static final double MAX_HEIGHT = 20;
    private static final double HEIGHT_MULTIPLY = 0.1;
    private static final double SIZE_MULTIPLY = 0.1;
    private static final Vector3f MAIN_DISPLAY_SIZE = new Vector3f(1.0F, 0.7F, 1.0F);
    private static final Vector3f MAIN_DISPLAY_OFFSET = new Vector3f(0.0F, -0.35F, 0.0F);
    private static final Vector3f PRISM_DISPLAY_SIZE = new Vector3f(0.4F, 0.4F, 0.4F);
    private static final Vector3f ITEM_DISPLAY_ADDITIONAL_SIZE = new Vector3f(0.1F);
    private static final Vector3f ITEM_DISPLAY_ADDITIONAL_OFFSET = new Vector3f(0, 0.6F, 0);
    private final Vector inputPointLocation = new Vector(0.0F, 0.0F, -settings.getConnectionRadius());

    public ItemProjector(final ItemGroup itemGroup, final SlimefunItemStack item, final RecipeType recipeType, final ItemStack[] recipe, final Settings settings) {
        super(itemGroup, item, recipeType, recipe, settings);
    }

    @Override
    protected void addDisplays(@NotNull final DisplayGroup displayGroup, @NotNull final Location location, final @NotNull Player player) {
        displayGroup.addDisplay("main", new BlockDisplayBuilder(location.toCenterLocation())
                .setBlockData(Material.LIGHT_GRAY_CONCRETE.createBlockData())
                .setTransformation(Transformations.adjustedScaleOffset(MAIN_DISPLAY_SIZE, MAIN_DISPLAY_OFFSET))
                .build());
        displayGroup.addDisplay("prism", new BlockDisplayBuilder(location.toCenterLocation())
                .setBlockData(Material.CYAN_STAINED_GLASS.createBlockData())
                .setTransformation(Transformations.adjustedRotateScale(PRISM_DISPLAY_SIZE, Transformations.GENERIC_ROTATION_ANGLES))
                .setBrightness(Utils.BRIGHTNESS_OFF)
                .build());
        displayGroup.addDisplay("item", new ItemDisplayBuilder(location.toCenterLocation())
                .setTransformation(calculateItemTransformation(0, 0))
                .setViewRange(VIEW_RANGE_OFF)
                .setBillboard(Billboard.VERTICAL)
                .setBrightness(Utils.BRIGHTNESS_ON)
                .build());
        BlockStorageAPI.set(location, Keys.BS_HEIGHT, 0);
        BlockStorageAPI.set(location, Keys.BS_SIZE, ITEM_DISPLAY_ADDITIONAL_SIZE.x);
    }

    @Override
    protected List<ConnectionPoint> generateConnectionPoints(final ConnectionGroupId groupId, final Player player, final Location location) {
        return List.of(new ConnectionPoint(ConnectionPointType.INPUT, groupId, "input", formatPointLocation(player, location, inputPointLocation)));
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onPlace(@NotNull final BlockPlaceEvent event) {
        super.onPlace(event);
        final Location location = event.getBlock().getLocation();
        final Optional<ConnectionGroup> optionalGroup = getGroup(location);
        if (optionalGroup.isEmpty()) {
            return;
        }

        ConfigPanelBlock.setPanelId(location, new ItemProjectorConfigPanel(
                formatPointLocation(event.getPlayer(), location, RELATIVE_PANEL_LOCATION),
                optionalGroup.get().getId(),
                (float) Transformations.yawToCardinalDirection(event.getPlayer().getEyeLocation().getYaw()))
                .getId());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onBreak(@NotNull final Location location) {
        super.onBreak(location);
        final Optional<ConfigPanelId> panelId = ConfigPanelBlock.getPanelId(location);
        final Optional<ConfigPanelContainer> panel = panelId.isPresent() ? panelId.get().get() : Optional.empty();
        panel.ifPresent(ConfigPanelContainer::remove);
        ItemHolderBlock.getStack(location).ifPresent(stack -> location.getWorld().dropItem(location, stack));
    }

    @Override
    protected void onRightClick(final @NotNull Location location, final @NotNull Player player) {
        itemHolderInteract(location, player);
    }

    @Override
    public void onInputLinkUpdated(@NotNull final ConnectionGroup group) {
        doBurnoutCheck(group, "input");

        final Optional<Location> location = group.getLocation();
        if (location.isEmpty()) {
            return;
        }

        final Optional<Link> inputLink = getLink(group, "input");
        onPoweredAnimation(location.get(), inputLink.isPresent() && settings.isOperational(inputLink));
    }

    @Override
    public boolean onInsert(@NotNull final ItemStack stack, @NotNull final Player player) {
        return true;
    }

    @Override
    public Optional<ItemStack> onRemove(@NotNull final Location location, @NotNull final ItemStack stack) {
        return Optional.of(stack);
    }

    @Override
    public void onPoweredAnimation(final Location location, final boolean powered) {
        final Optional<Display> mainDisplay = getDisplay(location, "prism");
        if (mainDisplay.isEmpty()) {
            return;
        }

        mainDisplay.get().setBrightness(new Brightness(powered ? Utils.BRIGHTNESS_ON : Utils.BRIGHTNESS_OFF, 0));

        final Optional<ItemDisplay> itemDisplay = ItemHolderBlock.getItemDisplay(location);
        if (itemDisplay.isEmpty()) {
            return;
        }

        itemDisplay.get().setViewRange(powered ? VIEW_RANGE_ON : VIEW_RANGE_OFF);
    }

    @Override
    public ConfigPanel createPanel(final ConfigPanelId panelId, final ConnectionGroupId groupId) {
        return new ItemProjectorConfigPanel(panelId, groupId);
    }

    private static Matrix4f calculateItemTransformation(final double size, final double offset) {
        return Transformations.unadjustedScaleTranslate(
                new Vector3f(ITEM_DISPLAY_ADDITIONAL_SIZE).add(new Vector3f((float) size)),
                new Vector3f(ITEM_DISPLAY_ADDITIONAL_OFFSET).add(new Vector3f(0, (float) offset, 0)));
    }

    public static void onConfigUpdated(final Location location) {
        final Optional<Display> display = getDisplay(location, "item");
        if (display.isEmpty() || !(display.get() instanceof final ItemDisplay itemDisplay)) {
            return;
        }

        itemDisplay.setTransformationMatrix(calculateItemTransformation(
                BlockStorageAPI.getDouble(location, Keys.BS_SIZE) * SIZE_MULTIPLY,
                BlockStorageAPI.getDouble(location, Keys.BS_HEIGHT) * HEIGHT_MULTIPLY));
    }
}
