package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;

public class EfficiencyPower extends AbstractPower
{
	public static final String NAME = "Efficiency";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"Every time an Orb loses #yPotency, gain that much #yBlock.",
    		"Every time an Orb loses #yPotency, gain #b",
    		" times as much #yBlock."
	};
    
    public EfficiencyPower(final AbstractCreature owner, final int newAmount) {
        this.name = NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = newAmount;
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
}
