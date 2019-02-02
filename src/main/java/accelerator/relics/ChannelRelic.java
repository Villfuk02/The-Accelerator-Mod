package accelerator.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import basemod.abstracts.CustomRelic;

public class ChannelRelic extends CustomRelic {
		
	public ChannelRelic(String ID, Texture tex, RelicTier tier, LandingSound sound) {
		super(ID, tex, tier, sound); 
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
    
	
	public void onChannel(AbstractOrb o) {
		
	}   
	
}