package com.nology.pokemon.service;

import com.nology.pokemon.model.Pokemon;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling all things Pokemon.
 */
public interface PokemonService {

    /**
     * List all available pokemon characters.
     * @return a list of all available {@link Pokemon}.
     */
    List<Pokemon> listAllPokemons();

    /**
     * Find a {@link Pokemon} using the given id.
     * @param id the id of Pokemon to find.
     * @return the found {@link Pokemon} object wrapped in an {@link Optional}.
     */
    Optional<Pokemon> findPokemonById(int id);

}
