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

package net.frozenblock.lib.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.frozenblock.lib.FrozenMain;
import net.frozenblock.lib.entity.render.EntityTextureOverride;
import net.frozenblock.lib.sound.SoundPredicate.SoundPredicate;
import net.minecraft.core.MappedRegistry;
import net.minecraft.sounds.SoundEvent;

public class FrozenRegistry {

    public static final MappedRegistry<SoundEvent> STARTING_SOUND = FabricRegistryBuilder.createSimple(SoundEvent.class, FrozenMain.id("starting_sound"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static final MappedRegistry<EntityTextureOverride> ENTITY_TEXTURE_OVERRIDE = FabricRegistryBuilder.createSimple(EntityTextureOverride.class, FrozenMain.id("entity_texture_override"))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

	public static final MappedRegistry<SoundPredicate> SOUND_PREDICATE_SYNCED = FabricRegistryBuilder.createSimple(SoundPredicate.class, FrozenMain.id("sound_predicate_synced"))
			.attribute(RegistryAttribute.SYNCED)
			.buildAndRegister();

	public static final MappedRegistry<SoundPredicate> SOUND_PREDICATE = FabricRegistryBuilder.createSimple(SoundPredicate.class, FrozenMain.id("sound_predicate"))
			.buildAndRegister();

    public static void initRegistry() {

    }
}
