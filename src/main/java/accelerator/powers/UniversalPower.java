package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import accelerator.AcceleratorMod;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class UniversalPower extends AbstractPower
{
	public static final String NAME = "Universal";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"At the start of every turn, gain #yStrength and #yDexterity equal to the amount of unique Orbs you have."
	};
    
    public UniversalPower(final AbstractCreature owner) {
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
    
    @Override
    public void atStartOfTurnPostDraw() {
    	this.flash();
    	int d = 0;
        final ArrayList<String> orbList = new ArrayList<String>();
        for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
                d++;
            }
        }
        
        if(d > 0) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner,d), d));
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner,d), d));
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new DexterityPower(owner,d), d));
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner,d), d));
        }
    }
   
}
