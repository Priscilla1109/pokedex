package com.pokedex.pokedex.config;

import com.pokedex.pokedex.model.PokemonResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant {
    //Pokemon Bulbasaur
    public static final String NAME_BULBASAUR = "bulbasaur";
    public static String NAME_IVYSAUR = "ivysaur";
    public static String NAME_VENUSAUR = "venusaur";
    public static final Long NUMBER_BULBASAUR = 1L;
    public static final Long NUMBER_IVYSAUR = 2L;
    public static final Long NUMBER_VENUSAUR = 3L;

    public static final List<String> TYPE_BULBASAUR = Arrays.asList("Grass, Poison");
    public static final List<PokemonResponse> EVOLUTION_BULBASAUR = Arrays.asList(new PokemonResponse());
    public static final String TRIGGER_NAME_BULBASAUR = "level-up";
    public static final int MIN_LEVEL_BULBASAUR = 16;
    public static final String IMAGE_URL_BULBASAUR = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png";


}
