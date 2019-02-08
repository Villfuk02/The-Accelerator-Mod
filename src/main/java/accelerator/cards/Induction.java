package accelerator.cards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.orbs.MagneticOrb;
import accelerator.orbs.ThermalOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Induction extends CustomCard{
	public static final String ID = "Induction";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int POT = 5;
	private static final int UP = 1;

	public Induction() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL);
		this.magicNumber = this.baseMagicNumber = POT;
		this.baseDamage = 0;
		this.baseBlock = 0;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Induction();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.isInnate = true;
			this.upgradeMagicNumber(UP);
			this.rawDescription = UP_DESCRIPTION;
			this.initializeDescription();
		} 
	}		

	@Override
    public void applyPowers() {         
		this.baseDamage = 0;
		this.baseBlock = 0;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.baseDamage++;
                this.baseBlock++;
            }
        }
        this.baseDamage *= this.magicNumber;
        this.baseBlock *= this.magicNumber;
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void calculateCardDamage(final AbstractMonster m) {
		this.baseDamage = 0;
		this.baseBlock = 0;
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.baseDamage++;
                this.baseBlock++;
            }
        }
        this.baseDamage *= this.magicNumber;
        this.baseBlock *= this.magicNumber;
		super.calculateCardDamage(m);
        this.initializeDescription();
	}
    
    @Override
    public void onMoveToDiscard() {
        this.rawDescription = upgraded?UP_DESCRIPTION:DESCRIPTION;
        this.initializeDescription();
    }

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new MagneticOrb(this.block))); 	
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ThermalOrb(this.block))); 
	}
}
