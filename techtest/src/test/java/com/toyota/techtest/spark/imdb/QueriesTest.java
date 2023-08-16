package com.toyota.techtest.spark.imdb;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.toyota.techtest.data.Loader;

class QueriesTest {

    private static Queries results;
    private static VotesView votes;
    private static BasicsView basics;

    private static Loader loader = new Loader();

    @BeforeAll
    public static void setUp() {
        var store = loader.store();
        results = new Queries(store);
        votes = new VotesView(store, loader.getDataResourceParentPath(VotesView.FILE));
        basics = new BasicsView(store, loader.getDataResourceParentPath(BasicsView.FILE));
    }

    @Test
    void testTopTen() {
        var topTenMovies = results.topTenMovies(votes, basics);
        topTenMovies.persist();

        assertEquals(10, topTenMovies.count());
        assertArrayEquals(new String[] { "primaryTitle", "numVotes", "averageRating", "ranking" },
                topTenMovies.columns());

        // etc.
    }
}
