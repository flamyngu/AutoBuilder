package com.flamyngu.autobuilder.autobuilder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BuilderBlock {
    ActionResult onUse(net.minecraft.block.BlockState state, World world, BlockPos pos,
                       PlayerEntity player, Hand hand, BlockHitResult hit);
}
