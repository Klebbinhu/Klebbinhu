package io.github.klebbinhu.rpg.items;

import io.github.klebbinhu.collections.Pair;
import io.github.klebbinhu.rpg.player.Atributo;

import java.util.ArrayList;

public class Weapon extends Item{

    private final int dano;
    private final ArrayList<Pair<Atributo, Integer>> plus;


    public Weapon(String name, Rarity rarity,  ArrayList<Pair<Atributo, Integer>>plus, int dano){
        super(name, rarity);
        this.dano = dano;
        this.plus = plus;
    }
    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        for(Pair<Atributo,Integer> aux: this.plus){
            sb.append(aux.toString()).append("\n");
        }
        sb.append("Dano: ").append(this.dano);
        return sb.toString();
    }
}
