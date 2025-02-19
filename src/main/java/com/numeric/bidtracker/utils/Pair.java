package com.numeric.bidtracker.utils;

// Simple implementation of pair of values

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {
	private static final long serialVersionUID = 9076541384138644394L;
	private T1 first;
	private T2 second;

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 first() { return first; }
	public T2 second() { return second; }

	public void setFirst(T1 first) { this.first = first; }
	public void setSecond(T2 second) { this.second = second; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pair<?, ?>))
			return false;
		final Pair<?, ?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
}
