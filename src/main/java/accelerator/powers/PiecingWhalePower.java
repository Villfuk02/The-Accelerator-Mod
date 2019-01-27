package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PiecingWhalePower extends AbstractPower
{
	public static final String NAME = "Piecing";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"If you kill this enemy, heal #b",
    		" HP and gain #b",
    		" gold. At the end of this turn, decrease amount by 1."
	};
    
    public PiecingWhalePower(final AbstractCreature owner, final int newAmount) {
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
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }  
    
    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, ID, 1));
        }
    }
    
    @Override
    public void onDeath() {
    	AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, owner, amount));
    	for(int i = 0; i < amount; i++)
    		AbstractDungeon.effectsQueue.add(new GainPennyEffect(owner, owner.hb.cX, owner.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
    	AbstractDungeon.player.gainGold(amount);
    }
}
