package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.junit.Assert.*;

public class TestWeightedLevenshteinDistance extends AbstractStardogTest {

   
    @Test
    public void testLevenshteinDistance() throws Exception {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?dist where { bind(stringmetric:weightedLevenshteinDistance(\"String1\", \"Srring2\", \"t\", \"r\", 0.5) as ?dist) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("dist").get();

                assertEquals(1.5, Double.parseDouble(aValue), 0.0);
                assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testLevenstheinTooManyArgs() throws Exception {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +

                    "select ?str where { bind(stringmetric:weightedLevenshteinDistance(\"one\", \"two\", \"three\") as ?str) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
       
                // there should be a result because implicit in the query is the singleton set, so because the bind
                // should fail due to the value error, we expect a single empty binding
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            
    }

    @Test
    public void testLevenshteinDistanceWrongType() throws Exception {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +

     
                    "select ?str where { bind(stringmetric:weightedLevenshteinDistance(7) as ?str) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
       
                // there should be a result because implicit in the query is the singleton set, so because the bind
                // should fail due to the value error, we expect a single empty binding
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
    }
}
