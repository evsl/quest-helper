package com.questhelper.quests.shadowofthestorm;

import com.questhelper.QuestHelperQuest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import com.questhelper.requirements.ItemRequirement;
import com.questhelper.QuestDescriptor;
import com.questhelper.Zone;
import com.questhelper.panel.PanelDetails;
import com.questhelper.questhelpers.BasicQuestHelper;
import com.questhelper.steps.NpcStep;
import com.questhelper.steps.QuestStep;
import com.questhelper.steps.conditional.ConditionForStep;

@QuestDescriptor(
	quest = QuestHelperQuest.SHADOW_OF_THE_STORM
)
public class ShadowOfTheStorm extends BasicQuestHelper
{
	ItemRequirement darkItems;

	ConditionForStep inRuin, inThroneRoom;

	Zone ruin, throneRoom;

	QuestStep talkToReen;

	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		Map<Integer, QuestStep> steps = new HashMap<>();
		setupZones();
		setupItemRequirements();
		setupConditions();
		setupSteps();

		steps.put(0, talkToReen);

		return steps;
	}

	private void setupZones()
	{

	}

	private void setupItemRequirements()
	{
		darkItems = new ItemRequirement("3 pieces of black clothing", ItemID.BLACK_DESERT_SHIRT, 3, true);
		darkItems.addAlternates(ItemID.BLACK_DESERT_ROBE, ItemID.BLACK_CHAINBODY, ItemID.BLACK_PLATEBODY, ItemID.BLACK_PLATELEGS, ItemID.BLACK_FULL_HELM, ItemID.BLACK_MED_HELM, ItemID.BLACK_GLOVES, ItemID.PRIEST_GOWN, ItemID.PRIEST_GOWN_428, ItemID.MYSTIC_HAT_DARK,
			ItemID.MYSTIC_ROBE_BOTTOM_DARK, ItemID.MYSTIC_ROBE_TOP_DARK, ItemID.GHOSTLY_BOOTS, ItemID.GHOSTLY_CLOAK, ItemID.GHOSTLY_GLOVES, ItemID.GHOSTLY_HOOD, ItemID.GHOSTLY_ROBE, ItemID.GHOSTLY_ROBE_6108, ItemID.SHADE_ROBE, ItemID.SHADE_ROBE_TOP, ItemID.ANTISANTA_BOOTS,
			ItemID.ANTISANTA_GLOVES, ItemID.ANTISANTA_JACKET, ItemID.ANTISANTA_MASK, ItemID.ANTISANTA_PANTALOONS, ItemID.WIZARD_HAT, ItemID.BLACK_CAPE, ItemID.BLACK_PARTYHAT, ItemID.BLACK_HWEEN_MASK, ItemID.BLACK_DRAGON_MASK, ItemID.BLACK_UNICORN_MASK, ItemID.BLACK_DEMON_MASK,
			ItemID.BLACK_DHIDE_BODY, ItemID.BLACK_DHIDE_CHAPS, ItemID.BLACK_DHIDE_VAMBRACES);
	}

	private void setupConditions()
	{

	}

	private void setupSteps()
	{
		talkToReen = new NpcStep(this, NpcID.FATHER_REEN, new WorldPoint(3270, 3159, 0), "Talk to Father Reen outside Al Kharid bank.");
	}

	@Override
	public ArrayList<ItemRequirement> getItemRequirements()
	{
		return new ArrayList<>(Arrays.asList());
	}

	@Override
	public ArrayList<ItemRequirement> getItemRecommended()
	{
		return new ArrayList<>(Arrays.asList());
	}

	@Override
	public ArrayList<PanelDetails> getPanels()
	{
		ArrayList<PanelDetails> allSteps = new ArrayList<>();

		allSteps.add(new PanelDetails("Starting off", new ArrayList<>(Arrays.asList(talkToReen))));

		return allSteps;
	}
}
