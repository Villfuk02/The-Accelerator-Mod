package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;
import accelerator.orbs.KineticOrb;
import accelerator.orbs.ThermalOrb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class FrictionPower extends AbstractPower
{
	public static final String NAME = "Friction";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"Every time you #yEvoke a #yKinetic Orb, #yChannel a #yThermal Orb with the same Potency.",
    		"Every time you #yEvoke a #yKinetic Orb, #yChannel a #yThermal Orb with #b",
    		" times as much Potency."
	};
    
    public FrictionPower(final AbstractCreature owner, final int newAmount) {
        this.name = NAME;
        this.ID = NAME + AcceleratorMod.SUFFIX;
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
    
    @Override
    public void onEvokeOrb(AbstractOrb orb){
    	if(orb instanceof KineticOrb) {
    		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ThermalOrb(((KineticOrb)orb).potency * amount)));
    		this.flash();
    	}
    }
   
}
