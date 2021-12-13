package com.nology.pokemon.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents a Pokemon character.
 */
public class Pokemon implements Comparable<Pokemon> {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pokemon.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }

    /**
     * Sort a Pokemom by id.
     * @param o
     * @return
     */
    @Override
    public int compareTo(Pokemon o) {
        return Integer.compare( this.id, o.id);
    }

    /**
     * Creates a {@link Pokemon} from given id and name.
     * @param id the unique id for Pokemon.
     * @param name the name for the Pokemon.
     * @return
     */
    public static Pokemon create(int id, String name) {
        Objects.requireNonNull(name, "Name cannot be null!");
        Pokemon pokemon = new Pokemon();
        pokemon.setId(id);
        pokemon.setName(name);
        return pokemon;
    }

}
