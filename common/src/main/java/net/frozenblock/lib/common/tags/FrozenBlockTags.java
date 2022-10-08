package net.frozenblock.lib.common.tags;

import dev.architectury.platform.Platform;
import net.frozenblock.lib.common.FrozenMain;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class FrozenBlockTags {
    public static final TagKey<Block> DRIPSTONE_CAN_DRIP_ON = of(Platform.isDevelopmentEnvironment() ? "dripstone_can_drip_testing" : "dripstone_can_drip");

    private FrozenBlockTags() {
    }

    private static TagKey<Block> of(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, FrozenMain.id(path));
    }
}
