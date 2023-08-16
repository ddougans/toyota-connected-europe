package com.toyota.techtest;

import com.toyota.techtest.spark.SparkStore;
import com.toyota.techtest.spark.imdb.BasicsView;
import com.toyota.techtest.spark.imdb.Queries;
import com.toyota.techtest.spark.imdb.VotesView;

public final class Runner {

    public static void main(String[] args) {

        var pathPrefix = args[0];

        var store = SparkStore.getInstance();
        var votes = new VotesView(store, pathPrefix);
        var basics = new BasicsView(store, pathPrefix);

        var queries = new Queries(store);
        var topTenMovies = queries.topTenMovies(votes, basics);
        topTenMovies.show(10, 100);
    }

}
