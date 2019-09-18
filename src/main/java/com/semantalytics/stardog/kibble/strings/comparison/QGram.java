package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Value;

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
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String firstString = assertStringLiteral(values[0]).stringValue();
        final String secondString = assertStringLiteral(values[1]).stringValue();

        if(values.length == 3) {
            assertNumericLiteral(values[2]);
        }

        return literal(qGram.distance(firstString, secondString));
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

