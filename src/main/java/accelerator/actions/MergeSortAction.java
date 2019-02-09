package accelerator.actions;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import accelerator.AcceleratorMod;
import accelerator.orbs.CustomOrb;

public class MergeSortAction extends AbstractGameAction{

	boolean triggered = false;
	
	public MergeSortAction() {
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
	    	
	    	ArrayList<CustomOrb> types = new ArrayList<CustomOrb>();
	    	ArrayList<AbstractOrb> nonCustom = new ArrayList<AbstractOrb>();
	    	ArrayList<AbstractOrb> empty = new ArrayList<AbstractOrb>();
	    	
	    	for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof CustomOrb) {
    				boolean found = false;
    				
    				for (int j = 0; j < types.size(); j++) {
	    				
	    				if(types.get(j).orbID == ((CustomOrb)p.orbs.get(i)).orbID) {
	    					types.get(j).potency += ((CustomOrb)p.orbs.get(i)).potency;
	    					empty.add(new EmptyOrbSlot(p.orbs.get(i).cX, p.orbs.get(i).cY));
	    					found = true;
	    					break;
	    				}
	    			}
    				
    				if(!found) {
    					types.add((CustomOrb) p.orbs.get(i));
    				}
	    			
	    		}else {
	    			nonCustom.add(p.orbs.get(i));
	    		}
	    		
	    		Collections.sort(types, (CustomOrb o1, CustomOrb o2)->o1.potency-o2.potency);	    		   		
	    	}
	    	
	    	AcceleratorMod.logger.debug(types.size() + "," + nonCustom.size() + "," + empty.size() + "/" + p.orbs.size());
	    	
            for (int i = 0; i < p.orbs.size(); i++) {
            	if(i < types.size())
            		p.orbs.set(i, types.get(i));
            	else if(i < types.size() + nonCustom.size())
            		p.orbs.set(i, nonCustom.get(i - types.size()));
            	else
            		p.orbs.set(i, empty.get(i - types.size() - nonCustom.size()));
                p.orbs.get(i).setSlot(i, p.maxOrbs);                
            }
	    	triggered = true;	
	    }
		
	}
}
