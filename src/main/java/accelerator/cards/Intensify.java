package accelerator.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import accelerator.AcceleratorMod;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Intensify extends CustomCard{
	public static final String ID = "Intensify";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int UP = 1;

	public Intensify() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.POWER, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Intensify();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();	
			this.upgradeBaseCost(UP);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(2));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
	}
}
