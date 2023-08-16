package com.toyota.techtest.spark;

import java.util.Collections;
import java.util.Map;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public final class SparkStore {

    private SparkSession spark = null;
    private static SparkStore INSTANCE = null;

    private SparkStore(SparkSession spark) {
        this.spark = spark;
    }

    public static synchronized SparkStore getInstance(String master) {
        if (INSTANCE == null) {
            var bldr = SparkSession
                    .builder()
                    .appName("Toyota tech test");

            if (master != null) {
                bldr.master(master);
            }

            INSTANCE = new SparkStore(bldr.getOrCreate());
        }
        return INSTANCE;
    }

    public static SparkStore getInstance() {
        return getInstance(null);
    }

    public Dataset<Row> readTsv(String source) {
        return spark.read().option("sep", "\t").option("header", "true").csv(source);
    }

    public Dataset<Row> select(String statement, Map<String, Object> args) {
        return spark.sql(statement, args);
    }

    public Dataset<Row> select(String statement) {
        return spark.sql(statement, Collections.emptyMap());
    }

}
