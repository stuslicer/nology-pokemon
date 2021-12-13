package com.nology.pokemon.rest;

import com.nology.pokemon.model.Pokemon;
import com.nology.pokemon.service.PokemonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * RESTful controllwr for pokemon service.
 */
@RestController
@Log4j2
public class PokemanRestService {

    @Autowired
    private PokemonService pokemonService;

    /**
     * Gets all available {@link Pokemon} objects.
     * @return a list of {@link Pokemon}s.
     */
    @RequestMapping(path = "/pokemon", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public PokemanListResponse listAllPokemons() {
        List<Pokemon> pokemonList = pokemonService.listAllPokemons();
        PokemanListResponse pokemons = new PokemanListResponse(pokemonList);
        return pokemons;
    }

    /**
     * Get {@link Pokemon} object for given id.
     * @param id id for the pokemon.
     * @return pokemon or null if id is invalid.
     */
    @RequestMapping(path = "/pokemon/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Pokemon getPokemonById(@PathVariable int id) {
        checkArgument(id > 0, "Id must be greater than 0");
        Optional<Pokemon> pokemonById = pokemonService.findPokemonById(id);
        return pokemonById.orElse(null);
    }

    /**
     * Exposed for testing.
     * @param pokemonService
     */
    void setPokemonService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
}
