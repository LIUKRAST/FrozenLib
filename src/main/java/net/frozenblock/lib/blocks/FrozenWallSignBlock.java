/*
 * Copyright 2022 FrozenBlock
 * This file is part of FrozenLib.
 *
 * FrozenLib is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * FrozenLib is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with FrozenLib. If not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.lib.blocks;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FrozenWallSignBlock extends WallSignBlock {
    public final ResourceLocation lootTable;

    public FrozenWallSignBlock(Properties settings, WoodType signType, ResourceLocation lootTable) {
        super(settings, signType);
        this.lootTable = lootTable;
    }

    @Override
    public ResourceLocation getLootTable() {
        if (!Objects.equals(this.drops, this.lootTable)) {
            this.drops = this.lootTable;
        }

        return this.drops;
    }
}
