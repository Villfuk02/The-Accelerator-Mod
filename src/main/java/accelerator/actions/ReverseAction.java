package accelerator.actions;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReverseAction extends AbstractGameAction{

	boolean triggered = false;
	
	public ReverseAction() {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    if(Settings.FAST_MODE)
	    	this.duration = 0.2f;
	    else
	    	this.duration = 0.5f;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    AbstractPlayer p = AbstractDungeon.player;
	    this.tickDuration();
	    if(!triggered) {
	    	for (int i = 0; i < p.filledOrbCount()/2; i++) {
                Collections.swap(p.orbs, i, p.filledOrbCount() - 1 - i);
            }
            for (int i = 0; i < p.orbs.size(); ++i) {
                p.orbs.get(i).setSlot(i, p.maxOrbs);
            }
	    	triggered = true;	
	    }
		
	}
}
