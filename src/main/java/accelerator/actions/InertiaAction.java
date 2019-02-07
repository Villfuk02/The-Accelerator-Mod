package accelerator.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import accelerator.orbs.CustomOrb;
import accelerator.orbs.KineticOrb;
import accelerator.orbs.PotentialOrb;

public class InertiaAction extends AbstractGameAction{

	boolean triggered = false;
	boolean upgraded;
	
	public InertiaAction(boolean upgraded) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    this.upgraded = upgraded;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_FAST;
	    else
	    	this.duration = Settings.ACTION_DUR_MED;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    AbstractPlayer p = AbstractDungeon.player;
	    this.tickDuration();
	    if(!triggered) {
	    	
	    	CustomOrb pot = null;
	    	ArrayList<AbstractOrb> other = new ArrayList<AbstractOrb>();
	    	ArrayList<AbstractOrb> empty = new ArrayList<AbstractOrb>();
	    	
	    	for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof KineticOrb || (upgraded && p.orbs.get(i) instanceof PotentialOrb)) {    				
    				if(pot == null)
    					pot = new PotentialOrb(((CustomOrb)p.orbs.get(i)).potency, ((CustomOrb)p.orbs.get(i)).target);
    				else
	    				pot.potency += ((CustomOrb)p.orbs.get(i)).potency;
	    			empty.add(new EmptyOrbSlot(p.orbs.get(i).cX, p.orbs.get(i).cY));
	    			    			
	    		}else if (p.orbs.get(i) instanceof EmptyOrbSlot){
	    			empty.add(p.orbs.get(i));
	    		}else {
	    			other.add(p.orbs.get(i));
	    		}	    		   		
	    	}
	    	if(pot == null)
	    		return;
            for (int i = 0; i < p.orbs.size(); i++) {
            	if(i < other.size()) {
            		p.orbs.set(i, other.get(i));            		
            	} else if(i == other.size()) {
            		p.orbs.set(i, pot);
            		p.orbs.get(i).cX = empty.get(0).cX;
            		p.orbs.get(i).cY = empty.get(0).cY;
            	} else {
            		p.orbs.set(i, empty.get(i - other.size()));
            	}
                p.orbs.get(i).setSlot(i, p.maxOrbs);                
            }
	    	triggered = true;	
	    }
		
	}
}
