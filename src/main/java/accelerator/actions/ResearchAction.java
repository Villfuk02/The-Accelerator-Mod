package accelerator.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ResearchAction extends AbstractGameAction{

	boolean triggered = false;
	
	public ResearchAction() {
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
	    	if (AbstractDungeon.player.drawPile.isEmpty()) {
	            triggered = true;
	            return;
	        }	    	
	        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
	        if (card.type != AbstractCard.CardType.SKILL) {
	            AbstractDungeon.actionManager.addToTop(new ResearchAction());
	        }
	        AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player,1));
	    	triggered = true;	
	    }
		
	}
}
