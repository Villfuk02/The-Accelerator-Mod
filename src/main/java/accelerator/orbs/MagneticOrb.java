package accelerator.orbs;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import accelerator.AcceleratorMod;

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
		
		this.potency = recalculate();		
		this.fontScale *= 2f;
		
		updateDescription();
	
	}
	
	@Override
	public void evokeEffectUnique() {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, potency));
		AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
	}
	
	@Override
	public int recalculate() {
		int r = potency;
		int a = 0;
		if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
			a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		a += 2;		
		
		int b = 0;
		for(AbstractOrb o : p.orbs) {
			if(o instanceof MagneticOrb)
				b++;
		}		
		
		r += a * b;
		if(r < 0)
			r = 0;
		return r;
	}
	
	
	public AbstractOrb makeCopy() {
		return new MagneticOrb(potency);
	}
}