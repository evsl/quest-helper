//package com.questhelper.quests.sinsofthefather;
//
//import net.runelite.api.ItemID;
//import net.runelite.api.ObjectID;
//import net.runelite.api.coords.WorldPoint;
//import net.runelite.api.widgets.Widget;
//import net.runelite.api.widgets.WidgetInfo;
//import net.runelite.client.plugins.questhelper.ItemRequirement;
//import net.runelite.client.plugins.questhelper.QuestHelperPlugin;
//import net.runelite.client.plugins.questhelper.Zone;
//import net.runelite.client.plugins.questhelper.dialog.DialogueChoiceStep;
//import net.runelite.client.plugins.questhelper.questhelpers.QuestHelper;
//import net.runelite.client.plugins.questhelper.steps.*;
//import net.runelite.client.ui.overlay.components.LineComponent;
//import net.runelite.client.ui.overlay.components.PanelComponent;
//
//import java.awt.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ValveStep extends MultiStageStep {
//    private int valveTotalValue;
//    private int northTurns;
//    private int southTurns;
//
//    private final int GALLONS_NORTH = 7;
//    private final int GALLONS_SOUTH = 4;
//
//    private final Zone STANDING_AT_NORTH_VALVE = new Zone(new WorldPoint(3620, 3363, 0), new WorldPoint(3621, 3365, 0));
//    private final Zone STANDING_AT_SOUTH_VALVE = new Zone(new WorldPoint(3620, 3358, 0), new WorldPoint(3622, 3360, 0));
//
//    private boolean foundSum = false;
//    private boolean solving = false;
//    private boolean solved = false;
//    private boolean atSouthValve = false;
//    private boolean atNorthValve = false;
//    private boolean northDone = false;
//    private boolean southDone = false;
//
//    private enum Steps {
//        UNKNOWN(-1),
//        SET_NORTHERN_VALVE(0),
//        SET_SOUTHERN_VALVE(1),
//        SET_SOUTHERN_VALVE_NO_TEXT_HIGHLIGHT(-2),
//        SET_NORTHERN_VALVE_NO_TEXT_HIGHLIGHT(-3);
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
//    public ValveStep(QuestHelper questHelper) {
//        super(questHelper, null);
//        loadSteps();
//    }
//
//    @Override
//    public void startUp()
//    {
//        updateSteps();
//    }
//
//    protected void updateSteps() {
//        if (!foundSum) {
//            startUpStep(steps.get(Steps.UNKNOWN.getValue()));
//        } else {
//            if ((atSouthValve || northDone) && !southDone) {
//                if(!atSouthValve) {
//                    startUpStep(steps.get(Steps.SET_SOUTHERN_VALVE_NO_TEXT_HIGHLIGHT.getValue()));
//                } else {
//                    startUpStep(steps.get(Steps.SET_SOUTHERN_VALVE.getValue()));
//                }
//            } else {
//                if(!atNorthValve) {
//                    startUpStep(steps.get(Steps.SET_NORTHERN_VALVE_NO_TEXT_HIGHLIGHT.getValue()));
//                } else {
//                    startUpStep(steps.get(Steps.SET_NORTHERN_VALVE.getValue()));
//                }
//            }
//        }
//    }
//
//    public void gameTick() {
//        Widget widgetNote = client.getWidget(666, 5);
//        if (!foundSum && widgetNote != null) {
//            Matcher foundValveValue = Pattern.compile("[0-9]+").matcher(widgetNote.getText());
//            final boolean foundAnswer = foundValveValue.find();
//            if (foundAnswer && !solving) {
//                foundSum = true;
//                solving = true;
//                valveTotalValue = Integer.parseInt(foundValveValue.group(0));
//                getValveValues();
//                updateSteps();
//            }
//        } else if (foundSum) {
//            Widget widgetNumberOptions = client.getWidget(187, 3);
//            Widget widgetValveChoice = client.getWidget(229, 1);
//            WorldPoint position = client.getLocalPlayer().getWorldLocation();
//            if(STANDING_AT_SOUTH_VALVE.contains(position)) {
//                atSouthValve = true;
//                atNorthValve = false;
//                if (widgetNumberOptions != null) {
//                    southDone = checkValve(widgetNumberOptions, southTurns);
//                } else if (widgetValveChoice != null) {
//                    if (widgetValveChoice.getText().contains(String.valueOf(southTurns))) {
//                        southDone = true;
//                    } else if (widgetValveChoice.getText().contains("You set the valve")) {
//                        southDone = false;
//                    }
//                }
//            } else if (STANDING_AT_NORTH_VALVE.contains(position)) {
//                atNorthValve = true;
//                atSouthValve = false;
//                if (widgetNumberOptions != null) {
//                    northDone = checkValve(widgetNumberOptions, northTurns);
//                } else if (widgetValveChoice != null) {
//                    if (widgetValveChoice.getText().contains(String.valueOf(northTurns))) {
//                        northDone = true;
//                    } else if (widgetValveChoice.getText().contains("You set the valve")) {
//                        northDone = false;
//                    }
//                }
//            } else {
//                atSouthValve = false;
//                atNorthValve = false;
//            }
//            updateSteps();
//        }
//    }
//
//    private boolean checkValve(Widget choices, int turns) {
//        boolean isSelected = false;
//        for (Widget option : choices.getChildren()) {
//            if (option.getText().equals(turns + " (current)")) {
//                isSelected = true;
//            }
//        }
//        return isSelected;
//    }
//
//    private void getValveValues() {
//        int maxNorthTurns = valveTotalValue / GALLONS_NORTH;
//        iterateValueCombos(maxNorthTurns);
//    }
//
//    private void iterateValueCombos(int currentNorthTurns) {
//        if (currentNorthTurns == 0) {
//            return;
//        }
//
//        int northSum = currentNorthTurns * GALLONS_NORTH;
//        int remainderNorth = (valveTotalValue -  northSum);
//
//        if (currentNorthTurns <= 5 && (remainderNorth % GALLONS_SOUTH) == 0) {
//            solved = true;
//            northTurns = currentNorthTurns;
//            southTurns = (remainderNorth / GALLONS_SOUTH);
//            steps.get(Steps.SET_NORTHERN_VALVE.getValue()).addDialogueStep(new DialogueChoiceStep(String.valueOf(northTurns), 187, 3));
//            steps.get(Steps.SET_NORTHERN_VALVE.getValue()).addDialogueStep(new DialogueChoiceStep(northTurns + " (current)", 187, 3));
//            steps.get(Steps.SET_SOUTHERN_VALVE.getValue()).addDialogueStep(new DialogueChoiceStep(String.valueOf(southTurns), 187, 3));
//            steps.get(Steps.SET_SOUTHERN_VALVE.getValue()).addDialogueStep(new DialogueChoiceStep(southTurns  + " (current)", 187, 3));
//        } else {
//            int newNorthTurns = currentNorthTurns - 1;
//            iterateValueCombos(newNorthTurns);
//        }
//    }
//
//    @Override
//    public void makeOverlayHint(PanelComponent panelComponent, QuestHelperPlugin plugin) {
//        super.makeOverlayHint(panelComponent, plugin);
//        if (!solved && solving) {
//            String text = "Unable to calculate an answer for this puzzle. Good luck!";
//            Color color = Color.RED;
//            panelComponent.getChildren().add(LineComponent.builder()
//                    .left(text)
//                    .leftColor(color)
//                    .build());
//        }
//    }
//
//    @Override
//    protected void loadSteps()
//    {
//        ItemRequirement scentedTop =  new ItemRequirement(ItemID.VYRE_NOBLE_TOP, "You can get a replacement from a chest in Old Man Ral's basement.", true);
//        ItemRequirement scentedLegs = new ItemRequirement(ItemID.VYRE_NOBLE_LEGS, "You can get a replacement from a chest in Old Man Ral's basement.", true);
//        ItemRequirement scentedShoes = new ItemRequirement(ItemID.VYRE_NOBLE_SHOES, "You can get a replacement from a chest in Old Man Ral's basement.", true);
//        ItemRequirement oldNote = new ItemRequirement(ItemID.OLD_NOTE, "You can find this in the fallen bookcase on the south side of the Aboretum");
//        steps.put(Steps.UNKNOWN.getValue(), new ItemInteractionStep(getQuestHelper(), "Read the Old Note to find out the amount of water the Blisterwood Tree needs.",
//                oldNote, scentedTop, scentedLegs, scentedShoes));
//        steps.put(Steps.SET_NORTHERN_VALVE.getValue(), new ObjectStep(getQuestHelper(), ObjectID.VALVE_37997, new WorldPoint(3621, 3364, 0), "Turn the northern valve to the highlighted value."));
//        steps.put(Steps.SET_SOUTHERN_VALVE.getValue(), new ObjectStep(getQuestHelper(), ObjectID.VALVE_37998, new WorldPoint(3621, 3359, 0), "Turn the southern valve to the highlighted value."));
//        steps.put(Steps.SET_NORTHERN_VALVE_NO_TEXT_HIGHLIGHT.getValue(), new ObjectStep(getQuestHelper(), ObjectID.VALVE_37997, new WorldPoint(3621, 3364, 0), "Turn the northern valve to the highlighted value."));
//        steps.put(Steps.SET_SOUTHERN_VALVE_NO_TEXT_HIGHLIGHT.getValue(), new ObjectStep(getQuestHelper(), ObjectID.VALVE_37998, new WorldPoint(3621, 3359, 0), "Turn the southern valve to the highlighted value."));
//    }
//
//}
