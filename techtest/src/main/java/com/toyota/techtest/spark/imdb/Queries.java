package com.toyota.techtest.spark.imdb;

import java.text.MessageFormat;
import java.util.Optional;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.toyota.techtest.spark.SparkStore;

public class Queries {

    private SparkStore store;

    public Queries(SparkStore store) {
        this.store = store;
    }

    public Dataset<Row> topTenMovies(VotesView votes, BasicsView basics) {

        votes.create("votes", VotesView.ALL_COLUMNS);

        basics.create("basics",
                new String[] { "tconst", "titleType", "primaryTitle" },
                Optional.of("titleType = 'movie'"));

        // This answers Step 1.
        // Step 2 not attempted because it seemed like more of the same; creating views
        // and writing SQL (already demonstrated).
        var statement = MessageFormat.format("""
                   select b.primaryTitle, v.numVotes, v.averageRating,
                          v.numVotes / (select sum(numVotes) / count(*) from {0}) * v.averageRating as ranking
                   from {0} v
                   join {1} b on v.tconst = b.tconst
                   where v.numVotes >= 500
                   order by ranking desc
                   limit 10
                """, votes.getViewName(), basics.getViewName());

        return this.store.select(statement);
    }

}
