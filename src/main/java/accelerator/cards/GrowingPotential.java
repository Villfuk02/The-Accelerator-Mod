package accelerator.cards;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.actions.GrowingPotentialAction;
import accelerator.actions.PassiveOrbAction;
import accelerator.orbs.PotentialOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class GrowingPotential extends CustomCard{
	public static final String ID = "GrowingPotential";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;

	public GrowingPotential() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
	}

	@Override
	public AbstractCard makeCopy() {
		return new GrowingPotential();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = UP_DESCRIPTION;
			initializeDescription();
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {	
		AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
		AbstractDungeon.actionManager.addToBottom(new GrowingPotentialAction());
		
		for (int i = 0; i < p.filledOrbCount(); i++) {
    		if(p.orbs.get(i) instanceof PotentialOrb) {	 
    			AbstractDungeon.actionManager.addToBottom(new PassiveOrbAction(p.orbs.get(i)));
            }
        }
	
		if(upgraded) {
			for (int i = 0; i < p.filledOrbCount(); i++) {
	    		if(p.orbs.get(i) instanceof PotentialOrb) {	 
	    			AbstractDungeon.actionManager.addToBottom(new PassiveOrbAction(p.orbs.get(i)));
                }
            }		
		}
	}
}
