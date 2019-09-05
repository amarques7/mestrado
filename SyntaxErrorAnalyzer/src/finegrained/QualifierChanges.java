package finegrained;

public class QualifierChanges {

	int constvolatilec = 0,	volatileconstc = 0,
	nullvolatilec = 0,	nullconstc = 0;
	
	
	int total_changes;
	
	public QualifierChanges() {
		// TODO Auto-generated constructor stub
		constvolatilec = volatileconstc = nullvolatilec = nullconstc = 0;
		total_changes = 0;
	}
	
	public void increment() {
		this.total_changes++;
	}
	
	public int get_total() {
		return total_changes;
	}
	

	public int getConstvolatilec() {
		return constvolatilec;
	}

	public void setConstvolatilec(int constvolatilec) {
		this.constvolatilec += constvolatilec;
	}

	public int getVolatileconstc() {
		return volatileconstc;
	}

	public void setVolatileconstc(int volatileconstc) {
		this.volatileconstc += volatileconstc;
	}

	public int getNullvolatilec() {
		return nullvolatilec;
	}

	public void setNullvolatilec(int nullvolatilec) {
		this.nullvolatilec += nullvolatilec;
	}

	public int getNullconstc() {
		return nullconstc;
	}

	public void setNullconstc(int nullconstc) {
		this.nullconstc += nullconstc;
	}

}
