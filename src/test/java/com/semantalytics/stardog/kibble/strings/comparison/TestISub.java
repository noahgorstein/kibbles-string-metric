package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestISub extends AbstractStardogTest {

    @Test
    public void testISubTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:iSub(\"ABC\", \"ABCE\") as ?dist) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("dist").stringValue();

            assertEquals(0.9, Double.parseDouble(aValue), 0.0001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testISubThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:iSub(\"ABC\", \"ABCE\", true) as ?dist) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("dist").stringValue();

            assertEquals(0.9, Double.parseDouble(aValue), 0.0001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testISubTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:iSub(\"one\", \"two\", \"three\", \"four\") as ?str) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testISubWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:iSub(7) as ?str) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
