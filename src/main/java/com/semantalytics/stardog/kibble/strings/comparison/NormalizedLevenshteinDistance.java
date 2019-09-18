package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

public final class NormalizedLevenshteinDistance extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.NormalizedLevenshtein normalizedLevenshtein;

    static {
        normalizedLevenshtein = new info.debatty.java.stringsimilarity.NormalizedLevenshtein();
    }

    protected NormalizedLevenshteinDistance() {
        super(2, StringMetricVocabulary.normalizedLevenshteinDistance.stringValue());
    }

    private NormalizedLevenshteinDistance(final NormalizedLevenshteinDistance normalizedLevenshteinDistance) {
        super(normalizedLevenshteinDistance);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        return literal(normalizedLevenshtein.distance(firstString, secondString));
    }

    public Function copy() {
        return new NormalizedLevenshteinDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.normalizedLevenshteinDistance.name();
    }
}
