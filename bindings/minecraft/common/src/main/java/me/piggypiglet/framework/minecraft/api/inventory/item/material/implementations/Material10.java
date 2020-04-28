/*
 * MIT License
 *
 * Copyright (c) 2019-2020 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.minecraft.api.inventory.item.material.implementations;

import me.piggypiglet.framework.minecraft.api.inventory.item.material.MaterialName;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialEnum;
import me.piggypiglet.framework.minecraft.api.inventory.item.material.framework.MaterialVersion;
import me.piggypiglet.framework.minecraft.api.versions.Versions;
import org.jetbrains.annotations.Nullable;

@MaterialVersion(Versions.V1_10)
public enum Material10 implements MaterialEnum {
    ACACIA_BOAT(447, 0, MaterialName.ACACIA_BOAT),
    ACACIA_DOOR(430, 0, MaterialName.ACACIA_DOOR),
    ACACIA_FENCE(192, 0, MaterialName.ACACIA_FENCE),
    ACACIA_FENCE_GATE(187, 0, MaterialName.ACACIA_FENCE_GATE),
    ACACIA_WOOD_STAIRS(163, 0, MaterialName.ACACIA_STAIRS),
    ACTIVATOR_RAILS(157, 0, MaterialName.ACTIVATOR_RAIL),
    AIR(0, 0, MaterialName.AIR),
    ANVIL(145, 0, MaterialName.ANVIL),
    SLIGHTLY_DAMAGED_ANVIL(145, 1, MaterialName.CHIPPED_ANVIL),
    VERY_DAMAGED_ANVIL(145, 2, MaterialName.DAMAGED_ANVIL),
    APPLE(260, 0, MaterialName.APPLE),
    ARMOR_STAND(416, 0, MaterialName.ARMOR_STAND),
    ARROW(262, 0, MaterialName.ARROW),
    BAKED_POTATO(393, 0, MaterialName.BAKED_POTATO),
    BLACK_BANNER(425, 0, MaterialName.BLACK_BANNER),
    RED_BANNER(425, 1, MaterialName.RED_BANNER),
    GREEN_BANNER(425, 2, MaterialName.GREEN_BANNER),
    BROWN_BANNER(425, 3, MaterialName.BROWN_BANNER),
    BLUE_BANNER(425, 4, MaterialName.BLUE_BANNER),
    PURPLE_BANNER(425, 5, MaterialName.PURPLE_BANNER),
    CYAN_BANNER(425, 6, MaterialName.CYAN_BANNER),
    LIGHT_GRAY_BANNER(425, 7, MaterialName.LIGHT_GRAY_BANNER),
    GRAY_BANNER(425, 8, MaterialName.GRAY_BANNER),
    PINK_BANNER(425, 9, MaterialName.PINK_BANNER),
    LIME_BANNER(425, 10, MaterialName.LIME_BANNER),
    YELLOW_BANNER(425, 11, MaterialName.YELLOW_BANNER),
    LIGHT_BLUE_BANNER(425, 12, MaterialName.LIGHT_BLUE_BANNER),
    MAGENTA_BANNER(425, 13, MaterialName.MAGENTA_BANNER),
    ORANGE_BANNER(425, 14, MaterialName.ORANGE_BANNER),
    WHITE_BANNER(425, 15, MaterialName.WHITE_BANNER),
    BARRIER(166, 0, MaterialName.BARRIER),
    BEACON(138, 0, MaterialName.BEACON),
    RED_BED(355, 0, MaterialName.RED_BED),
    BEDROCK(7, 0, MaterialName.BEDROCK),
    RAW_BEEF(363, 0, MaterialName.BEEF),
    BEETROOT(434, 0, MaterialName.BEETROOT),
    BEETROOT_SEEDS(435, 0, MaterialName.BEETROOT_SEEDS),
    BEETROOT_SOUP(436, 0, MaterialName.BEETROOT_SOUP),
    BIRCH_BOAT(445, 0, MaterialName.BIRCH_BOAT),
    BIRCH_DOOR(428, 0, MaterialName.BIRCH_DOOR),
    BIRCH_FENCE(189, 0, MaterialName.BIRCH_FENCE),
    BIRCH_FENCE_GATE(184, 0, MaterialName.BIRCH_FENCE_GATE),
    BIRCH_WOOD_STAIRS(135, 0, MaterialName.BIRCH_STAIRS),
    BLAZE_POWDER(377, 0, MaterialName.BLAZE_POWDER),
    BLAZE_ROD(369, 0, MaterialName.BLAZE_ROD),
    OAK_BOAT(333, 0, MaterialName.OAK_BOAT),
    BONE(352, 0, MaterialName.BONE),
    BONE_BLOCK(216, 0, MaterialName.BONE_BLOCK),
    BOOK(340, 0, MaterialName.BOOK),
    BOOKSHELF(47, 0, MaterialName.BOOKSHELF),
    BOW(261, 0, MaterialName.BOW),
    BOWL(281, 0, MaterialName.BOWL),
    BREAD(297, 0, MaterialName.BREAD),
    BREWING_STAND(379, 0, MaterialName.BREWING_STAND),
    BRICK(336, 0, MaterialName.BRICK),
    BRICKS(45, 0, MaterialName.BRICKS),
    BRICK_STAIRS(108, 0, MaterialName.BRICK_STAIRS),
    BROWN_MUSHROOM(39, 0, MaterialName.BROWN_MUSHROOM),
    BROWN_MUSHROOM_BLOCK(99, 0, MaterialName.BROWN_MUSHROOM_BLOCK),
    BUCKET(325, 0, MaterialName.BUCKET),
    CACTUS(81, 0, MaterialName.CACTUS),
    CAKE(354, 0, MaterialName.CAKE),
    WHITE_CARPET(171, 0, MaterialName.WHITE_CARPET),
    ORANGE_CARPET(171, 1, MaterialName.ORANGE_CARPET),
    MAGENTA_CARPET(171, 2, MaterialName.MAGENTA_CARPET),
    LIGHT_BLUE_CARPET(171, 3, MaterialName.LIGHT_BLUE_CARPET),
    YELLOW_CARPET(171, 4, MaterialName.YELLOW_CARPET),
    LIME_CARPET(171, 5, MaterialName.LIME_CARPET),
    PINK_CARPET(171, 6, MaterialName.PINK_CARPET),
    GRAY_CARPET(171, 7, MaterialName.GRAY_CARPET),
    LIGHT_GRAY_CARPET(171, 8, MaterialName.LIGHT_GRAY_CARPET),
    CYAN_CARPET(171, 9, MaterialName.CYAN_CARPET),
    PURPLE_CARPET(171, 10, MaterialName.PURPLE_CARPET),
    BLUE_CARPET(171, 11, MaterialName.BLUE_CARPET),
    BROWN_CARPET(171, 12, MaterialName.BROWN_CARPET),
    GREEN_CARPET(171, 13, MaterialName.GREEN_CARPET),
    RED_CARPET(171, 14, MaterialName.RED_CARPET),
    BLACK_CARPET(171, 15, MaterialName.BLACK_CARPET),
    CARROT(391, 0, MaterialName.CARROT),
    CARROT_ON_A_STICK(398, 0, MaterialName.CARROT_ON_A_STICK),
    CAULDRON(380, 0, MaterialName.CAULDRON),
    CHAIN_COMMAND_BLOCK(211, 0, MaterialName.CHAIN_COMMAND_BLOCK),
    CHAIN_BOOTS(305, 0, MaterialName.CHAINMAIL_BOOTS),
    CHAIN_CHESTPLATE(303, 0, MaterialName.CHAINMAIL_CHESTPLATE),
    CHAIN_HELMET(302, 0, MaterialName.CHAINMAIL_HELMET),
    CHAIN_LEGGINGS(304, 0, MaterialName.CHAINMAIL_LEGGINGS),
    CHEST(54, 0, MaterialName.CHEST),
    MINECART_WITH_CHEST(342, 0, MaterialName.CHEST_MINECART),
    RAW_CHICKEN(365, 0, MaterialName.CHICKEN),
    CHORUS_FLOWER(200, 0, MaterialName.CHORUS_FLOWER),
    CHORUS_FRUIT(432, 0, MaterialName.CHORUS_FRUIT),
    POPPED_CHORUS_FRUIT(433, 0, MaterialName.POPPED_CHORUS_FRUIT),
    CHORUS_PLANT(199, 0, MaterialName.CHORUS_PLANT),
    CLAY_BLOCK(82, 0, MaterialName.CLAY),
    CLAY(337, 0, MaterialName.CLAY_BALL),
    CLOCK(347, 0, MaterialName.CLOCK),
    COAL(263, 0, MaterialName.COAL),
    CHARCOAL(263, 1, MaterialName.CHARCOAL),
    COAL_BLOCK(173, 0, MaterialName.COAL_BLOCK),
    COAL_ORE(16, 0, MaterialName.COAL_ORE),
    COBBLESTONE(4, 0, MaterialName.COBBLESTONE),
    COBBLESTONE_WALL(139, 0, MaterialName.COBBLESTONE_WALL),
    MOSSY_COBBLESTONE_WALL(139, 1, MaterialName.MOSSY_COBBLESTONE_WALL),
    COMMAND_BLOCK(137, 0, MaterialName.COMMAND_BLOCK),
    MINECART_WITH_COMMAND_BLOCK(422, 0, MaterialName.COMMAND_BLOCK_MINECART),
    REDSTONE_COMPARATOR(404, 0, MaterialName.COMPARATOR),
    COMPASS(345, 0, MaterialName.COMPASS),
    STEAK(364, 0, MaterialName.COOKED_BEEF),
    COOKED_CHICKEN(366, 0, MaterialName.COOKED_CHICKEN),
    COOKED_FISH(350, 0, null),
    COOKED_SALMON(350, 1, MaterialName.COOKED_SALMON),
    COOKED_MUTTON(424, 0, MaterialName.COOKED_MUTTON),
    COOKED_PORKCHOP(320, 0, MaterialName.COOKED_PORKCHOP),
    COOKED_RABBIT(412, 0, MaterialName.COOKED_RABBIT),
    COOKIE(357, 0, MaterialName.COOKIE),
    CRAFTING_TABLE(58, 0, MaterialName.CRAFTING_TABLE),
    DARK_OAK_BOAT(448, 0, MaterialName.DARK_OAK_BOAT),
    DARK_OAK_DOOR(431, 0, MaterialName.DARK_OAK_DOOR),
    DARK_OAK_FENCE(191, 0, MaterialName.DARK_OAK_FENCE),
    DARK_OAK_FENCE_GATE(186, 0, MaterialName.DARK_OAK_FENCE_GATE),
    DARK_OAK_WOOD_STAIRS(164, 0, MaterialName.DARK_OAK_STAIRS),
    DAYLIGHT_SENSOR(151, 0, MaterialName.DAYLIGHT_DETECTOR),
    DEAD_BUSH(32, 0, MaterialName.DEAD_BUSH),
    DETECTOR_RAILS(28, 0, MaterialName.DETECTOR_RAIL),
    DIAMOND(264, 0, MaterialName.DIAMOND),
    DIAMOND_AXE(279, 0, MaterialName.DIAMOND_AXE),
    BLOCK_OF_DIAMOND(57, 0, MaterialName.DIAMOND_BLOCK),
    DIAMOND_BOOTS(313, 0, MaterialName.DIAMOND_BOOTS),
    DIAMOND_CHESTPLATE(311, 0, MaterialName.DIAMOND_CHESTPLATE),
    DIAMOND_HELMET(310, 0, MaterialName.DIAMOND_HELMET),
    DIAMOND_HOE(293, 0, MaterialName.DIAMOND_HOE),
    DIAMOND_HORSE_ARMOR(419, 0, MaterialName.DIAMOND_HORSE_ARMOR),
    DIAMOND_LEGGINGS(312, 0, MaterialName.DIAMOND_LEGGINGS),
    DIAMOND_ORE(56, 0, MaterialName.DIAMOND_ORE),
    DIAMOND_PICKAXE(278, 0, MaterialName.DIAMOND_PICKAXE),
    DIAMOND_SHOVEL(277, 0, MaterialName.DIAMOND_SHOVEL),
    DIAMOND_SWORD(276, 0, MaterialName.DIAMOND_SWORD),
    DIRT(3, 0, MaterialName.DIRT),
    COARSE_DIRT(3, 1, MaterialName.COARSE_DIRT),
    PODZOL(3, 2, MaterialName.PODZOL),
    DISPENSER(23, 0, MaterialName.DISPENSER),
    SUNFLOWER(175, 0, MaterialName.SUNFLOWER),
    LILAC(175, 1, MaterialName.LILAC),
    DOUBLE_TALLGRASS(175, 2, MaterialName.TALL_GRASS),
    LARGE_FERN(175, 3, MaterialName.LARGE_FERN),
    ROSE_BUSH(175, 4, MaterialName.ROSE_BUSH),
    PEONY(175, 5, MaterialName.PEONY),
    DRAGONS_BREATH(437, 0, MaterialName.DRAGON_BREATH),
    DRAGON_EGG(122, 0, MaterialName.DRAGON_EGG),
    DROPPER(158, 0, MaterialName.DROPPER),
    INK_SAC(351, 0, MaterialName.INK_SAC),
    RED_DYE(351, 1, MaterialName.RED_DYE),
    GREEN_DYE(351, 2, MaterialName.GREEN_DYE),
    COCOA_BEANS(351, 3, MaterialName.COCOA_BEANS),
    LAPIS_LAZULI(351, 4, MaterialName.LAPIS_LAZULI),
    PURPLE_DYE(351, 5, MaterialName.PURPLE_DYE),
    CYAN_DYE(351, 6, MaterialName.CYAN_DYE),
    LIGHT_GRAY_DYE(351, 7, MaterialName.LIGHT_GRAY_DYE),
    GRAY_DYE(351, 8, MaterialName.GRAY_DYE),
    PINK_DYE(351, 9, MaterialName.PINK_DYE),
    LIME_DYE(351, 10, MaterialName.LIME_DYE),
    YELLOW_DYE(351, 11, MaterialName.YELLOW_DYE),
    LIGHT_BLUE_DYE(351, 12, MaterialName.LIGHT_BLUE_DYE),
    MAGENTA_DYE(351, 13, MaterialName.MAGENTA_DYE),
    ORANGE_DYE(351, 14, MaterialName.ORANGE_DYE),
    BONE_MEAL(351, 15, MaterialName.BONE_MEAL),
    EGG(344, 0, MaterialName.EGG),
    ELYTRA(443, 0, MaterialName.ELYTRA),
    EMERALD(388, 0, MaterialName.EMERALD),
    BLOCK_OF_EMERALD(133, 0, MaterialName.EMERALD_BLOCK),
    EMERALD_ORE(129, 0, MaterialName.EMERALD_ORE),
    ENCHANTED_BOOK(403, 0, MaterialName.ENCHANTED_BOOK),
    ENCHANTING_TABLE(116, 0, MaterialName.ENCHANTING_TABLE),
    END_STONE_BRICKS(206, 0, MaterialName.END_STONE_BRICKS),
    END_CRYSTAL(426, 0, MaterialName.END_CRYSTAL),
    END_PORTAL_FRAME(120, 0, MaterialName.END_PORTAL_FRAME),
    END_ROD(198, 0, MaterialName.END_ROD),
    END_STONE(121, 0, MaterialName.END_STONE),
    ENDER_CHEST(130, 0, MaterialName.ENDER_CHEST),
    EYE_OF_ENDER(381, 0, MaterialName.ENDER_EYE),
    ENDER_PEARL(368, 0, MaterialName.ENDER_PEARL),
    BOTTLE_O_ENCHANTING(384, 0, MaterialName.EXPERIENCE_BOTTLE),
    FARMLAND(60, 0, MaterialName.FARMLAND),
    FEATHER(288, 0, MaterialName.FEATHER),
    OAK_FENCE(85, 0, MaterialName.OAK_FENCE),
    OAK_FENCE_GATE(107, 0, MaterialName.OAK_FENCE_GATE),
    FERMENTED_SPIDER_EYE(376, 0, MaterialName.FERMENTED_SPIDER_EYE),
    FILLED_MAP(358, 0, MaterialName.FILLED_MAP),
    FIRE_CHARGE(385, 0, MaterialName.FIRE_CHARGE),
    FIREWORK_STAR(402, 0, MaterialName.FIREWORK_STAR),
    FIREWORK_ROCKET(401, 0, MaterialName.FIREWORK_ROCKET),
    RAW_FISH(349, 0, null),
    RAW_SALMON(349, 1, MaterialName.SALMON),
    CLOWNFISH(349, 2, null),
    PUFFERFISH(349, 3, MaterialName.PUFFERFISH),
    FISHING_ROD(346, 0, MaterialName.FISHING_ROD),
    FLINT(318, 0, MaterialName.FLINT),
    FLINT_AND_STEEL(259, 0, MaterialName.FLINT_AND_STEEL),
    FLOWER_POT(390, 0, MaterialName.FLOWER_POT),
    FURNACE(61, 0, MaterialName.FURNACE),
    MINECART_WITH_FURNACE(343, 0, MaterialName.FURNACE_MINECART),
    GHAST_TEAR(370, 0, MaterialName.GHAST_TEAR),
    GLASS(20, 0, MaterialName.GLASS),
    GLASS_BOTTLE(374, 0, MaterialName.GLASS_BOTTLE),
    GLASS_PANE(102, 0, MaterialName.GLASS_PANE),
    GLOWSTONE(89, 0, MaterialName.GLOWSTONE),
    GLOWSTONE_DUST(348, 0, MaterialName.GLOWSTONE_DUST),
    BLOCK_OF_GOLD(41, 0, MaterialName.GOLD_BLOCK),
    GOLD_INGOT(266, 0, MaterialName.GOLD_INGOT),
    GOLD_NUGGET(371, 0, MaterialName.GOLD_NUGGET),
    GOLD_ORE(14, 0, MaterialName.GOLD_ORE),
    GOLDEN_APPLE(322, 0, MaterialName.GOLDEN_APPLE),
    ENCHANTED_GOLDEN_APPLE(322, 1, MaterialName.ENCHANTED_GOLDEN_APPLE),
    GOLDEN_AXE(286, 0, MaterialName.GOLDEN_AXE),
    GOLDEN_BOOTS(317, 0, MaterialName.GOLDEN_BOOTS),
    GOLDEN_CARROT(396, 0, MaterialName.GOLDEN_CARROT),
    GOLDEN_CHESTPLATE(315, 0, MaterialName.GOLDEN_CHESTPLATE),
    GOLDEN_HELMET(314, 0, MaterialName.GOLDEN_HELMET),
    GOLDEN_HOE(294, 0, MaterialName.GOLDEN_HOE),
    GOLD_HORSE_ARMOR(418, 0, MaterialName.GOLDEN_HORSE_ARMOR),
    GOLDEN_LEGGINGS(316, 0, MaterialName.GOLDEN_LEGGINGS),
    GOLDEN_PICKAXE(285, 0, MaterialName.GOLDEN_PICKAXE),
    POWERED_RAILS(27, 0, MaterialName.POWERED_RAIL),
    GOLDEN_SHOVEL(284, 0, MaterialName.GOLDEN_SHOVEL),
    GOLDEN_SWORD(283, 0, MaterialName.GOLDEN_SWORD),
    GRASS_BLOCK(2, 0, MaterialName.GRASS_BLOCK),
    GRASS_PATH(208, 0, MaterialName.GRASS_PATH),
    GRAVEL(13, 0, MaterialName.GRAVEL),
    GUNPOWDER(289, 0, MaterialName.GUNPOWDER),
    TERRACOTTA(172, 0, MaterialName.TERRACOTTA),
    HAY_BALE(170, 0, MaterialName.HAY_BLOCK),
    HEAVY_WEIGHTED_PRESSURE_PLATE(148, 0, MaterialName.HEAVY_WEIGHTED_PRESSURE_PLATE),
    HOPPER(154, 0, MaterialName.HOPPER),
    MINECART_WITH_HOPPER(408, 0, MaterialName.HOPPER_MINECART),
    ICE(79, 0, MaterialName.ICE),
    IRON_AXE(258, 0, MaterialName.IRON_AXE),
    IRON_BARS(101, 0, MaterialName.IRON_BARS),
    BLOCK_OF_IRON(42, 0, MaterialName.IRON_BLOCK),
    IRON_BOOTS(309, 0, MaterialName.IRON_BOOTS),
    IRON_CHESTPLATE(307, 0, MaterialName.IRON_CHESTPLATE),
    IRON_DOOR(330, 0, MaterialName.IRON_DOOR),
    IRON_HELMET(306, 0, MaterialName.IRON_HELMET),
    IRON_HOE(292, 0, MaterialName.IRON_HOE),
    IRON_HORSE_ARMOR(417, 0, MaterialName.IRON_HORSE_ARMOR),
    IRON_INGOT(265, 0, MaterialName.IRON_INGOT),
    IRON_LEGGINGS(308, 0, MaterialName.IRON_LEGGINGS),
    IRON_ORE(15, 0, MaterialName.IRON_ORE),
    IRON_PICKAXE(257, 0, MaterialName.IRON_PICKAXE),
    IRON_SHOVEL(256, 0, MaterialName.IRON_SHOVEL),
    IRON_SWORD(267, 0, MaterialName.IRON_SWORD),
    IRON_TRAPDOOR(167, 0, MaterialName.IRON_TRAPDOOR),
    ITEM_FRAME(389, 0, MaterialName.ITEM_FRAME),
    JUKEBOX(84, 0, MaterialName.JUKEBOX),
    JUNGLE_BOAT(446, 0, MaterialName.JUNGLE_BOAT),
    JUNGLE_DOOR(429, 0, MaterialName.JUNGLE_DOOR),
    JUNGLE_FENCE(190, 0, MaterialName.JUNGLE_FENCE),
    JUNGLE_FENCE_GATE(185, 0, MaterialName.JUNGLE_FENCE_GATE),
    JUNGLE_WOOD_STAIRS(136, 0, MaterialName.JUNGLE_STAIRS),
    LADDER(65, 0, MaterialName.LADDER),
    LAPIS_LAZULI_BLOCK(22, 0, MaterialName.LAPIS_BLOCK),
    LAPIS_LAZULI_ORE(21, 0, MaterialName.LAPIS_ORE),
    LAVA_BUCKET(327, 0, MaterialName.LAVA_BUCKET),
    LEAD(420, 0, MaterialName.LEAD),
    LEATHER(334, 0, MaterialName.LEATHER),
    LEATHER_BOOTS(301, 0, MaterialName.LEATHER_BOOTS),
    LEATHER_TUNIC(299, 0, MaterialName.LEATHER_CHESTPLATE),
    LEATHER_CAP(298, 0, MaterialName.LEATHER_HELMET),
    LEATHER_PANTS(300, 0, MaterialName.LEATHER_LEGGINGS),
    OAK_LEAVES(18, 0, MaterialName.OAK_LEAVES),
    SPRUCE_LEAVES(18, 1, MaterialName.SPRUCE_LEAVES),
    BIRCH_LEAVES(18, 2, MaterialName.BIRCH_LEAVES),
    JUNGLE_LEAVES(18, 3, MaterialName.JUNGLE_LEAVES),
    ACACIA_LEAVES(161, 0, MaterialName.ACACIA_LEAVES),
    DARK_OAK_LEAVES(161, 1, MaterialName.DARK_OAK_LEAVES),
    LEVER(69, 0, MaterialName.LEVER),
    LIGHT_WEIGHTED_PRESSURE_PLATE(147, 0, MaterialName.LIGHT_WEIGHTED_PRESSURE_PLATE),
    LINGERING_POTION(441, 0, MaterialName.LINGERING_POTION),
    JACK_O_LANTERN(91, 0, MaterialName.JACK_O_LANTERN),
    OAK_LOG(17, 0, MaterialName.OAK_LOG),
    SPRUCE_LOG(17, 1, MaterialName.SPRUCE_LOG),
    BIRCH_LOG(17, 2, MaterialName.BIRCH_LOG),
    JUNGLE_LOG(17, 3, MaterialName.JUNGLE_LOG),
    ACACIA_LOG(162, 0, MaterialName.ACACIA_LOG),
    DARK_OAK_LOG(162, 1, MaterialName.DARK_OAK_LOG),
    MAGMA_BLOCK(213, 0, MaterialName.MAGMA_BLOCK),
    MAGMA_CREAM(378, 0, MaterialName.MAGMA_CREAM),
    MAP(395, 0, MaterialName.MAP),
    MELON_SLICE(360, 0, MaterialName.MELON_SLICE),
    BLOCK_OF_MELON(103, 0, MaterialName.MELON),
    MELON_SEEDS(362, 0, MaterialName.MELON_SEEDS),
    MILK(335, 0, MaterialName.MILK_BUCKET),
    MINECART(328, 0, MaterialName.MINECART),
    MONSTER_SPAWNER(52, 0, MaterialName.SPAWNER),
    STONE_MONSTER_EGG(97, 0, MaterialName.INFESTED_STONE),
    COBBLESTONE_MONSTER_EGG(97, 1, MaterialName.INFESTED_COBBLESTONE),
    STONE_BRICK_MONSTER_EGG(97, 2, MaterialName.INFESTED_STONE_BRICKS),
    MOSSY_STONE_BRICK_MONSTER_EGG(97, 3, MaterialName.INFESTED_MOSSY_STONE_BRICKS),
    CRACKED_STONE_BRICK_MONSTER_EGG(97, 4, MaterialName.INFESTED_CRACKED_STONE_BRICKS),
    CHISELED_STONE_BRICK_MONSTER_EGG(97, 5, MaterialName.INFESTED_CHISELED_STONE_BRICKS),
    MOSSY_COBBLESTONE(48, 0, MaterialName.MOSSY_COBBLESTONE),
    MUSHROOM_STEW(282, 0, MaterialName.MUSHROOM_STEW),
    RAW_MUTTON(423, 0, MaterialName.MUTTON),
    MYCELIUM(110, 0, MaterialName.MYCELIUM),
    NAME_TAG(421, 0, MaterialName.NAME_TAG),
    BLOCK_OF_NETHER_BRICK(112, 0, MaterialName.NETHER_BRICKS),
    NETHER_BRICK_FENCE(113, 0, MaterialName.NETHER_BRICK_FENCE),
    NETHER_BRICK_STAIRS(114, 0, MaterialName.NETHER_BRICK_STAIRS),
    NETHER_STAR(399, 0, MaterialName.NETHER_STAR),
    NETHER_WART(372, 0, MaterialName.NETHER_WART),
    NETHER_WART_BLOCK(214, 0, MaterialName.NETHER_WART_BLOCK),
    NETHER_BRICK(405, 0, MaterialName.NETHER_BRICK),
    NETHERRACK(87, 0, MaterialName.NETHERRACK),
    NOTE_BLOCK(25, 0, MaterialName.NOTE_BLOCK),
    OAK_WOOD_STAIRS(53, 0, MaterialName.OAK_STAIRS),
    OBSIDIAN(49, 0, MaterialName.OBSIDIAN),
    PACKED_ICE(174, 0, MaterialName.PACKED_ICE),
    PAINTING(321, 0, MaterialName.PAINTING),
    PAPER(339, 0, MaterialName.PAPER),
    PISTON(33, 0, MaterialName.PISTON),
    OAK_WOOD_PLANK(5, 0, MaterialName.OAK_PLANKS),
    SPRUCE_WOOD_PLANK(5, 1, MaterialName.SPRUCE_PLANKS),
    BIRCH_WOOD_PLANK(5, 2, MaterialName.BIRCH_PLANKS),
    JUNGLE_WOOD_PLANK(5, 3, MaterialName.JUNGLE_PLANKS),
    ACACIA_WOOD_PLANK(5, 4, MaterialName.ACACIA_PLANKS),
    DARK_OAK_WOOD_PLANK(5, 5, MaterialName.DARK_OAK_PLANKS),
    POISONOUS_POTATO(394, 0, MaterialName.POISONOUS_POTATO),
    RAW_PORKCHOP(319, 0, MaterialName.PORKCHOP),
    POTATO(392, 0, MaterialName.POTATO),
    POTION(373, 0, MaterialName.POTION),
    PRISMARINE(168, 0, MaterialName.PRISMARINE),
    PRISMARINE_BRICKS(168, 1, MaterialName.PRISMARINE_BRICKS),
    DARK_PRISMARINE(168, 2, MaterialName.DARK_PRISMARINE),
    PRISMARINE_CRYSTALS(410, 0, MaterialName.PRISMARINE_CRYSTALS),
    PRISMARINE_SHARD(409, 0, MaterialName.PRISMARINE_SHARD),
    PUMPKIN(86, 0, MaterialName.PUMPKIN),
    PUMPKIN_PIE(400, 0, MaterialName.PUMPKIN_PIE),
    PUMPKIN_SEEDS(361, 0, MaterialName.PUMPKIN_SEEDS),
    PURPUR_BLOCK(201, 0, MaterialName.PURPUR_BLOCK),
    PURPUR_PILLAR(202, 0, MaterialName.PURPUR_PILLAR),
    PURPUR_SLAB(205, 0, MaterialName.PURPUR_SLAB),
    PURPUR_STAIRS(203, 0, MaterialName.PURPUR_STAIRS),
    NETHER_QUARTZ(406, 0, MaterialName.QUARTZ),
    BLOCK_OF_QUARTZ(155, 0, MaterialName.QUARTZ_BLOCK),
    CHISELED_QUARTZ_BLOCK(155, 1, MaterialName.CHISELED_QUARTZ_BLOCK),
    PILLAR_QUARTZ_BLOCK(155, 2, MaterialName.QUARTZ_PILLAR),
    NETHER_QUARTZ_ORE(153, 0, MaterialName.NETHER_QUARTZ_ORE),
    QUARTZ_STAIRS(156, 0, MaterialName.QUARTZ_STAIRS),
    RAW_RABBIT(411, 0, MaterialName.RABBIT),
    RABBITS_FOOT(414, 0, MaterialName.RABBIT_FOOT),
    RABBIT_HIDE(415, 0, MaterialName.RABBIT_HIDE),
    RABBIT_STEW(413, 0, MaterialName.RABBIT_STEW),
    RAILS(66, 0, MaterialName.RAIL),
    MUSIC_DISC_11(2266, 0, MaterialName.MUSIC_DISC_11),
    MUSIC_DISC_13(2256, 0, MaterialName.MUSIC_DISC_13),
    MUSIC_DISC_BLOCKS(2258, 0, MaterialName.MUSIC_DISC_BLOCKS),
    MUSIC_DISC_CAT(2257, 0, MaterialName.MUSIC_DISC_CAT),
    MUSIC_DISC_CHIRP(2259, 0, MaterialName.MUSIC_DISC_CHIRP),
    MUSIC_DISC_FAR(2260, 0, MaterialName.MUSIC_DISC_FAR),
    MUSIC_DISC_MALL(2261, 0, MaterialName.MUSIC_DISC_MALL),
    MUSIC_DISC_MELLOHI(2262, 0, MaterialName.MUSIC_DISC_MELLOHI),
    MUSIC_DISC_STAL(2263, 0, MaterialName.MUSIC_DISC_STAL),
    MUSIC_DISC_STRAD(2264, 0, MaterialName.MUSIC_DISC_STRAD),
    MUSIC_DISC_WAIT(2267, 0, MaterialName.MUSIC_DISC_WAIT),
    MUSIC_DISC_WARD(2265, 0, MaterialName.MUSIC_DISC_WARD),
    POPPY(38, 0, MaterialName.POPPY),
    BLUE_ORCHID(38, 1, MaterialName.BLUE_ORCHID),
    ALLIUM(38, 2, MaterialName.ALLIUM),
    AZURE_BLUET(38, 3, MaterialName.AZURE_BLUET),
    RED_TULIP(38, 4, MaterialName.RED_TULIP),
    ORANGE_TULIP(38, 5, MaterialName.ORANGE_TULIP),
    WHITE_TULIP(38, 6, MaterialName.WHITE_TULIP),
    PINK_TULIP(38, 7, MaterialName.PINK_TULIP),
    OXEYE_DAISY(38, 8, MaterialName.OXEYE_DAISY),
    RED_MUSHROOM(40, 0, MaterialName.RED_MUSHROOM),
    RED_MUSHROOM_BLOCK(100, 0, MaterialName.RED_MUSHROOM_BLOCK),
    RED_NETHER_BRICK(215, 0, MaterialName.RED_NETHER_BRICKS),
    RED_SANDSTONE(179, 0, MaterialName.RED_SANDSTONE),
    CHISELED_RED_SANDSTONE(179, 1, MaterialName.CHISELED_RED_SANDSTONE),
    SMOOTH_RED_SANDSTONE(179, 2, MaterialName.SMOOTH_RED_SANDSTONE),
    RED_SANDSTONE_STAIRS(180, 0, MaterialName.RED_SANDSTONE_STAIRS),
    REDSTONE(331, 0, MaterialName.REDSTONE),
    BLOCK_OF_REDSTONE(152, 0, MaterialName.REDSTONE_BLOCK),
    REDSTONE_LAMP(123, 0, MaterialName.REDSTONE_LAMP),
    REDSTONE_ORE(73, 0, MaterialName.REDSTONE_ORE),
    REDSTONE_TORCH(76, 0, MaterialName.REDSTONE_TORCH),
    SUGAR_CANES(338, 0, MaterialName.SUGAR_CANE),
    REDSTONE_REPEATER(356, 0, MaterialName.REPEATER),
    REPEATING_COMMAND_BLOCK(210, 0, MaterialName.REPEATING_COMMAND_BLOCK),
    ROTTEN_FLESH(367, 0, MaterialName.ROTTEN_FLESH),
    SADDLE(329, 0, MaterialName.SADDLE),
    SAND(12, 0, MaterialName.SAND),
    RED_SAND(12, 1, MaterialName.RED_SAND),
    SANDSTONE(24, 0, MaterialName.SANDSTONE),
    CHISELED_SANDSTONE(24, 1, MaterialName.CHISELED_SANDSTONE),
    SMOOTH_SANDSTONE(24, 2, MaterialName.SMOOTH_SANDSTONE),
    SANDSTONE_STAIRS(128, 0, MaterialName.SANDSTONE_STAIRS),
    OAK_SAPLING(6, 0, MaterialName.OAK_SAPLING),
    SPRUCE_SAPLING(6, 1, MaterialName.SPRUCE_SAPLING),
    BIRCH_SAPLING(6, 2, MaterialName.BIRCH_SAPLING),
    JUNGLE_SAPLING(6, 3, MaterialName.JUNGLE_SAPLING),
    ACACIA_SAPLING(6, 4, MaterialName.ACACIA_SAPLING),
    DARK_OAK_SAPLING(6, 5, MaterialName.DARK_OAK_SAPLING),
    SEA_LANTERN(169, 0, MaterialName.SEA_LANTERN),
    SHEARS(359, 0, MaterialName.SHEARS),
    SHIELD(442, 0, MaterialName.SHIELD),
    OAK_SIGN(323, 0, MaterialName.OAK_SIGN),
    SKELETON_SKULL(397, 0, MaterialName.SKELETON_SKULL),
    WITHER_SKELETON_SKULL(397, 1, MaterialName.WITHER_SKELETON_SKULL),
    ZOMBIE_HEAD(397, 2, MaterialName.ZOMBIE_HEAD),
    STEVE_HEAD(397, 3, MaterialName.PLAYER_HEAD),
    CREEPER_HEAD(397, 4, MaterialName.CREEPER_HEAD),
    DRAGON_HEAD(397, 5, MaterialName.DRAGON_HEAD),
    SLIME_BLOCK(165, 0, MaterialName.SLIME_BLOCK),
    SLIMEBALL(341, 0, MaterialName.SLIME_BALL),
    SNOW_BLOCK(80, 0, MaterialName.SNOW_BLOCK),
    SNOW(78, 0, MaterialName.SNOW),
    SNOWBALL(332, 0, MaterialName.SNOWBALL),
    SOUL_SAND(88, 0, MaterialName.SOUL_SAND),
    SPAWN_EGG(383, 0, null),
    GLISTERING_MELON(382, 0, MaterialName.GLISTERING_MELON_SLICE),
    SPECTRAL_ARROW(439, 0, MaterialName.SPECTRAL_ARROW),
    SPIDER_EYE(375, 0, MaterialName.SPIDER_EYE),
    SPLASH_POTION(438, 0, MaterialName.SPLASH_POTION),
    SPONGE(19, 0, MaterialName.SPONGE),
    WET_SPONGE(19, 1, MaterialName.WET_SPONGE),
    SPRUCE_BOAT(444, 0, MaterialName.SPRUCE_BOAT),
    SPRUCE_DOOR(427, 0, MaterialName.SPRUCE_DOOR),
    SPRUCE_FENCE(188, 0, MaterialName.SPRUCE_FENCE),
    SPRUCE_FENCE_GATE(183, 0, MaterialName.SPRUCE_FENCE_GATE),
    SPRUCE_WOOD_STAIRS(134, 0, MaterialName.SPRUCE_STAIRS),
    WHITE_STAINED_GLASS(95, 0, MaterialName.WHITE_STAINED_GLASS),
    ORANGE_STAINED_GLASS(95, 1, MaterialName.ORANGE_STAINED_GLASS),
    MAGENTA_STAINED_GLASS(95, 2, MaterialName.MAGENTA_STAINED_GLASS),
    LIGHT_BLUE_STAINED_GLASS(95, 3, MaterialName.LIGHT_BLUE_STAINED_GLASS),
    YELLOW_STAINED_GLASS(95, 4, MaterialName.YELLOW_STAINED_GLASS),
    LIME_STAINED_GLASS(95, 5, MaterialName.LIME_STAINED_GLASS),
    PINK_STAINED_GLASS(95, 6, MaterialName.PINK_STAINED_GLASS),
    GRAY_STAINED_GLASS(95, 7, MaterialName.GRAY_STAINED_GLASS),
    LIGHT_GRAY_STAINED_GLASS(95, 8, MaterialName.LIGHT_GRAY_STAINED_GLASS),
    CYAN_STAINED_GLASS(95, 9, MaterialName.CYAN_STAINED_GLASS),
    PURPLE_STAINED_GLASS(95, 10, MaterialName.PURPLE_STAINED_GLASS),
    BLUE_STAINED_GLASS(95, 11, MaterialName.BLUE_STAINED_GLASS),
    BROWN_STAINED_GLASS(95, 12, MaterialName.BROWN_STAINED_GLASS),
    GREEN_STAINED_GLASS(95, 13, MaterialName.GREEN_STAINED_GLASS),
    RED_STAINED_GLASS(95, 14, MaterialName.RED_STAINED_GLASS),
    BLACK_STAINED_GLASS(95, 15, MaterialName.BLACK_STAINED_GLASS),
    WHITE_STAINED_GLASS_PANE(160, 0, MaterialName.WHITE_STAINED_GLASS_PANE),
    ORANGE_STAINED_GLASS_PANE(160, 1, MaterialName.ORANGE_STAINED_GLASS_PANE),
    MAGENTA_STAINED_GLASS_PANE(160, 2, MaterialName.MAGENTA_STAINED_GLASS_PANE),
    LIGHT_BLUE_STAINED_GLASS_PANE(160, 3, MaterialName.LIGHT_BLUE_STAINED_GLASS_PANE),
    YELLOW_STAINED_GLASS_PANE(160, 4, MaterialName.YELLOW_STAINED_GLASS_PANE),
    LIME_STAINED_GLASS_PANE(160, 5, MaterialName.LIME_STAINED_GLASS_PANE),
    PINK_STAINED_GLASS_PANE(160, 6, MaterialName.PINK_STAINED_GLASS_PANE),
    GRAY_STAINED_GLASS_PANE(160, 7, MaterialName.GRAY_STAINED_GLASS_PANE),
    LIGHT_GRAY_STAINED_GLASS_PANE(160, 8, MaterialName.LIGHT_GRAY_STAINED_GLASS_PANE),
    CYAN_STAINED_GLASS_PANE(160, 9, MaterialName.CYAN_STAINED_GLASS_PANE),
    PURPLE_STAINED_GLASS_PANE(160, 10, MaterialName.PURPLE_STAINED_GLASS_PANE),
    BLUE_STAINED_GLASS_PANE(160, 11, MaterialName.BLUE_STAINED_GLASS_PANE),
    BROWN_STAINED_GLASS_PANE(160, 12, MaterialName.BROWN_STAINED_GLASS_PANE),
    GREEN_STAINED_GLASS_PANE(160, 13, MaterialName.GREEN_STAINED_GLASS_PANE),
    RED_STAINED_GLASS_PANE(160, 14, MaterialName.RED_STAINED_GLASS_PANE),
    BLACK_STAINED_GLASS_PANE(160, 15, MaterialName.BLACK_STAINED_GLASS_PANE),
    WHITE_TERRACOTTA(159, 0, MaterialName.WHITE_TERRACOTTA),
    ORANGE_TERRACOTTA(159, 1, MaterialName.ORANGE_TERRACOTTA),
    MAGENTA_TERRACOTTA(159, 2, MaterialName.MAGENTA_TERRACOTTA),
    LIGHT_BLUE_TERRACOTTA(159, 3, MaterialName.LIGHT_BLUE_TERRACOTTA),
    YELLOW_TERRACOTTA(159, 4, MaterialName.YELLOW_TERRACOTTA),
    LIME_TERRACOTTA(159, 5, MaterialName.LIME_TERRACOTTA),
    PINK_TERRACOTTA(159, 6, MaterialName.PINK_TERRACOTTA),
    GRAY_TERRACOTTA(159, 7, MaterialName.GRAY_TERRACOTTA),
    LIGHT_GRAY_TERRACOTTA(159, 8, MaterialName.LIGHT_GRAY_TERRACOTTA),
    CYAN_TERRACOTTA(159, 9, MaterialName.CYAN_TERRACOTTA),
    PURPLE_TERRACOTTA(159, 10, MaterialName.PURPLE_TERRACOTTA),
    BLUE_TERRACOTTA(159, 11, MaterialName.BLUE_TERRACOTTA),
    BROWN_TERRACOTTA(159, 12, MaterialName.BROWN_TERRACOTTA),
    GREEN_TERRACOTTA(159, 13, MaterialName.GREEN_TERRACOTTA),
    RED_TERRACOTTA(159, 14, MaterialName.RED_TERRACOTTA),
    BLACK_TERRACOTTA(159, 15, MaterialName.BLACK_TERRACOTTA),
    STICK(280, 0, MaterialName.STICK),
    STICKY_PISTON(29, 0, MaterialName.STICKY_PISTON),
    STONE(1, 0, MaterialName.STONE),
    GRANITE(1, 1, MaterialName.GRANITE),
    POLISHED_GRANITE(1, 2, MaterialName.POLISHED_GRANITE),
    DIORITE(1, 3, MaterialName.DIORITE),
    POLISHED_DIORITE(1, 4, MaterialName.POLISHED_DIORITE),
    ANDESITE(1, 5, MaterialName.ANDESITE),
    POLISHED_ANDESITE(1, 6, MaterialName.POLISHED_ANDESITE),
    STONE_AXE(275, 0, MaterialName.STONE_AXE),
    STONE_BRICK_STAIRS(109, 0, MaterialName.STONE_BRICK_STAIRS),
    STONE_BUTTON(77, 0, MaterialName.STONE_BUTTON),
    STONE_HOE(291, 0, MaterialName.STONE_HOE),
    STONE_PICKAXE(274, 0, MaterialName.STONE_PICKAXE),
    STONE_PRESSURE_PLATE(70, 0, MaterialName.STONE_PRESSURE_PLATE),
    STONE_SHOVEL(273, 0, MaterialName.STONE_SHOVEL),
    STONE_SLAB(44, 0, MaterialName.STONE_SLAB),
    SANDSTONE_SLAB(44, 1, MaterialName.SANDSTONE_SLAB),
    COBBLESTONE_SLAB(44, 3, MaterialName.COBBLESTONE_SLAB),
    BRICK_SLAB(44, 4, MaterialName.BRICK_SLAB),
    STONE_BRICK_SLAB(44, 5, MaterialName.STONE_BRICK_SLAB),
    NETHER_BRICK_SLAB(44, 6, MaterialName.NETHER_BRICK_SLAB),
    QUARTZ_SLAB(44, 7, MaterialName.QUARTZ_SLAB),
    RED_SANDSTONE_SLAB(182, 0, MaterialName.RED_SANDSTONE_SLAB),
    COBBLESTONE_STAIRS(67, 0, MaterialName.COBBLESTONE_STAIRS),
    STONE_SWORD(272, 0, MaterialName.STONE_SWORD),
    STONE_BRICKS(98, 0, MaterialName.STONE_BRICKS),
    MOSSY_STONE_BRICKS(98, 1, MaterialName.MOSSY_STONE_BRICKS),
    CRACKED_STONE_BRICKS(98, 2, MaterialName.CRACKED_STONE_BRICKS),
    CHISELED_STONE_BRICKS(98, 3, MaterialName.CHISELED_STONE_BRICKS),
    STRING(287, 0, MaterialName.STRING),
    STRUCTURE_BLOCK(255, 0, MaterialName.STRUCTURE_BLOCK),
    STRUCTURE_VOID(217, 0, MaterialName.STRUCTURE_VOID),
    SUGAR(353, 0, MaterialName.SUGAR),
    GRASS(31, 1, MaterialName.GRASS),
    FERN(31, 2, MaterialName.FERN),
    TIPPED_ARROW(440, 0, MaterialName.TIPPED_ARROW),
    TNT(46, 0, MaterialName.TNT),
    MINECART_WITH_TNT(407, 0, MaterialName.TNT_MINECART),
    TORCH(50, 0, MaterialName.TORCH),
    OAK_TRAPDOOR(96, 0, MaterialName.OAK_TRAPDOOR),
    TRAPPED_CHEST(146, 0, MaterialName.TRAPPED_CHEST),
    TRIPWIRE_HOOK(131, 0, MaterialName.TRIPWIRE_HOOK),
    VINES(106, 0, MaterialName.VINE),
    WATER_BUCKET(326, 0, MaterialName.WATER_BUCKET),
    LILY_PAD(111, 0, MaterialName.LILY_PAD),
    COBWEB(30, 0, MaterialName.COBWEB),
    WHEAT(296, 0, MaterialName.WHEAT),
    SEEDS(295, 0, MaterialName.WHEAT_SEEDS),
    WOODEN_AXE(271, 0, MaterialName.WOODEN_AXE),
    OAK_BUTTON(143, 0, MaterialName.OAK_BUTTON),
    OAK_DOOR(324, 0, MaterialName.OAK_DOOR),
    WOODEN_HOE(290, 0, MaterialName.WOODEN_HOE),
    WOODEN_PICKAXE(270, 0, MaterialName.WOODEN_PICKAXE),
    OAK_PRESSURE_PLATE(72, 0, MaterialName.OAK_PRESSURE_PLATE),
    WOODEN_SHOVEL(269, 0, MaterialName.WOODEN_SHOVEL),
    OAK_WOOD_SLAB(126, 0, MaterialName.OAK_SLAB),
    SPRUCE_WOOD_SLAB(126, 1, MaterialName.SPRUCE_SLAB),
    BIRCH_WOOD_SLAB(126, 2, MaterialName.BIRCH_SLAB),
    JUNGLE_WOOD_SLAB(126, 3, MaterialName.JUNGLE_SLAB),
    ACACIA_WOOD_SLAB(126, 4, MaterialName.ACACIA_SLAB),
    DARK_OAK_WOOD_SLAB(126, 5, MaterialName.DARK_OAK_SLAB),
    WOODEN_SWORD(268, 0, MaterialName.WOODEN_SWORD),
    WHITE_WOOL(35, 0, MaterialName.WHITE_WOOL),
    ORANGE_WOOL(35, 1, MaterialName.ORANGE_WOOL),
    MAGENTA_WOOL(35, 2, MaterialName.MAGENTA_WOOL),
    LIGHT_BLUE_WOOL(35, 3, MaterialName.LIGHT_BLUE_WOOL),
    YELLOW_WOOL(35, 4, MaterialName.YELLOW_WOOL),
    LIME_WOOL(35, 5, MaterialName.LIME_WOOL),
    PINK_WOOL(35, 6, MaterialName.PINK_WOOL),
    GRAY_WOOL(35, 7, MaterialName.GRAY_WOOL),
    LIGHT_GRAY_WOOL(35, 8, MaterialName.LIGHT_GRAY_WOOL),
    CYAN_WOOL(35, 9, MaterialName.CYAN_WOOL),
    PURPLE_WOOL(35, 10, MaterialName.PURPLE_WOOL),
    BLUE_WOOL(35, 11, MaterialName.BLUE_WOOL),
    BROWN_WOOL(35, 12, MaterialName.BROWN_WOOL),
    GREEN_WOOL(35, 13, MaterialName.GREEN_WOOL),
    RED_WOOL(35, 14, MaterialName.RED_WOOL),
    BLACK_WOOL(35, 15, MaterialName.BLACK_WOOL),
    BOOK_AND_QUILL(386, 0, MaterialName.WRITABLE_BOOK),
    WRITTEN_BOOK(387, 0, MaterialName.WRITTEN_BOOK),
    DANDELION(37, 0, MaterialName.DANDELION);

    private final int id;
    private final int data;
    private final MaterialName name;

    Material10(final int id, final int data,
               @Nullable final MaterialName name) {
        this.id = id;
        this.data = data;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getData() {
        return data;
    }

    @Nullable
    @Override
    public MaterialName getName() {
        return name;
    }
}
