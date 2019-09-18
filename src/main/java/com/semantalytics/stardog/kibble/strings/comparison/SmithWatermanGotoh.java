package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import org.simmetrics.metrics.functions.MatchMismatch;

public final class SmithWatermanGotoh extends AbstractFunction implements StringFunction {

    private org.simmetrics.metrics.SmithWatermanGotoh smithWatermanGotoh;

    protected SmithWatermanGotoh() {
        super(Range.closed(2, 5), StringMetricVocabulary.smithWatermanGotoh.stringValue());
    }

    private SmithWatermanGotoh(final SmithWatermanGotoh smithWatermanGotoh) {
        super(smithWatermanGotoh);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        return Values.literal(getSmithWatermanGotohFunction().compare(firstString, secondString));
    }

    private org.simmetrics.metrics.SmithWatermanGotoh getSmithWatermanGotohFunction(final Value... values) throws ExpressionEvaluationException {
        if (smithWatermanGotoh == null) {
            if (values.length == 5) {
                for (final Expression expression : getArgs()) {
                    //FIXME check only 2 through 7 not all params
                    if (!(expression instanceof Constant)) {
                        throw new ExpressionEvaluationException("Parameters 2 through 7 must be constant expressions");
                    }
                }
                
                final float gapValue = assertNumericLiteral(values[2]).floatValue();
                final float matchPenalty = assertNumericLiteral(values[3]).floatValue();
                final float mismatchPenalty = assertNumericLiteral(values[4]).floatValue();
                
                smithWatermanGotoh = new org.simmetrics.metrics.SmithWatermanGotoh(gapValue, new MatchMismatch(matchPenalty, mismatchPenalty));
            } else {
                smithWatermanGotoh = new org.simmetrics.metrics.SmithWatermanGotoh();
            }
        }
        return smithWatermanGotoh;
    }

    @Override
    public void initialize() {
        smithWatermanGotoh = null;
    }

    @Override
    public Function copy() {
        return new SmithWatermanGotoh(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.smithWatermanGotoh.name();
    }
}
