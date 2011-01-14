package com.floreysoft.jmte;

import java.util.Map;
import java.util.concurrent.Callable;

public class IfCmpToken extends IfToken {
	private final String operand;

	public IfCmpToken(String expression, String operand, boolean negated) {
		super(expression, negated);
		this.operand = operand;
	}

	public String getOperand() {
		return operand;
	}

	@Override
	public String getText() {
		if (text == null) {
			text = String
					.format(IF + " %s='%s'", getExpression(), getOperand());
		}
		return text;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object evaluate(Engine engine, Map<String, Object> model,
			ErrorHandler errorHandler) {

		Object value = traverse(getSegments(), model, errorHandler);
		if (value instanceof Callable) {
			try {
				value = ((Callable) value).call();
			} catch (Exception e) {
			}
		}

		final boolean condition = getOperand().equals(value);

		final Object evaluated = negated ? !condition : condition;
		return evaluated;
	}

}