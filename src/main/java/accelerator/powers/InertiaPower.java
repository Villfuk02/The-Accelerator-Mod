package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InertiaPower extends AbstractPower
{
	public static final String NAME = "Inertia";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"#yKinetic Orbs don't decay this turn.",
    		"#yKinetic Orbs don't decay for #b",
    		" turns."
	};
    
    public InertiaPower(final AbstractCreature owner, final int newAmount) {
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
    public void atStartOfTurnPostDraw() {
    	this.flash();
    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, ID, 1));
    }
}
