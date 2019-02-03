package accelerator.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.AcceleratorMod;

public class HolographicScroll extends ChannelRelic {
	public static final String ID = "HolographicScroll";
		
	public HolographicScroll() {
		super(AcceleratorMod.PREFIX + ID, new Texture(AcceleratorMod.RELIC_IMG_PATH + ID + ".png"),
				RelicTier.UNCOMMON, LandingSound.MAGICAL); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
    public void atTurnStart() {
        this.counter = 0;
    }
    
	@Override
	public void onChannel(AbstractOrb orb) {
		final ArrayList<String> orbList = new ArrayList<String>();
        for (final AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisTurn) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {
                orbList.add(o.ID);
            }
        }
        if (orb.ID != null && !orb.ID.equals("Empty") && !orbList.contains(orb.ID)) {
            orbList.add(orb.ID);
        }
        if(counter != orbList.size() % 4) {
        	counter = orbList.size() % 4;
        	if(counter == 0 && orbList.size() > 0) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
        	}
        }
	}
    
	@Override
    public void onVictory() {
        this.counter = -1;
    }
	
	@Override
	public AbstractRelic makeCopy() {
		return new HolographicScroll();
	}
	
}