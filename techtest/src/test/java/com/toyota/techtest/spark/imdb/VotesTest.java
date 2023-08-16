package com.toyota.techtest.spark.imdb;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.toyota.techtest.data.Loader;

class VotesTest {

    private static VotesView votes;
    private static Loader loader = new Loader();

    @BeforeAll
    public static void setUp() {
        var store = loader.store();
        votes = new VotesView(store, loader.getDataResourceParentPath(VotesView.FILE));
    }

    @Test
    void testAllDataPresent() {
        votes.create("allVoteColumns", VotesView.ALL_COLUMNS);
        var store = loader.store();
        var allVoteColumns = store.select("select * from allVoteColumns");
        allVoteColumns.persist();

        assertArrayEquals(allVoteColumns.columns(), VotesView.ALL_COLUMNS);
        assertEquals(1337835, allVoteColumns.count());
    }

    @Test
    void testColumnSubsetPresent() {
        String[] expectedColumns = { "averageRating", "numVotes" };
        votes.create("someVoteColumns", expectedColumns);
        var store = loader.store();
        var someVoteColumns = store.select("select * from someVoteColumns");
        someVoteColumns.persist();

        assertArrayEquals(someVoteColumns.columns(), expectedColumns);
        assertEquals(1337835, someVoteColumns.count());
    }

    @Test
    void testFilterAppplied() {
        String[] expectedColumns = { "averageRating", "numVotes" };
        votes.create("someVoteColumns", expectedColumns, Optional.of("numVotes in (2783170, 2135360)"));
        var store = loader.store();
        var someVoteColumns = store.select("select * from someVoteColumns");
        someVoteColumns.persist();

        assertArrayEquals(someVoteColumns.columns(), expectedColumns);
        assertEquals(2, someVoteColumns.count());

        // etc.
    }

}
