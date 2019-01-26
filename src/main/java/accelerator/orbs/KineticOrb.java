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
import accelerator.powers.InertiaPower;

public class KineticOrb extends CustomOrb {
public static final String ID = "KineticOrb";

	
	public KineticOrb(int potency, AbstractCreature target) {
		super(ID, Color.YELLOW, Color.LIME,
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
		a += 2;
		a *= 5;
		
		this.description = (a>0? this.descriptions[0] + a + this.descriptions[1]: this.descriptions[5]) + potency + this.descriptions[2] + getTargetDescription() + this.descriptions[3];
		}
	
	@Override
	public void activateEffectUnique() {	
		
		if(p.hasPower(AcceleratorMod.PREFIX + InertiaPower.NAME)) return;
		
		CardCrawlGame.sound.play("POWER_FOCUS", 0.1F);
		
		this.potency = recalculate();		
		this.fontScale *= 2f;
		
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
		if(p.hasPower(AcceleratorMod.PREFIX + InertiaPower.NAME)) return -1;
		int r = potency;
		int a = 0;
		if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
			a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		a += 2;
		a *= 5;
		
		if(a > 0)
			r = (r*(a-1))/a;
		else
			r = 0;
		if(r < 0)
			r = 0;
		if(r > 999)
			r = 999;
		return r;
	}
	
	
	public AbstractOrb makeCopy() {
		return new KineticOrb(potency, target);
	}
}