package accelerator.orbs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;

import accelerator.AcceleratorMod;
import accelerator.actions.OrbEffectAction;
import accelerator.vfx.OrbFlareEffect;


public abstract class CustomOrb
        extends AbstractOrb {

    public float NUM_X_OFFSET = 15.0F * Settings.scale;
    public float NUM_Y_OFFSET = -15.0F * Settings.scale;

    public float animX;
    public float animY;
    public String[] descriptions;
    public String orbID = "";
    public float scale = 1F;
    private Texture img;
    public float x;
    public float y;
    
    public Color inner;
    public Color outer;
    
    public AbstractPlayer p;

    public String customDescription;
    
    public int potency = 0;
	public AbstractMonster target;
	private float vfxTimer = 1f;
	private float vfxIntervalMax = 0.7f;
	private float vfxIntervalMin = 0.2f;
	private boolean evokeVFXplayed;


    public CustomOrb(String ID, Color inner, Color outer, String IMGURL, int potency, AbstractMonster target) {
    	
        this.ID = ID + AcceleratorMod.SUFFIX;
        this.orbID = ID;

        this.img = ImageMaster.loadImage(IMGURL);
        
        this.p = AbstractDungeon.player;

        this.evokeAmount = 1;
        
        this.inner = inner.cpy();
        this.outer = outer.cpy();


        this.channelAnimTimer = 0.5F;
        
        this.potency = potency;
        this.target = target;

        this.descriptions = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

        this.name = CardCrawlGame.languagePack.getOrbString(ID).NAME;
        //SlimeboundMod.mostRecentSlime = this;



        //AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeFlareEffect(this, OrbVFXColor), .1F));
        this.applyFocus();

        updateDescription();


    }

 public void spawnVFX(){
 }

    public void onStartOfTurn() {
    	activateEffect();
    }



    
    public void applyFocus() {
        super.applyFocus();
        updateDescription();
    }


    public void onEvoke() {    	
        triggerEvokeAnimation();
        evokeEffectUnique();
    }


    public void triggerEvokeAnimation() {

        if (!this.evokeVFXplayed) {
            CardCrawlGame.sound.play("ORB_PLASMA_EVOKE", 0.1F);
            AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
            AbstractDungeon.effectsQueue.add(new OrbFlareEffect(this, inner, outer));

            this.evokeVFXplayed = true;
        }

    }


    public void activateEffect() {        
    	AbstractDungeon.actionManager.addToBottom(new OrbEffectAction(this));
    }

    public void activateEffectUnique() {
    }
    
    public void evokeEffectUnique() {
    }
    
    public int recalculate() {
    	return -1;
    }
    
    public void monsterDeath(AbstractMonster m) {
    	if(target == m) {
    		target = null;
    		this.fontScale = 2f;
            AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    		updateDescription();
    	}
    }

    public void playChannelSFX() {

        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);

    }
    
    @Override
    public void updateAnimation()
    {
      super.updateAnimation();
      this.angle += Gdx.graphics.getDeltaTime() * 45.0F;
      
      this.vfxTimer -= Gdx.graphics.getDeltaTime();
      if (this.vfxTimer < 0.0F)
      {
        AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
        AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
        this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
      }
    }

    public void render(SpriteBatch sb) {

    	sb.setColor(this.c);
        sb.draw(img, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        
        renderText(sb);           
    }


    public void renderText(SpriteBatch sb) {
        if (recalculate() != -1) {


            float fontOffset = 20 * Settings.scale;
            if (potency > 9) 
            	fontOffset += (7 * Settings.scale);
            if (potency > 99) 
            	fontOffset += (7 * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(potency), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "(" + recalculate() + ")", this.cX + this.NUM_X_OFFSET + fontOffset, this.cY + this.NUM_Y_OFFSET - 3F * Settings.scale, Color.LIGHT_GRAY, this.fontScale*0.6f);

        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(potency), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);
        }
    }
}

