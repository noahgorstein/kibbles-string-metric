package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;

public final class JaroWinklerDistance extends AbstractFunction implements StringFunction {


    protected JaroWinklerDistance() {
        super(Range.closed(2, 5), StringMetricVocabulary.jaroWinklerDistance.stringValue());
    }

    private JaroWinklerDistance(final JaroWinklerDistance jaroWinklerDistance) {
        super(jaroWinklerDistance);
    }

    @Override
    protected ValueOrError internalEvaluate(final ValueOrError... values) {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        float boostThreshold = 0.7f;
        float prefixScale = 0.1f;
        int maxPrefixLength = 4;

        if(values.length >= 3) {
            boostThreshold = assertNumericLiteral(values[2]).floatValue();
        }
        if(values.length >= 4) {
            prefixScale = assertNumericLiteral(values[3]).floatValue();
        }
        if(values.length == 5) {
            maxPrefixLength = assertNumericLiteral(values[4]).intValue();
        }

        final org.simmetrics.metrics.JaroWinkler jaroWinkler;
        jaroWinkler = new org.simmetrics.metrics.JaroWinkler(boostThreshold, prefixScale, maxPrefixLength);

        return ValueOrError.Float.of(jaroWinkler.distance(firstString, secondString));
    }

    public Function copy() {
        return new JaroWinklerDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.jaroWinklerDistance.name();
    }
}
