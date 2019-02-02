package accelerator.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.AcceleratorMod;
import accelerator.actions.PassiveOrbAction;
import accelerator.character.TheAccelerator;

public class AutoCompiler extends ChannelRelic {
	public static final String ID = "AutoCompiler";
		
	public AutoCompiler() {
		super(AcceleratorMod.PREFIX + ID, new Texture(AcceleratorMod.RELIC_IMG_PATH + ID + ".png"),
				RelicTier.RARE, LandingSound.MAGICAL); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
    
	@Override
	public void onChannel(AbstractOrb o) {
		this.flash();
		AbstractDungeon.actionManager.addToTop(new PassiveOrbAction(o));
	}
    
    @Override
    public boolean canSpawn() {
    	if(AbstractDungeon.player instanceof Defect || AbstractDungeon.player instanceof TheAccelerator)
    		return true;
    	return false;
    }
    
	
	@Override
	public AbstractRelic makeCopy() {
		return new AutoCompiler();
	}
	
}