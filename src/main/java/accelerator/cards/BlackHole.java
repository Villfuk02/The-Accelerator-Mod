package accelerator.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

import accelerator.AcceleratorMod;
import accelerator.orbs.PotentialOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class BlackHole extends CustomCard{
	public static final String ID = "BlackHole";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 3;
	private static final int DMG = 24;

	public BlackHole() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DMG;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new BlackHole();
	}
	
	@Override
    public boolean canUpgrade() {
        return true;
    }

	@Override
	public void upgrade() {
		this.upgraded = true;
		this.timesUpgraded++;
		this.name = NAME + "+" + timesUpgraded;
		this.initializeTitle();
		this.upgradeDamage(this.baseDamage/6);
		
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {	
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), 2.0f));
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new PotentialOrb(this.damage, m)));     
	}
}
