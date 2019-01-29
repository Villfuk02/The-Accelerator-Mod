package accelerator.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import accelerator.AcceleratorMod;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SpinPower extends AbstractPower
{
	public static final String NAME = "Spin";
	
    public static final String[] DESCRIPTIONS = new String[] {
    		"Every time you play a #ySkill, draw #b1 card.",
    		"Every time you play a #ySkill, draw #b",
    		" cards."
	};
    
    public SpinPower(final AbstractCreature owner, final int newAmount) {
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
    public void onPlayCard(AbstractCard c, AbstractMonster m){
    	if(c.type == CardType.SKILL) {
    		this.flash();
    		AbstractDungeon.actionManager.addToTop(new DrawCardAction(owner, amount));
    	}
    }
}
