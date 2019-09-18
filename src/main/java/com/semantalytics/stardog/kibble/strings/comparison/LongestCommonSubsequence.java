package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;

public final class LongestCommonSubsequence extends AbstractFunction implements StringFunction {

    static {
        longestCommonSubsequence = new org.simmetrics.metrics.LongestCommonSubsequence();
    }

    private static final org.simmetrics.metrics.LongestCommonSubsequence longestCommonSubsequence;

    protected LongestCommonSubsequence() {
        super(2, StringMetricVocabulary.longestCommonSubsequence.stringValue());
    }

    private LongestCommonSubsequence(final LongestCommonSubsequence longestCommonSubsequence) {
        super(longestCommonSubsequence);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        return literal(longestCommonSubsequence.compare(firstString, secondString));
    }

    public Function copy() {
        return new LongestCommonSubsequence(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.longestCommonSubsequence.name();
    }
}

