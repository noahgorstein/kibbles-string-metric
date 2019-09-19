package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;

import static org.junit.Assert.*;

public class TestCosineSimilarity extends AbstractStardogTest {

    @Test
    public void testCosineTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineSimilarity(\"ABC\", \"ABCE\") as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals(0.7071, Double.parseDouble(aValue), 0.0001);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineSimilarity(\"ABC\", \"ABCE\", 3) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals(0.7071, Double.parseDouble(aValue), 0.0001);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineSimilarity(\"one\", \"two\", \"three\", \"four\") as ?result) }";

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
                    "select ?result where { bind(stringmetric:cosineSimilarity(7) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineWrongTypeSecondArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineSimilarity(\"Stardog\", 2) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineWrongTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineSimilarity(\"Stardog\", \"Starlight\", \"Starship\") as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testCosineThirdArgNotConstant() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineSimilarity(\"Stardog\", \"Starlight\", ?thirdArg) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
