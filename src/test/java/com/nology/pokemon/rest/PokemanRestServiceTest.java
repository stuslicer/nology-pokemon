package com.nology.pokemon.rest;

import com.nology.pokemon.model.Pokemon;
import com.nology.pokemon.service.PokemonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PokemanRestServiceTest {

    private PokemanRestService target;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private Pokemon pokemonA;
    @Mock
    private Pokemon pokemonB;

    @Before
    public void setUp() throws Exception {
        target = new PokemanRestService();
        target.setPokemonService(pokemonService);

        // set up stubbings.
        when(pokemonService.listAllPokemons()).thenReturn(List.of( pokemonA, pokemonB ));
        when(pokemonService.findPokemonById(ArgumentMatchers.eq(1))).thenReturn(Optional.of(pokemonA));
        when(pokemonService.findPokemonById(ArgumentMatchers.eq(2))).thenReturn(Optional.of(pokemonB));
    }

    @Test
    public void listAllPokemons() {
        PokemanListResponse result = target.listAllPokemons();

        assertThat(result, notNullValue());
        assertThat(result.getCount(), equalTo(2));
        assertThat(result.getResults().size(), equalTo(2));
    }

    /**
     * Tests gets a valid pokemon successfully.
     */
    @Test
    public void getPokemonByIdSuccessful() {
        Pokemon result;

        result = target.getPokemonById(1);
        assertThat(result, notNullValue());
        assertThat(result, equalTo( pokemonA ));

        result = target.getPokemonById(2);
        assertThat(result, notNullValue());
        assertThat(result, equalTo( pokemonB ));
    }

    /**
     * Test for an invalid pokemon.
     */
    @Test
    public void getPokemonByIdInvalid() {
        Pokemon result;

        result = target.getPokemonById(150);
        assertThat(result, nullValue());
    }


}