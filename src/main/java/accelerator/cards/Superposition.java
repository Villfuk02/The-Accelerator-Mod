package accelerator.cards;

import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.actions.ChangePotencyAction;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Superposition extends CustomCard{
	public static final String ID = "Superposition";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int MAGIC = 2;
	private static final int UPGRADE = 1;

	public Superposition() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Superposition();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(UPGRADE);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		for (int i = 0; i < this.magicNumber; i++) {
	        AbstractDungeon.actionManager.addToBottom(new EvokeWithoutRemovingOrbAction(1));
		}		
		AbstractDungeon.actionManager.addToBottom(new ChangePotencyAction(p.orbs.get(0), -5));
	}
}