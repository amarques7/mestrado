package finegrained;

public class ModifierChanges {
	int shortlongc = 0, longshortc = 0,	signedshort = 0,	unsignedshort = 0,	nullshort = 0,
	shortsigned = 0,	longsigned = 0, signedlong = 0,	unsignedlong = 0, nulllong = 0,
	shortunsigned = 0,	longunsigned = 0,	signedunsigned = 0,	unsignedsigned = 0, nullsigned = 0,
	shortnull = 0,	longnull = 0,	signednull = 0,	unsignednull = 0, nullunsigned = 0;

	
	int total_changes;
	
	
	public ModifierChanges() {
		// TODO Auto-generated constructor stub
		shortlongc = longshortc = signedshort = unsignedshort = nullshort = shortsigned = longsigned = signedlong = unsignedlong = nulllong =
		shortunsigned = longunsigned = signedunsigned = unsignedsigned = nullsigned = shortnull = longnull = signednull = unsignednull = nullunsigned = 0;
		total_changes = 0;
	}
	
	public void increment() {
		this.total_changes++;
	}
	
	public int get_total() {
		return total_changes;
	}
	
	public int getShortlongc() {
		return shortlongc;
	}

	public void setShortlongc(int shortlongc) {
		this.shortlongc += shortlongc;
	}

	public int getLongshortc() {
		return longshortc;
	}

	public void setLongshortc(int longshortc) {
		this.longshortc += longshortc;
	}

	public int getSignedshort() {
		return signedshort;
	}

	public void setSignedshort(int signedshort) {
		this.signedshort += signedshort;
	}

	public int getUnsignedshort() {
		return unsignedshort;
	}

	public void setUnsignedshort(int unsignedshort) {
		this.unsignedshort += unsignedshort;
	}

	public int getNullshort() {
		return nullshort;
	}

	public void setNullshort(int nullshort) {
		this.nullshort += nullshort;
	}

	public int getShortsigned() {
		return shortsigned;
	}

	public void setShortsigned(int shortsigned) {
		this.shortsigned += shortsigned;
	}

	public int getLongsigned() {
		return longsigned;
	}

	public void setLongsigned(int longsigned) {
		this.longsigned += longsigned;
	}

	public int getSignedlong() {
		return signedlong;
	}

	public void setSignedlong(int signedlong) {
		this.signedlong += signedlong;
	}

	public int getUnsignedlong() {
		return unsignedlong;
	}

	public void setUnsignedlong(int unsignedlong) {
		this.unsignedlong += unsignedlong;
	}

	public int getNulllong() {
		return nulllong;
	}

	public void setNulllong(int nulllong) {
		this.nulllong += nulllong;
	}

	public int getShortunsigned() {
		return shortunsigned;
	}

	public void setShortunsigned(int shortunsigned) {
		this.shortunsigned += shortunsigned;
	}

	public int getLongunsigned() {
		return longunsigned;
	}

	public void setLongunsigned(int longunsigned) {
		this.longunsigned += longunsigned;
	}

	public int getSignedunsigned() {
		return signedunsigned;
	}

	public void setSignedunsigned(int signedunsigned) {
		this.signedunsigned += signedunsigned;
	}

	public int getUnsignedsigned() {
		return unsignedsigned;
	}

	public void setUnsignedsigned(int unsignedsigned) {
		this.unsignedsigned += unsignedsigned;
	}

	public int getNullsigned() {
		return nullsigned;
	}

	public void setNullsigned(int nullsigned) {
		this.nullsigned += nullsigned;
	}

	public int getShortnull() {
		return shortnull;
	}

	public void setShortnull(int shortnull) {
		this.shortnull += shortnull;
	}

	public int getLongnull() {
		return longnull;
	}

	public void setLongnull(int longnull) {
		this.longnull += longnull;
	}

	public int getSignednull() {
		return signednull;
	}

	public void setSignednull(int signednull) {
		this.signednull += signednull;
	}

	public int getUnsignednull() {
		return unsignednull;
	}

	public void setUnsignednull(int unsignednull) {
		this.unsignednull += unsignednull;
	}

	public int getNullunsigned() {
		return nullunsigned;
	}

	public void setNullunsigned(int nullunsigned) {
		this.nullunsigned += nullunsigned;
	}


}
