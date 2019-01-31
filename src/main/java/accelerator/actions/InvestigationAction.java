package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

public class InvestigationAction extends AbstractGameAction{

	boolean triggered = false;
	
	public InvestigationAction() {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    triggered = false;
	    if(Settings.FAST_MODE)
	    	this.duration = Settings.ACTION_DUR_XFAST;
	    else
	    	this.duration = Settings.ACTION_DUR_FASTER;
	    
	    if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.triggered = true;
            this.isDone = true;
        }
	}

	@Override
	public void update() {
	    if (this.isDone) return;
	    this.tickDuration();
	    if(!triggered) {
	    	
	    	if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {	    		
	    		this.isDone = true;
	    		return;
	        }
	    	
	    	if (AbstractDungeon.player.drawPile.isEmpty()) {
	    		if (AbstractDungeon.player.discardPile.size() > 0) {
	    			AbstractDungeon.actionManager.addToTop(new InvestigationAction());
	                AbstractDungeon.actionManager.addToTop(new ShuffleAction(AbstractDungeon.player.drawPile, false));
	                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
	            }
	    		this.isDone = true;
	    		return;
	        }
	    	
	        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
	        if (card.costForTurn != 0) {
	            AbstractDungeon.actionManager.addToTop(new InvestigationAction());
	        }
	        AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player,1));
	    	triggered = true;	
	    }
		
	}
}
