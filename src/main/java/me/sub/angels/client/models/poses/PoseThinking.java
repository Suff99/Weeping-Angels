package me.sub.angels.client.models.poses;

import me.sub.angels.common.entities.EntityWeepingAngel;
import net.minecraft.client.model.Cuboid;

public class PoseThinking extends PoseBase {

    public PoseThinking(EntityWeepingAngel entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netheadYaw, float headPitch, float swingProgress) {
		super(entity, limbSwing, limbSwingAmount, ageInTicks, netheadYaw, headPitch, swingProgress);
	}
	
	public PoseThinking() {}
	
	@Override
	public void setArmAngles(Cuboid left_arm, Cuboid right_arm, Cuboid wrist_left, Cuboid wrist_right) {
		left_arm.pitch = 0;
		left_arm.yaw = 0.2F;
		left_arm.roll = -0.3F;
		wrist_left.pitch = -0.4F;
		right_arm.pitch = -1.3F;
		right_arm.yaw = -0.9F;
		wrist_right.pitch = -0.9F;
	}
	
	@Override
    public boolean angryFace() {
		return false;
	}

    @Override
    public void setBodyAngles(Cuboid body) {
    }
	
	@Override
	public void setWingAngles(Cuboid left_wing, Cuboid right_wing) {}
	
	@Override
    public void setHeadAngles(Cuboid head) {
        head.pitch = 0;
        head.yaw = 0;
        head.roll = 0;
    }
	
}