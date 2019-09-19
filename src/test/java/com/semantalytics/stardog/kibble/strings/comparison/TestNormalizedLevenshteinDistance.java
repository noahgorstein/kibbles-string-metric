package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestNormalizedLevenshteinDistance extends AbstractStardogTest {

    @Test
    public void testNormalizedLevenshteinDistance() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:normalizedLevenshteinDistance(\"My string\", \"My $tring\") as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("dist").stringValue();

        assertEquals(0.1111, Double.parseDouble(aValue), 0.001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testNormalizedLevenstheinTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:normalizedLevenshteinDistance(\"one\", \"two\", \"three\") as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testNormalizedLevenshteinDistanceWrongType() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:normalizedLevenshteinDistance(7) as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
