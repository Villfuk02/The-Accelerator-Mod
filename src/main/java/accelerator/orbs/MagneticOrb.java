package accelerator.orbs;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import accelerator.AcceleratorMod;
import accelerator.actions.ChangePotencyAction;
import accelerator.actions.MagneticEvokeAction;
import accelerator.powers.DemagnetizedPower;

public class MagneticOrb extends CustomOrb {
public static final String ID = "MagneticOrb";

	
	public MagneticOrb(int potency) {
		super(ID, Color.CYAN, Color.PINK,
				AcceleratorMod.ORB_TEXTURE_PATH + ID + ".png", 
				potency, null);
		updateDescription();
		spawnVFX();
	}
	
	
	public void updateDescription() {		
		int a = 0;
		if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
			a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		a += 2;
		
		this.description = this.descriptions[0] + a + this.descriptions[1] + potency + this.descriptions[2];
		}
	
	@Override
	public void activateEffectUnique() {	
		
		CardCrawlGame.sound.play("POWER_FOCUS", 0.1F);
		
		AbstractDungeon.actionManager.addToTop(new ChangePotencyAction(this, recalculate() - potency));	
		
		updateDescription();
	
	}
	
	@Override
	public void evokeEffectUnique() {
		if(!p.hasPower(AcceleratorMod.PREFIX + DemagnetizedPower.NAME))
			AbstractDungeon.actionManager.addToTop(new MagneticEvokeAction(this));
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, potency));
	}
	
	@Override
	public int recalculate() {
		int r = potency;
		int a = 0;
		for(AbstractOrb o : p.orbs) {
			if(o instanceof MagneticOrb)
				a++;
		}		
		
		r += a;
		if(r < 0)
			r = 0;
		if(r > 999)
			r = 999;
		return r;
	}
	
	
	public AbstractOrb makeCopy() {
		return new MagneticOrb(potency);
	}
}