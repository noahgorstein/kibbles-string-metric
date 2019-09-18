package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;

import static com.google.common.base.Preconditions.checkArgument;

public final class HammingDistance extends AbstractFunction implements StringFunction {

    protected HammingDistance() {
        super(2, StringMetricVocabulary.hammingDistance.stringValue());
    }

    private HammingDistance(final HammingDistance hammingDistance) {
        super(hammingDistance);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string1 = assertStringLiteral(values[0]).stringValue();
        final String string2 = assertStringLiteral(values[1]).stringValue();
        
        if(string1.length() != string2.length()) {
            throw new ExpressionEvaluationException("Argument lengths must be equal");
        }

        return Values.literal(distance(string1, string2));
    }

    private double distance(final String a, final String b) {
        checkArgument(a.length() == b.length());

        if (a.isEmpty()) {
            return 0;
        }

        int distance = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    @Override
    public Function copy() {
        return new HammingDistance(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.hammingDistance.name();
    }
}
