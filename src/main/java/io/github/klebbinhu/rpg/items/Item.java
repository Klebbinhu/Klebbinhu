package io.github.klebbinhu.rpg.items;

public abstract class Item {

    private final String name;
    private final Rarity rarity;

    public Item(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    public abstract String getStatus();

    public Rarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }
}
