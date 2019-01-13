package accelerator.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import accelerator.orbs.CustomOrb;

@SpirePatch(cls="com.megacrit.cardcrawl.monsters.AbstractMonster", method="die", paramtypez = {boolean.class})
public class MonsterDeathUpdateOrbsPatch {

	@SpirePostfixPatch
	public static void Postfix(AbstractMonster __instance, boolean triggerRelics) {
		if(triggerRelics) {
			for(AbstractOrb orb : AbstractDungeon.player.orbs) {
				if(orb instanceof CustomOrb)
					((CustomOrb)orb).monsterDeath(__instance);
			}
		}
	}
	
}