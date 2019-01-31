package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.orbs.CustomOrb;
import accelerator.vfx.OrbFlareEffect;


public class PassiveOrbAction extends AbstractGameAction{
	private AbstractOrb orb;
	public boolean triggered;

	public PassiveOrbAction(AbstractOrb orb) {
	    this.actionType = AbstractGameAction.ActionType.WAIT;
	    this.orb = orb;
	    triggered = false;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_XFAST;
	    else
	    	this.duration = Settings.ACTION_DUR_FAST;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    this.tickDuration();
	    if(!triggered) {
	    	if(orb instanceof CustomOrb) {
	    		((CustomOrb) orb).activateEffectUnique();
	    		AbstractDungeon.effectsQueue.add(new OrbFlareEffect(orb, ((CustomOrb) orb).inner, ((CustomOrb) orb).outer));
	        	AbstractDungeon.player.hand.applyPowers();
            }else {
            	orb.onStartOfTurn();
            	orb.onEndOfTurn();
            }
	    	triggered = true;	
	    }
		
	}
}
