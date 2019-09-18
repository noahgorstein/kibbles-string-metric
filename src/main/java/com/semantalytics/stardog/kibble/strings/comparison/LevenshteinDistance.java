package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class LevenshteinDistance extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.Levenshtein levenshtein;

    static {
        levenshtein = new info.debatty.java.stringsimilarity.Levenshtein();
    }

    protected LevenshteinDistance() {
        super(2, StringMetricVocabulary.levenshteinDistance.stringValue());
    }

    private LevenshteinDistance(final LevenshteinDistance levenshteinDistance) {
        super(levenshteinDistance);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertStringLiteral(values[0]);
        assertStringLiteral(values[1]);

        return literal(levenshtein.distance(values[0].stringValue(), values[1].stringValue()));
    }

    public Function copy() {
        return new LevenshteinDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.levenshteinDistance.name();
    }
}
