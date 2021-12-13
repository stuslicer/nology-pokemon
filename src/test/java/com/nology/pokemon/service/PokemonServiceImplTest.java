package com.nology.pokemon.service;

import com.nology.pokemon.loader.PokemonLoader;
import com.nology.pokemon.model.Pokemon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PokemonServiceImplTest {

    private PokemonServiceImpl target;

    @Mock
    private PokemonLoader pokemonLoader;
    @Mock
    private Pokemon pokemonA;
    @Mock
    private Pokemon pokemonB;

    @Before
    public void setUp() throws Exception {
        target = new PokemonServiceImpl();
        target.setPokemonLoader(pokemonLoader);

        // set up stubbings.
        when(pokemonLoader.loadPokemons()).thenReturn(List.of( pokemonA, pokemonB ));

        when(pokemonA.getId()).thenReturn(1);
        when(pokemonB.getId()).thenReturn(2);

        target.initialiseService();
    }

    /**
     * Test getting all pokemons.
     */
    @Test
    public void listAllPokemons() {
        List<Pokemon> result = target.listAllPokemons();

        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));

    }

    /**
     * Tests getting a valid Pokemon.
     */
    @Test
    public void findPokemonByIdSuccessful() {
        Optional<Pokemon> result = target.findPokemonById(1);
        assertThat( result.isPresent(), equalTo(true));
        assertThat( result.get(), equalTo(pokemonA));
    }

    /**
     * Tests getting a invalid Pokemon.
     */
    @Test
    public void findPokemonByIdInvalid() {
        Optional<Pokemon> result = target.findPokemonById(42);
        assertThat( result.isPresent(), equalTo(false));
    }
}