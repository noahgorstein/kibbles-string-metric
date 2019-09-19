package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestHammingDistance extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?hammingDistance where { bind(stringmetric:hammingDistance(\"Stardog\", \"Starman\") as ?hammingDistance) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("hammingDistance").stringValue();

            assertEquals(3.0, Double.parseDouble(aValue), 0.0001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?hammingDistance where { bind(stringmetric:hammingDistance(\"one\", \"two\", \"three\", \"four\") as ?hammingDistance) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?hammingDistance where { bind(stringmetric:hammingDistance(7) as ?hammingDistance) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
