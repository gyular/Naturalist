package com.starfish_studios.naturalist.registry;

import com.starfish_studios.naturalist.Naturalist;
import com.starfish_studios.naturalist.block.ChrysalisBlock;
import com.starfish_studios.naturalist.platform.CommonPlatformHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class NaturalistBlocks {
    public static final Supplier<Block> CHRYSALIS = CommonPlatformHelper.registerBlock("chrysalis", () -> new ChrysalisBlock(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().strength(0.2F, 3.0F).sound(SoundType.GRASS).noOcclusion().noCollission()));

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> supplier = CommonPlatformHelper.registerBlock(name, block);
        CommonPlatformHelper.registerItem(name, () -> new BlockItem(supplier.get(), new Item.Properties().tab(Naturalist.TAB)));
        return supplier;
    }

    public static void init() {}
}
