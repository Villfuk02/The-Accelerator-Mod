package accelerator.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.AcceleratorMod;
import basemod.abstracts.CustomRelic;

public class Cloud extends CustomRelic {
	public static final String ID = "Cloud";
	
	boolean firstTurn = true;
	
	public Cloud() {
		super(AcceleratorMod.PREFIX + ID, new Texture(AcceleratorMod.RELIC_IMG_PATH + ID + ".png"),
				RelicTier.STARTER, LandingSound.MAGICAL); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public void atBattleStart() {
		firstTurn = true;
	}
	
	@Override
    public void atTurnStart() {
        if (this.firstTurn ) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 3));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.firstTurn = false;
        }
    }
	
	@Override
    public void onPlayerEndTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1)); 
    }
    
	
	@Override
	public AbstractRelic makeCopy() {
		return new Cloud();
	}
	
}