package accelerator.orbs;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import accelerator.AcceleratorMod;

public class PotentialOrb extends CustomOrb {
public static final String ID = "PotentialOrb";

	
	public PotentialOrb(int potency, AbstractMonster target) {
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
		a += 2;
		a *= 10;
		this.description = this.descriptions[0] + a + this.descriptions[1] + potency + this.descriptions[2] + (target != null?target.name:"a #rrandom #renemy") + this.descriptions[3];
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
		if(target != null && !target.isDeadOrEscaped())
			AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(p, potency), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		else
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p, potency), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
	
	@Override
	public int recalculate() {
		int r = potency;
		int a = 0;
		if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
			a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		a += 2;		
		r = (r*(10+a)+9)/10;
		if(r < 0)
			r = 0;
		return r;
	}
	
	
	public AbstractOrb makeCopy() {
		return new PotentialOrb(potency, target);
	}
}