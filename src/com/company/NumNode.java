package com.company;

public class NumNode {
	
	private static Exception error() { return new Exception("Invalid Number Format"); }
	
	enum Type {
		SINGLE,
		DOUBLE,
		TEN,
		HUNDRED,
		MODIFIER
	}

	long value;
	NumNode next;
	NumNode prev;
	Type type;
	
	public NumNode(long value, NumNode next, NumNode prev){
		this.value = value;
		this.next = next;
		this.prev = prev;
		
		if (value < 10) type = Type.SINGLE;
		else if (value < 20) type = Type.DOUBLE;
		else if (value < 100) type = Type.TEN;
		else if (value == 100) type = Type.HUNDRED;
		else type = Type.MODIFIER;
	}
	
	/*
	Collapses nodes until we run out or into a modifier
	 */
	private long computeSmallValue() throws Exception{
		
		// return if we are last
		if (next == null) return value;
		
		// modify our value, absorb node, continue on
		else if (next.type == Type.HUNDRED) {
			// we shouldn't be DOUBLE or TEN
			if (type == Type.DOUBLE || type == Type.TEN) throw error();
			
			// absorb
			value *= next.value;
			next = next.next;
			
			// do we need to keep going?
			if (next != null) {
				// cannot be another hundred
				if (next.type == Type.HUNDRED) throw error();
				
				// if SINGLE or DOUBLE just add the value and absorb
				else if (next.type == Type.SINGLE || next.type == Type.DOUBLE){
					value += next.value;
					next = next.next;
				}
				
				// if TEN do one or two absorbs
				else if (next.type == Type.TEN){
					value += next.value;
					next = next.next;
					
					// if not last, must be SINGLE or MODIFIER
					if (next != null){
						if (next.type == Type.SINGLE){
							value += next.value;
							next = next.next;
						}
						else if (next.type != Type.MODIFIER) throw error();
					}
				}
				
			}
		}
		
		// if we are TEN do one or two absorbs
		else if (type == Type.TEN){
			value += next.value;
			next = next.next;
			
			// if not last, must be SINGLE or MODIFIER
			if (next != null){
				if (next.type == Type.SINGLE){
					value += next.value;
					next = next.next;
				}
				else if (next.type != Type.MODIFIER) throw error();
			}
		}
		
		// SINGLE should be followed by modifier
		else if (type == Type.SINGLE || type == Type.DOUBLE){
			if (next.type != Type.MODIFIER) throw error();
		}
		
		// Anything else is wrong
		else throw error();
		
		return value;
	}
	
	/*
	Computes the value of a NumNode list
	 */
	public long computeValue() throws Exception {
		// Compute 3 digits at a time
		if (type == Type.HUNDRED || type == Type.MODIFIER) throw error();
		else value = this.computeSmallValue();
		
		// Is there more?
		if (next != null){
			// must be a MODIFIER
			if (next.type != Type.MODIFIER) throw error();
			
			// absorb
			value *= next.value;
			next = next.next;
			
			// continue and recurse if still more
			if (next != null) {
				value += next.computeValue();
			}
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		if (next == null) return "" + value;
		else return "" + value + "->" + next.toString();
	}
}
