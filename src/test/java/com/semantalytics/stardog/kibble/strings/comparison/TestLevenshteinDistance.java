package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLevenshteinDistance extends AbstractStardogTest {

    @Test
    public void testLevenshtein() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:levenshteinDistance(\"My string\", \"My tring\") as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("dist").stringValue();

        assertEquals(1.0, Double.parseDouble(aValue), 0.0);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testLevenstheinTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:levenshteinDistance(\"one\", \"two\", \"three\") as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testLevenshteinWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:levenshteinDistance(7) as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
