/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of FrozenLib.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.lib.sound.api.networking;

import net.frozenblock.lib.FrozenSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

public record LocalPlayerSoundPacket(Holder<SoundEvent> sound, float volume, float pitch) implements CustomPacketPayload {
	public static final Type<LocalPlayerSoundPacket> PACKET_TYPE = CustomPacketPayload.createType(
		FrozenSharedConstants.string("local_player_sound_packet")
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, LocalPlayerSoundPacket> CODEC = StreamCodec.ofMember(LocalPlayerSoundPacket::write, LocalPlayerSoundPacket::new);

	public LocalPlayerSoundPacket(@NotNull RegistryFriendlyByteBuf buf) {
		this(
			ByteBufCodecs.holderRegistry(Registries.SOUND_EVENT).decode(buf),
			buf.readFloat(),
			buf.readFloat()
		);
	}

	public void write(@NotNull RegistryFriendlyByteBuf buf) {
		ByteBufCodecs.holderRegistry(Registries.SOUND_EVENT).encode(buf, this.sound);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
	}

	@Override
	@NotNull
	public Type<?> type() {
		return PACKET_TYPE;
	}
}
