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
import accelerator.orbs.PotentialOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class DarkMatter extends CustomCard{
	public static final String ID = "DarkMatter";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 7;
	private static final int DMG = 20;
	private static final int UPGRADE = 3;

	public DarkMatter() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DMG;
	}

	@Override
	public AbstractCard makeCopy() {
		return new DarkMatter();
	}
	
	@Override
    public void applyPowers() {
        int g = 0;
        for(AbstractOrb orb :   
    		AbstractDungeon.actionManager.orbsChanneledThisCombat){
        	if(orb instanceof PotentialOrb)
        		g++;
        }  
        this.setCostForTurn(this.cost - g);
        super.applyPowers();
        this.initializeDescription();
    }

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(COST-1);
			this.upgradeDamage(UPGRADE);
			
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new PotentialOrb(this.damage, m))); 
	}
}
