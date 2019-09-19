package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestMetricLongestCommonSubsequence extends AbstractStardogTest {

    @Test
    public void testMetricLongestCommonSubsequence() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:metricLongestCommonSubsequence(\"ABCDEFG\", \"ABCDEFHJKL\") as ?dist) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("dist").stringValue();

        assertEquals(0.4, Double.parseDouble(aValue), 0.0);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testMetricLongestCommonSubsequenceTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:metricLongestCommonSubsequence(\"one\", \"two\", \"three\") as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testMetricLongestCommonSubsequenceWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:metricLongestCommonSubsequence(7) as ?str) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
