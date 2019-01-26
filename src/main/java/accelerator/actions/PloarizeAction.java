package accelerator.actions;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import accelerator.orbs.MagneticOrb;

public class PloarizeAction extends AbstractGameAction{

	boolean triggered = false;
	
	public PloarizeAction() {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
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
	    	
	    	for (int i = 0; i < p.orbs.size() - 1; i++) {
	    		if(p.orbs.get(i) instanceof MagneticOrb && p.orbs.get(i+1) instanceof MagneticOrb) {
	    			
	    			((MagneticOrb)p.orbs.get(i)).potency += ((MagneticOrb)p.orbs.get(i+1)).potency;
	    			p.orbs.set(i+1, new EmptyOrbSlot(p.orbs.get(i+1).cX, p.orbs.get(i+1).cY));
	    			
	    			for (int j = i+2; j < p.orbs.size(); j++) {
		                Collections.swap(p.orbs, j, j - 1);
		            }
	    			
	    			i--;
	    		}	    		
	    	}
            for (int i = 0; i < p.orbs.size(); ++i) {
                p.orbs.get(i).setSlot(i, p.maxOrbs);                
            }
	    	triggered = true;	
	    }
		
	}
}
