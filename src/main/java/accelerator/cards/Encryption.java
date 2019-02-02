package accelerator.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.AcceleratorMod;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Encryption extends CustomCard{
	public static final String ID = "Encryption";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int BLOCK = 5;
	private static final int UP = 3;

	public Encryption() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = BLOCK;
		this.baseMagicNumber = this.magicNumber = 0;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Encryption();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBlock(UP);
		} 
	}
	

	@Override
    public void applyPowers() { 
        final ArrayList<String> orbList = new ArrayList<String>();
        for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
                this.magicNumber++;
            }
        }
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void calculateCardDamage(final AbstractMonster m) {
        final ArrayList<String> orbList = new ArrayList<String>();
        for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
                this.magicNumber++;
            }
        }
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
		for(int i = 0; i < this.magicNumber; i++)
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,block)); 		
	}
}
