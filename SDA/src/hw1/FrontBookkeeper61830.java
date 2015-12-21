package hw1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FrontBookkeeper61830 implements IFrontBookkeeper {

    private Map<String, List<Integer>> units;
    private Map<String, String> attachedUnits;

    public FrontBookkeeper61830() {
        units = new HashMap<String, List<Integer>>();
        attachedUnits = new HashMap<String, String>();
    }

    @Override
    public String updateFront(String[] news) {
        StringBuilder sb = new StringBuilder();
        for (String newsLine : news) {
            switch (filter(newsLine)) {
                case 1: {
                    assign(newsLine);
                    break;
                }
                case 2:
                    attach(newsLine);
                    break;
                case 3:
                    attachAfter(newsLine);
                    break;
                case 4:
                    kill(newsLine);
                    break;
                case 5:
                    sb.append(show(newsLine));
                    break;
                case 6:
                    sb.append(showSoldier(newsLine));
                default:
                    break;
            }
        }
        return sb.toString().trim();
    }

    private void kill(String newsLine) {
        String[] commands = newsLine.split(" ");
        int s1 = Integer.parseInt(commands[1].replace("..", " ").split(" ")[0]);
        int s2 = Integer.parseInt(commands[1].replace("..", " ").split(" ")[1]);
        Set<Map.Entry<String, List<Integer>>> set = units.entrySet();
        for (Map.Entry<String, List<Integer>> entry1 : set) {
            List<Integer> soldiers = entry1.getValue();
            for (int i = 0; i < soldiers.size(); i++) {
                if (soldiers.get(i) == s1) {
                    while (soldiers.get(i) != s2) {
                        soldiers.remove(soldiers.get(i));
                    }
                    soldiers.remove(soldiers.get(i));
                    break;
                }
            }
        }
    }

    private void attachAfter(String newsLine) {
        String[] commands = newsLine.split(" ");
        String attachedUnit = commands[0];
        String unitTo = commands[3];
        int idAfter = Integer.parseInt(commands[6]);
        int index = 0;
        Set<Map.Entry<String, String>> set1 = attachedUnits.entrySet();
        for (Map.Entry<String, String> entry : set1) {
            if (entry.getKey().equals(attachedUnit))
                if (!entry.getValue().equals("__")) {
                    detach(entry.getKey(), entry.getValue());
                    entry.setValue("__");
                    break;
                }
        }
        Set<Map.Entry<String, List<Integer>>> set = units.entrySet();
        for (Map.Entry<String, List<Integer>> entry1 : set) {
            if (entry1.getKey().equals(unitTo)) {
                List<Integer> soldiersAttachedTo = entry1.getValue();
                for (int i = 0; i < soldiersAttachedTo.size(); i++)
                    if (soldiersAttachedTo.get(i) == idAfter) {
                        index = idAfter;
                        break;
                    }
                for (Map.Entry<String, List<Integer>> entry2 : set) {
                    if (entry2.getKey().equals(attachedUnit)) {
                        List<Integer> soldiersToAttach = entry2.getValue();
                        for (int i = soldiersToAttach.size() - 1; i >= 0; i--) {
                            soldiersAttachedTo.add(index, soldiersToAttach.get(i));
                        }
                        attachedUnits.put(entry2.getKey(), entry1.getKey());
                        break;
                    }
                }
                break;
            }
        }

    }

    private void attach(String newsLine) {
        String[] commands = newsLine.split(" ");
        String attachedUnit = commands[0];
        String unitTo = commands[3];
        Set<Map.Entry<String, String>> set1 = attachedUnits.entrySet();
        for (Map.Entry<String, String> entry : set1) {
            if (entry.getKey().equals(attachedUnit))
                if (!entry.getValue().equals("__")) {
                    detach(entry.getKey(), entry.getValue());
                    entry.setValue("__");
                    break;
                }
        }
        Set<Map.Entry<String, List<Integer>>> set2 = units.entrySet();
        for (Map.Entry<String, List<Integer>> entry1 : set2) {
            if (entry1.getKey().equals(unitTo)) {
                List<Integer> soldiersAttachedTo = entry1.getValue();
                for (Map.Entry<String, List<Integer>> entry2 : set2) {
                    if (entry2.getKey().equals(attachedUnit)) {
                        List<Integer> soldiersToAttach = entry2.getValue();
                        for (int i = 0; i < soldiersToAttach.size(); i++)
                            soldiersAttachedTo.add(soldiersToAttach.get(i));
                        attachedUnits.put(entry2.getKey(), entry1.getKey());
                        break;
                    }
                }
                break;
            }
        }
    }

    private void detach(String unitToDetach, String unitFrom) {
        Set<Map.Entry<String, List<Integer>>> set = units.entrySet();
        for (Map.Entry<String, List<Integer>> entry1 : set) {
            if (entry1.getKey().equals(unitToDetach)) {
                for (Map.Entry<String, List<Integer>> entry2 : set) {
                    if (entry2.getKey().equals(unitFrom)) {
                        List<Integer> soldiers1 = entry1.getValue();
                        List<Integer> soldiers2 = entry2.getValue();
                        for (int i = 0; i < soldiers1.size(); i++)
                            soldiers2.remove(soldiers1.get(i));
                        break;
                    }
                }
                break;
            }
        }

    }

    private int filter(String newsLine) {
        int result = 0;
        if (newsLine.contains("="))
            result = 1;
        if (newsLine.contains("attached to"))
            result = 2;
        if (newsLine.contains("after soldier"))
            result = 3;
        if (newsLine.contains("died heroically"))
            result = 4;
        if (newsLine.contains("show "))
            result = 5;
        if (newsLine.contains("show soldier"))
            result = 6;
        return result;
    }

    private String showSoldier(String command) {
        String result = "";
        int id = Integer.parseInt(command.split(" ")[2]);
        Set<Map.Entry<String, List<Integer>>> set = units.entrySet();
        for (Map.Entry<String, List<Integer>> entry : set) {
            List<Integer> soldiers = entry.getValue();
            for (int i = 0; i < soldiers.size(); i++)
                if (soldiers.contains(id)) {
                    result += new StringBuilder(entry.getKey() + ", ").toString();
                    break;
                }
        }
        if (result.charAt(result.length() - 2) == ',')
            result = new StringBuilder(result.substring(0, result.length() - 2)).toString();
        return result + "\r\n";
    }

    private String show(String command) {
        String result = "";
        String name = command.split(" ")[1];
        Set<Map.Entry<String, List<Integer>>> set = units.entrySet();
        for (Map.Entry<String, List<Integer>> entry : set) {
            if (entry.getKey().equals(name)) {
                List<Integer> soldiers = entry.getValue();
                for (int i = 0; i < soldiers.size() - 1; i++) {
                    result += new StringBuilder(soldiers.get(i) + ", ").toString();
                }
                if (soldiers.size() != 0)
                    result += new StringBuilder(soldiers.get(soldiers.size() - 1) + "]\r\n").toString();
                else
                    result += new StringBuilder("]\r\n");
            }
        }
        return "[" + result;
    }

    private void assign(String command) {
        command.trim();
        String[] stringParts = command.split("=");
        List<Integer> soldiers = new ArrayList<Integer>();
        String soldsTemp = stringParts[1].substring(2, stringParts[1].length() - 1);
        if (soldsTemp.length() != 0) {
            String[] solds = soldsTemp.split(",");
            for (String sol : solds) {
                soldiers.add(Integer.parseInt(sol.trim()));
            }
        }
        units.put(stringParts[0].trim(), soldiers);
        attachedUnits.put(stringParts[0].trim(), "__");
    }
}
