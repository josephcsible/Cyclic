package com.lothrazar.cyclicmagic.component.dropper;
import com.lothrazar.cyclicmagic.block.base.TileEntityBaseMachineInvo;
import com.lothrazar.cyclicmagic.gui.ITileRedstoneToggle;
import com.lothrazar.cyclicmagic.util.UtilItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityDropperExact extends TileEntityBaseMachineInvo implements ITileRedstoneToggle, ITickable {
  private int needsRedstone = 1;
  private int slotCurrent = 0;
  private int dropCount = 1;
  private int delay = 10;
  private int hOffset = 0;
  public static enum Fields {
    TIMER, REDSTONE, DROPCOUNT, DELAY, OFFSET;
  }
  public TileEntityDropperExact() {
    super(9);
    timer = delay;
  }
  @Override
  public void update() {
    if (this.isRunning() == false) {
      return;
    }
    if (this.updateTimerIsZero()) {
      ItemStack dropMe = this.getStackInSlot(slotCurrent).copy();
      if (dropMe.isEmpty() == false) {
        timer = delay;
        BlockPos target = this.getCurrentFacingPos().offset(this.getCurrentFacing(), hOffset);
        int amtDrop = Math.min(this.dropCount, dropMe.getCount());
        dropMe.setCount(amtDrop);
        UtilItemStack.dropItemStackMotionless(world, target, dropMe);
        this.decrStackSize(slotCurrent, amtDrop);
      }
    }
  }
  @Override
  public void readFromNBT(NBTTagCompound tagCompound) {
    super.readFromNBT(tagCompound);
    this.needsRedstone = tagCompound.getInteger(NBT_REDST);
    this.timer = tagCompound.getInteger(NBT_TIMER);
    this.delay = tagCompound.getInteger("delay");
    this.dropCount = tagCompound.getInteger("dropCount");
    this.hOffset = tagCompound.getInteger("hOffset");
  }
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
    tagCompound.setInteger(NBT_TIMER, timer);
    tagCompound.setInteger(NBT_REDST, this.needsRedstone);
    tagCompound.setInteger("delay", this.delay);
    tagCompound.setInteger("dropCount", this.dropCount);
    tagCompound.setInteger("hOffset", this.hOffset);
    return super.writeToNBT(tagCompound);
  }
  @Override
  public void toggleNeedsRedstone() {
    int val = (this.needsRedstone + 1) % 2;
    this.setField(Fields.REDSTONE.ordinal(), val);
  }
  @Override
  public boolean onlyRunIfPowered() {
    return this.needsRedstone == 1;
  }
  @Override
  public int getField(int id) {
    switch (Fields.values()[id]) {
      case TIMER:
        return timer;
      case REDSTONE:
        return this.needsRedstone;
      case DELAY:
        return this.delay;
      case DROPCOUNT:
        return this.dropCount;
      case OFFSET:
        return this.hOffset;
    }
    return -1;
  }
  @Override
  public void setField(int id, int value) {
    switch (Fields.values()[id]) {
      case TIMER:
        this.timer = value;
      break;
      case REDSTONE:
        this.needsRedstone = value;
      break;
      case DELAY:
        delay = Math.max(0, value);
      break;
      case DROPCOUNT:
        dropCount = Math.max(1, value);
      break;
      case OFFSET:
        hOffset = Math.max(0, value);
      break;
    }
  }
  @Override
  public int[] getFieldOrdinals() {
    return super.getFieldArray(getFieldCount());
  }
  @Override
  public int getFieldCount() {
    return Fields.values().length;
  }
}
