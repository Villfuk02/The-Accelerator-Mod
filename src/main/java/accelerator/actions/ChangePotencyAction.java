package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.orbs.CustomOrb;


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
				((CustomOrb)orb).potency += change;
				if(((CustomOrb)orb).potency < 0)
					((CustomOrb)orb).potency = 0;
				if(((CustomOrb)orb).potency > 999)
					((CustomOrb)orb).potency = 999;
				((CustomOrb)orb).fontScale();
				orb.updateDescription();
			}
	    	triggered = true;	
	    }
		
	}
}
