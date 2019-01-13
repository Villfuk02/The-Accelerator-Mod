
package accelerator;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import accelerator.cards.Blaze;
import accelerator.cards.Defend;
import accelerator.cards.Deflect;
import accelerator.cards.Friction;
import accelerator.cards.Intensify;
import accelerator.cards.Knockback;
import accelerator.cards.MagneticLink;
import accelerator.cards.MergeSort;
import accelerator.cards.NuclearDecay;
import accelerator.cards.Polarize;
import accelerator.cards.Propulsion;
import accelerator.cards.Reverse;
import accelerator.cards.SafeBoot;
import accelerator.cards.Stream;
import accelerator.cards.Strike;
import accelerator.cards.Threat;
import accelerator.character.TheAccelerator;
import accelerator.patches.AbstractCardEnum;
import accelerator.patches.TheAcceleratorEnum;
import accelerator.relics.Cloud;
import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;

@SpireInitializer
public class AcceleratorMod implements PostExhaustSubscriber,
	PostBattleSubscriber, PostDungeonInitializeSubscriber,
	EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber,
	EditStringsSubscriber, EditRelicsSubscriber{	
	
	public static final Logger logger = LogManager.getLogger(AcceleratorMod.class.getName());
    
    private static final Color COLOR = CardHelper.getColor(255F, 100F, 0F);

    public static final String SUFFIX = ":accelerator";
    
	public static final String RESOURCE_PATH = "accelerator/";
	public static final String IMG_PATH = RESOURCE_PATH + "img/";
	public static final String LOCALIZATION_PATH = RESOURCE_PATH + "localization/";
	public static final String CARD_STRINGS = LOCALIZATION_PATH + "AcceleratorMod-CardStrings.json";
	public static final String RELIC_STRINGS = LOCALIZATION_PATH + "AcceleratorMod-RelicStrings.json";
	public static final String ORB_STRINGS = LOCALIZATION_PATH + "AcceleratorMod-OrbStrings.json";

	public static final String CARD_IMG_PATH = IMG_PATH + "cards/";
	public static final String POWER_IMG_PATH = IMG_PATH + "powers/";
	public static final String RELIC_IMG_PATH = IMG_PATH + "relics/";

	// card backgrounds
	private static final String ATTACK_BLACK = IMG_PATH + "512/bg_attack_black.png";
	private static final String SKILL_BLACK = IMG_PATH + "512/bg_skill_black.png";
	private static final String POWER_BLACK = IMG_PATH + "512/bg_power_black.png";
	private static final String ENERGY_ORB_BLACK = IMG_PATH + "512/card_black_orb.png";

	private static final String ATTACK_BLACK_PORTRAIT = IMG_PATH + "1024/bg_attack_black.png";
	private static final String SKILL_BLACK_PORTRAIT = IMG_PATH + "1024/bg_skill_black.png";
	private static final String POWER_BLACK_PORTRAIT = IMG_PATH + "1024/bg_power_black.png";
	private static final String ENERGY_ORB_BLACK_PORTRAIT = IMG_PATH + "1024/card_black_orb.png";

	private static final String ENERGY_ORB_IN_DESCRIPTION = IMG_PATH + "energy/energyOrbInDescription.png";

	private static final String BUTTON = IMG_PATH + "charSelect/classButton.png";
	private static final String PORTRAIT = IMG_PATH + "charSelect/classPortrait.jpg";

	public static final String CHARACTER_PATH = IMG_PATH + "char/accelerator/";
	public static final String SHOULDER_1 = CHARACTER_PATH + "shoulder.png";
	public static final String SHOULDER_2 = CHARACTER_PATH + "shoulder2.png";
	public static final String CORPSE = CHARACTER_PATH + "corpse.png";
	public static final String SKELETON_ATLAS = CHARACTER_PATH + "skeleton.atlas";
	public static final String SKELETON_JSON = CHARACTER_PATH + "skeleton.json";
	public static final String ANIMATION_PATH = CHARACTER_PATH + "anim/Construct.scml";
	
	public static final String ORB_TEXTURE_PATH = IMG_PATH + "orbs/";
	
		
	public AcceleratorMod() {
		BaseMod.subscribe(this);
		BaseMod.addColor(
			AbstractCardEnum.ACC
			, COLOR
			, ATTACK_BLACK
			, SKILL_BLACK
			, POWER_BLACK
			, ENERGY_ORB_BLACK
			, ATTACK_BLACK_PORTRAIT
			, SKILL_BLACK_PORTRAIT
			, POWER_BLACK_PORTRAIT
			, ENERGY_ORB_BLACK_PORTRAIT
			, ENERGY_ORB_IN_DESCRIPTION
		);
	}
	
	public static void initialize() {
		new AcceleratorMod();
	}
	
	@Override
	public void receivePostExhaust(AbstractCard c) {
		
	}
	
	@Override
	public void receivePostBattle(AbstractRoom r) {
		
	}
	
	@Override
	public void receivePostDungeonInitialize() {
		
	}	

	@Override
	public void receiveEditCharacters() {
		BaseMod.addCharacter(
			new TheAccelerator(
				TheAccelerator.NAME
				, TheAcceleratorEnum.THE_ACCELERATOR
			)
			,BUTTON
			, PORTRAIT,
			TheAcceleratorEnum.THE_ACCELERATOR
		);
	}
	
	@Override
	public void receiveEditStrings() {
		
		// RelicStrings
        String relicStrings = Gdx.files.internal(RELIC_STRINGS).readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		// CardStrings
        String cardStrings = Gdx.files.internal(CARD_STRINGS).readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        
        //OrbStrings
        String orbStrings = Gdx.files.internal(ORB_STRINGS).readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
        
	}
	
	@Override
	public void receiveEditRelics() {
		logger.info("Adding Accelerator Relics");		
		BaseMod.addRelicToCustomPool(new Cloud(), AbstractCardEnum.ACC);
	}
	
	@Override
	public void receiveEditCards() {
		logger.info("Adding Accelerator Cards");
		
		
		// COLORLESS ()
		
		// BASIC (4)
		BaseMod.addCard(new Defend());
		BaseMod.addCard(new Strike());
		BaseMod.addCard(new Stream());
		BaseMod.addCard(new Reverse());
		
		// COMMON (4)
		//	Attacks (3)
		BaseMod.addCard(new Propulsion());
		BaseMod.addCard(new Threat());
		BaseMod.addCard(new Knockback());
		
		//	Skills (1)
		BaseMod.addCard(new Deflect());
		
		
		// UNCOMMON (5)
		// 	Attacks(1)
		BaseMod.addCard(new Blaze());
		
		//	Skills (3)
		BaseMod.addCard(new SafeBoot());
		BaseMod.addCard(new MagneticLink());
		BaseMod.addCard(new Polarize());
		
		// 	Powers (1)
		BaseMod.addCard(new Intensify());
		
		// RARE (3)
		//	Attacks (1)
		BaseMod.addCard(new NuclearDecay());
		
		//	Skills (1)
		BaseMod.addCard(new MergeSort());
		
		//	Powers (1)
		BaseMod.addCard(new Friction());
		
	}

	@Override
	public void receiveEditKeywords() {	
		BaseMod.addKeyword(new String[]{"potential"}, "#yOrb: Increases it's #yPotency every turn and deals #yDamage to the selected enemy when #yEvoked.");
		BaseMod.addKeyword(new String[]{"kinetic"}, "#yOrb: Loses it's #yPotency every turn and deals #yDamage to the selected enemy when #yEvoked.");
		BaseMod.addKeyword(new String[]{"magnetic"}, "#yOrb: Increases it's #yPotency every turn. When it's #yEvoked, you gain #yBlock and #yEvoke the next Orb.");
		BaseMod.addKeyword(new String[]{"thermal"}, "#yOrb: Swaps it's position with the Orb in front of it every turn and deals #yDamage to ALL enemies when #yEvoked.");
		BaseMod.addKeyword(new String[]{"nuclear"}, "#yOrb: When #yEvoked, Deals #yDamage to a random enemy and #yChannels 2 #yNuclear Orbs with half the Potency. Damage dealt by this Orb is #rNOT considered as Attack damage.");
	}
	
}