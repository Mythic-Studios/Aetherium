package org.mythic_goose.aetherium.entity;

import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class AstralexRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<AstralexBoss, R> {

    // The animatable (entity) is discarded after render-state fill, so its
    // Phase has to be captured here at fill time and read back later from the
    // render state rather than referenced directly during rendering.
    public static final DataTicket<AstralexBoss.Phase> ASTRALEX_PHASE =
            DataTicket.create("aetherium:astralex_phase", AstralexBoss.Phase.class);

    public AstralexRenderer(EntityRendererProvider.Context context) {
        super(context, new AstralexModel());
    }

    @Override
    public void addRenderData(AstralexBoss animatable, Void relatedObject, R renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(ASTRALEX_PHASE, animatable.getPhase());
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        AstralexBoss.Phase phase = renderPassInfo.renderState().getGeckolibData(ASTRALEX_PHASE);

        snapshots.ifPresent("shield", bone -> bone.skipRender(phase != AstralexBoss.Phase.SHIELDED));
        snapshots.ifPresent("membrane", bone -> bone.skipRender(phase == AstralexBoss.Phase.HEART || phase == AstralexBoss.Phase.DEAD));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0f;
    }
}