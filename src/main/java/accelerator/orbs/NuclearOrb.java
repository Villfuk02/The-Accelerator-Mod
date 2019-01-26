package accelerator.orbs;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.AcceleratorMod;

public class NuclearOrb extends CustomOrb {
public static final String ID = "NuclearOrb";


	
	public NuclearOrb(int potency) {
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
	public void evokeEffectUnique() {
		if(potency > 1) {
			AbstractDungeon.actionManager.addToTop(new ChannelAction(new NuclearOrb(potency/2)));
			AbstractDungeon.actionManager.addToTop(new ChannelAction(new NuclearOrb(potency/2)));
		}
		AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(p, potency, DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
	
	@Override
	public int recalculate() {		
		return -1;
	}
	
	
	public AbstractOrb makeCopy() {
		return new NuclearOrb(potency);
	}
}