package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import info.debatty.java.stringsimilarity.MetricLCS;
import org.openrdf.model.Value;

public class MetricLongestCommonSubsequence extends AbstractFunction implements StringFunction {

    private static final MetricLCS metricLCS = new MetricLCS();

    protected MetricLongestCommonSubsequence() {
        super(2, StringMetricVocabulary.metricLongestCommonSubsequence.stringValue());
    }

    private MetricLongestCommonSubsequence(final MetricLongestCommonSubsequence metricLongestCommonSubsequence) {
        super(metricLongestCommonSubsequence);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertStringLiteral(values[0]);
        assertStringLiteral(values[1]);

        return literal(metricLCS.distance(values[0].stringValue(), values[1].stringValue()));
    }

    public Function copy() {
        return new MetricLongestCommonSubsequence(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                                                                                  }

    @Override
    public String toString() {
        return StringMetricVocabulary.metricLongestCommonSubsequence.name();
    }
}

