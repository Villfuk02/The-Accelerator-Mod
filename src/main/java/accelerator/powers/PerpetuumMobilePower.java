package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;
import accelerator.orbs.KineticOrb;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PerpetuumMobilePower extends AbstractPower
{
	public static final String NAME = "PerpetuumMobile";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"At the start of every turn, #yChannel a #yKinetic Orb with #b",
    		" #yPotency."
	};
    
    public PerpetuumMobilePower(final AbstractCreature owner, final int newAmount) {
        this.name = NAME;
        this.ID = AcceleratorMod.PREFIX + NAME;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = new Texture(AcceleratorMod.POWER_IMG_PATH + NAME + ".png");
    }  
    
    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }  
    
    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new ChannelAction(new KineticOrb(amount, null)));
    }
   
}
