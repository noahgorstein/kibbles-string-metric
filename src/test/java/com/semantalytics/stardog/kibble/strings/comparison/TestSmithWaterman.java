package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import org.junit.*;
import org.openrdf.model.Value;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestSmithWaterman extends AbstractStardogTest {

    @Test
    public void testSmithWatermanTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWaterman(\"Stardog\", \"Starman\") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final float aLiteralValue = ((Literal) aValue).floatValue();

            assertEquals(0.5714286, aLiteralValue, 0.00001);

            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testSmithWatermanSevenArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWaterman(\"Stardog\", \"Starman\", -5.0, -1.0, 5.0, -3.0, " + Integer.MAX_VALUE + ") as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());
            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final float aLiteralValue = ((Literal) aValue).floatValue();

            assertEquals(0.5714286, aLiteralValue, 0.00001);

            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWaterman(\"Stardog\", \"Starman\", 1.0) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testSmithWatermanWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWaterman(7) as ?result) }";

        try(final QueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
