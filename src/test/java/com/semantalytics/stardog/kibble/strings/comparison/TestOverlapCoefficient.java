package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestOverlapCoefficient extends AbstractStardogTest {

    @Test
    public void testOverlapCoefficient() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(\"Stardog\", \"Starman\") as ?overlapCoefficient) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("overlapCoefficient").stringValue();

        assertEquals(0.0, Float.parseFloat(aValue), 0.0001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(\"one\", \"two\", \"three\", \"four\") as ?overlapCoefficient) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testCosineWrongType() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(7) as ?overlapCoefficient) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
