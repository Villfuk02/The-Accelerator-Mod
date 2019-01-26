package accelerator.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;

import accelerator.AcceleratorMod;

import java.util.ArrayList;

import basemod.abstracts.CustomEnergyOrb;

public class AcceleratorEnergyOrb extends CustomEnergyOrb {
	
	public static final ArrayList<Texture> activeLayers = new ArrayList<Texture>();
	public static final ArrayList<Texture> disabledLayers = new ArrayList<Texture>();
	
    public static float fontScale = 1.0f;
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;
    private float angle1;
    private float angle2;
    private float angle3;
    private float angle4;
	
	public AcceleratorEnergyOrb() {
		super(null, null, null);
		activeLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "active/layer1.png"));
		activeLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "active/layer2.png"));
		activeLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "active/layer3.png"));
		activeLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "active/layer4.png"));
		activeLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "active/layer5.png"));
		activeLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "active/layer6.png"));
		
		disabledLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "disabled/layer1.png"));
		disabledLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "disabled/layer2.png"));
		disabledLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "disabled/layer3.png"));
		disabledLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "disabled/layer4.png"));
		disabledLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "disabled/layer5.png"));		
		disabledLayers.add(new Texture(AcceleratorMod.ENERGY_ORB_PATH + "disabled/layer6.png"));
	}

    
    @Override
    public void updateOrb(final int orbCount) {
        if (orbCount == 0) {
            this.angle1 += Gdx.graphics.getDeltaTime() * 6.0f;
            this.angle2 += Gdx.graphics.getDeltaTime() * -8.0f;
            this.angle3 += Gdx.graphics.getDeltaTime() * 7.0f;
            this.angle4 += Gdx.graphics.getDeltaTime() * 15.0f;
        }
        else {
            this.angle1 += Gdx.graphics.getDeltaTime() * 25.0f;
            this.angle2 += Gdx.graphics.getDeltaTime() * -40.0f;
            this.angle3 += Gdx.graphics.getDeltaTime() * -120.0f;
            this.angle4 += Gdx.graphics.getDeltaTime() * 60.0f;
        }
    }
    
    @Override
    public void renderOrb(final SpriteBatch sb, final boolean enabled, final float current_x, final float current_y) {
        if (enabled) {
            sb.setColor(Color.WHITE);
            sb.draw(activeLayers.get(1), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 128, 128, false, false);
            sb.draw(activeLayers.get(2), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 128, 128, false, false);
            sb.draw(activeLayers.get(3), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 128, 128, false, false);
            sb.draw(activeLayers.get(4), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
            sb.draw(activeLayers.get(0), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(Color.WHITE);
            sb.draw(activeLayers.get(5), current_x - 128.0f, current_y - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 256, 256, false, false);
        }
        else {
            sb.setColor(Color.WHITE);
            sb.draw(disabledLayers.get(1), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 128, 128, false, false);
            sb.draw(disabledLayers.get(2), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 128, 128, false, false);
            sb.draw(disabledLayers.get(3), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 128, 128, false, false);
            sb.draw(disabledLayers.get(4), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 128, 128, false, false);
            sb.draw(disabledLayers.get(0), current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 128, 128, false, false);
            sb.draw(disabledLayers.get(5), current_x - 128.0f, current_y - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 256, 256, false, false);
        }
    }
    
}

