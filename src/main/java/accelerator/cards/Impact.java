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
import accelerator.orbs.KineticOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Impact extends CustomCard{
	public static final String ID = "Impact";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 4;
	private static final int DMG = 8;

	public Impact() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DMG;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Impact();
	}
	
	@Override
    public void applyPowers() {
        int g = 0;
        for(AbstractOrb orb :   
    		AbstractDungeon.actionManager.orbsChanneledThisTurn){
        	if(orb instanceof KineticOrb)
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
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {		
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new KineticOrb(this.damage, m)));
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(new KineticOrb(this.damage, m)));
	}
}
