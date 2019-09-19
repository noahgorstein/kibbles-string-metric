package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.simmetrics.metrics.functions.AffineGap;
import org.simmetrics.metrics.functions.MatchMismatch;

public final class SmithWaterman extends AbstractFunction implements StringFunction {

    private org.simmetrics.metrics.SmithWaterman smithWaterman;

    protected SmithWaterman() {
        super(Range.closed(2, 7), StringMetricVocabulary.smithWaterman.stringValue());
    }

    private SmithWaterman(final SmithWaterman smithWaterman) {
        super(smithWaterman);
    }

    @Override
    public void initialize() {
        smithWaterman = null;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            switch(values.length) {
                case 7: {
                    for (final Expression expression : getArgs()) {
                        if (!(expression instanceof Constant)) {
                            return ValueOrError.Error;
                        }
                    }
                    if(assertNumericLiteral(values[2]) && assertNumericLiteral(values[3]) ) {
                        final float gapA = Literal.floatValue((Literal)values[2]);
                        final float gapB = Literal.floatValue((Literal)values[3]);
                    } else {
                        return ValueOrError.Error;
                    }

                    if(assertNumericLiteral(values[4]) && assertNumericLiteral(values[5])) {

                        final float subPenaltyA = Literal.floatValue((Literal)values[4]);
                        final float subPenaltyB = Literal.floatValue((Literal)values[5]);
                    } else {
                        return ValueOrError.Error;
                    }

                    if(assertNumericLiteral(values[6])) {
                        final int windowSize = Literal.intValue((Literal)values[6]);
                    } else {
                        return ValueOrError.Error;
                    }

                    org.simmetrics.metrics.SmithWaterman sw  = new org.simmetrics.metrics.SmithWaterman(new AffineGap(gapA, gapB), new MatchMismatch(subPenaltyA, subPenaltyB), windowSize);
                    return ValueOrError.Float.of(getSmithWatermanFunction(values).compare(firstString, secondString));
                }
                case 2: {
                    org.simmetrics.metrics.SmithWaterman sw  = new org.simmetrics.metrics.SmithWaterman();
                    return ValueOrError.Float.of(sw.compare(firstString, secondString));
                }
                default: {
                    return ValueOrError.Error;
                }
            }

            return ValueOrError.Float.of(getSmithWatermanFunction(values).compare(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }

    }

    private org.simmetrics.metrics.SmithWaterman getSmithWatermanFunction(final Value... values) throws ExpressionEvaluationException {
    }

    @Override
    public Function copy() {
        return new SmithWaterman(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.smithWaterman.name();
    }
}
