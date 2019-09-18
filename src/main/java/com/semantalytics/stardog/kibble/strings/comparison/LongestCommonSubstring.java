package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

public final class LongestCommonSubstring extends AbstractFunction implements StringFunction {

    static {
        longestCommonSubstring = new org.simmetrics.metrics.LongestCommonSubstring();
    }

    private static final org.simmetrics.metrics.LongestCommonSubstring longestCommonSubstring;

    protected LongestCommonSubstring() {
        super(2, StringMetricVocabulary.longestCommonSubstring.stringValue());
    }

    private LongestCommonSubstring(final LongestCommonSubstring longestCommonSubsequence) {
        super(longestCommonSubsequence);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertStringLiteral(values[0]);
        assertStringLiteral(values[1]);

        return literal(longestCommonSubstring.compare(values[0].toString(), values[1].toString()));
    }

    public Function copy() {
        return new LongestCommonSubstring(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                                                                                  }

    @Override
    public String toString() {
        return StringMetricVocabulary.longestCommonSubstring.name();
    }
}

