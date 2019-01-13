package accelerator.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class OrbFlareEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static TextureAtlas.AtlasRegion outer;
    private static TextureAtlas.AtlasRegion inner;
    private float scaleY;
    private AbstractOrb orb;
    private Color color2;
    
    public void dispose() {


    }
    public OrbFlareEffect(AbstractOrb orb, Color innerColor, Color outerColor) {
        if (outer == null) {
            outer = ImageMaster.vfxAtlas.findRegion("combat/orbFlareOuter");
            inner = ImageMaster.vfxAtlas.findRegion("combat/orbFlareInner");
        }

        this.orb = orb;
        this.renderBehind = true;
        this.duration = 0.5F;
        this.startingDuration = 0.5F;
        this.color = innerColor;
        this.color2 = outerColor;
        this.scale = (2.5F * Settings.scale);
        this.scaleY = 0.0F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.duration = 0.0F;
            this.isDone = true;
        }

        this.scaleY = Interpolation.elasticIn.apply(2.2F, 0.8F, this.duration * 2.0F);
        this.scale = Interpolation.elasticIn.apply(2.1F, 1.9F, this.duration * 2.0F);
        this.color.a = Interpolation.pow2Out.apply(0.0F, 0.6F, this.duration * 2.0F);
        this.color2.a = this.color.a;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color2);
        sb.draw(inner, this.orb.cX - inner.packedWidth / 2.0F, this.orb.cY - inner.packedHeight / 2.0F, inner.packedWidth / 2.0F, inner.packedHeight / 2.0F, inner.packedWidth, inner.packedHeight, this.scale * Settings.scale * 1.1F, this.scaleY * Settings.scale,


                MathUtils.random(-1.0F, 1.0F));
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(outer, this.orb.cX - outer.packedWidth / 2.0F, this.orb.cY - outer.packedHeight / 2.0F, outer.packedWidth / 2.0F, outer.packedHeight / 2.0F, outer.packedWidth, outer.packedHeight, this.scale, this.scaleY * Settings.scale,


                MathUtils.random(-2.0F, 2.0F));
        sb.draw(outer, this.orb.cX - outer.packedWidth / 2.0F, this.orb.cY - outer.packedHeight / 2.0F, outer.packedWidth / 2.0F, outer.packedHeight / 2.0F, outer.packedWidth, outer.packedHeight, this.scale, this.scaleY * Settings.scale,


                MathUtils.random(-2.0F, 2.0F));
        sb.setBlendFunction(770, 771);
        sb.setColor(this.color2);
        sb.draw(inner, this.orb.cX - inner.packedWidth / 2.0F, this.orb.cY - inner.packedHeight / 2.0F, inner.packedWidth / 2.0F, inner.packedHeight / 2.0F, inner.packedWidth, inner.packedHeight, this.scale * Settings.scale * 1.1F, this.scaleY * Settings.scale,


                MathUtils.random(-1.0F, 1.0F));
    }
}