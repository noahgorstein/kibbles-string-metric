package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.junit.Assert.*;

public class TestNormalizedLevenshteinDistance extends AbstractStardogTest {

    @Test
    public void testNormalizedLevenshteinDistance() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:normalizedLevenshteinDistance(\"My string\", \"My $tring\") as ?dist) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("dist").get();

        assertEquals(0.1111, Double.parseDouble(aValue), 0.001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testNormalizedLevenstheinTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:normalizedLevenshteinDistance(\"one\", \"two\", \"three\") as ?str) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testNormalizedLevenshteinDistanceWrongType() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:normalizedLevenshteinDistance(7) as ?str) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
