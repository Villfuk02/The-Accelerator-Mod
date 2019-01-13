package accelerator.relics;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.AcceleratorMod;
import basemod.abstracts.CustomRelic;

public class Cloud extends CustomRelic {
	public static final String ID = "Cloud";
	
	public Cloud() {
		super(ID + AcceleratorMod.SUFFIX, new Texture(AcceleratorMod.RELIC_IMG_PATH + ID + ".png"),
				RelicTier.STARTER, LandingSound.MAGICAL); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
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