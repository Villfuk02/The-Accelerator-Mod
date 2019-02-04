package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class DynamoPower extends AbstractPower
{
	public static final String NAME = "Dynamo";
	public int energy = 0;
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"Gain #b",
    		" [E] for every 5 Orbs you #yEvoke. NL #b",
    		" Orbs left."
	};
    
    public DynamoPower(final AbstractCreature owner, final int newAmount) {
        this.name = NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = 4;
        this.energy = newAmount;
        this.updateDescription();
        this.img = new Texture(AcceleratorMod.POWER_IMG_PATH + NAME + ".png");
    }  
    
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0] + this.energy + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }  
    
    @Override
    public void onEvokeOrb(AbstractOrb orb){
    	amount--;    	
    	if(amount <= 0) {
    		amount += 5;
    		this.flash();
    		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(energy));
    	}
    	fontScale = 8.0f;
    	this.updateDescription();
    }
    
    @Override
    public void stackPower(int amt) {
    	fontScale = 8.0f;
    	this.energy += amt;
    }
   
}
