package uk.nominet.techtest.VitorSilva.towers.model;

import java.util.List;

public class Result {
    //List of transmitters
	public final List<Transmitter> transmitters;

    //constructor
	public Result(List<Transmitter> transmitters) {
        this.transmitters = transmitters;
    }

	//get total Power
    public int getTotalPower() {
        return transmitters.stream().map(t -> t.getPower()).mapToInt(Integer::intValue).sum();
    }

    
    //Override methods equal, hasCode and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        return transmitters != null ? transmitters.equals(result.transmitters) : result.transmitters == null;
    }

    @Override
    public int hashCode() {
        return transmitters != null ? transmitters.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Result{" + "transmitters=" + transmitters +'}';
    }
}
