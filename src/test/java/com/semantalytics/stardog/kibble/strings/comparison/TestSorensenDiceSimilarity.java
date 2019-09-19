package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import org.junit.*;
import org.openrdf.model.Value;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestSorensenDiceSimilarity extends AbstractStardogTest {

    @Test
    public void testThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:sorensenDiceSimilarity(\"ABCDE\", \"ABCDFG\", 2) as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().getValue("result");

                assertTrue(aValue instanceof Literal);

                final double aLiteralValue = ((Literal) aValue).doubleValue();

                assertEquals(0.6666, aLiteralValue, 0.0001);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:sorensenDiceSimilarity(\"one\", \"two\", \"three\", \"four\") as ?str) }";

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
                    "select ?result where { bind(stringmetric:sorensenDiceSimilarity(\"one\") as ?result) }";

            try(final QueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
