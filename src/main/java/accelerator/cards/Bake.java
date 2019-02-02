package accelerator.cards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.actions.PassiveOrbAction;
import accelerator.orbs.ThermalOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Bake extends CustomCard{
	public static final String ID = "Bake";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int DMG = 6;
	private static final int UP = 2;
	private static final int MAGIC = 2;

	public Bake() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = DMG;
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Bake();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(1);
			this.upgradeDamage(UP);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for(int i = 0; i < this.magicNumber; i++) {
			if(p.filledOrbCount() - i - 1 >= 0) {
				AbstractDungeon.actionManager.addToBottom(new PassiveOrbAction(p.orbs.get(p.filledOrbCount() - i - 1)));
			}
		}
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ThermalOrb(this.damage)));
	}
}
