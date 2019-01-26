package accelerator.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.AcceleratorMod;
import accelerator.actions.PassiveOrbAction;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Acceleration extends CustomCard{
	public static final String ID = "Acceleration";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADE = 0;

	public Acceleration() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Acceleration();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(UPGRADE);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for(AbstractOrb o : p.orbs) {
			AbstractDungeon.actionManager.addToBottom(new PassiveOrbAction(o));
		}
	}
}
