package accelerator.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.actions.Newton3Action;
import accelerator.orbs.KineticOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Newtons3 extends CustomCard{
	public static final String ID = "Newtons3";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;

	public Newtons3() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.baseBlock = 0;
	}
	
	@Override
    public void applyPowers() { 
		AbstractPlayer p = AbstractDungeon.player;
        this.baseBlock = 0;
        if(upgraded) {
	    	int max = -1;
	    	for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof KineticOrb) {
	    			if(((KineticOrb)p.orbs.get(i)).potency > max)
	    				max = ((KineticOrb)p.orbs.get(i)).potency;
	    		}	    		
	    	}
	    	this.baseBlock = max;
    	}else {
    		for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof KineticOrb) {
	    			this.baseBlock = ((KineticOrb)p.orbs.get(i)).potency;
	    			break;
	    		}	    			    		
	    	}
    	}
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void calculateCardDamage(final AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
        this.baseBlock = 0;
        if(upgraded) {
	    	int max = -1;
	    	for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof KineticOrb) {
	    			if(((KineticOrb)p.orbs.get(i)).potency > max)
	    				max = ((KineticOrb)p.orbs.get(i)).potency;
	    		}	    		
	    	}
	    	this.baseBlock = max;
    	}else {
    		for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof KineticOrb) {
	    			this.baseBlock = ((KineticOrb)p.orbs.get(i)).potency;
	    			break;
	    		}	    			    		
	    	}
    	}
		super.calculateCardDamage(m);
        this.initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new Newtons3();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();	
			this.rawDescription = UP_DESCRIPTION;
			initializeDescription();		
		} 
	}	

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new Newton3Action(upgraded));
	}
}
