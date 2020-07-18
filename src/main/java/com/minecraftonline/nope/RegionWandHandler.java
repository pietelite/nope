package com.minecraftonline.nope;

import com.flowpowered.math.vector.Vector3i;
import com.minecraftonline.nope.control.Settings;
import com.minecraftonline.nope.key.regionwand.RegionWandManipulator;
import com.minecraftonline.nope.util.Format;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;
import jdk.internal.jline.internal.Nullable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Map;

public class RegionWandHandler {
  private final Map<Player, Selection> selectionMap = new HashMap<>();

  public Map<Player, Selection> getSelectionMap() {
    return selectionMap;
  }

  @Listener
  public void InteractBlockEvent(InteractBlockEvent event) {
    if (handleEvent(event, event.getTargetBlock())) {
      event.setCancelled(true);
    }
  }

  private boolean handleEvent(Event event, BlockSnapshot block) {
    MutableBoolean mutableBoolean = new MutableBoolean(false);
    event.getCause().first(Player.class).ifPresent(player -> player.getItemInHand(HandTypes.MAIN_HAND).filter(this::isWand).ifPresent(wand -> {
      mutableBoolean.setTrue();
      selectionMap.compute(player, (k,v) -> {
        if (v == null) {
          v = new Selection();
        }
        if (event instanceof InteractBlockEvent.Primary) {
          v.setPos1(block.getLocation().get(), player); // left click
        }
        else if (event instanceof InteractBlockEvent.Secondary){
          v.setPos2(block.getLocation().get(), player); // right click
        }
        return v;
      });
    }));
    return mutableBoolean.booleanValue();
  }

  private boolean isWand(ItemStack itemStack) {
    ItemStack wandItemStack = SpongeWorldEdit.inst().getAdapter().makeSpongeStack(new BaseItemStack(Nope.getInstance().getGlobalHost().getSettingValueOrDefault(Settings.WAND_ITEM).getID(), 1));
    if (!wandItemStack.getType().equals(itemStack.getType())) {
      return false;
    }
    if (!itemStack.get(RegionWandManipulator.class)
        .map(RegionWandManipulator::isWand)
        .map(Value::get)
        .orElse(false)) {
      return false;
    }
    return true;
  }

  public static class Selection {
    @Nullable
    private World world = null;
    @Nullable
    private Vector3i pos1 = null;
    @Nullable
    private Vector3i pos2 = null;

    public Selection() {}

    public void setPos1(Location<World> location, CommandSource src) {
      if (this.world != null && !this.world.equals(location.getExtent())) {
        this.pos2 = null;
        src.sendMessage(Format.info("Your selection has changed worlds, your other position was removed"));
      }
      // Position changed?
      if (!(location.getExtent().equals(this.world) && location.getBlockPosition().equals(this.pos1))) {
        this.world = location.getExtent();
        this.pos1 = location.getBlockPosition();
        src.sendMessage(Format.info("Position 1 set " + this.pos1.toString()));
      }
    }

    public void setPos2(Location<World> location, CommandSource src) {
      if (this.world != null && !this.world.equals(location.getExtent())) {
        this.pos1 = null;
        src.sendMessage(Format.info("Your selection has changed worlds, your other position was removed"));
      }
      // Position changed?
      if (!(location.getExtent().equals(this.world) && location.getBlockPosition().equals(this.pos2))) {
        this.world = location.getExtent();
        this.pos2 = location.getBlockPosition();
        src.sendMessage(Format.info("Position 2 set " + this.pos2.toString()));
      }
    }

    public boolean isComplete() {
      return this.pos1 != null
          && this.pos2 != null;
    }

    public Vector3i getPos1() {
      return pos1;
    }

    public Vector3i getPos2() {
      return pos2;
    }

    public World getWorld() {
      return world;
    }
  }
}