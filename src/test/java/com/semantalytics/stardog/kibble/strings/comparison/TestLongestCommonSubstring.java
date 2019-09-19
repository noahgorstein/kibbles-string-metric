package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.QueryResult;

import static org.junit.Assert.*;

public class TestLongestCommonSubstring extends AbstractStardogTest {

    @Test
    public void testLongestCommonSubstring() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:longestCommonSubstring(\"Stardog\", \"Starman\") as ?result) }";

        final QueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final String aValue = aResult.next().getValue("result").stringValue();

        assertEquals(0.84615386, Double.parseDouble(aValue), 0.0001);

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testLongestCommonSubstringTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:longestCommonSubstring(\"one\", \"two\", \"three\") as ?result) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testLongestCommonSubstringWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:longestCommonSubstring(7) as ?result) }";

        final QueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
