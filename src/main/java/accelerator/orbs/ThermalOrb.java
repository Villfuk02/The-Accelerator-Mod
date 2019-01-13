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

import accelerator.AcceleratorMod;

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
		this.description = this.descriptions[0] + potency + this.descriptions[1];
		}
	
	@Override
	public void activateEffectUnique() {		
		
		if(slot > 0) {
			CardCrawlGame.sound.play("POWER_FOCUS", 0.1F);
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
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(potency, true), DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
	}
	
	@Override
	public int recalculate() {		
		return -1;
	}
	
	
	public AbstractOrb makeCopy() {
		return new ThermalOrb(potency);
	}
}