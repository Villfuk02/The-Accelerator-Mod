package accelerator.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.AcceleratorMod;
import basemod.abstracts.CustomRelic;

public class BetterCloud extends CustomRelic {
	public static final String ID = "BetterCloud";	
	
	public BetterCloud() {
		super(AcceleratorMod.PREFIX + ID, new Texture(AcceleratorMod.RELIC_IMG_PATH + ID + ".png"),
				RelicTier.BOSS, LandingSound.SOLID); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	

	@Override
	public void atBattleStart() {
        this.counter = 2;
    }
	
	@Override
	public void atTurnStart() {
		if(this.counter > 0) {
			this.counter--;
			this.flash();
	        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(2));
	        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}
	
	@Override
    public void onPlayerEndTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1)); 
    }
	
	@Override
    public void onVictory() {
        this.counter = -1;
    }
	
	@Override
    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(Cloud.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Cloud.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
		
	
	@Override
    public boolean canSpawn() {
		return AbstractDungeon.player.hasRelic(Cloud.ID);
	}
    
	
	@Override
	public AbstractRelic makeCopy() {
		return new BetterCloud();
	}
	
}