# Preamble
    
This is a skeletal, but representative, Java/Spark version of the job specified in the test spec. In the spec, it says 'Your task is to write a **streaming** application' but Paul said it was the same spec for the batch role, so I wrote it as a batch job.

I wrote it in Java because, although I've used Java extensively for systems programming, I'd never written a Spark job in it before (Python and Scala only). That made it more interesting, plus it seems like an important language for you, so it is, perhaps, more useful as an interview tool.

The implementation is a bit more complex than strictly required. The class based approach to creating the queries was mainly just to write some code. I'd probably have done a simpler (pyspark) inline SQL approach in a real env, to make it more maintainable.

I know a code review isn't strictly a part of the process, so if you don't have the time/inclination to review, I understand, but I thought as I've done it.. 

# Navigation

The main class is `com.toyota.techtest.Runner` so start there. Examining the classes in usage order in the `main` method will navigate the tree. The job only does Step 1 of the spec. Step 2 seemed to add joins and grouping but as I would have solved this in the same way - views and SQL - I thought I'd spare you/me the additional, but conceptually similar, code.

I always try to use Spark SQL in preference to the API. While identical in Catalyst's representation, I find the declarative style clearer to read. It's also much harder to drop in a UDF without thinking, which requires serialisation and an external process.

The idea of a `com.toyota.techtest.spark.View` is to vary the selected columns and filters to create different views of the same underlying data file. `com.toyota.techtest.spark.imdb.VotesTest` demonstrates this. All the testing is 'representative'!

# Build and run 

\<PATH\> above 'toyota-connected-europe' will vary depending on where the code was checked-out.

See the README in `src/test/resources/imdb` for the IMDB data files which must be copied there for the following to work. No point adding them to this repo.

Run the 'tests' and build the jar using:

`mvn package -f "/\<PATH\>/toyota-connected-europe/techtest/pom.xml"`

then run the job, which just prints the results to the console:

```
spark-submit \
--class com.toyota.techtest.Runner \
--master local[*] \
/\<PATH\>/toyota-connected-europe/techtest/target/techtest-1.0-SNAPSHOT.jar \
/\<PATH\>/toyota-connected-europe/techtest/src/test/resources/imdb
```
