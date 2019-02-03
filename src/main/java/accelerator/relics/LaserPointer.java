package accelerator.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.AcceleratorMod;
import accelerator.character.TheAccelerator;
import basemod.abstracts.CustomRelic;

public class LaserPointer extends CustomRelic {
	public static final String ID = "LaserPointer";
		
	public LaserPointer() {
		super(AcceleratorMod.PREFIX + ID, new Texture(AcceleratorMod.RELIC_IMG_PATH + ID + ".png"),
				RelicTier.RARE, LandingSound.CLINK); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
    public void atTurnStart() {
        this.counter = 0;
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            ++this.counter;
            if (this.counter % 4 == 0) {
                this.counter = 0;
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
            }
        }
    }
    
    @Override
    public void onVictory() {
        this.counter = -1;
    }
    
    @Override
    public boolean canSpawn() {
    	if(AbstractDungeon.player instanceof Defect || AbstractDungeon.player instanceof TheAccelerator)
    		return true;
    	return false;
    }
    
	
	@Override
	public AbstractRelic makeCopy() {
		return new LaserPointer();
	}
	
}