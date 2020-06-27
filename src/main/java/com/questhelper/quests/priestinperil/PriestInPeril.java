package com.questhelper.quests.priestinperil;

import com.questhelper.ItemRequirement;
import com.questhelper.QuestDescriptor;
import com.questhelper.QuestHelperQuest;
import com.questhelper.Zone;
import com.questhelper.panel.PanelDetails;
import com.questhelper.questhelpers.BasicQuestHelper;
import com.questhelper.steps.ConditionalStep;
import com.questhelper.steps.NpcTalkStep;
import com.questhelper.steps.ObjectStep;
import com.questhelper.steps.QuestStep;
import com.questhelper.steps.conditional.ConditionForStep;
import com.questhelper.steps.conditional.Conditions;
import com.questhelper.steps.conditional.ItemRequirementCondition;
import com.questhelper.steps.conditional.ObjectCondition;
import com.questhelper.steps.conditional.ZoneCondition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.ObjectID;
import net.runelite.api.coords.WorldPoint;

@QuestDescriptor(
	quest = QuestHelperQuest.PRIEST_IN_PERIL
)
public class PriestInPeril extends BasicQuestHelper
{
	ItemRequirement runeEssence, lotsOfRuneEssence, bucket, runePouches, varrockTeleport, weaponAndArmour, goldenKey, rangedMagedGear;

	ConditionForStep inUnderground, hasGoldenKey, inTempleGroundFloor, inTemple, inTempleFirstFloor, inTempleSecondFloor;

	QuestStep talkToRoald, goToTemple, goDownToDog, goDownToDog1, killTheDog, climbUpAfterKillingDog, returnToKingRoald,
		returnToTemple, killMonk, goSpeakToDrezel, goUpToFloorTwoTemple, goUpToFloorOneTemple;

	Zone underground, temple1, temple2, temple3, temple4, temple5, temple6, templeFloorOne, templeFloorTwo;

	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		loadZones();
		setupItemRequirements();
		setupConditions();
		setupSteps();
		Map<Integer, QuestStep> steps = new HashMap<>();

		steps.put(0, talkToRoald);
		steps.put(1, goToTemple);

		ConditionalStep goDownAndKillDog = new ConditionalStep(this, goDownToDog);
		goDownAndKillDog.addStep(inUnderground, killTheDog);
		goDownAndKillDog.addStep(new ObjectCondition(ObjectID.TRAPDOOR_1581), goDownToDog1);

		steps.put(2, goDownAndKillDog);

		ConditionalStep reportKillingDog = new ConditionalStep(this, returnToKingRoald);
		reportKillingDog.addStep(inUnderground, climbUpAfterKillingDog);

		steps.put(3, reportKillingDog);

		ConditionalStep talkToDrezel = new ConditionalStep(this, returnToTemple);
		talkToDrezel.addStep(new Conditions(hasGoldenKey, inTempleSecondFloor), goSpeakToDrezel);
		talkToDrezel.addStep(new Conditions(hasGoldenKey, inTempleFirstFloor), goUpToFloorTwoTemple);
		talkToDrezel.addStep(new Conditions(hasGoldenKey, inTempleGroundFloor), goUpToFloorOneTemple);
		talkToDrezel.addStep(inTemple, killMonk);

		steps.put(4, talkToDrezel);
		return steps;
	}

	public void setupItemRequirements()
	{
		runeEssence = new ItemRequirement("Rune or Pure Essence", ItemID.RUNE_ESSENCE, 50);
		runeEssence.addAlternates(ItemID.PURE_ESSENCE);
		bucket = new ItemRequirement("Bucket", ItemID.BUCKET);
		runePouches = new ItemRequirement("Rune pouches for carrying essence", ItemID.SMALL_POUCH, -1);
		runePouches.addAlternates(ItemID.MEDIUM_POUCH, ItemID.LARGE_POUCH, ItemID.GIANT_POUCH);
		varrockTeleport = new ItemRequirement("Varrock teleports", ItemID.VARROCK_TELEPORT, 3);
		weaponAndArmour = new ItemRequirement("Ranged or melee weapon + armour", -1, -1);
		goldenKey = new ItemRequirement("Golden key", ItemID.GOLDEN_KEY);
		rangedMagedGear = new ItemRequirement("Combat gear, ranged or mage to safespot", -1, -1);
		lotsOfRuneEssence = new ItemRequirement("As much essence as you can carry, you'll need to bring 50 UNNOTED in total", ItemID.PURE_ESSENCE, -1);
	}

	public void loadZones()
	{
		underground = new Zone(new WorldPoint(3402, 9880, 0), new WorldPoint(3443, 9907, 0));
		temple1 = new Zone(new WorldPoint(3409, 3483, 0), new WorldPoint(3411, 3494, 0));
		temple2 = new Zone(new WorldPoint(3408, 3485, 0), new WorldPoint(3408, 3486, 0));
		temple3 = new Zone(new WorldPoint(3408, 3491, 0), new WorldPoint(3408, 3492, 0));
		temple4 = new Zone(new WorldPoint(3412, 3484, 0), new WorldPoint(3415, 3493, 0));
		temple5 = new Zone(new WorldPoint(3416, 3483, 0), new WorldPoint(3417, 3494, 0));
		temple6 = new Zone(new WorldPoint(3418, 3484, 0), new WorldPoint(3418, 3493, 0));
		templeFloorOne = new Zone(new WorldPoint(3408, 3483, 1), new WorldPoint(3419, 3494, 1));
		templeFloorTwo = new Zone(new WorldPoint(3408, 3483, 2), new WorldPoint(3419, 3494, 2));
	}

	public void setupConditions()
	{
		 inUnderground = new ZoneCondition(underground);
		 hasGoldenKey = new ItemRequirementCondition(goldenKey);
		 inTempleGroundFloor = new ZoneCondition(temple1, temple2, temple3, temple4, temple5, temple6);
		 inTempleFirstFloor = new ZoneCondition(templeFloorOne);
		 inTempleSecondFloor = new ZoneCondition(templeFloorTwo);
		 inTemple = new ZoneCondition(temple1, temple2, temple3, temple4, temple5, temple6, templeFloorOne, templeFloorTwo);
	}

	public void setupSteps()
	{
		talkToRoald = new NpcTalkStep(this, NpcID.KING_ROALD_5215, new WorldPoint(3222, 3473, 0), "Speak to King Roald in Varrock Castle.");
		talkToRoald.addDialogStep("I'm looking for a quest!");
		talkToRoald.addDialogStep("Yes.");
		goToTemple = new ObjectStep(this, ObjectID.LARGE_DOOR_3490, new WorldPoint(3408, 3488, 0),
			"Go to the temple east of Varrock by the river and click on the large door.", weaponAndArmour);
		goToTemple.addDialogStep("I'll get going.");
		goToTemple.addDialogStep("Roald sent me to check on Drezel.");
		goToTemple.addDialogStep("Sure. I'm a helpful person!");
		goDownToDog = new ObjectStep(this, ObjectID.TRAPDOOR_1579, new WorldPoint(3405, 3507, 0), "Go down the ladder north of the temple.");
		goDownToDog.addDialogStep("Yes.");
		goDownToDog1 = new ObjectStep(this, ObjectID.TRAPDOOR_1581, new WorldPoint(3405, 3507, 0), "Go down the ladder north of the temple.");
		goDownToDog1.addDialogStep("Yes.");
		killTheDog = new NpcTalkStep(this, NpcID.TEMPLE_GUARDIAN, new WorldPoint(3405, 9901, 0),
			"Kill the Temple Guardian (level 30). It is immune to magic so you will need to use either ranged or melee.");
		climbUpAfterKillingDog = new ObjectStep(this, ObjectID.LADDER_17385, new WorldPoint(3405, 9907, 0),
			"Climb back up the ladder and return to King Roald.");
		returnToKingRoald = new NpcTalkStep(this, NpcID.KING_ROALD_5215, new WorldPoint(3222, 3473, 0),
			"Return to King Roald.");

		// TODO: Replace this with more details on stuff to bring
		returnToTemple = new ObjectStep(this, ObjectID.LARGE_DOOR_3490, new WorldPoint(3408, 3488, 0),
			"Return to the temple.", bucket, lotsOfRuneEssence, rangedMagedGear);
		killMonk = new NpcTalkStep(this, NpcID.MONK_OF_ZAMORAK_3486, new WorldPoint(3412, 3488, 0), "Kill a Monk of Zamorak (level 30) for a golden key. You can safespot using the pews.", goldenKey);

		// TODO: If item on floor, detailed step...
		goUpToFloorOneTemple = new ObjectStep(this, ObjectID.STAIRCASE_16671, new WorldPoint(3418, 3493, 0), "Go upstairs.");
		goUpToFloorTwoTemple = new ObjectStep(this, ObjectID.LADDER_16683, new WorldPoint(3410, 3485, 1), "Climb up the ladder.");
		goSpeakToDrezel = new NpcTalkStep(this, NpcID.DREZEL, new WorldPoint(3418, 3489, 2), "Talk to Drezel.");
		goSpeakToDrezel.addDialogStep("So, what now?");
		goSpeakToDrezel.addDialogStep("Yes, of course.");


		// goSpeakToDrezel = new ObjectStep(this, ObjectID.CELL_DOOR, new WorldPoint(3415, 3489, 2), "Try opening the cell door to talk to Drezel.");

	}

	@Override
	public ArrayList<ItemRequirement> getItemRecommended()
	{
		ArrayList<ItemRequirement> reqs = new ArrayList<>();
		reqs.add(varrockTeleport);
		reqs.add(runePouches);
		return reqs;
	}

	@Override
	public ArrayList<ItemRequirement> getItemRequirements()
	{
		ArrayList<ItemRequirement> reqs = new ArrayList<>();
		reqs.add(runeEssence);
		reqs.add(bucket);
		return reqs;
	}

	@Override
	public String getCombatRequirements()
	{
		return "Temple guardian (level 30). Can only be hurt by ranged or melee.";
	}

	@Override
	public ArrayList<PanelDetails> getPanels()
	{
		ArrayList<PanelDetails> allSteps = new ArrayList<>();
		allSteps.add(new PanelDetails("Start the quest", new ArrayList<>(Arrays.asList(talkToRoald))));
		allSteps.add(new PanelDetails("Go to the temple", new ArrayList<>(Arrays.asList(goToTemple)), weaponAndArmour));

		return allSteps;
	}
}
