package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

public final class OverlapCoefficient extends AbstractFunction implements StringFunction {

    protected OverlapCoefficient() {
        super(2, StringMetricVocabulary.overlapCoefficient.stringValue());
    }

    private OverlapCoefficient(final OverlapCoefficient overlapCoefficient) {
        super(overlapCoefficient);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        return literal(StringMetrics.overlapCoefficient().compare(firstString, secondString));
    }

    @Override
    public OverlapCoefficient copy() {
        return new OverlapCoefficient(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.overlapCoefficient.name();
    }
}
