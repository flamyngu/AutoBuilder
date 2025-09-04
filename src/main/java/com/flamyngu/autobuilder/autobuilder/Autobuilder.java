package com.flamyngu.autobuilder.autobuilder;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Blocks;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class Autobuilder implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("build")
                            .then(CommandManager.argument("type", StringArgumentType.word())
                                    .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                                            .executes(context -> {
                                                String type = StringArgumentType.getString(context, "type");
                                                BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
                                                ServerWorld world = context.getSource().getWorld();

                                                switch (type.toLowerCase()) {
                                                    case "house" -> buildHouse(world, pos);
                                                    case "tower" -> buildTower(world, pos);
                                                    case "hut" -> buildHut(world, pos);
                                                    default -> context.getSource().sendFeedback(
                                                            () -> net.minecraft.text.Text.literal("Unknown structure type!"), false
                                                    );
                                                }
                                                return 1;
                                            })
                                    )
                            )
            );
        });
    }

    private void buildHouse(ServerWorld world, BlockPos base) {
        // Einfaches 5x5 Haus
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 4; y++)
                for (int z = 0; z < 5; z++)
                    if (x == 0 || x == 4 || z == 0 || z == 4 || y == 0)
                        world.setBlockState(base.add(x, y, z), Blocks.OAK_PLANKS.getDefaultState());

        // Dach
        for (int x = -1; x <= 5; x++)
            for (int z = -1; z <= 5; z++)
                world.setBlockState(base.add(x, 4, z), Blocks.OAK_SLAB.getDefaultState());

        // Tür
        world.setBlockState(base.add(2,1,0), Blocks.AIR.getDefaultState());
        world.setBlockState(base.add(2,0,0), Blocks.AIR.getDefaultState());
    }

    private void buildTower(ServerWorld world, BlockPos base) {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 8; y++)
                for (int z = 0; z < 3; z++)
                    if (x == 0 || x == 2 || z == 0 || z == 2 || y == 0)
                        world.setBlockState(base.add(x, y, z), Blocks.STONE_BRICKS.getDefaultState());

        // Eingang
        world.setBlockState(base.add(1,1,0), Blocks.AIR.getDefaultState());
        world.setBlockState(base.add(1,2,0), Blocks.AIR.getDefaultState());
    }

    private void buildHut(ServerWorld world, BlockPos base) {
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 4; z++)
                    if (x==0 || x==3 || z==0 || z==3 || y==0)
                        world.setBlockState(base.add(x,y,z), Blocks.BIRCH_PLANKS.getDefaultState());

        // Dach
        for (int x = -1; x <= 4; x++)
            for (int z = -1; z <= 4; z++)
                world.setBlockState(base.add(x,3,z), Blocks.BIRCH_SLAB.getDefaultState());

        // Tür
        world.setBlockState(base.add(2,1,0), Blocks.AIR.getDefaultState());
        world.setBlockState(base.add(2,0,0), Blocks.AIR.getDefaultState());
    }
}
