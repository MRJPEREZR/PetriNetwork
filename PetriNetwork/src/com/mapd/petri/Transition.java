package com.mapd.petri;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Transition {
	
	private Set<InArc> inArcs;
	private Set<OutArc> outArcs;
	
	Transition(){
		this.inArcs = new HashSet<InArc>(); 
		this.outArcs = new HashSet<OutArc>(); 
	};

	public Set<InArc> getInArcs() {
		return inArcs;
	}

	public void setInArcs(Set<InArc> inArcs) {
		this.inArcs = inArcs;
	}

	public Set<OutArc> getOutArcs() {
		return outArcs;
	}

	public void setOutArcs(Set<OutArc> outArcs) {
		this.outArcs = outArcs;
	};
	
	public void addInArc(InArc inArc) {
		this.inArcs.add(inArc);
	}
	
	public void addOutArc(OutArc outArc) {
		this.outArcs.add(outArc);
	}
	
	public void rmInArc(InArc inArc) {
		this.inArcs.remove(inArc);
	}
	
	public void rmOutArc(OutArc outArc) {
		this.outArcs.remove(outArc);
	}
	
	public Boolean isFireable() {
		
		Boolean fireable = false;
		Iterator<OutArc> outArcsIterator = outArcs.iterator();
		List<Boolean> outArcsStatus = new ArrayList<Boolean>();
		
		while(outArcsIterator.hasNext()) {
			fireable = outArcsIterator.next().isFireable();
			outArcsStatus.add(fireable);
		}
		
		return !outArcsStatus.contains(false);
	}
	
	
	
	
	
	
	

}
