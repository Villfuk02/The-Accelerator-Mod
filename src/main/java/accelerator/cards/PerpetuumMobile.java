package accelerator.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.patches.AbstractCardEnum;
import accelerator.powers.PerpetuumMobilePower;
import basemod.abstracts.CustomCard;

public class PerpetuumMobile extends CustomCard{
	public static final String ID = "PerpetuumMobile";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int POT = 6;
	private static final int UP = 3;

	public PerpetuumMobile() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.POWER, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = this.magicNumber = POT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new PerpetuumMobile();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(UP);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PerpetuumMobilePower(p, this.magicNumber), this.magicNumber)); 
	}
}
