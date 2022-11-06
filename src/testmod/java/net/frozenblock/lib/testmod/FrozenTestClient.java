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

package net.frozenblock.lib.testmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.impl.NewPanoramas;
import net.frozenblock.lib.impl.NewSplahes;
import net.frozenblock.lib.sound.api.FlyBySoundHub;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public final class FrozenTestClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FlyBySoundHub.AUTO_ENTITIES_AND_SOUNDS.put(EntityType.ARROW, new FlyBySoundHub.FlyBySound(1.0F, 1.0F, SoundSource.NEUTRAL, SoundEvents.AXE_SCRAPE));
		NewSplahes.addNewSplashLocation(new ResourceLocation("frozenlib", "texts/splashes.txt"));
		NewPanoramas.addNewPanorama(new ResourceLocation("frozenlib", "textures/gui/title/background/panorama"));
    }
}
