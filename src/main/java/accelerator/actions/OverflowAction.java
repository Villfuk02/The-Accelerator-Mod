package accelerator.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OverflowAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    boolean combat;

    public OverflowAction (boolean upgraded) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.combat = upgraded;
    }

    public void update() {
    	for (AbstractCard c : AbstractDungeon.player.hand.group) {
	        if (c.cost >= 3 || c.costForTurn >= 3) {
	        	if(combat)
		            c.modifyCostForCombat(-999);
	        	else
	        		c.freeToPlayOnce = true;
	        	c.superFlash();
	        }
	    }
    	for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
	        if (c.cost >= 3 || c.costForTurn >= 3) {
	        	if(combat)
		            c.modifyCostForCombat(-999);
	        	else
	        		c.freeToPlayOnce = true;
	        	c.superFlash();
	        }
	    }
    	for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
	        if (c.cost >= 3 || c.costForTurn >= 3) {
	        	if(combat)
		            c.modifyCostForCombat(-999);
	        	else
	        		c.freeToPlayOnce = true;
	        	c.superFlash();
	        }
	    }
    	for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
	        if (c.cost >= 3 || c.costForTurn >= 3) {
	        	if(combat)
		            c.modifyCostForCombat(-999);
	        	else
	        		c.freeToPlayOnce = true;
	        	c.superFlash();
	        }
	    }
        this.isDone = true;
    }

}
