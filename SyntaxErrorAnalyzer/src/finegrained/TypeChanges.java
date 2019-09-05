package finegrained;

public class TypeChanges {
	int intfloatc, floatintc, charintc, doubleintc, voidintc,
	intcharc, floatcharc, charfloatc, doublecharc, voidcharc,
	intdoublec, floatdoublec, chardoublec, doublefloatc, voiddoublec,
	intvoidc, floatvoidc, charvoidc, doublevoidc, voidfloatc;
	
	int total_changes;
	
	
	public TypeChanges() {
		// TODO Auto-generated constructor stub
		intfloatc = floatintc = charintc = doubleintc = voidintc = intcharc = floatcharc = charfloatc 
		= doublecharc = voidcharc = intdoublec = floatdoublec = chardoublec = doublefloatc = voiddoublec 
		= intvoidc = floatvoidc = charvoidc = doublevoidc = voidfloatc = 0;
		total_changes = 0;
	}
	
	public void increment() {
		this.total_changes++;
	}
	
	public int get_total() {
		return total_changes;
	}

	public int getIntfloatc() {
		return intfloatc;
	}

	public void setIntfloatc(int intfloatc) {
		this.intfloatc += intfloatc;
	}

	public int getFloatintc() {
		return floatintc;
	}

	public void setFloatintc(int floatintc) {
		this.floatintc += floatintc;
	}

	public int getCharintc() {
		return charintc;
	}

	public void setCharintc(int charintc) {
		this.charintc += charintc;
	}

	public int getDoubleintc() {
		return doubleintc;
	}

	public void setDoubleintc(int doubleintc) {
		this.doubleintc += doubleintc;
	}

	public int getVoidintc() {
		return voidintc;
	}

	public void setVoidintc(int voidintc) {
		this.voidintc += voidintc;
	}

	public int getIntcharc() {
		return intcharc;
	}

	public void setIntcharc(int intcharc) {
		this.intcharc += intcharc;
	}

	public int getFloatcharc() {
		return floatcharc;
	}

	public void setFloatcharc(int floatcharc) {
		this.floatcharc += floatcharc;
	}

	public int getCharfloatc() {
		return charfloatc;
	}

	public void setCharfloatc(int charfloatc) {
		this.charfloatc += charfloatc;
	}

	public int getDoublecharc() {
		return doublecharc;
	}

	public void setDoublecharc(int doublecharc) {
		this.doublecharc += doublecharc;
	}

	public int getVoidcharc() {
		return voidcharc;
	}

	public void setVoidcharc(int voidcharc) {
		this.voidcharc += voidcharc;
	}

	public int getIntdoublec() {
		return intdoublec;
	}

	public void setIntdoublec(int intdoublec) {
		this.intdoublec += intdoublec;
	}

	public int getFloatdoublec() {
		return floatdoublec;
	}

	public void setFloatdoublec(int floatdoublec) {
		this.floatdoublec += floatdoublec;
	}

	public int getChardoublec() {
		return chardoublec;
	}

	public void setChardoublec(int chardoublec) {
		this.chardoublec += chardoublec;
	}

	public int getDoublefloatc() {
		return doublefloatc;
	}

	public void setDoublefloatc(int doublefloatc) {
		this.doublefloatc += doublefloatc;
	}

	public int getVoiddoublec() {
		return voiddoublec;
	}

	public void setVoiddoublec(int voiddoublec) {
		this.voiddoublec += voiddoublec;
	}

	public int getIntvoidc() {
		return intvoidc;
	}

	public void setIntvoidc(int intvoidc) {
		this.intvoidc += intvoidc;
	}

	public int getFloatvoidc() {
		return floatvoidc;
	}

	public void setFloatvoidc(int floatvoidc) {
		this.floatvoidc += floatvoidc;
	}

	public int getCharvoidc() {
		return charvoidc;
	}

	public void setCharvoidc(int charvoidc) {
		this.charvoidc += charvoidc;
	}

	public int getDoublevoidc() {
		return doublevoidc;
	}

	public void setDoublevoidc(int doublevoidc) {
		this.doublevoidc += doublevoidc;
	}

	public int getVoidfloatc() {
		return voidfloatc;
	}

	public void setVoidfloatc(int voidfloatc) {
		this.voidfloatc += voidfloatc;
	}

}
