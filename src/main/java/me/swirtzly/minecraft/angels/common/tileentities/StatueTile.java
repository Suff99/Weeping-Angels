package me.swirtzly.minecraft.angels.common.tileentities;

import me.swirtzly.minecraft.angels.client.poses.WeepingAngelPose;
import me.swirtzly.minecraft.angels.common.WAObjects;
import me.swirtzly.minecraft.angels.common.entities.AngelEnums;
import me.swirtzly.minecraft.angels.common.entities.AngelEnums.AngelType;
import me.swirtzly.minecraft.angels.common.entities.WeepingAngelEntity;
import me.swirtzly.minecraft.angels.common.misc.WAConstants;
import me.swirtzly.minecraft.angels.config.WAConfig;
import me.swirtzly.minecraft.angels.utils.AngelUtils;
import me.swirtzly.minecraft.angels.utils.NBTPatcher;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Random;

public class StatueTile extends TileEntity implements ITickableTileEntity {

    private int rotation = 0;
    private String type = AngelEnums.AngelType.ANGELA_MC.name();
    private WeepingAngelPose pose = WeepingAngelPose.getRandomPose(AngelUtils.RAND);
    private WeepingAngelEntity.AngelVariants angelVariants = WeepingAngelEntity.AngelVariants.NORMAL;


    public StatueTile() {
        super(WAObjects.Tiles.STATUE.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NBTPatcher.angelaToVillager(compound, "model");
        setPose(WeepingAngelPose.getPose(compound.getString("pose")));
        rotation = compound.getInt("rotation");
        type = compound.getString("model");
        if (compound.contains(WAConstants.VARIENT)) {
            setAngelVarients(WeepingAngelEntity.AngelVariants.valueOf(compound.getString(WAConstants.VARIENT)));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("rotation", rotation);
        compound.putString("model", type);
        compound.putString("pose", pose.name());
        compound.putString(WAConstants.VARIENT, angelVariants.name());
        return compound;
    }

    public AngelEnums.AngelType getAngelType() {
        return AngelEnums.AngelType.valueOf(type.isEmpty() ? AngelType.ANGELA_MC.name() : type);
    }

    public void setAngelType(String type) {
        this.type = type;
    }

    public void setAngelType(AngelEnums.AngelType type) {
        this.type = type.name();
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        sendUpdates();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 3, getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.read(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().grow(8, 8, 8);
    }

    public void sendUpdates() {
        world.updateComparatorOutputLevel(pos, getBlockState().getBlock());
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        markDirty();
    }

    @Override
    public void tick() {
        if (world.isRemote) return;

        if (WAConfig.CONFIG.spawnFromBlocks.get() && world.getRedstonePowerFromNeighbors(pos) > 0 && world.getTileEntity(pos) instanceof StatueTile) {
            WeepingAngelEntity angel = new WeepingAngelEntity(world);
            angel.setVarient(angelVariants);
            angel.setType(type);
            angel.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0, 0);
            angel.setPose(getPose());
            world.addEntity(angel);
            world.removeBlock(pos, false);
            sendUpdates();
        }
    }

    public WeepingAngelPose getPose() {
        return pose;
    }

    public void setPose(WeepingAngelPose pose) {
        this.pose = pose;
    }

    public WeepingAngelEntity.AngelVariants getAngelVarients() {
        return angelVariants;
    }

    public void setAngelVarients(WeepingAngelEntity.AngelVariants angelVariants) {
        this.angelVariants = angelVariants;
    }

    @Override
    public void onLoad() {
        if(getPose() == null){
            setPose(WeepingAngelPose.HIDING);
            markDirty();
        }
    }
}
