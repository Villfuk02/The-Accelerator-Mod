package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import accelerator.orbs.MagneticOrb;


public class MagneticEvokeAction extends AbstractGameAction{
	public boolean triggered;
	public MagneticOrb source;

	public MagneticEvokeAction(MagneticOrb source) {
	    this.actionType = AbstractGameAction.ActionType.DAMAGE;
	    triggered = false;
	    this.source = source;
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
	    	if(AbstractDungeon.player.orbs.get(0) instanceof MagneticOrb && AbstractDungeon.player.orbs.get(0) != source)
	    		AbstractDungeon.player.evokeOrb();
	    	triggered = true;	
	    }
		
	}
}
