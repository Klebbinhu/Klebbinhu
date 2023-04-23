package io.github.klebbinhu.rpg;
import io.github.klebbinhu.collections.Pair;
import java.util.ArrayList;


public class Player {
    private String name;
    private final int  id;
    private int level;
    private ArrayList<Pair<Atributos, Integer>> atributos;
    private int xp;
    private int necXp;
    private int remainingSkillPoints;

    public Player(String name, int id){
        this.id = id;
        this.name = name;
        this.level = 0;
        this.xp = 0;
        this.necXp = 100;
        this.remainingSkillPoints = 0;
        this.atributos = new ArrayList<>();
        this.atributos.add(new Pair<>(Atributos.STRENGTH,1));
        this.atributos.add(new Pair<>(Atributos.CHARISMA,1));
        this.atributos.add(new Pair<>(Atributos.CONSTITUTION,1));
        this.atributos.add(new Pair<>(Atributos.DEXTERITY,1));
        this.atributos.add(new Pair<>(Atributos.INTELLIGENCE,1));
        this.atributos.add(new Pair<>(Atributos.LUCK,1));
        this.atributos.add(new Pair<>(Atributos.PERCEPTION,1));
        this.atributos.add(new Pair<>(Atributos.WISDOM,1));
    }

    public void incAtributo(Atributos at, int points){
        if(this.remainingSkillPoints >= 1){
            for(Pair<Atributos, Integer> aux: this.atributos ){
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

    public void resetProfile(){
        this.level = 0;
        for(Pair<Atributos, Integer> aux: this.atributos ){
            aux.setValue(0);
        }
    }

    public void decAtributo(Atributos at, int points){
        for(Pair<Atributos, Integer> aux: this.atributos ){
            if(aux.getKey().equals(at)){
                if(aux.getValue() - points < 1){
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

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getXp() {
        return this.xp;
    }

    public ArrayList<Pair<Atributos, Integer>> getAtributos() {
        return this.atributos;
    }

    public int getId() {
        return this.id;
    }

    public int getNecXp() {
        return this.necXp;
    }

    public int getAtributeLevel(Atributos a){
        for(Pair<Atributos, Integer> aux: this.atributos ){
            if(aux.getKey().equals(a)){
                return aux.getValue();
            }
        }
        return -1;
    }
}
