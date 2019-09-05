package finegrained;

public class SpecifierChanges {
	
	int externstatic = 0,	staticextern = 0,	autoextern = 0,	registerextern = 0,
	externauto = 0,	staticauto = 0,	autostatic = 0,	registerstatic = 0,
	externregister = 0,	staticregister = 0,	autoregister = 0, registerauto = 0;
	
	int total_changes;
	
	
	public SpecifierChanges() {
		// TODO Auto-generated constructor stub
		externstatic = staticextern = autoextern = registerextern = externauto = staticauto =
		autostatic = registerstatic = externregister = staticregister = autoregister = registerauto = 0;
		total_changes = 0;
	}
	
	public void increment() {
		this.total_changes++;
	}
	
	public int get_total() {
		return total_changes;
	}

	public int getExternstatic() {
		return externstatic;
	}

	public void setExternstatic(int externstatic) {
		this.externstatic += externstatic;
	}

	public int getStaticextern() {
		return staticextern;
	}

	public void setStaticextern(int staticextern) {
		this.staticextern += staticextern;
	}

	public int getAutoextern() {
		return autoextern;
	}

	public void setAutoextern(int autoextern) {
		this.autoextern += autoextern;
	}

	public int getRegisterextern() {
		return registerextern;
	}

	public void setRegisterextern(int registerextern) {
		this.registerextern += registerextern;
	}

	public int getExternauto() {
		return externauto;
	}

	public void setExternauto(int externauto) {
		this.externauto += externauto;
	}

	public int getStaticauto() {
		return staticauto;
	}

	public void setStaticauto(int staticauto) {
		this.staticauto += staticauto;
	}

	public int getAutostatic() {
		return autostatic;
	}

	public void setAutostatic(int autostatic) {
		this.autostatic += autostatic;
	}

	public int getRegisterstatic() {
		return registerstatic;
	}

	public void setRegisterstatic(int registerstatic) {
		this.registerstatic += registerstatic;
	}

	public int getExternregister() {
		return externregister;
	}

	public void setExternregister(int externregister) {
		this.externregister += externregister;
	}

	public int getStaticregister() {
		return staticregister;
	}

	public void setStaticregister(int staticregister) {
		this.staticregister += staticregister;
	}

	public int getAutoregister() {
		return autoregister;
	}

	public void setAutoregister(int autoregister) {
		this.autoregister += autoregister;
	}

	public int getRegisterauto() {
		return registerauto;
	}

	public void setRegisterauto(int registerauto) {
		this.registerauto += registerauto;
	}


}
