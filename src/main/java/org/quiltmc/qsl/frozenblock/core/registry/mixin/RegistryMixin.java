/*
 * Copyright 2022 QuiltMC
 * Modified to work on Fabric
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.frozenblock.core.registry.mixin;

import java.util.List;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.mixin.registry.sync.DebugChunkGeneratorAccessor;
import net.frozenblock.lib.events.api.FrozenEvents;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import org.quiltmc.qsl.frozenblock.core.registry.api.RegistryEvents;
import org.quiltmc.qsl.frozenblock.core.registry.impl.event.RegistryEventStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Registry.class)
public abstract class RegistryMixin<V> implements RegistryEventStorage<V> {
	@Unique
	private final Event<RegistryEvents.EntryAdded<V>> frozenLibQuilt$entryAddedEvent = FrozenEvents.createEnvironmentEvent(RegistryEvents.EntryAdded.class,
			callbacks -> context -> {
				for (var callback : callbacks) {
					callback.onAdded(context);
				}
			});

	@Override
	public Event<RegistryEvents.EntryAdded<V>> quilt$getEntryAddedEvent() {
		return this.frozenLibQuilt$entryAddedEvent;
	}

	@Inject(method = "freezeBuiltins", at = @At("RETURN"))
	private static void onFreezeBuiltins(CallbackInfo ci) {
		//region Fix MC-197259
		final List<BlockState> states = Registry.BLOCK.stream()
				.flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
				.toList();

		final int xLength = Mth.ceil(Mth.sqrt(states.size()));
		final int zLength = Mth.ceil(states.size() / (float) xLength);

		DebugChunkGeneratorAccessor.setBLOCK_STATES(states);
		DebugChunkGeneratorAccessor.setX_SIDE_LENGTH(xLength);
		DebugChunkGeneratorAccessor.setZ_SIDE_LENGTH(zLength);
		//endregion
	}
}
