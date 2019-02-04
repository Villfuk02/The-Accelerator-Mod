package accelerator.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.AcceleratorMod;
import accelerator.orbs.MagneticOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Fragmentation extends CustomCard{
	public static final String ID = "Fragmentation";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int POT = 3;
	private static final int UPGRADE = 2;

	public Fragmentation() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = POT;
		this.baseBlock = POT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Fragmentation();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(UPGRADE);
			this.upgradeBlock(UPGRADE);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		for(AbstractOrb o : p.orbs) {
			if(o instanceof MagneticOrb) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.SMASH, true));
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block, true));
			}
		}
	}
}
