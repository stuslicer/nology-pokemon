package com.nology.pokemon.rest;

import com.nology.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Holds the response from a list pokemen request.
 */
public class PokemanListResponse {

    private int count;
    private List<Pokemon> results = new ArrayList<>();

    public PokemanListResponse(List<Pokemon> results) {
        checkNotNull(results, "Results must not be null");
        this.results = results;
        this.count = results.size();
    }

    public int getCount() {
        return count;
    }

    public List<Pokemon> getResults() {
        return results;
    }

}
