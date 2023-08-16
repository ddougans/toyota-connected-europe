package com.toyota.techtest.spark.imdb;

import com.toyota.techtest.spark.SparkStore;
import com.toyota.techtest.spark.View;

public class VotesView extends View {

    public static final String FILE = "title.ratings.tsv.gz";
    public static final String[] ALL_COLUMNS = { "tconst", "averageRating", "numVotes" };

    public VotesView(SparkStore store, String prefix) {
        super(store, prefix, FILE);
    }

}
