package accelerator.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.orbs.MagneticOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Exchange extends CustomCard{
	public static final String ID = "Exchange";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK = 6;
	private static final int UP = 2;

	public Exchange() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Exchange();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();	
			this.upgradeBlock(UP);
			this.rawDescription = UP_DESCRIPTION;
			initializeDescription();		
		} 
	}	

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {	
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new MagneticOrb(this.block)));
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, !upgraded));
		AbstractDungeon.actionManager.addToBottom(new DiscardPileToHandAction(1));		
	}
}
