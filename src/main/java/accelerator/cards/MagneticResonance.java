package accelerator.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import accelerator.AcceleratorMod;
import accelerator.actions.MagneticResonanceAction;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class MagneticResonance extends CustomCard{
	public static final String ID = "MagneticResonance";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -1;
	private static final int MAGIC = 2;

	public MagneticResonance() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseBlock = 0;
	}

	@Override
	public AbstractCard makeCopy() {
		return new MagneticResonance();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(1);
			
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {	
		if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
		AbstractDungeon.actionManager.addToBottom(new MagneticResonanceAction(p, block, magicNumber, freeToPlayOnce, energyOnUse));
	}
}
