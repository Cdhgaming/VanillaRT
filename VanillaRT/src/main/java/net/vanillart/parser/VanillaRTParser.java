package net.vanillart.parser;

import java.nio.file.*;
import java.util.*;

public class VanillaRTParser {

    public static class ParsedItem {
        public String texture;
        public String name;
        public String tolt;
        public int damage = 0;
        public int durability = 0;
        public int stackSize = 64;
        public List<String> canBreak = new ArrayList<>();
        public boolean enchantable = true;
        public String effect;
        public float speed = 1.0f;
        public int armor = 0;
        public String armorType;
        public int foodValue = 0;
        public float saturation = 0f;
        public String potionEffect;
        public String customTag;
        public boolean valid = true;
        public List<String> errors = new ArrayList<>();
    }

    public List<ParsedItem> parseFile(Path vrtPath) throws Exception {
        List<String> lines = Files.readAllLines(vrtPath);
        List<ParsedItem> items = new ArrayList<>();
        ParsedItem currentItem = null;
        boolean inBlock = false;

        if (lines.isEmpty() || !lines.get(0).startsWith("/!decl!\\vanillaRT-")) return items;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            if (line.endsWith("{")) {
                inBlock = true;
                currentItem = new ParsedItem();
            } else if (line.equals("}")) {
                inBlock = false;
                if (currentItem != null) {
                    if (currentItem.texture == null) {
                        currentItem.valid = false;
                        currentItem.errors.add("Missing texture field");
                    }
                    items.add(currentItem);
                    currentItem = null;
                }
            } else if (inBlock && currentItem != null) {
                int colon = line.indexOf(':');
                if (colon < 0) {
                    currentItem.errors.add("Invalid field: " + line);
                    currentItem.valid = false;
                    continue;
                }
                String key = line.substring(0, colon).trim().toLowerCase();
                String value = line.substring(colon + 1).trim();
                if (value.startsWith("\"") && value.endsWith("\"")) value = value.substring(1, value.length() - 1);

                switch (key) {
                    case "texture": currentItem.texture = value; break;
                    case "name": currentItem.name = value; break;
                    case "tolt": currentItem.tolt = value; break;
                    case "damage": try{currentItem.damage=Integer.parseInt(value);}catch(Exception e){currentItem.errors.add("Invalid damage"); currentItem.valid=false;} break;
                    case "durability": try{currentItem.durability=Integer.parseInt(value);}catch(Exception e){currentItem.errors.add("Invalid durability"); currentItem.valid=false;} break;
                    case "stack_size": try{currentItem.stackSize=Integer.parseInt(value);}catch(Exception e){currentItem.errors.add("Invalid stack_size"); currentItem.valid=false;} break;
                    case "can_break": currentItem.canBreak = Arrays.asList(value.split(",")); break;
                    case "enchantable": currentItem.enchantable = value.equalsIgnoreCase("true"); break;
                    case "effect": currentItem.effect = value; break;
                    case "speed": try{currentItem.speed=Float.parseFloat(value);}catch(Exception e){currentItem.errors.add("Invalid speed"); currentItem.valid=false;} break;
                    case "armor": try{currentItem.armor=Integer.parseInt(value);}catch(Exception e){currentItem.errors.add("Invalid armor"); currentItem.valid=false;} break;
                    case "armor_type": currentItem.armorType = value; break;
                    case "food_value": try{currentItem.foodValue=Integer.parseInt(value);}catch(Exception e){currentItem.errors.add("Invalid food_value"); currentItem.valid=false;} break;
                    case "saturation": try{currentItem.saturation=Float.parseFloat(value);}catch(Exception e){currentItem.errors.add("Invalid saturation"); currentItem.valid=false;} break;
                    case "potion_effect": currentItem.potionEffect = value; break;
                    case "custom_tag": currentItem.customTag = value; break;
                    default: currentItem.errors.add("Unknown field: " + key); break;
                }
            }
        }
        return items;
    }
}
