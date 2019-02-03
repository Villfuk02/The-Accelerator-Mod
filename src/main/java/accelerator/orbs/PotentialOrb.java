package accelerator.orbs;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import accelerator.AcceleratorMod;
import accelerator.actions.ChangePotencyAction;

public class PotentialOrb extends CustomOrb {
public static final String ID = "PotentialOrb";

	
	public PotentialOrb(int potency, AbstractCreature target) {
		super(ID, Color.ORANGE, Color.PURPLE, 
				AcceleratorMod.ORB_TEXTURE_PATH + ID + ".png", 
				potency, target);
		this.target = target;
		updateDescription();
		spawnVFX();
	}
	
	
	public void updateDescription() {
		
		if(target!= null && target.isDeadOrEscaped())
			target = null;
		
		int a = 0;
		if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
			a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		a += 5;
		a *= 5;
		this.description = this.descriptions[0] + a + this.descriptions[1] + potency + this.descriptions[2] + getTargetDescription() + this.descriptions[3];
		}
	
	@Override
	public void activateEffectUnique() {	
		
		CardCrawlGame.sound.play("POWER_FOCUS", 0.1F);
		
		AbstractDungeon.actionManager.addToTop(new ChangePotencyAction(this, recalculate() - potency));
		
		updateDescription();
	
	}
	
	@Override
	public void evokeEffectUnique() {
		if(target != null && !target.isDeadOrEscaped())
			AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(p, potency), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		else
			AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p, potency), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
	
	@Override
	public int recalculate() {
		int r = potency;
		int a = 0;
		if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
			a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		a += 5;		
		r = (r*(20+a)+19)/20;
		if(r < 0)
			r = 0;
		if(r > 999)
			r = 999;
		return r;
	}
	
	
	public AbstractOrb makeCopy() {
		return new PotentialOrb(potency, target);
	}
}