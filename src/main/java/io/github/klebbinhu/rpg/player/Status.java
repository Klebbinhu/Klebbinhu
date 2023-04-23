package io.github.klebbinhu.rpg.player;

import io.github.klebbinhu.collections.Pair;
import io.github.klebbinhu.rpg.player.Atributo;

import java.util.ArrayList;

public record Status(String name,int dano, int critR, int critD, ArrayList<Pair<Atributo, Integer>> req) {
}
