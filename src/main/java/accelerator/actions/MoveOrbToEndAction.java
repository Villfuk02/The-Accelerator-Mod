package accelerator.actions;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoveOrbToEndAction extends AbstractGameAction{

	boolean triggered = false;
	int slot = 0;
	
	public MoveOrbToEndAction(int slot) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    this.slot = slot;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_XFAST;
	    else
	    	this.duration = Settings.ACTION_DUR_FAST;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    AbstractPlayer p = AbstractDungeon.player;
	    this.tickDuration();
	    if(!triggered) {
	    	for (int i = slot + 1; i < p.filledOrbCount(); i++) {
                Collections.swap(p.orbs, i, i-1);
            }
            for (int i = 0; i < p.orbs.size(); ++i) {
                p.orbs.get(i).setSlot(i, p.maxOrbs);
            }
	    	triggered = true;	
	    }
		
	}
}
