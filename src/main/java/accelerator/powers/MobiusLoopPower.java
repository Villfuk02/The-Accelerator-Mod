package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;
import accelerator.actions.MobiusLoopAction;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MobiusLoopPower extends AbstractPower
{
	public static final String NAME = "MöbiusLoop";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"At the start of every turn, trigger the passive ability of your leftmost Orb #b",
    		" times."
	};
    
    public MobiusLoopPower(final AbstractCreature owner, final int newAmount) {
        this.name = NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.priority = -99;
        this.img = new Texture(AcceleratorMod.POWER_IMG_PATH + NAME + ".png");
    }  
    
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }  
    
    @Override
    public void atStartOfTurn() {
    	this.flash();
    	AbstractDungeon.actionManager.addToTop(new MobiusLoopAction(amount));
    }
   
}
