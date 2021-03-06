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
import accelerator.powers.InertiaPower;
import basemod.abstracts.CustomCard;

public class Newtons1 extends CustomCard{
	public static final String ID = "Newtons1";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int MAGIC = 2;
	private static final int UP = 1;

	public Newtons1() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = MAGIC;
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new Newtons1();
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
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InertiaPower(p, this.magicNumber), this.magicNumber));
	}
}
