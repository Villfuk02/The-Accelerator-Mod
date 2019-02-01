package accelerator.orbs;

import java.util.Collections;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import accelerator.AcceleratorMod;
import accelerator.actions.ChangePotencyAction;

public class ThermalOrb extends CustomOrb {
public static final String ID = "ThermalOrb";

	public int slot = -1;

	
	public ThermalOrb(int potency) {
		super(ID, Color.ORANGE, Color.PURPLE, 
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
		
		if(slot > 0) {
			CardCrawlGame.sound.play("POWER_FOCUS", 0.1F);
			AbstractDungeon.actionManager.addToTop(new ChangePotencyAction(this, recalculate() - potency));			
			Collections.swap(p.orbs, slot, slot - 1);
			for (int i = 0; i < p.orbs.size(); ++i) {
				p.orbs.get(i).setSlot(i, p.maxOrbs);
			}
        }
		
	
	}
	
	@Override
	public void setSlot(int slotNum, int maxOrbs) {
		super.setSlot(slotNum, maxOrbs);
		slot = slotNum;
	}
	
	@Override
	public void evokeEffectUnique() {
		AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(potency, true), DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
	}
	
	@Override
	public int recalculate() {
		if(slot <= 0) return -1;
		int r = potency;
		if(p.orbs.get(slot-1) instanceof ThermalOrb) {			
			int a = 0;
			if(AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
				a = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			a += 2;		
			r += a;
		}else {
			r--;
		}
		if(r < 0)
			r = 0;
		if(r > 999)
			r = 999;
		return r;
		
	}
	
	
	public AbstractOrb makeCopy() {
		return new ThermalOrb(potency);
	}
}