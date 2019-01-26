package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;
import accelerator.orbs.KineticOrb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnergyTransferPower extends AbstractPower
{
	public static final String NAME = "EnergyTransfer";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"The next time you would lose #rHP, #yChannel a #yKinetic Orb targeted at yourself with #yPotency of the #rHP you would've lost.",
    		"The next #b",
    		" times you would lose #rHP, #yChannel a #yKinetic Orb targeted at yourself with #yPotency of the #rHP you would've lost."
	};
    
    public EnergyTransferPower(final AbstractCreature owner, final int newAmount) {
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
    
    @Override
    public int onLoseHp(final int damageAmount) {
        if (damageAmount > 0) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new KineticOrb(damageAmount, this.owner)));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
        return 0;
    }
   
}
