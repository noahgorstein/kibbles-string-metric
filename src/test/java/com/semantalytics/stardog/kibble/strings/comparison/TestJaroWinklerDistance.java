package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestJaroWinklerDistance extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = "prefix stringmetric: <" + StringMetricVocabulary.NAMESPACE + "> " +
                "select ?dist where { bind(stringmetric:jaroWinklerDistance(\"My string\", \"My tsring\") as ?dist) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("dist").stringValue();

            assertEquals(0.025925, Double.parseDouble(aValue), 0.000001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?distance where { bind(stringmetric:jaroWinklerDistance(\"My string\", \"My tsring\", 0.1) as ?distance) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("distance").stringValue();

        assertEquals(0.025925, Double.parseDouble(aValue), 0.000001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testThreeArgIncorrectTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?distance where { bind(stringmetric:jaroWinklerDistance(\"My string\", \"My tsring\", \"x\") as ?distance) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:jaroWinklerDistance(\"one\", \"two\", 0.7, \"four\") as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testTooFewArgs() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:jaroWinklerDistance(7) as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
