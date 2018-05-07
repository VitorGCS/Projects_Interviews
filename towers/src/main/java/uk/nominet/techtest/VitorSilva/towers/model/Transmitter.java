package uk.nominet.techtest.VitorSilva.towers.model;

public class Transmitter extends Tower {
    private int power;

    public Transmitter(int id, Point location, int power) {
        super(id, location);
        this.power = power;
    }

    //Override methods equal, hasCode and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Transmitter that = (Transmitter) o;

        return power == that.power;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + power;
        return result;
    }

    @Override
    public String toString() {
        return "Transmitter{" + "power=" + power +", id=" + id +", location=" + location +'}';
    }

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	
	public Transmitter clone() {
		return new Transmitter(this.id, this.location, this.power);
	}
}
