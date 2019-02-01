package accelerator.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.orbs.KineticOrb;
import accelerator.orbs.MagneticOrb;
import accelerator.orbs.NuclearOrb;
import accelerator.orbs.PotentialOrb;
import accelerator.orbs.ThermalOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Chaos extends CustomCard{
	public static final String ID = "Chaos";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int AMT = 3;

	public Chaos() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF_AND_ENEMY);
		this.baseMagicNumber = this.magicNumber = AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Chaos();
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
		for (int i = 0; i < this.magicNumber; i++) {
			switch(AbstractDungeon.cardRandomRng.random(4)) {
			case 0:
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new KineticOrb(3, null)));
				break;
			case 1:
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new PotentialOrb(3, null)));
				break;
			case 2:
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new MagneticOrb(3)));
				break;
			case 3:
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ThermalOrb(3)));
				break;
			case 4:
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new NuclearOrb(3)));
				break;
			}
		}
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, true));
	}
}
