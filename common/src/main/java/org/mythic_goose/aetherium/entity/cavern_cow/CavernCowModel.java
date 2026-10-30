package org.mythic_goose.aetherium.entity.cavern_cow;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import org.mythic_goose.aetherium.AetheriumConstants;
import org.mythic_goose.aetherium.entity.astralex.AstralexBoss;

public class CavernCowModel extends GeoModel<CavernCowAnimal> {
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "cavern_cow");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "textures/entity/cavern_cow.png");
    }

    @Override
    public Identifier getAnimationResource(CavernCowAnimal animatable) {
        return Identifier.fromNamespaceAndPath(AetheriumConstants.MOD_ID, "cavern_cow");
    }
}
