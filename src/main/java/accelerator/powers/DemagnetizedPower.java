package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;

public class DemagnetizedPower extends AbstractPower
{
	public static final String NAME = "Demagnetized";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"#yMagnetic Orbs no longer #yEvoke other #yMagnetic Orbs."
	};
    
    public DemagnetizedPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = -1;
        this.updateDescription();
        this.img = new Texture(AcceleratorMod.POWER_IMG_PATH + NAME + ".png");
    }  
    
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0];
    }  
   
}
