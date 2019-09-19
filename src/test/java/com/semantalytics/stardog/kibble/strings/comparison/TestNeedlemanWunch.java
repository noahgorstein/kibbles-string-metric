package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestNeedlemanWunch extends AbstractStardogTest {

    @Test
    public void testNeedlemanWunch() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:needlemanWunch(\"Stardog\", \"Starman\") as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("dist").stringValue();

        assertEquals(0.78571427, Double.parseDouble(aValue), 0.00001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testNeedlemanWunchTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:needlemanWunch(\"one\", \"two\", \"three\", \"four\", \"five\", \"six\") as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testNeedlemanWunchWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:needlemanWunch(7) as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
