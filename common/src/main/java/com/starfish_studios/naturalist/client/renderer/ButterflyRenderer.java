package com.starfish_studios.naturalist.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starfish_studios.naturalist.client.model.ButterflyModel;
import com.starfish_studios.naturalist.entity.Butterfly;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class ButterflyRenderer extends GeoEntityRenderer<Butterfly> {
    public ButterflyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ButterflyModel());
        this.shadowRadius = 0.4F;
    }

    @Override
    public ResourceLocation getTextureLocation(Butterfly entity) {
        return getTextureResource(entity);
    }

    @Override
    public RenderType getRenderType(Butterfly animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
