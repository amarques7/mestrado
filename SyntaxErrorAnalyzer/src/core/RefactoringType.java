package core;

public enum RefactoringType {
	
	REFACT_INCOMPLETESTMTS(1, "Incomplete Statements"), 
	REFACT_IFSTMTS(2, "If Statements"), 
	REFACT_WHILESTMTS(3, "While Statements"),
	REFACT_ARRAYELMTS(4, "Array Elements"),
	REFACT_ENUMELMTS(5, "Enum Elements"),
	REFACT_SWITCHSTMTS(6, "Switch Statements"),
	REFACT_FUNCDEFS(7, "Function Definitions");
	
	private final int value;
	private final String label;

	RefactoringType(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

}
