package accelerator.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import accelerator.AcceleratorMod;
import accelerator.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class TheoryOfEverything extends CustomCard{
	public static final String ID = "TheoryOfEverything";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(AcceleratorMod.PREFIX + ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UP_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;

	public TheoryOfEverything() {
		super(AcceleratorMod.PREFIX + ID, NAME, AcceleratorMod.CARD_IMG_PATH + ID + ".png", COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.ACC,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = 1;
	}

	@Override
	public AbstractCard makeCopy() {
		return new TheoryOfEverything();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.retain = true;
			this.rawDescription = UP_DESCRIPTION;
			this.initializeDescription();
		} 
	}
	

	@Override
    public void applyPowers() {         
        this.baseDamage = 1;
        final ArrayList<String> orbList = new ArrayList<String>();
        for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
                this.baseDamage*=2;
            }
        }
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void calculateCardDamage(final AbstractMonster m) {
		this.baseDamage = 1;
        final ArrayList<String> orbList = new ArrayList<String>();
        for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
                this.baseDamage*=2;
            }
        }
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
		if(damage > 50) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.NONE));
		}else if(damage > 25) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_HEAVY));
		}else {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_LIGHT));  
		}
	}
}
