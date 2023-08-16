package com.toyota.techtest.spark.imdb;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

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
    void testAllDataPresent() throws IOException {
        votes.create("allVoteColumns", VotesView.ALL_COLUMNS);
        var store = loader.store();
        var allVoteColumns = store.select("select * from allVoteColumns");
        allVoteColumns.persist();

        var expectedCount = getLineCount(votes.getSource());
        assertArrayEquals(allVoteColumns.columns(), VotesView.ALL_COLUMNS);
        assertEquals(expectedCount, allVoteColumns.count());
    }

    @Test
    void testColumnSubsetPresent() throws IOException {
        String[] expectedColumns = { "averageRating", "numVotes" };
        votes.create("someVoteColumns", expectedColumns);
        var store = loader.store();
        var someVoteColumns = store.select("select * from someVoteColumns");
        someVoteColumns.persist();

        var expectedCount = getLineCount(votes.getSource());
        assertArrayEquals(someVoteColumns.columns(), expectedColumns);
        assertEquals(expectedCount, someVoteColumns.count());
    }

    @Test
    void testFilterAppplied() {
        String[] expectedColumns = { "averageRating", "numVotes" };
        votes.create("someVoteColumns", expectedColumns, Optional.of("tconst in ('tt9916682', 'tt9916216')"));
        var store = loader.store();
        var someVoteColumns = store.select("select * from someVoteColumns");
        someVoteColumns.persist();
        
        assertArrayEquals(someVoteColumns.columns(), expectedColumns);
        assertEquals(2, someVoteColumns.count());

        // etc.
    }

    private long getLineCount(String path) throws IOException {
        long lines = -1; // Header offset
        try (FileInputStream fis = new FileInputStream(votes.getSource());
            GZIPInputStream gz = new GZIPInputStream(fis);
            InputStreamReader reader = new InputStreamReader(gz);
            BufferedReader br = new BufferedReader(reader)) {
                while (br.readLine() != null) lines++;
        }     
        
        return lines;
    }

}
