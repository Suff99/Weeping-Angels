package mc.craig.software.angels.util;

import mc.craig.software.angels.WeepingAngels;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class WATags {

    public static TagKey<Block> NO_BREAKING = makeBlock(WeepingAngels.MODID, "no_breaking");
    public static TagKey<Item> STEALABLE_ITEMS = makeItem(WeepingAngels.MODID, "stealable_items");
    public static TagKey<Biome> ANGEL_SPAWNS = makeBiome(WeepingAngels.MODID, "spawns/weeping_angels");


    private static TagKey<Item> makeItem(String domain, String path) {
        return ItemTags.create(new ResourceLocation(domain, path));
    }

    private static TagKey<Block> makeBlock(String domain, String path) {
        return BlockTags.create(new ResourceLocation(domain, path));
    }

    private static TagKey<Structure> makeStructure(String domain, String path) {
        return TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(domain, path));
    }

    private static TagKey<Biome> makeBiome(String domain, String path) {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(domain, path));
    }

}
