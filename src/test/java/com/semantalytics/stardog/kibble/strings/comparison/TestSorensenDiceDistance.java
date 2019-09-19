package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestSorensenDiceDistance extends AbstractStardogTest {

    @Test
    public void testSorensenDiceDistance() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:sorensenDiceDistance(\"ABCDE\", \"ABCDFG\", 2) as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("dist").stringValue();

        assertEquals(0.3333, Double.parseDouble(aValue), 0.0001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testSorensenDiceDistanceTooManyArgs() {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?str where { bind(stringmetric:sorensenDiceDistance(\"one\", \"two\", \"three\", \"four\") as ?str) }";

            final QueryResult aResult = connection.select(aQuery).execute();
         
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

            assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testSorensenDiceDistanceWrongType() {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?str where { bind(stringmetric:sorensenDiceDistance(7) as ?str) }";

            final QueryResult aResult = connection.select(aQuery).execute();
        
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

            assertFalse("Should have no more results", aResult.hasNext());
    }
}
