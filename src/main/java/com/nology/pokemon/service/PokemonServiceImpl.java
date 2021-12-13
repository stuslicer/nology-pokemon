package com.nology.pokemon.service;

import com.nology.pokemon.loader.PokemonLoader;
import com.nology.pokemon.model.Pokemon;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

@Component
@Log4j2
public class PokemonServiceImpl implements PokemonService {

    @Autowired
    private PokemonLoader pokemonLoader;

    /**
     * A list of {@link Pokemon} objects, cached for performance.
     */
    private List<Pokemon> cachedList = List.of();
    /**
     * A mapping of {@link Pokemon}s, using a key of id.
     */
    private Map<Integer, Pokemon> pokemonMap = new HashMap<>();

    @Override
    public List<Pokemon> listAllPokemons() {
        return this.cachedList;
    }

    @Override
    public Optional<Pokemon> findPokemonById(int id) {
        checkArgument(id > 0, "Id must be greater than 0");
        Pokemon pokemon = this.pokemonMap.get(id);
        return Optional.ofNullable(pokemon);
    }

    /**
     * Initialise the service, populating the cached list and map.
     */
    @PostConstruct
    void initialiseService() {

        if( log.isDebugEnabled() ) {
            log.debug("Initialising service.");
        }

        this.cachedList = pokemonLoader.loadPokemons();
        this.pokemonMap = this.cachedList
                .stream()
                .collect(Collectors.toMap( P -> P.getId(), P -> P ));

        if( log.isInfoEnabled() ) {
            log.info(String.format("Successfully loaded %d Pokemons!", this.pokemonMap.size() ));
        }
    }

    /**
     * Exposed for testing.
     * @param pokemonLoader
     */
    void setPokemonLoader(PokemonLoader pokemonLoader) {
        this.pokemonLoader = pokemonLoader;
    }
}
