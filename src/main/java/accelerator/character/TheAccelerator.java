package accelerator.character;

import java.util.ArrayList;

import basemod.abstracts.CustomPlayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import accelerator.AcceleratorMod;
import accelerator.cards.Defend;
import accelerator.cards.Reverse;
import accelerator.cards.Stream;
import accelerator.cards.Strike;
import accelerator.patches.AbstractCardEnum;
import accelerator.patches.TheAcceleratorEnum;
import accelerator.relics.Cloud;

public class TheAccelerator extends CustomPlayer{


	public static final String NAME = "The Orang";

	public TheAccelerator(String name, PlayerClass setClass) {
		super(name, setClass, null, AcceleratorMod.ENERGY_ORB_PATH + "vfx.png", (String)null, (String)null);
		
		initializeClass(null, AcceleratorMod.SHOULDER_2, AcceleratorMod.SHOULDER_1, AcceleratorMod.CORPSE,
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(3));
		
		this.loadAnimation(AcceleratorMod.SKELETON_ATLAS, AcceleratorMod.SKELETON_JSON, 1.0f);
		
		this.energyOrb = new AcceleratorEnergyOrb();
		
		
	}

	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(AcceleratorMod.PREFIX + Strike.ID);
		retVal.add(AcceleratorMod.PREFIX + Strike.ID);
		retVal.add(AcceleratorMod.PREFIX + Strike.ID);
		retVal.add(AcceleratorMod.PREFIX + Stream.ID);
		retVal.add(AcceleratorMod.PREFIX + Stream.ID);
		retVal.add(AcceleratorMod.PREFIX + Defend.ID);
		retVal.add(AcceleratorMod.PREFIX + Defend.ID);
		retVal.add(AcceleratorMod.PREFIX + Defend.ID);
		retVal.add(AcceleratorMod.PREFIX + Defend.ID);
		retVal.add(AcceleratorMod.PREFIX + Reverse.ID);
		retVal.add(AcceleratorMod.PREFIX + Reverse.ID);
		return retVal;
	}
	
	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(AcceleratorMod.PREFIX + Cloud.ID);
		UnlockTracker.markRelicAsSeen(AcceleratorMod.PREFIX + Cloud.ID);
		return retVal;
	}
	
	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME, "Yeah, I meant the #rreal one. NL This one's got the power of the #gOctahedron #gof #gTranscendence.",
				100, 100, 4, 99, 5,
			this, getStartingRelics(), getStartingDeck(), false);
	}

	@Override
	public String getTitle(PlayerClass playerClass) {
		return NAME;
	}

	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.ACC;
	}

	@Override
	public Color getCardRenderColor() {
		return Color.ORANGE;
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new Reverse();
	}

	@Override
	public Color getCardTrailColor() {
		return Color.ORANGE;
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 4;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2f, 0.2f));
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_HEAVY";
	}

	@Override
	public String getLocalizedCharacterName() {
		return NAME;
	}

	@Override
	public AbstractPlayer newInstance() {
		return new TheAccelerator(NAME, TheAcceleratorEnum.THE_ACCELERATOR);
	}

	@Override
	public String getSpireHeartText() {
		return "NL You ready your Weapon...";
	}

	@Override
	public Color getSlashAttackColor() {
		return Color.MAROON;
	}

	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[]{
			AbstractGameAction.AttackEffect.SLASH_DIAGONAL
			, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
			, AbstractGameAction.AttackEffect.SLASH_VERTICAL
			, AbstractGameAction.AttackEffect.SLASH_HEAVY
		};
	}

	//TODO: Character Specific Dialog
	@Override
	public String getVampireText() {
		return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us,~ ~oh Mighty Warrior,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
	}
}
