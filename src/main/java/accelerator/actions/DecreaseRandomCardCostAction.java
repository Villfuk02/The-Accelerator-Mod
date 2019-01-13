package accelerator.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DecreaseRandomCardCostAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    int amt;
    int cycles;

    public DecreaseRandomCardCostAction (int amt, int cycles) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.amt = amt;
        this.cycles = cycles;
    }

    public void update() {
    	final ArrayList<AbstractCard> groupCopy = new ArrayList<AbstractCard>();
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                groupCopy.add(c);
            }
        }
        for (final CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
            if (i.card != null) {
                groupCopy.remove(i.card);
            }
        }
        while (cycles > 0 && !groupCopy.isEmpty()) {
        	AbstractCard c2 = null;          
        	int r = AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1);
	        c2 = groupCopy.remove(r);
	        if (c2 != null) {
	            c2.modifyCostForTurn(-amt);
	            cycles--;
	        }
        }
        this.isDone = true;
    }

}
