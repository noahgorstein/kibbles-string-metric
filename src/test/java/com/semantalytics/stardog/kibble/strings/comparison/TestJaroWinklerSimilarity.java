package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestJaroWinklerSimilarity extends AbstractStardogTest {

    @Test
    public void testJaroWinkler() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:jaroWinklerSimilarity(\"My string\", \"My tsring\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals(0.9740740656852722, Double.parseDouble(aValue), 0.000001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:jaroWinklerSimilarity(\"\", \"\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final double aValue = ((Literal) aResult.next().getValue("result")).doubleValue();

            assertEquals(1.0, aValue, 0.000001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testJaroWinklerThreeArgIncorrectThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:jaroWinklerSimilarity(\"My string\", \"My tsring\", \"x\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testJaroWinklerTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:jaroWinklerSimilarity(\"one\", \"two\", \"three\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testJaroWinklerWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
               "select ?result where { bind(stringmetric:jaroWinklerSimilarity(7) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
