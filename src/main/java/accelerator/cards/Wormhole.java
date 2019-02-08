package accelerator.cards;

import java.util.Collections;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;

import accelerator.AcceleratorMod;
import accelerator.orbs.CustomOrb;
import accelerator.orbs.KineticOrb;
import accelerator.orbs.PotentialOrb;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Wormhole extends CustomCard{
	public static final String ID = "Wormhole";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;

	public Wormhole() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Wormhole();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			rawDescription = UP_DESCRIPTION;
			initializeDescription();
		} 
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for(int i = 0; i < p.orbs.size(); i++) {
			if(p.orbs.get(i) instanceof KineticOrb || p.orbs.get(i) instanceof PotentialOrb) {
				CustomOrb o = (CustomOrb) p.orbs.get(i);
				o.target = m;
	    		o.fontScale();
	            AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(o.cX, o.cY));
				o.updateDescription();	            
			}
		}
		if(upgraded) {
			for (int i = 0; i < p.orbs.size(); i++) {
	    		if(p.orbs.get(i) instanceof KineticOrb || p.orbs.get(i) instanceof PotentialOrb) {
	    			p.orbs.get(i).onEvoke();
	    	        final AbstractOrb orbSlot = new EmptyOrbSlot();
	    	        for (int j = i+1; j < p.orbs.size(); ++j) {
	    	            Collections.swap(p.orbs, j, j - 1);
	    	        }
	    	        p.orbs.set(p.orbs.size() - 1, orbSlot);	    				
	    	        for (int j = 0; j < p.orbs.size(); ++j) {
		                p.orbs.get(j).setSlot(j, p.maxOrbs);                
		            }
	    		}	    		
	    	}
        }
	}
}
