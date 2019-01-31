package accelerator.orbs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
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
	public AbstractCreature target;
	public int targetHP = -1;
	public int targetMAX = -1;
	
	
	private float vfxTimer = 1f;
	private float vfxIntervalMax = 0.7f;
	private float vfxIntervalMin = 0.2f;
	private boolean evokeVFXplayed;


    public CustomOrb(String ID, Color inner, Color outer, String IMGURL, int potency, AbstractCreature target) {
    	
        this.ID = AcceleratorMod.PREFIX + ID;
        this.orbID = ID;

        this.img = ImageMaster.loadImage(IMGURL);
        
        this.p = AbstractDungeon.player;

        this.evokeAmount = 1;
        
        this.inner = inner.cpy();
        this.outer = outer.cpy();


        this.channelAnimTimer = 0.5F;
        
        this.potency = potency;
        if(this.potency < 0)
			this.potency = 0;
		if(this.potency > 999)
			this.potency = 999;
        this.target = target;
        

        this.descriptions = CardCrawlGame.languagePack.getOrbString(this.ID).DESCRIPTION;

        this.name = CardCrawlGame.languagePack.getOrbString(this.ID).NAME;
        this.applyFocus();

        updateDescription();


    }

 public void spawnVFX(){
 }

    public void onStartOfTurn() {
    	activateEffect();
    }

    public void fontScale() {
    	this.fontScale *= 2;
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

    	float size = Settings.scale * 96.0f;
    	
    	sb.setColor(this.c);
        sb.draw(img, this.cX - size/2 + this.bobEffect.y / 4.0F, this.cY - size/2 + this.bobEffect.y / 4.0F, size, size);
        
        renderText(sb);     
        checkHP();
    }


    public void renderText(SpriteBatch sb) {
        if (recalculate() != -1) {


            float fontOffset = 19 * Settings.scale;
            if (potency > 9) 
            	fontOffset += (9 * Settings.scale);
            
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(potency), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, getTargetColor(), this.fontScale);
            
            if (recalculate() > 99) 
            	FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "(" + recalculate() + ")", this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET - 19 * Settings.scale, Color.LIGHT_GRAY, this.fontScale*0.6f);
            else            
            	FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "(" + recalculate() + ")", this.cX + this.NUM_X_OFFSET + fontOffset, this.cY + this.NUM_Y_OFFSET - 3F * Settings.scale, Color.LIGHT_GRAY, this.fontScale*0.6f);

        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(potency), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, getTargetColor(), this.fontScale);
        }
    }
    
    public Color getTargetColor() {
    	if(target == null)
    		return Color.WHITE;
    	if(target instanceof AbstractPlayer)
    		return Color.RED;
    	return Color.GOLD;
    }
    
    public String getTargetDescription() {
    	if(target == null)
    		return "#ra #rrandom #renemy";
    	if(target instanceof AbstractPlayer)
    		return "#rYOU";    	
    	return "#r" + target.name.replace(" ", " #r") + " #r(" + targetHP + "/" + targetMAX +")";
    }
    
    public void checkHP() {
    	if(target == null || target.isDeadOrEscaped())
    		return;
    	if(target instanceof AbstractPlayer)
    		return;
    	if(target != null && targetHP != target.currentHealth) {
    		targetHP = target.currentHealth;
    		updateDescription();
    	}
    	if(target != null && targetMAX != target.maxHealth) {
    		targetMAX = target.maxHealth;    		 
    		updateDescription();
    	}
    }
}

