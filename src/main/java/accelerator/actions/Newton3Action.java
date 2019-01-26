package accelerator.actions;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import accelerator.orbs.KineticOrb;

public class Newton3Action extends AbstractGameAction{

	boolean triggered = false;
	boolean upgraded = false;
	
	public Newton3Action(boolean upgraded) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    this.upgraded = upgraded;
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
	    	if(upgraded) {
		    	int max = -1;
		    	for (int i = 0; i < p.orbs.size(); i++) {
		    		if(p.orbs.get(i) instanceof KineticOrb) {
		    			if(((KineticOrb)p.orbs.get(i)).potency > max)
		    				max = ((KineticOrb)p.orbs.get(i)).potency;
		    		}	    		
		    	}
		    	for (int i = 0; i < p.orbs.size(); i++) {
		    		if(p.orbs.get(i) instanceof KineticOrb) {
		    			if(((KineticOrb)p.orbs.get(i)).potency == max) {

			    			AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, ((KineticOrb)p.orbs.get(i)).potency));
		    				
		    				p.orbs.get(i).onEvoke();
		    	            final AbstractOrb orbSlot = new EmptyOrbSlot();
		    	            for (int j = i+1; j < p.orbs.size(); ++j) {
		    	                Collections.swap(p.orbs, j, j - 1);
		    	            }
		    	            p.orbs.set(p.orbs.size() - 1, orbSlot);	    				
			    			for (int j = 0; j < p.orbs.size(); ++j) {
			                    p.orbs.get(j).setSlot(j, p.maxOrbs);                
			                }
			    	    	triggered = true;
			    			return;
		    			}
		    		}	    		
		    	}
	    	}else {
	    		for (int i = 0; i < p.orbs.size(); i++) {
		    		if(p.orbs.get(i) instanceof KineticOrb) {
		    				
		    			AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, ((KineticOrb)p.orbs.get(i)).potency));
		    			
		    			p.orbs.get(i).onEvoke();
		    	        final AbstractOrb orbSlot = new EmptyOrbSlot();
		    	        for (int j = i+1; j < p.orbs.size(); ++j) {
		    	            Collections.swap(p.orbs, j, j - 1);
		    	        }
		    	        p.orbs.set(p.orbs.size() - 1, orbSlot);	    				
			    		for (int j = 0; j < p.orbs.size(); ++j) {
			                p.orbs.get(j).setSlot(j, p.maxOrbs);                
			            }
			    	    triggered = true;
			    		return;
		    		}
		    			    		
		    	}
	    	}
	    	triggered = true;
	    }		
	}
}
