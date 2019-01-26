package accelerator.cards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

public class Deflect extends CustomCard{
	public static final String ID = "Deflect";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK = 7;
	private static final int UPGRADE = 3;

	public Deflect() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Deflect();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(UPGRADE);
			
		} 
	}
	

	@Override
    public void applyPowers() {
        int g = 0;
        for(AbstractOrb orb : AbstractDungeon.player.orbs){
        	if(orb instanceof MagneticOrb)
        		g++;
        }  
        this.baseBlock = BLOCK + g;
        if(upgraded)
        	this.baseBlock += UPGRADE;
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void calculateCardDamage(final AbstractMonster m) {
		int g = 0;
        for(AbstractOrb orb : AbstractDungeon.player.orbs){
        	if(orb instanceof MagneticOrb)
        		g++;
        }  
        this.baseBlock = BLOCK + g;
        if(upgraded)
        	this.baseBlock += UPGRADE;
		super.calculateCardDamage(m);
        this.initializeDescription();
	}
    
    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new MagneticOrb(this.block)));  
	}
}
