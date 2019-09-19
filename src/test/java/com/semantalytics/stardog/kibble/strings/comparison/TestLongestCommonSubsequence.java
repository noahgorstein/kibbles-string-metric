package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.api.Connection;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestLongestCommonSubsequence extends AbstractStardogTest {

    @Test
    public void testLongestCommonSubsequence() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:longestCommonSubsequence(\"AGCAT\", \"GAC\") as ?result) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("result").stringValue();

        assertEquals(0.4, Double.parseDouble(aValue), 0.0);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testLongestCommonSubsequenceTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:longestCommonSubsequence(\"one\", \"two\", \"three\") as ?result) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testLongestCommonSubsequenceWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:longestCommonSubsequence(7) as ?result) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
