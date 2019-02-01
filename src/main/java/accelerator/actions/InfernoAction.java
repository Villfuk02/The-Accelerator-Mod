package accelerator.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import accelerator.orbs.ThermalOrb;

public class InfernoAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
	 	private boolean freeToPlayOnce;
	    private int damage;
	    private AbstractPlayer p;
	    private int energyOnUse;
	    
	    public InfernoAction(final AbstractPlayer p, final int damage, final boolean freeToPlayOnce, final int energyOnUse) {
	        this.freeToPlayOnce = false;
	        this.energyOnUse = -1;
	        this.p = p;
	        this.damage = damage;
	        this.freeToPlayOnce = freeToPlayOnce;
	        this.duration = Settings.ACTION_DUR_XFAST;
	        this.actionType = ActionType.SPECIAL;
	        this.energyOnUse = energyOnUse;
	    }
	    
	    @Override
	    public void update() {
	        int effect = EnergyPanel.totalCount;
	        if (this.energyOnUse != -1) {
	            effect = this.energyOnUse;
	        }
	        if (this.p.hasRelic("Chemical X")) {
	            effect += 2;
	            this.p.getRelic("Chemical X").flash();
	        }	        
	        if (effect > 0) {
	        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, effect), effect));
	            for (int i = 0; i < effect; ++i) {
	            	AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ThermalOrb(damage)));
	            }
	            if (!this.freeToPlayOnce) {
	                this.p.energy.use(EnergyPanel.totalCount);
	            }
	        }
	        this.isDone = true;
	    }

}
