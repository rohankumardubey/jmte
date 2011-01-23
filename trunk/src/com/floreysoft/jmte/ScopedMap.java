package com.floreysoft.jmte;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ScopedMap implements Map<String, Object> {
	private final Map<String, Object> rawModel;
	private final Stack<Map<String, Object>> scopes = new Stack<Map<String, Object>>();

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public Set<Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

	public Object get(Object key) {
		for (int i = scopes.size() - 1; i >= 0; i--) {
			Map<String, Object> map = scopes.get(i);
			Object value = map.get(key);
			if (value != null) {
				return value;
			}
		}
		return rawModel.get(key);
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	public Object put(String key, Object value) {
		return getCurrentScope().put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new UnsupportedOperationException();
	}

	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}

	public ScopedMap(Map<String, Object> rawModel) {
		if (rawModel == null) {
			throw new IllegalArgumentException("Model must not be null");
		}
		this.rawModel = rawModel;
	}

	public Map<String, Object> getRawModel() {
		return rawModel;
	}

	public boolean isLocal(String key) {
		return containsKey(key) && !rawModel.containsKey(key);
	}

	public void enterScope() {
		scopes.push(createScope());
	}

	public void exitScope() {
		if (scopes.size() == 0) {
			throw new IllegalStateException("There is no state to exit");
		}
		scopes.pop();
	}

	protected Map<String, Object> getCurrentScope() {
		if (scopes.size() > 0) {
			return scopes.peek();
		} else {
			return rawModel;
		}

	}

	protected Map<String, Object> createScope() {
		return new HashMap<String, Object>();
	}

	@Override
	public String toString() {
		return rawModel.toString() + scopes.toString();
	}
	
}