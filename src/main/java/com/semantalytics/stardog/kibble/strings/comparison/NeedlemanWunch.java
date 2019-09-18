package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Value;
import org.simmetrics.metrics.functions.MatchMismatch;

public final class NeedlemanWunch extends AbstractFunction implements StringFunction {

    private static org.simmetrics.metrics.NeedlemanWunch needlemanWunch;

    protected NeedlemanWunch() {
        super(Range.closed(2, 5), StringMetricVocabulary.needlemanWunch.stringValue());
    }

    private NeedlemanWunch(final NeedlemanWunch needlemanWunch) {
        super(needlemanWunch);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        if(values.length == 5) {
            for(final Expression expression : getArgs()) {
                // FIXME this should only check args 2-4 not all args
                if(!(expression instanceof Constant)) {
                    throw new ExpressionEvaluationException("Parameters 2 through 5 must be constant expressions");
                }
            }
        }

        return Values.literal(getNeedlemanWunchFunction(values).compare(firstString, secondString));
    }


    private org.simmetrics.metrics.NeedlemanWunch getNeedlemanWunchFunction(final Value... values) throws ExpressionEvaluationException {
        if (needlemanWunch == null) {
            if (values.length == 5) {
                
                final float gapValue = assertNumericLiteral(values[2]).floatValue();
                final float subPenaltyA = assertNumericLiteral(values[3]).floatValue();
                final float subPenaltyB = assertNumericLiteral(values[4]).floatValue();
                
                needlemanWunch = new org.simmetrics.metrics.NeedlemanWunch(gapValue, new MatchMismatch(subPenaltyA, subPenaltyB));
            } else {
                needlemanWunch = new org.simmetrics.metrics.NeedlemanWunch();
            }
        }
        return needlemanWunch;
    }

    @Override
    public Function copy() {
        return new NeedlemanWunch(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.needlemanWunch.name();
    }
}
