package com.starfish_studios.naturalist.platform.fabric;

import com.starfish_studios.naturalist.Naturalist;
import com.starfish_studios.naturalist.item.fabric.NoFluidMobBucketItem;
import com.starfish_studios.naturalist.mixin.fabric.PotionBrewingInvoker;
import com.starfish_studios.naturalist.mixin.fabric.SpawnPlacementsInvoker;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class CommonPlatformHelperImpl {
    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        var registry = Registry.register(Registry.BLOCK, new ResourceLocation(Naturalist.MOD_ID, name), block.get());
        return () -> registry;
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        var registry = Registry.register(Registry.ITEM, new ResourceLocation(Naturalist.MOD_ID, name), item.get());
        return () -> registry;
    }

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEggItem(String name, Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor) {
        var registry = Registry.register(Registry.ITEM, new ResourceLocation(Naturalist.MOD_ID, name), new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, new Item.Properties().tab(Naturalist.TAB)));
        return () -> registry;
    }

    public static Supplier<Item> registerMobBucketItem(String name, Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier) {
        var registry = Registry.register(Registry.ITEM, new ResourceLocation(Naturalist.MOD_ID, name), new NoFluidMobBucketItem(entitySupplier, fluidSupplier.get(), soundSupplier.get(), new Item.Properties().tab(Naturalist.TAB).stacksTo(1)));
        return () -> registry;
    }

    public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
        var registry = Registry.register(Registry.SOUND_EVENT, new ResourceLocation(Naturalist.MOD_ID, name), soundEvent.get());
        return () -> registry;
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        var registry = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Naturalist.MOD_ID, name), FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.fixed(width, height)).trackRangeChunks(clientTrackingRange).build());
        return () -> registry;
    }

    public static CreativeModeTab registerCreativeModeTab(ResourceLocation name, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(name, icon);
    }

    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        var registry = Registry.register(Registry.POTION, new ResourceLocation(Naturalist.MOD_ID, name), potion.get());
        return () -> registry;
    }

    public static void registerBrewingRecipe(Potion input, Item ingredient, Potion output) {
        PotionBrewingInvoker.invokeAddMix(input, ingredient, output);
    }

    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        SpawnPlacementsInvoker.invokeRegister(entityType, decoratorType, heightMapType, decoratorPredicate);
    }

    public static void registerCompostable(float chance, ItemLike item) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }
}
