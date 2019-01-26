package accelerator.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.AcceleratorMod;
import accelerator.actions.EntropyAction;
import accelerator.orbs.CustomOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Entropy extends CustomCard{
	public static final String ID = "Entropy";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 0;

	public Entropy() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Entropy();
	}
	
	@Override
    public void applyPowers() {
        super.applyPowers();
    	int count = 0;
    	int potency = 0;
    	for(AbstractOrb o : AbstractDungeon.player.orbs) {
        	if(o instanceof CustomOrb) {
        		count++;
        		potency += ((CustomOrb) o).potency;
        	}
        }	    	
    	potency = (potency+count-1)/count;
        this.rawDescription = getDesc() + EXTENDED_DESCRIPTION[0] + potency + EXTENDED_DESCRIPTION[1];        
        this.initializeDescription();
    }
	
	private String getDesc() {
		if(upgraded)
			return UP_DESCRIPTION;
		return DESCRIPTION;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = UP_DESCRIPTION;
			this.exhaust = false;
			initializeDescription();
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {	
		AbstractDungeon.actionManager.addToBottom(new EntropyAction());
	}
}