package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.AcceleratorMod;
import accelerator.orbs.CustomOrb;
import accelerator.powers.EfficiencyPower;


public class ChangePotencyAction extends AbstractGameAction{
	private AbstractOrb orb;
	private int change;
	public boolean triggered;

	public ChangePotencyAction(AbstractOrb orb, int change) {
	    this.actionType = AbstractGameAction.ActionType.WAIT;
	    this.orb = orb;
	    this.change = change;
	    triggered = false;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_XFAST;
	    else
	    	this.duration = Settings.ACTION_DUR_FASTER;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    this.tickDuration();
	    if(!triggered) {
	    	if(orb instanceof CustomOrb) {				
				if(((CustomOrb)orb).potency + change < 0)
					change = -((CustomOrb)orb).potency;
				if(((CustomOrb)orb).potency  + change > 999)
					change = 999 - ((CustomOrb)orb).potency;
				
				((CustomOrb)orb).potency += change;
				((CustomOrb)orb).fontScale();				
				orb.updateDescription();	
				
				if(change < 0 && AbstractDungeon.player.hasPower(AcceleratorMod.PREFIX + EfficiencyPower.NAME))
					AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, -change * AbstractDungeon.player.getPower(AcceleratorMod.PREFIX + EfficiencyPower.NAME).amount, true));
			}
	    	triggered = true;	
	    }
		
	}
}
