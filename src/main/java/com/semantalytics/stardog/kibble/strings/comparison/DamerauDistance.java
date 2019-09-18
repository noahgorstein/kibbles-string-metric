package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;

public final class DamerauDistance extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.Damerau damerau;

    static {
        damerau = new info.debatty.java.stringsimilarity.Damerau();
    }

    protected DamerauDistance() {
        super(2, StringMetricVocabulary.damerauDistance.stringValue());
    }

    private DamerauDistance(final DamerauDistance damerauDistance) {
        super(damerauDistance);
    }

    @Override
    protected Value internalEvaluate(final Value... values) {

        final String string1 = assertStringLiteral(values[0]).stringValue();
        final String string2 = assertStringLiteral(values[1]).stringValue();

        return literal(damerau.distance(string1, string2));
    }

    public Function copy() {
        return new DamerauDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.damerauDistance.name();
    }
}
