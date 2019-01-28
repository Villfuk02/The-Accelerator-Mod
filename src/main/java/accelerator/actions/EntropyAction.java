package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.orbs.CustomOrb;

public class EntropyAction extends AbstractGameAction{

	boolean triggered = false;
	int increase= 0;
	
	public EntropyAction(int inc) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    increase = inc;
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
	    	int count = 0;
	    	int potency = 0;
	    	for(AbstractOrb o : AbstractDungeon.player.orbs) {
	        	if(o instanceof CustomOrb) {
	        		count++;
	        		potency += ((CustomOrb) o).potency;
	        	}
	        }	   
	    	if(count <= 0)
	    		return;

	    	potency = potency/count;
	    	potency += increase;
	    	for(AbstractOrb o : AbstractDungeon.player.orbs) {
	        	if(o instanceof CustomOrb) {
	        		AbstractDungeon.actionManager.addToBottom(new ChangePotencyAction(o, potency - ((CustomOrb)o).potency));
	        	}
	        }
	    	triggered = true;	
	    }
		
	}
}
