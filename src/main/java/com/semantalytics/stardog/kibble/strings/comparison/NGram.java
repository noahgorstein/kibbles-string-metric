package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Value;

public final class NGram extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.NGram nGram;

    {
        if (getArgs().size() == 3 && getArgs().get(2) instanceof Constant) {
            final int n = Integer.parseInt(((Constant) getArgs().get(2)).getValue().stringValue());
            nGram = new info.debatty.java.stringsimilarity.NGram(n);
        } else {
            nGram = new info.debatty.java.stringsimilarity.NGram();
        }
    }

    protected NGram() {
        super(Range.closed(2, 3), StringMetricVocabulary.ngram.stringValue());
    }

    private NGram(final NGram nGram) {
        super(nGram);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertStringLiteral(values[0]);
        assertStringLiteral(values[1]);
        if(values.length == 3) {
            assertNumericLiteral(values[2]);
        }

        return literal(nGram.distance(values[0].stringValue(), values[1].stringValue()));
    }

    public Function copy() {
        NGram that = new NGram(this);
        that.nGram = this.nGram;
        return that;
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.needlemanWunch.name();
    }
}
