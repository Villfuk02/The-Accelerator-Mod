package accelerator.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpgradeHealAction extends AbstractGameAction{
	
	public int healAmt;

	public UpgradeHealAction(int healAmt) {
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    this.healAmt = healAmt;
	}

	@Override
	public void update() {
	    if (this.isDone) return;	
	    AbstractPlayer p = AbstractDungeon.player;
	    int heal = 0;
	    for (AbstractCard c : p.hand.group) {
	    	if(c.canUpgrade()) {
	    		c.upgrade();
	    		c.superFlash(Color.CYAN);
	    		heal += healAmt;
	    	}
	    }
	    if(heal > 0)
	    	AbstractDungeon.actionManager.addToTop(new HealAction(p, p, heal));
	    this.isDone = true;
	}
}
