package org.mythic_goose.aetherium.entity.cavern_cow;

import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class CavernCowRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<CavernCowAnimal, R> {
    public CavernCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CavernCowModel());
    }
}