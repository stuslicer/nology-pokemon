package com.nology.pokemon.loader;

import com.nology.pokemon.model.Pokemon;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CsvPokemonLoaderTest {

    private static final String POKEMON_FILE = "file:src/test/resources/tiny-pokemons.csv";
    private static final String POKEMON_ERROR_FILE = "file:src/test/resources/errors-pokemons.csv";

    private CsvPokemonLoader target;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Before
    public void setUp() throws Exception {
        target = new CsvPokemonLoader();
        target.setResourceLoader(resourceLoader);
        target.setPokemonFile(POKEMON_FILE);
    }

    /**
     * Loads pokemons successfully, no problems.
     */
    @Test
    public void testLoadsSuccesfully() {
        List<Pokemon> result = target.loadPokemons();
        assertThat( result, notNullValue() );
        assertThat( result.size(), equalTo(10) );
    }

    /**
     * Loads a file successfully, however there are a few illegal lines, which are logged and ignored.
     */
    @Test
    public void testLoadWithErrors() {
        target.setPokemonFile(POKEMON_ERROR_FILE);
        List<Pokemon> result = target.loadPokemons();
        assertThat( result, notNullValue() );
        assertThat( result.size(), equalTo(6) );
    }

    /**
     * Missing file name.
     */
    @Test(expected = NullPointerException.class)
    public void testMissingFile() {
        target.setPokemonFile(null);
        List<Pokemon> result = target.loadPokemons();
    }
}