package com.toyota.techtest.spark.imdb;

import com.toyota.techtest.spark.SparkStore;
import com.toyota.techtest.spark.View;

public class BasicsView extends View {

    public static final String FILE = "title.basics.tsv.gz";

    public BasicsView(SparkStore store, String prefix) {
        super(store, prefix, FILE);
    }

}
