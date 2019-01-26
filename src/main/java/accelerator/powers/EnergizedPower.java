package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnergizedPower extends AbstractPower
{
	public static final String NAME = "Energized";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"At the start of next turn, gain #b",
    		"At the start of next turn, lose #b",
    		" #yEnergy."
	};
    
    public EnergizedPower(final AbstractCreature owner, final int newAmount) {
    	this.name =  NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = newAmount;
        this.canGoNegative = true;
        this.isTurnBased = true;
        this.updateDescription();
        this.img = new Texture(AcceleratorMod.POWER_IMG_PATH + NAME + ".png");
    }  
    
    @Override
    public void updateDescription() {
    	if(this.amount < 0) {
    		this.description = DESCRIPTIONS[1] + -this.amount + DESCRIPTIONS[2];
    	}else {
        	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];    		
    	}
    }   
    
    @Override
    public void atStartOfTurn() {
    	AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(amount));
    	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
    }
}
