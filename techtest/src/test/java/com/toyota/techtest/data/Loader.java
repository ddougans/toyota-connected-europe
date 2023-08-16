package com.toyota.techtest.data;

import java.io.File;

import com.toyota.techtest.spark.SparkStore;

public class Loader {

    public String getDataResourceParentPath(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("imdb/" + resourceName).getFile());
        return file.getParentFile().getAbsolutePath();
    }

    public SparkStore store() {
        return SparkStore.getInstance("local[*]");
    }

}
