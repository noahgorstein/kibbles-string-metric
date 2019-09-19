package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class QGram extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.QGram qGram;

    {
        if (getArgs().size() == 3 && getArgs().get(2) instanceof Constant) {
            final int n = Integer.parseInt(((Constant) getArgs().get(2)).getValue().stringValue());
            qGram = new info.debatty.java.stringsimilarity.QGram(n);
        } else {
            qGram = new info.debatty.java.stringsimilarity.QGram();
        }
    }

    protected QGram() {
        super(Range.closed(2, 3), StringMetricVocabulary.qgram.stringValue());
    }

    private QGram(final QGram qGram) {
        super(qGram);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            if(values.length == 3) {
                assertNumericLiteral(values[2]);
            }

            return ValueOrError.Double.of(qGram.distance(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        QGram that = new QGram(this);
        that.qGram = this.qGram;
        return that;
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.qgram.name();
    }
}

