package mapd;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidTokenNumber;

public class Transition {

	private List<InArc> inArcs;
	private List<OutArc> outArcs;

	Transition() {
		this.inArcs = new ArrayList<InArc>(); 
		this.outArcs = new ArrayList<OutArc>(); 
	}

	private Boolean existedArc(List<? extends Arc> arcs, Arc newArc) {
		Place place = newArc.getPlace();
        return arcs.stream().anyMatch(arc -> arc.getPlace().equals(place));
	}

	public List<InArc> getInArcs() {
		return inArcs;
	}

	public List<OutArc> getOutArcs() {
		return outArcs;
	}

	public void addInArc(InArc inArc) {
		if (!existedArc(this.inArcs, inArc)) {
			this.inArcs.add(inArc);
		}
	}

	public void addOutArc(OutArc outArc) {
		if (!existedArc(this.outArcs, outArc)) {
			this.outArcs.add(outArc);
		}
	}

	public void rmInArc(InArc inArc) {
		this.inArcs.remove(inArc);
	}

	public void rmOutArc(OutArc outArc) {
		this.outArcs.remove(outArc);
	}

	public Boolean isFireable() {
        return outArcs.stream().anyMatch(arc -> arc.getIsActive());
    }

	public void fire() {
		if (isFireable()) {
			System.out.println("Firing ...");
			outArcs.stream().forEach(arg0 -> {
				try {
					arg0.modifyTokens();
				} catch (InvalidTokenNumber e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			outArcs.stream().forEach(OutArc::setIsActive);
			inArcs.stream().forEach(arg0 -> {
				try {
					arg0.modifyTokens();
				} catch (InvalidTokenNumber e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} else {
			System.out.println("Transition is not fireable");
		}
	}

	@Override
	public String toString () {
		return "InArcs" + this.getInArcs() + "OutArcs" + this.getOutArcs();
	}

}
