package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import org.ivml.alimo.I_Sub;

public final class ISub extends AbstractFunction implements StringFunction {

    private static final I_Sub iSub = new I_Sub();

    private ISub(ISub iSub) {
        super(iSub);
    }

    protected ISub() {
        super(Range.closed(2, 3), StringMetricVocabulary.isub.stringValue());
    }

    @Override
    public Function copy() {
        return new ISub(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final String firstString = assertLiteral(values[0]).stringValue();
        final String secondString = assertLiteral(values[1]).stringValue();

        boolean normalizeStrings = false;

        if(values.length == 3) {
            if(!(getThirdArg() instanceof Constant)) {
                throw new ExpressionEvaluationException("Parameter must be a constant expression");
            }
            normalizeStrings = EvalUtil.toBoolean(values[2].stringValue());
        }

        return Values.literal(iSub.score(firstString, secondString, normalizeStrings));
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.isub.name();
    }
}
