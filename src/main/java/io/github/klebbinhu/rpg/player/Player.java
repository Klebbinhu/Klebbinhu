package io.github.klebbinhu.rpg.player;
import io.github.klebbinhu.collections.Pair;

import java.util.ArrayList;


public class Player {
    private final String name;
    private final int  id;
    private int level;
    private ArrayList<Pair<Atributo, Integer>> atributos;
    private int xp;
    private int necXp;
    private int remainingSkillPoints;

    private int dano;

    private ArrayList<Skill> skills;

    public Player(String name, int id){
        this.id = id;
        this.name = name;
        this.level = 0;
        this.xp = 0;
        this.necXp = 100;
        this.remainingSkillPoints = 0;
        this.dano = 1;
        this.atributos = new ArrayList<>();
        this.atributos.add(new Pair<>(Atributo.STRENGTH,1));
        this.atributos.add(new Pair<>(Atributo.CHARISMA,1));
        this.atributos.add(new Pair<>(Atributo.CONSTITUTION,1));
        this.atributos.add(new Pair<>(Atributo.DEXTERITY,1));
        this.atributos.add(new Pair<>(Atributo.INTELLIGENCE,1));
        this.atributos.add(new Pair<>(Atributo.LUCK,1));
        this.atributos.add(new Pair<>(Atributo.PERCEPTION,1));
        this.atributos.add(new Pair<>(Atributo.WISDOM,1));
        this.skills = new ArrayList<>();
    }

    public void incAtributo(Atributo at, int points){
        if(this.remainingSkillPoints >= 1){
            for(Pair<Atributo, Integer> aux: this.atributos ){
                if(aux.getKey().equals(at)){
                    if(aux.getValue() + points > 99){
                        return;
                    }
                    aux.setValue(aux.getValue() + points);
                    this.remainingSkillPoints -=points;
                    return;
                }
            }
        }
    }

    public void addSkill(Status stats){
        this.skills.add(new Skill(stats));
    }

    public void resetProfile(){
        this.level = 0;
        for(Pair<Atributo, Integer> aux: this.atributos ){
            aux.setValue(0);
        }
    }

    public void decAtributo(Atributo at, int points){
        for (Pair<Atributo, Integer> aux : this.atributos) {
            if (aux.getKey().equals(at)) {
                if (aux.getValue() - points < 1) {
                    return;
                }
                aux.setValue(aux.getValue() - points);
                this.remainingSkillPoints += points;
                return;
            }
        }
    }
    public void levelUp(){
        if(this.xp == this.necXp){
            this.level+= 1;
            this.necXp *= 2;
            this.remainingSkillPoints +=1;
        }
    }

    public void actDano(){
        int count = 0;
        for(Pair<Atributo, Integer> aux : this.atributos){
            count+= aux.getValue();
        }
        this.dano = count;
    }

    public void addXp(int quantity){
        this.xp += quantity;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getXp() {
        return this.xp;
    }

    public ArrayList<Pair<Atributo, Integer>> getAtributos() {
        return this.atributos;
    }

    public int getId() {
        return this.id;
    }

    public int getNecXp() {
        return this.necXp;
    }

    public int getAtributeLevel(Atributo a){
        for(Pair<Atributo, Integer> aux: this.atributos ){
            if(aux.getKey().equals(a)){
                return aux.getValue();
            }
        }
        return -1;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return "Player(" +
                 name + '\'' +
                ", id=" + id +
                ", level=" + level +
                "," + atributos +
                ", xp=" + xp +
                ", necXp=" + necXp +
                ", remainingSkillPoints=" + remainingSkillPoints + ", skills= " + skills +
                ')';
    }
}
