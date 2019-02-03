package accelerator.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;

import accelerator.AcceleratorMod;
import accelerator.orbs.ThermalOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Overheat extends CustomCard{
	public static final String ID = "Overheat";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;

	public Overheat() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Overheat();
	}
	
		@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.exhaust = false;
			this.rawDescription = UP_DESCRIPTION;
			initializeDescription();
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		for(int i = 0; i < p.orbs.size(); i++){
			if(p.orbs.get(i) instanceof ThermalOrb) {
				float x = p.orbs.get(i).cX;
				float y = p.orbs.get(i).cY;
				AbstractOrb o = new Plasma();
				o.cX = x;
				o.cY = y;
				p.orbs.set(i, o);
				p.orbs.get(i).setSlot(i, p.maxOrbs);
			}
		}
	}
}
