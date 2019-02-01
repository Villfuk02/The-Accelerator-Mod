package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;
import accelerator.actions.DecreaseRandomCardCostAction;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;

public class GeneratorPower extends AbstractPower
{
	public static final String NAME = "Fission";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"Every time you #yEvoke #yPlasma, decrease the cost of a random card in your hand by 1 for this combat.",
    		"Every time you #yEvoke #yPlasma, decrease the cost of #b",
    		" random cards in your hand by 1 for this combat."
	};
    
    public GeneratorPower(final AbstractCreature owner, final int newAmount) {
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
    public void onEvokeOrb(AbstractOrb orb){
    	if(orb instanceof Plasma) {
    		AbstractDungeon.actionManager.addToTop(new DecreaseRandomCardCostAction(1, amount, true));
    	}
    }   
}
