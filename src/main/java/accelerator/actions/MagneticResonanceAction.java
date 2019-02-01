package accelerator.actions;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import accelerator.orbs.MagneticOrb;

public class MagneticResonanceAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
	 	private boolean freeToPlayOnce;
	    private AbstractPlayer p;
	    private int energyOnUse;
		private int block;
	    
	    public MagneticResonanceAction(final AbstractPlayer p, final int block, final int amount, final boolean freeToPlayOnce, final int energyOnUse) {
	        this.freeToPlayOnce = false;
	        this.energyOnUse = -1;
	        this.p = p;
	        this.amount = amount;
	        this.block = block;
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
	        effect *= amount;
	        AbstractOrb o = new MagneticOrb(block);
	        AbstractDungeon.actionManager.addToBottom(new ChannelAction(o));
	        if (effect > 0) {
	            for (int i = 0; i < effect; ++i) {
	            	AbstractDungeon.actionManager.addToBottom(new PassiveOrbAction(o));
	            }
	            if (!this.freeToPlayOnce) {
	                this.p.energy.use(EnergyPanel.totalCount);
	            }
	        }
	        this.isDone = true;
	    }

}
