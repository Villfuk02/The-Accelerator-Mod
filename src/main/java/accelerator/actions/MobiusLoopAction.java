package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MobiusLoopAction extends AbstractGameAction{

	boolean triggered = false;
	
	public MobiusLoopAction(int amt) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    amount = amt;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_FASTER;
	    else
	    	this.duration = Settings.ACTION_DUR_MED;
	}

	@Override
	public void update() {
	    if (this.isDone) return;
	    this.tickDuration();
	    if(!triggered) {
	    	if(AbstractDungeon.player.filledOrbCount() < 1) {
	    		this.isDone = true;
	    		return;
	    	}
	    	AbstractOrb o = AbstractDungeon.player.orbs.get(AbstractDungeon.player.filledOrbCount()-1);
	    	for(int i = 0; i < amount; i++)
	        	AbstractDungeon.actionManager.addToBottom(new PassiveOrbAction(o));
	        	
	    	triggered = true;	
	    }
		
	}
}
