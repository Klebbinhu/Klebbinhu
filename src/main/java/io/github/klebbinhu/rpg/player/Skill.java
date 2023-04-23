package io.github.klebbinhu.rpg.player;

public class Skill {

    private final Status stat;

    public Skill(Status stat){
        this.stat = stat;
    }

    @Override
    public String toString() {
        return this.stat.toString();
    }
}
