package com.nology.pokemon.loader;

import com.nology.pokemon.model.Pokemon;

import java.util.List;

/**
 * A loader to load all those {@link Pokemon} objects.
 */
public interface PokemonLoader {

    /**
     * Load all those {@link Pokemon}s!
     * @return an immutable list of {@link Pokemon} objects.
     */
    List<Pokemon> loadPokemons();

}
