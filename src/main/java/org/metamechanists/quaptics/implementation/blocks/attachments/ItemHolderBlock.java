package org.metamechanists.quaptics.implementation.blocks.attachments;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.metalib.utils.ItemUtils;
import org.metamechanists.quaptics.connections.ConnectionGroup;
import org.metamechanists.quaptics.implementation.blocks.base.DisplayGroupTickerBlock;

import java.util.Optional;

public interface ItemHolderBlock {
    static Optional<ItemDisplay> getItemDisplay(final Location location) {
        final Optional<Display> display = DisplayGroupTickerBlock.getDisplay(location, "item");
        if (display.isEmpty()) {
            return Optional.empty();
        }

        if (!(display.get() instanceof final ItemDisplay itemDisplay)) {
            return Optional.empty();
        }

        return Optional.of(itemDisplay);
    }

    static Optional<ItemStack> getStack(final @NotNull ConnectionGroup group) {
        final Optional<Location> location = group.getLocation();
        if (location.isEmpty()) {
            return Optional.empty();
        }

        return getStack(location.get());
    }

    static Optional<ItemStack> getStack(final @NotNull Location location) {
        final Optional<ItemDisplay> itemDisplay = getItemDisplay(location);
        if (itemDisplay.isEmpty()) {
            return Optional.empty();
        }

        final ItemStack stack = itemDisplay.get().getItemStack();
        return stack == null || stack.getItemMeta() == null ? Optional.empty() : Optional.of(stack);
    }

    static void insertItem(final Location location, @NotNull final ItemStack itemStack) {
        final Optional<ItemDisplay> itemDisplay = getItemDisplay(location);
        if (itemDisplay.isEmpty()) {
            return;
        }

        itemDisplay.get().setItemStack(itemStack);
    }

    static Optional<ItemStack> removeItem(@NotNull final Location location) {
        final Optional<ItemDisplay> itemDisplay = getItemDisplay(location);
        if (itemDisplay.isEmpty()) {
            return Optional.empty();
        }

        final ItemStack itemStack = itemDisplay.get().getItemStack();
        itemDisplay.get().setItemStack(null);
        return Optional.ofNullable(itemStack);
    }

    default void interact(@NotNull final Location location, @NotNull final Player player) {
        final Optional<ItemStack> currentStack = removeItem(location);
        if (currentStack.isEmpty() || currentStack.get().getType().isEmpty()) {
            final ItemStack newStack = player.getInventory().getItemInMainHand().clone();
            if (newStack.getType().isEmpty()) {
                return;
            }

            if (onInsert(newStack, player)) {
                insertItem(location, newStack);
                player.getInventory().setItemInMainHand(null);
            }

            return;
        }

        final Optional<ItemStack> finalStack = onRemove(location, currentStack.get());
        if (finalStack.isEmpty()) {
            return;
        }

        ItemUtils.addOrDropItemMainHand(player, finalStack.get());
    }

    boolean onInsert(@NotNull final ItemStack stack, @NotNull final Player player);
    Optional<ItemStack> onRemove(@NotNull final Location location, @NotNull final ItemStack stack);
}
