package com.nology.pokemon.loader;

import com.nology.pokemon.model.Pokemon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A super simple implementation, just hard coding those guys.
 * Used initially for testing. No longer sued.
 */
@Deprecated
public class HardcodedPokemonLoader implements PokemonLoader {

    @Override
    public List<Pokemon> loadPokemons() {
        List<Pokemon> list = new ArrayList<>();

        list.add(Pokemon.create(1, "Bulbasaur"));
        list.add(Pokemon.create(2, "Ivysaur"));
        list.add(Pokemon.create(150, "Mew"));

        return List.copyOf(list);
    }

}
