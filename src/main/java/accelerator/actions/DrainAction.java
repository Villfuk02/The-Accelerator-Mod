package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.orbs.PotentialOrb;

public class DrainAction extends AbstractGameAction{

	boolean triggered = false;	
	
	public DrainAction(int amt, AbstractMonster m) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    this.amount = amt;
	    this.target = m;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_FASTER;
	    else
	    	this.duration = Settings.ACTION_DUR_MED;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    AbstractPlayer p = AbstractDungeon.player;
	    this.tickDuration();
	    if(!triggered) {
		    int max = -1;
		    for (int i = 0; i < p.orbs.size(); i++) {
		    	if(p.orbs.get(i) instanceof PotentialOrb) {
		    		if(((PotentialOrb)p.orbs.get(i)).potency > max)
		    			max = ((PotentialOrb)p.orbs.get(i)).potency;
		    	}	    		
		    }
		    for (int i = 0; i < p.orbs.size(); i++) {
		    	if(p.orbs.get(i) instanceof PotentialOrb) {
		    		if(((PotentialOrb)p.orbs.get(i)).potency == max) {
		    			PotentialOrb o = (PotentialOrb)p.orbs.get(i);

		    			AbstractDungeon.actionManager.addToTop(new ChangePotencyAction(o, -3));		    			
			    		
			    	    triggered = true;
			    		return;
		    		}
		    	}	    		
		    }
	    	
	    	triggered = true;
	    }		
	}
}
