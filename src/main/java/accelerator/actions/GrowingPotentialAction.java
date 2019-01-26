package accelerator.actions;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import accelerator.orbs.PotentialOrb;

public class GrowingPotentialAction extends AbstractGameAction{

	boolean triggered = false;
	
	public GrowingPotentialAction() {
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
	    	int amt = 0;
	    	for (int i = 0; i < p.filledOrbCount(); i++) {
	    		if(p.orbs.get(i - amt) instanceof PotentialOrb) {	    			
	    			for(int j = i + 1 - amt; j < p.filledOrbCount(); j++)
	    				Collections.swap(p.orbs, j - 1, j);
	    			amt++;
                }
            }
            for (int i = 0; i < p.orbs.size(); ++i) {
                p.orbs.get(i).setSlot(i, p.maxOrbs);
            }
	    	triggered = true;	
	    }
		
	}
}
