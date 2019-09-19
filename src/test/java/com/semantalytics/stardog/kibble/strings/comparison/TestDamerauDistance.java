package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestDamerauDistance extends AbstractStardogTest {

    @Test
    public void testTwoArgument() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:damerauDistance(\"ABCDEF\", \"BACDFE\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals(2.0, Double.parseDouble(aValue), 0.0);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:damerauDistance(\"one\", \"two\", \"three\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:damerauDistance(\"one\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testFirstArgumentWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:damerauDistance(7, \"Stardog\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testSecondArgumentWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:damerauDistance(\"Stardog\", 7) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
