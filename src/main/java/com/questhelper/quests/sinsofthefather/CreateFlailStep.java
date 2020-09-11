//package com.questhelper.quests.sinsofthefather;
//
//import net.runelite.api.*;
//import net.runelite.api.coords.WorldPoint;
//import net.runelite.client.plugins.questhelper.AxeRequirement;
//import net.runelite.client.plugins.questhelper.ItemRequirement;
//import net.runelite.client.plugins.questhelper.questhelpers.QuestHelper;
//import net.runelite.client.plugins.questhelper.steps.ItemInteractionStep;
//import net.runelite.client.plugins.questhelper.steps.MultiStageStep;
//import net.runelite.client.plugins.questhelper.steps.ObjectStep;
//import net.runelite.client.plugins.questhelper.steps.QuestStep;
//
//public class CreateFlailStep extends MultiStageStep {
//    private enum Steps {
//        ADD_RUBY(0),
//        ENCHANT(1),
//        ADD_BLISTERWOOD(2),
//        ADD_TO_FLAIL(3);
//
//        private final int value;
//
//        Steps(int value) {
//            this.value = value;
//        }
//
//        public int getValue() {
//            return value;
//        }
//    }
//
//    QuestStep step2;
//
//    public CreateFlailStep(QuestHelper questHelper) {
//        super(questHelper, null);
//        loadSteps();
//    }
//
//    @Override
//    public void startUp() {
//        updateSteps();
//    }
//
//    protected void updateSteps() {
//        if (hasItem(client, ItemID.BLISTERWOOD_SICKLE, 1)) {
//            startUpStep(steps.get(Steps.ADD_TO_FLAIL.getValue()));
//        } else if (hasItem(client, ItemID.ENCHANTED_RUBY_SICKLE_B, 1)) {
//            startUpStep(steps.get(Steps.ADD_BLISTERWOOD.getValue()));
//        } else if (hasItem(client, ItemID.RUBY_SICKLE_B, 1)) {
//            startUpStep(steps.get(Steps.ENCHANT.getValue()));
//        } else {
//            startUpStep(steps.get(Steps.ADD_RUBY.getValue()));
//        }
//    }
//
//    public void gameTick() {
//
//    }
//
//
//    @Override
//    protected void loadSteps()
//    {
//        ItemRequirement ruby = new ItemRequirement(ItemID.RUBY);
//        ItemRequirement chisel = new ItemRequirement(ItemID.CHISEL);
//        ItemRequirement knife = new ItemRequirement(ItemID.KNIFE);
//        ItemRequirement sickleB = new ItemRequirement(ItemID.SILVER_SICKLE_B, "You can get one from a crate in the Myreque base under Old Man Ral's house.");
//        ItemRequirement cosmic = new ItemRequirement(ItemID.COSMIC_RUNE);
//        ItemRequirement blisterwood = new ItemRequirement(ItemID.BLISTERWOOD_LOGS, "You can get another log from the Blisterwood tree in the Aboretum in Darkmeyer.");
//        ItemRequirement flail = new ItemRequirement(ItemID.IVANDIS_FLAIL, "You can get another from Vertida in the Myreque base under Old Man Ral's house. Costs 20k.");
//        ItemRequirement fire = new ItemRequirement(ItemID.FIRE_RUNE, 5);
//
//        ItemRequirement sickleRuby = new ItemRequirement(ItemID.RUBY_SICKLE_B);
//        ItemRequirement sickleRubyEnchanted = new ItemRequirement(ItemID.ENCHANTED_RUBY_SICKLE_B);
//        ItemRequirement blisterwoodSickle = new ItemRequirement(ItemID.BLISTERWOOD_SICKLE);
//
//        steps.put(Steps.ADD_RUBY.getValue(), new ItemInteractionStep(getQuestHelper(), "Use a ruby on a Blessed Silver Sickle", ruby,
//               chisel, sickleB, cosmic, fire, blisterwood,  knife, flail));
//        steps.put(Steps.ENCHANT.getValue(), new ItemInteractionStep(getQuestHelper(), "Use the Enchant Ruby spell on your Ruby Sickle", sickleRuby,
//                knife, cosmic, fire, blisterwood, flail));
//        steps.put(Steps.ADD_BLISTERWOOD.getValue(), new ItemInteractionStep(getQuestHelper(), "Use a blisterwood log on your Enchanted Ruby Sickle", sickleRubyEnchanted,
//                knife, blisterwood, flail));
//        steps.put(Steps.ADD_TO_FLAIL.getValue(), new ItemInteractionStep(getQuestHelper(), "Use the Blisterwood Sickle on the Ivandis Flail", blisterwoodSickle,
//                flail));
//    }
//}
