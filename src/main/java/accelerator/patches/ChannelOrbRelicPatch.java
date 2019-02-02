package accelerator.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import accelerator.relics.ChannelRelic;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="channelOrb", paramtypez = {AbstractOrb.class})
public class ChannelOrbRelicPatch {

	@SpirePrefixPatch
	public static void Prefix(AbstractPlayer __instance, AbstractOrb orb) {
		if (__instance.maxOrbs > 0) {            
            int index = -1;
            for (int i = 0; i < __instance.orbs.size(); ++i) {
                if (__instance.orbs.get(i) instanceof EmptyOrbSlot) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                for(AbstractRelic r : __instance.relics) {
                	if(r instanceof ChannelRelic) {
                		((ChannelRelic)r).onChannel(orb);
                	}
                }
            }
        }
	}
	
}