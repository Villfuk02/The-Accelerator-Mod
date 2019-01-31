package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import accelerator.orbs.CustomOrb;
import accelerator.vfx.OrbFlareEffect;


public class OrbEffectAction extends AbstractGameAction{
	private CustomOrb orb;
	public boolean triggered;

	public OrbEffectAction(CustomOrb orb) {
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
	    	orb.activateEffectUnique();
            AbstractDungeon.effectsQueue.add(new OrbFlareEffect(orb, orb.inner, orb.outer));
        	AbstractDungeon.player.hand.applyPowers();
	    	triggered = true;	
	    }
		
	}
}
