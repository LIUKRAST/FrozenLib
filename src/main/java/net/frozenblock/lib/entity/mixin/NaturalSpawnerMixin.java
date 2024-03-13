package net.frozenblock.lib.entity.mixin;

import net.frozenblock.lib.entity.api.FrozenSpawnPlacementTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

	@Inject(
		method = "getTopNonCollidingPos",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;immutable()Lnet/minecraft/core/BlockPos;",
			shift = At.Shift.BEFORE
		),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void frozenLib$getTopNonCollidingPos(
		LevelReader level, EntityType<?> entityType, int x, int z, CallbackInfoReturnable<BlockPos> info,
		int i, BlockPos.MutableBlockPos mutableBlockPos, BlockPos blockPos
	) {
		if (SpawnPlacements.getPlacementType(entityType) == FrozenSpawnPlacementTypes.ON_GROUND_OR_ON_LAVA_SURFACE) {
			BlockPos belowPos = mutableBlockPos.below();
			if (level.getBlockState(belowPos).isPathfindable(level, belowPos, PathComputationType.LAND)) {
				info.setReturnValue(belowPos);
			}
		}
	}

	@Inject(
		method = "isSpawnPositionOk",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;",
			shift = At.Shift.AFTER
		),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void frozenLib$isSpawnPositionOk(
		SpawnPlacements.Type placeType, LevelReader level, BlockPos pos, EntityType<?> entityType, CallbackInfoReturnable<Boolean> info,
		BlockState blockState, FluidState fluidState, BlockPos blockPos
	) {
		BlockPos blockPos2 = pos.below();
		if (SpawnPlacements.getPlacementType(entityType) == FrozenSpawnPlacementTypes.ON_GROUND_OR_ON_LAVA_SURFACE) {
			BlockState belowState = level.getBlockState(blockPos2);
			if (!belowState.isValidSpawn(level, blockPos2, entityType) && !belowState.getFluidState().is(FluidTags.LAVA)) {
				info.setReturnValue(false);
			} else {
				info.setReturnValue(frozenLib$isValidEmptySpawnBlock(level, blockPos, entityType) && frozenLib$isValidEmptySpawnBlock(level, blockPos, entityType));
			}
			info.setReturnValue(false);
		}
	}

	@Unique
	private static boolean frozenLib$isValidEmptySpawnBlock(@NotNull LevelReader levelReader, BlockPos blockPos, EntityType<?> entityType) {
		BlockState blockState = levelReader.getBlockState(blockPos);
		boolean isSafeBurning = blockState.is(BlockTags.FIRE) || blockState.is(Blocks.LAVA);
		return isSafeBurning || NaturalSpawner.isValidEmptySpawnBlock(levelReader, blockPos, blockState, blockState.getFluidState(), entityType);
	}

}
