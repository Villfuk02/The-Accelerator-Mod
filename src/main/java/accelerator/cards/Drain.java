package accelerator.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import accelerator.AcceleratorMod;
import accelerator.actions.DrainAction;
import accelerator.orbs.PotentialOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Drain extends CustomCard{
	public static final String ID = "Drain";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int MAGIC = 80;
	private static final int UPGRADE = 40;

	public Drain() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseDamage = 0;
	}
	
	@Override
    public void applyPowers() { 
		AbstractPlayer p = AbstractDungeon.player;
        this.baseDamage = 0;
        int max = 0;
	    for (int i = 0; i < p.orbs.size(); i++) {
	    	if(p.orbs.get(i) instanceof PotentialOrb) {
	    		if(((PotentialOrb)p.orbs.get(i)).potency > max)
	    			max = ((PotentialOrb)p.orbs.get(i)).potency;
	    	}	    		
	    }
	    this.baseDamage = (max * this.magicNumber)/100;
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void calculateCardDamage(final AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
        this.baseDamage = 0;
        int max = 0;
	    for (int i = 0; i < p.orbs.size(); i++) {
	    	if(p.orbs.get(i) instanceof PotentialOrb) {
	    		if(((PotentialOrb)p.orbs.get(i)).potency > max)
	    			max = ((PotentialOrb)p.orbs.get(i)).potency;
	    	}	    		
	    }
	    this.baseDamage = (max * this.magicNumber)/100;
		super.calculateCardDamage(m);
        this.initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new Drain();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeMagicNumber(UPGRADE);
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.BLUNT_HEAVY));		
		AbstractDungeon.actionManager.addToBottom(new DrainAction(this.magicNumber, m));
	}
}
