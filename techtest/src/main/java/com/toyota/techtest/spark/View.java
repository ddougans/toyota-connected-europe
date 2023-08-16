package com.toyota.techtest.spark;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Side-affecting class to created a temp view in the the metastore.
 */
public abstract class View {

    protected SparkStore store;
    private String source;
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public String getSource() {
        return source;
    }    

    public void create(String viewName, String[] columns, Optional<String> filter) {
        this.viewName = viewName;
        this.createView(this.source, viewName, Optional.of(columns), filter);
    }

    public void create(String viewName, String[] columns) {
        this.create(viewName, columns, Optional.empty());
    }

    protected View(SparkStore store, String pathPrefix, String file) {
        this.store = store;
        this.source = this.path(pathPrefix, file);
    }

    private String path(String pathPrefix, String file) {
        return Path.of(pathPrefix, file).toAbsolutePath().toString();
    }

    private void createView(String source, String viewName, Optional<String[]> columns, Optional<String> filter) {
        var df = this.store.readTsv(source);
        var filtered = filter.map(predicate -> df.filter(predicate)).orElse(df);
        var results = columns.map(subset -> filtered.selectExpr(subset)).orElse(filtered);
        results.createOrReplaceTempView(viewName);
    }

}
