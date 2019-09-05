package finegrained;

public class VariableChanges {
	TypeChanges typeschanges = new TypeChanges ();
	SpecifierChanges specifierchanges = new SpecifierChanges ();
	QualifierChanges qualifierchanges = new QualifierChanges ();
	ModifierChanges modifierchanges = new ModifierChanges ();
	private int removal;
	private int insert;
	
	
	
	private int total_changes;
	
	public VariableChanges() {
		// TODO Auto-generated constructor stub
		typeschanges = new TypeChanges ();
		specifierchanges = new SpecifierChanges ();
		qualifierchanges = new QualifierChanges ();
		modifierchanges = new ModifierChanges ();	
		total_changes = 0;
		removal = 0;
		insert = 0;
	}
	
	public void incrementRemoval() {
		this.removal++;
		this.increment();
	}
	
	public int get_Removal() {
		return removal;
	}
	
	public void incrementInsert() {
		this.insert++;
		this.increment();
	}
	
	public int get_Insert() {
		return insert;
	}
	
	
	public void increment() {
		this.total_changes++;
	}
	
	public int get_total() {
		return total_changes;
	}


}
