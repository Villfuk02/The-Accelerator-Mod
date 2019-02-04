package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;
import accelerator.orbs.CustomOrb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class VacuumPower extends AbstractPower
{
	public static final String NAME = "Vacuum";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"The next Orb you Channel this turn has twice as much Potency. Works only on Orbs with Potency.",
    		"The next #b", 
    		" Orbs you Channel this turn have twice as much Potency. Works only on Orbs with Potency."
	};
    
    public VacuumPower(final AbstractCreature owner, final int newAmount) {
        this.name = NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = newAmount;
        this.isTurnBased = true;
        this.updateDescription();
        this.img = new Texture(AcceleratorMod.POWER_IMG_PATH + NAME + ".png");
    }  
    
    @Override
    public void updateDescription() {
    	if(amount == 1)
    		this.description = DESCRIPTIONS[0];
    	else
    		this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }  
       
    @Override
    public void onChannel(AbstractOrb orb) {
    	if(orb instanceof CustomOrb) {
    		((CustomOrb) orb).potency *= 2;
    		((CustomOrb) orb).fontScale();
    		orb.updateDescription();
    		this.flash();
    		--this.amount;
    		this.updateDescription();
            if (this.amount == 0)
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));            
        }
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
    
}
