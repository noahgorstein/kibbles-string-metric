package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestMongeElkan extends AbstractStardogTest {

    @Test
    public void testCosineTwoArg() {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"ABC\", \"ABCE\") as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals(0.29289, Double.parseDouble(aValue), 0.0001);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"ABC\", \"ABCE\", 3) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals(0.29289, Double.parseDouble(aValue), 0.0001);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"one\", \"two\", \"three\", \"four\") as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineWrongTypeFirstArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(7) as ?result) }";

            final QueryResult aResult = connection.select(aQuery).execute();
            try {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
            }
            finally {
                aResult.close();
            }
    }

    @Test
    public void testCosineWrongTypeSecondArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"Stardog\", 2) as ?result) }";

            final QueryResult aResult = connection.select(aQuery).execute();
            try {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
            }
            finally {
                aResult.close();
            }
    }

    @Test
    public void testCosineWrongTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"Stardog\", \"Starlight\", \"Starship\") as ?result) }";

            final QueryResult aResult = connection.select(aQuery).execute();
            try {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
            }
            finally {
                aResult.close();
            }
    }

    @Test
    public void testCosineThirdArgNotConstant() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"Stardog\", \"Starlight\", ?thirdArg) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
