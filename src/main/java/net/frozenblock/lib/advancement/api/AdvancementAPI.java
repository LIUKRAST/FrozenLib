package net.frozenblock.lib.advancement.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.commands.CommandFunction;
import net.minecraft.resources.ResourceLocation;

public final class AdvancementAPI {
	private AdvancementAPI() {}

	/**
	 * Makes a copy of {@link AdvancementRewards#EMPTY} for use in the Advancement API
	 * <p>
	 * Use only when needed, as this will increase memory usage
	 */
	public static void setupRewards(Advancement advancement) {
		if (advancement.rewards == AdvancementRewards.EMPTY) {
			advancement.rewards = new AdvancementRewards(0, new ResourceLocation[0], new ResourceLocation[0], new CommandFunction.CacheableFunction((ResourceLocation) null));
		}
	}

	/**
	 * Makes a copy of {@link AdvancementRequirements#EMPTY} for use in the Advancement API
	 * <p>
	 * Use only when needed, as this will increase memory usage
	 */
	public static void setupRequirements(Advancement advancement) {
		if (advancement.requirements == AdvancementRequirements.EMPTY) {
			advancement.requirements = new AdvancementRequirements(new String[0][]);
		}
	}

	public static void addCriteria(Advancement advancement, String key, Criterion<?> criterion) {
		advancement.criteria().putIfAbsent(key, criterion);
	}

	public static void addRequirements(Advancement advancement, AdvancementRequirements requirements) {
		setupRequirements(advancement);
		List<String[]> list = new ArrayList<>();
		list.addAll(Arrays.stream(advancement.requirements().requirements).toList());
		list.addAll(Arrays.stream(requirements.requirements).toList());
		advancement.requirements().requirements = list.toArray(new String[][]{});
	}

	public static void addLootTables(Advancement advancement, List<ResourceLocation> lootTables) {
		setupRewards(advancement);
		AdvancementRewards rewards = advancement.rewards();
		List<ResourceLocation> newLoot = new ArrayList<>(Arrays.stream(rewards.loot).toList());
		newLoot.addAll(lootTables);
		rewards.loot = newLoot.toArray(new ResourceLocation[]{});
	}

	public static void addRecipes(Advancement advancement, List<ResourceLocation> recipes) {
		AdvancementRewards rewards = advancement.rewards();
		List<ResourceLocation> newLoot = new ArrayList<>(Arrays.stream(rewards.recipes).toList());
		newLoot.addAll(recipes);
		rewards.recipes = newLoot.toArray(new ResourceLocation[]{});
	}
}
