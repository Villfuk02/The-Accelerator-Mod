package accelerator.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import accelerator.AcceleratorMod;
import accelerator.orbs.KineticOrb;
import accelerator.orbs.PotentialOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Ground extends CustomCard{
	public static final String ID = "Ground";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;
	private static final int DMG = 8;
	private static final int UP = 3;

	public Ground() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DMG;
		
	}

	@Override
	public AbstractCard makeCopy() {
		return new Ground();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();	
			this.upgradeDamage(UP);
			this.rawDescription = UP_DESCRIPTION;
			initializeDescription();		
		} 
	}	

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if(upgraded) {
			AbstractDungeon.actionManager.addToBottom(new ChannelAction(new PotentialOrb(this.damage, m)));
			AbstractDungeon.actionManager.addToBottom(new ChannelAction(new KineticOrb(this.damage, m)));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 2, false), 2));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), 2));
		}else {
			AbstractDungeon.actionManager.addToBottom(new ChannelAction(new KineticOrb(this.damage, m)));
			AbstractDungeon.actionManager.addToBottom(new ChannelAction(new PotentialOrb(this.damage, m)));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), 2));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 2, false), 2));
		}
		
	}
}
