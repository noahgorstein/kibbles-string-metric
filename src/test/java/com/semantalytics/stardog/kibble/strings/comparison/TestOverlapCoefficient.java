package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.junit.Assert.*;

public class TestOverlapCoefficient extends AbstractStardogTest {

    @Test
    public void testOverlapCoefficient() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(\"Stardog\", \"Starman\") as ?overlapCoefficient) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("overlapCoefficient").get();

        assertEquals(0.0, Float.parseFloat(aValue), 0.0001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(\"one\", \"two\", \"three\", \"four\") as ?overlapCoefficient) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testCosineWrongType() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?overlapCoefficient where { bind(stringmetric:overlapCoefficient(7) as ?overlapCoefficient) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
