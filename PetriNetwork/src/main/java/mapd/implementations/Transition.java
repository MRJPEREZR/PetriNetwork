package mapd.implementations;

import mapd.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Transition {

	private List<InArc> inArcs;
	private List<OutArc> outArcs;
	private String label;
	private Boolean isFireable;

	public Transition(String label) {
		this.inArcs = new ArrayList<InArc>(); 
		this.outArcs = new ArrayList<OutArc>();
		this.label = label;
		this.isFireable = false;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	private Boolean existedArc(List<? extends Arc> arcs, Arc newArc) {
		Place place = newArc.getPlace();
        return arcs.stream().anyMatch(arc -> arc.getPlace().getLabel().equals(place.getLabel()));
	}

	public List<InArc> getInArcs() {
		return inArcs;
	}

	public List<OutArc> getOutArcs() {
		return outArcs;
	}

	public void addInArc(InArc inArc) throws RepeatedArc {
		if (!existedArc(this.inArcs, inArc)) {
			this.inArcs.add(inArc);
			updateIsFireable();
		} else {
			throw new RepeatedArc("An Arc in the same direction already exists");
		}
	}

	public void addOutArc(OutArc outArc) throws RepeatedArc {
		if (!existedArc(this.outArcs, outArc)) {
			this.outArcs.add(outArc);
			updateIsFireable();
		} else {
			throw new RepeatedArc("An Arc in the same direction already exists");
		}
	}

	public void rmInArc(InArc inArc) {
		this.inArcs.remove(inArc);
	}

	public void rmOutArc(OutArc outArc) {
		this.outArcs.remove(outArc);
		updateIsFireable();
	}
	
	public Boolean isFireable() {
		return this.isFireable;
	}

	public void updateIsFireable() {
		outArcs.stream().forEach(OutArc::updateIsActive);
        this.isFireable = outArcs.stream().anyMatch(arc -> arc.isActive()) || (outArcs.size() == 0 && inArcs.size() > 0 );
    }

	public void fire() {
		if (isFireable) {
			System.out.println("Firing ...");
			outArcs.stream().forEach(arg0 -> {
				try {
					arg0.modifyTokens();
				} catch (InvalidTokenNumber e) {
					e.printStackTrace();
				}
			});
			inArcs.stream().forEach(arg0 -> {
				try {
					arg0.modifyTokens();
				} catch (InvalidTokenNumber e) {
					e.printStackTrace();
				}
			});
			updateIsFireable();
		} else {
			System.out.println("Transition is not fireable");
		}
	}

	@Override
	public String toString () {
		return "InArcs: " + this.getInArcs() + "OutArcs: " + this.getOutArcs();
	}

}
