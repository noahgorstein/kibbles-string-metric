package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import org.junit.*;
import org.openrdf.model.Value;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestSift4 extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:sift4(\"This is the first string\", \"And this is another string\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final double aLiteralValue = ((Literal)aValue).doubleValue();

            assertEquals(11.0, aLiteralValue, 0.0);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:sift4(\"This is the first string\", \"And this is another string\", 5) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final double aLiteralValue = ((Literal)aValue).doubleValue();

            assertEquals(11.0, aLiteralValue, 0.0);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:sift4(\"one\", \"two\", \"three\", \"four\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:sift4(7) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:sift4(\"Stardog\", 2) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:sift4(\"Stardog\", \"Starlight\", \"Starship\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testThirdArgNotConstant() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:sift4(\"Stardog\", \"Starlight\", ?thirdArg) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
