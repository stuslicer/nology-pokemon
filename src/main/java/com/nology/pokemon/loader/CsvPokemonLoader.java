package com.nology.pokemon.loader;


import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nology.pokemon.model.Pokemon;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Load the {@link Pokemon} list from a CSV file.
 * List is immutable and ordered by id.
 */
@Component
@Log4j2
public class CsvPokemonLoader implements PokemonLoader {

    @Value("${pokemon.filepath:}")
    private String pokemonFile;

    /**
     * Spring's {@link ResourceLoader} allows loading from a file from the file system or a classpath.
     */
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<Pokemon> loadPokemons() {
        checkNotNull(this.pokemonFile, "Property pokemon.filepath must be set.");

        File file = new File(this.pokemonFile);

        List<String> lines = loadLinesFromFile();

        List<Pokemon> sortedPokemons = lines.stream()
                .map(transform)
                .filter( Objects::nonNull )
                .sorted()
                .collect(Collectors.toList());

        return sortedPokemons;
    }

    /**
     * Read the file using Spring's {@link ResourceLoader}, allows reading from file system and classpath.
     * @return contents of file as a list of Strings.
     */
    private List<String> loadLinesFromFile() {
        final List<String> result = Lists.newArrayList();
        try {
            final String fullPath;
            if( ! this.pokemonFile.startsWith("classpath:") && ! this.pokemonFile.startsWith("file:") ) {
                fullPath = "classpath:" + this.pokemonFile;
            } else {
                fullPath = this.pokemonFile;
            }
            Resource resource = this.resourceLoader.getResource(fullPath);
            try {
                InputStream is = resource.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    result.add(line);
                }
                br.close();
            } catch (IOException e) {
                log.error(String.format("Exception attempting to load file from classpath %s", this.pokemonFile), e);
            }
        } finally {
        }
        return result;
    }

    /**
     * Set file for loading pokemons. Exposed for testing.
     * @param pokemonFile
     */
    void setPokemonFile(String pokemonFile) {
        this.pokemonFile = pokemonFile;
    }

    /**
     * Exposed for testing.
     * @param resourceLoader
     */
    void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Function to transform a CSV line into a {@link Pokemon} object.
     * Assumes a very simple CSV, with no ',' in the Pokemon's name!
     * Returns null if exception or empty line.
     */
    private static Function<String, Pokemon> transform = s -> {
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        try {
            String[] tokens = s.split(",");
            return Pokemon.create( Integer.valueOf( tokens[0] ), tokens[1] );
        } catch (NumberFormatException e) {
            log.error(String.format("Exception with line %s - %s",s, e.getMessage()));
            return null;
        }
    };

}
