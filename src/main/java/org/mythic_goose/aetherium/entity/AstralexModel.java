package org.mythic_goose.aetherium.entity;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.Aetherium;

public class AstralexModel extends GeoModel<AstralexBoss> {
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "astralex");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "textures/entity/astralex_texture.png");
    }

    @Override
    public Identifier getAnimationResource(AstralexBoss animatable) {
        return Identifier.fromNamespaceAndPath(Aetherium.MOD_ID, "astralex");
    }
}
