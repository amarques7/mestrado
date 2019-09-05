package finegrained;

import analysis.ID;
import analysis.Link;
import analysis.Variability;
import analysis.core.Function;
import analysis.core.ProgramElement;
import analysis.core.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FoundChanges {
	
	static int cont_change_parameter;
	private int cont_type_parameter;
	private int cont_add_parameter;
	private int cont_remove_parameter;
	FunctionChanges funch = new FunctionChanges();
	VariableChanges varch = new VariableChanges();
	
	private List<Impact> impactList = new ArrayList<Impact>(10000);
	
	public void addImpact(ProgramElement fromPe, ProgramElement pe, String category, Variability var) {
		Impact imp = new Impact();
		imp.setChangeCategory(category);
		imp.setProgramElement(pe);
		imp.setFromProgramElement(fromPe);
		imp.setVariability(var);
		this.impactList.add(imp);	
	}

	public List<Impact> getImpactList(){
		return impactList;
	}

	public Boolean differ(Link a, Link b, Variability variability){
		Boolean flag = false;
		if (a.getCallee().id == ID.Function) {
			if(compare_parameters(a.getCallee(), b.getCallee())) {
				addImpact(a.getCallee(), a.getCaller(), "parameter", variability);
				flag = true;
			}
				
		}
		if(compare_type(a.getCallee(), b.getCallee(), 1)) {
			addImpact(a.getCallee(), a.getCaller(), "type", variability);
			flag = true;
		}
		if(compare_modifier(a.getCallee(), b.getCallee(), 1)) {
			addImpact(a.getCallee(), a.getCaller(), "modifier", variability);
			flag = true;
		}
			
		if(compare_qualifier(a.getCallee(), b.getCallee(), 1)) {
			addImpact(a.getCallee(), a.getCaller(), "qualifier", variability);
			flag = true;
		}
			
		if(compare_specifier(a.getCallee(), b.getCallee(), 1)) {
			addImpact(a.getCallee(), a.getCaller(), "specifier", variability);
			flag = true;
		}
			
		
		return flag;

	}
	

	public void addLink(Link link, Variability variability) {
		if(link.getCallee().id == ID.Function ) {
			funch.incrementInsert();
		}else if (link.getCallee().id == ID.Variable) {
			varch.incrementInsert();
		}
		addImpact(link.getCallee(), link.getCaller(), "additon", variability);
		
	}
	public void delLink(Link link, Variability variability) {
		if(link.getCallee().id == ID.Function ) {
			funch.incrementRemoval();
		}else if (link.getCallee().id == ID.Variable) {
			varch.incrementRemoval();
		}

		addImpact(link.getCallee(), link.getCaller(), "exclusion", variability);
	}
	public Boolean compare_type(ProgramElement a, ProgramElement  b, int element){
		Boolean flag = false;
		if(a.id == ID.Function) {
			if (a.getType() != b.getType()) {
				if (b.getType() != "") {
					flag = true;				
					funch.typeschanges.increment();
					switch_change(a.getType()+" <> "+ b.getType(), "type","funch");
				}
			
			}			
		}else if (a.id == ID.Variable ) {
			if (a.getType() != b.getType()) {
				varch.typeschanges.increment();
				switch_change(a.getType()+" <> "+ b.getType(), "type","varch");
				flag = true;

			}
		}else if (a.id == null ) {
				if (a.getType() != b.getType()) {
					flag = true;
					cont_change_parameter++;
					cont_type_parameter++;
				}
		}
		return flag;
	}
	public Boolean compare_modifier(ProgramElement a, ProgramElement  b, int element){
		Boolean flag = false;
		if(a.id == ID.Function) {
			if (a.getModifier() != b.getModifier()) {
				funch.modifierchanges.increment();
				switch_change(a.getModifier()+" <> "+ b.getModifier(), "modifier","funch");
				flag = true;
			}			
		}else if (a.id != ID.Variable) {
			if (a.getModifier() != b.getModifier()) {
				varch.modifierchanges.increment();
				switch_change(a.getModifier()+" <> "+ b.getModifier(), "modifier","varch");
				flag = true;
			}
		}else if (a.id == null) {
			if (a.getModifier() != b.getModifier()) {
				flag = true;
				cont_change_parameter++;
			}
		}
		return flag;
	}
	public Boolean compare_qualifier(ProgramElement a, ProgramElement  b, int element){
		Boolean flag = false;
		if(a.id == ID.Function) {
			if (a.getQualifier() != b.getQualifier()) {
				funch.qualifierchanges.increment();
				switch_change(a.getQualifier()+" <> "+ b.getQualifier(), "qualifier","funch");
				flag = true;
				
			}			
		}else if (a.id != ID.Variable) {
			if (a.getQualifier() != b.getQualifier()) {
				varch.qualifierchanges.increment();
				switch_change(a.getQualifier()+" <> "+ b.getQualifier(), "qualifier","varch");
				flag = true;
			}
		}else if (a.id == null) {
			if (a.getQualifier() != b.getQualifier()) {
				flag = true;
				cont_change_parameter++;
			}
			
		}
		return flag;
		
	}
	public Boolean compare_specifier(ProgramElement a, ProgramElement  b, int element){
		Boolean flag = false;
		if(a.id == ID.Function) {
			if (a.getSpecifier()!= b.getSpecifier()) {
				funch.specifierchanges.increment();
				switch_change(a.getSpecifier()+" <> "+ b.getSpecifier(), "specifier","funch");
				flag = true;
			}			
		}else if (a.id != ID.Variable) {
			if (a.getSpecifier() != b.getSpecifier()) {
				varch.specifierchanges.increment();
				switch_change(a.getSpecifier()+" <> "+ b.getSpecifier(), "specifier","varch");
				flag = true;
			}
		}
		return flag;
	}
	public Boolean compare_parameters(ProgramElement a,  ProgramElement b){
		Boolean flag = false;
		if (a.id == ID.Function  && b.id == ID.Function) {
			if ((((Function)a).getParameters()).size() != (((Function)b).getParameters()).size()) {
				flag = true;
				cont_change_parameter++;
				if((((Function)a).getParameters()).size() < (((Function)b).getParameters()).size()) {
					cont_add_parameter++;
				}
				if((((Function)a).getParameters()).size() > (((Function)b).getParameters()).size()) {
					cont_remove_parameter++;
				}
			
			}
			for(Variable var: ((Function)a).getParameters()) {
				for(Variable v: ((Function)b).getParameters()) {
					if (var.getName().compareTo(v.getName()) == 0) {
						if (compare_type(var, v, 2 )) {flag = true;}
						if (compare_modifier(var, v, 2)) {flag = true;}
						if (compare_qualifier(var, v, 2)) {flag = true;}
					}	
				}				
			}
			
			
		}

		return flag;
	}

	public void switch_change(String change, String category, String element) {
		if (element.compareTo("funch") == 0 ) {
		if (category.compareTo("modifier") == 0) {	
			switch (change) {
			case "short <> long":
				funch.modifierchanges.setShortlongc(1);
				break;
			case "long <> short":
				funch.modifierchanges.setLongshortc(1);
				break;
			case "signed <> short":
				funch.modifierchanges.setSignedshort(1);
				break;
			case "unsigned <> short":
				funch.modifierchanges.setUnsignedshort(1);
				break;
			case " <> short":
				funch.modifierchanges.setNullshort(1);
				break;
			case "short <> signed":
				funch.modifierchanges.setShortsigned(1);
				break;
			case "long <> signed":
				funch.modifierchanges.setLongsigned(1);
				break;
			case "signed <> long":
				funch.modifierchanges.setSignedlong(1);
				break;
			case "unsigned <> long":
				funch.modifierchanges.setUnsignedlong(1);
				break;
			case " <> long":
				funch.modifierchanges.setNulllong(1);
				break;
			case "short <> unsigned":
				funch.modifierchanges.setShortunsigned(1);
				break;
			case "long <> unsigned":
				funch.modifierchanges.setLongunsigned(1);
				break;
			case "signed <> unsigned":	
				funch.modifierchanges.setSignedunsigned(1);
				break;
			case "unsigned <> signed":
				funch.modifierchanges.setUnsignedsigned(1);
				break;
			case " <> signed":
				funch.modifierchanges.setNullsigned(1);
				break;
			case "short <> ":
				funch.modifierchanges.setNullshort(1);
				break;
			case "long <> ":
				funch.modifierchanges.setLongnull(1);
				break;
			case "signed <> ":
				funch.modifierchanges.setSignednull(1);
				break;
			case "unsigned <> ":
				funch.modifierchanges.setUnsignednull(1);
				break;
			case " <> unsigned":
				funch.modifierchanges.setNullunsigned(1);
				break;

			default:
				break;
			}
			
		}
		if (category.compareTo("specifier") == 0) {
			switch (change) {
			case "extern <> static":
				funch.specifierchanges.setExternstatic(1);
				break;
			case "static <> extern":
				funch.specifierchanges.setStaticextern(1);
				break;
			case "auto <> extern":
				funch.specifierchanges.setAutoextern(1);
				break;
			case "register <> extern":
				funch.specifierchanges.setRegisterextern(1);
				break;
			case "extern <> auto":
				funch.specifierchanges.setExternauto(1);
				break;
			case "static <> auto":
				funch.specifierchanges.setStaticauto(1);
				break;
			case "auto <> static":
				funch.specifierchanges.setAutostatic(1);
				break;
			case "register <> static":
				funch.specifierchanges.setRegisterstatic(1);
				break;
			case "extern <> register":
				funch.specifierchanges.setExternregister(1);
				break;
			case "static <> register":
				funch.specifierchanges.setStaticregister(1);
				break;
			case "auto <> register":
				funch.specifierchanges.setAutoregister(1);
				break;
			case "register <> auto":
				funch.specifierchanges.setRegisterauto(1);
				break;
			default:
				break;
			}
			
			
		}
		if (category.compareTo("qualifier") == 0) {
			switch (change) {
			case "const <> volatile":
				funch.qualifierchanges.setConstvolatilec(1);
				break;
			case "volatile <> const":
				funch.qualifierchanges.setVolatileconstc(1);
				break;
			case " <> volatile":
				funch.qualifierchanges.setNullvolatilec(1);
				break;
			case " <> const":
				funch.qualifierchanges.setNullconstc(1);
				break;
			default:
				break;
			}
			
			
		}
		if (category.compareTo("type") == 0) {
			switch (change) {
				case "int <> float":
					funch.typeschanges.setIntfloatc(1);				
					break;
				case "float <> int":
					funch.typeschanges.setFloatintc(1);;
					break;
				case "char <> int":
					funch.typeschanges.setCharintc(1);;
					break;
				case "double <> int":
					funch.typeschanges.setDoubleintc(1);
					break;
				case "void <> int":
					funch.typeschanges.setVoidintc(1);
					break;
				case "int <> char":
					funch.typeschanges.setIntcharc(1);
					break;
				case "float<> char":
					funch.typeschanges.setFloatcharc(1);
					break;
				case "char <> float":
					funch.typeschanges.setCharfloatc(1);
					break;
				case "double <> char":
					funch.typeschanges.setDoublecharc(1);
					break;
				case "void <> char":
					funch.typeschanges.setVoidcharc(1);
					break;
				case "int <> double":
					funch.typeschanges.setIntdoublec(1);
					break;
				case "float <> double":
					funch.typeschanges.setFloatdoublec(1);
					break;
				case "char <> double":
					funch.typeschanges.setChardoublec(1);
					break;
				case "double <> float":
					funch.typeschanges.setDoublefloatc(1);
					break;
				case "void <> double":
					funch.typeschanges.setVoiddoublec(1);
					break;
				case "int <> void":
					funch.typeschanges.setIntvoidc(1);
					break;
				case "float <> void":
					funch.typeschanges.setFloatvoidc(1);
					break;
				case "char <> void":
					funch.typeschanges.setCharvoidc(1);
					break;
				case "double <> void":
					funch.typeschanges.setDoublevoidc(1);
					break;
				case "void <> float":
					funch.typeschanges.setVoidfloatc(1);
					break;
				default:
					break;
				}	
			}
		}else {
			if (category.compareTo("modifier") == 0) {
				
				switch (change) {
				case "short <> long":
					varch.modifierchanges.setShortlongc(1);
					break;
				case "long <> short":
					varch.modifierchanges.setLongshortc(1);
					break;
				case "signed <> short":
					varch.modifierchanges.setSignedshort(1);
					break;
				case "unsigned <> short":
					varch.modifierchanges.setUnsignedshort(1);
					break;
				case " <> short":
					varch.modifierchanges.setNullshort(1);
					break;
				case "short <> signed":
					varch.modifierchanges.setShortsigned(1);
					break;
				case "long <> signed":
					varch.modifierchanges.setLongsigned(1);
					break;
				case "signed <> long":
					varch.modifierchanges.setSignedlong(1);
					break;
				case "unsigned <> long":
					varch.modifierchanges.setUnsignedlong(1);
					break;
				case " <> long":
					varch.modifierchanges.setNulllong(1);
					break;
				case "short <> unsigned":
					varch.modifierchanges.setShortunsigned(1);
					break;
				case "long <> unsigned":
					varch.modifierchanges.setLongunsigned(1);
					break;
				case "signed <> unsigned":	
					varch.modifierchanges.setSignedunsigned(1);
					break;
				case "unsigned <> signed":
					varch.modifierchanges.setUnsignedsigned(1);
					break;
				case " <> signed":
					varch.modifierchanges.setNullsigned(1);
					break;
				case "short <> ":
					varch.modifierchanges.setShortnull(1);;
					break;
				case "long <> ":
					varch.modifierchanges.setLongnull(1);
					break;
				case "signed <> ":
					varch.modifierchanges.setSignednull(1);
					break;
				case "unsigned <> ":
					varch.modifierchanges.setUnsignednull(1);
					break;
				case " <> unsigned":
					varch.modifierchanges.setNullunsigned(1);
					break;

				default:
					break;
				}
				
			}
			if (category.compareTo("specifier") == 0) {
				switch (change) {
				case "extern <> static":
					varch.specifierchanges.setExternstatic(1);
					break;
				case "static <> extern":
					varch.specifierchanges.setStaticextern(1);
					break;
				case "auto <> extern":
					varch.specifierchanges.setAutoextern(1);
					break;
				case "register <> extern":
					varch.specifierchanges.setRegisterextern(1);
					break;
				case "extern <> auto":
					varch.specifierchanges.setExternauto(1);
					break;
				case "static <> auto":
					varch.specifierchanges.setStaticauto(1);
					break;
				case "auto <> static":
					varch.specifierchanges.setAutostatic(1);
					break;
				case "register <> static":
					varch.specifierchanges.setRegisterstatic(1);
					break;
				case "extern <> register":
					varch.specifierchanges.setExternregister(1);
					break;
				case "static <> register":
					varch.specifierchanges.setStaticregister(1);
					break;
				case "auto <> register":
					varch.specifierchanges.setAutoregister(1);
					break;
				case "register <> auto":
					varch.specifierchanges.setRegisterauto(1);
					break;
				default:
					break;
				}
				
				
			}
			if (category.compareTo("qualifier") == 0) {
				switch (change) {
				case "const <> volatile":
					varch.qualifierchanges.setConstvolatilec(1);
					break;
				case "volatile <> const":
					varch.qualifierchanges.setVolatileconstc(1);
					break;
				case " <> volatile":
					varch.qualifierchanges.setNullvolatilec(1);
					break;
				case " <> const":
					varch.qualifierchanges.setNullconstc(1);
					break;
				default:
					break;
				}
				
				
			}
			if (category.compareTo("type") == 0) {
				switch (change) {
					case "int <> float":
						varch.typeschanges.setIntfloatc(1);				
						break;
					case "float <> int":
						varch.typeschanges.setFloatintc(1);;
						break;
					case "char <> int":
						varch.typeschanges.setCharintc(1);;
						break;
					case "double <> int":
						varch.typeschanges.setDoubleintc(1);
						break;
					case "void <> int":
						varch.typeschanges.setVoidintc(1);
						break;
					case "int <> char":
						varch.typeschanges.setIntcharc(1);
						break;
					case "float<> char":
						varch.typeschanges.setFloatcharc(1);
						break;
					case "char <> float":
						varch.typeschanges.setCharfloatc(1);
						break;
					case "double <> char":
						varch.typeschanges.setDoublecharc(1);
						break;
					case "void <> char":
						varch.typeschanges.setVoidcharc(1);
						break;
					case "int <> double":
						varch.typeschanges.setIntdoublec(1);
						break;
					case "float <> double":
						varch.typeschanges.setFloatdoublec(1);
						break;
					case "char <> double":
						varch.typeschanges.setChardoublec(1);
						break;
					case "double <> float":
						varch.typeschanges.setDoublefloatc(1);
						break;
					case "void <> double":
						varch.typeschanges.setVoiddoublec(1);
						break;
					case "int <> void":
						varch.typeschanges.setIntvoidc(1);
						break;
					case "float <> void":
						varch.typeschanges.setFloatvoidc(1);
						break;
					case "char <> void":
						varch.typeschanges.setCharvoidc(1);
						break;
					case "double <> void":
						varch.typeschanges.setDoublevoidc(1);
						break;
					case "void <> float":
						varch.typeschanges.setVoidfloatc(1);
						break;
					default:
						break;
					}	
				}
		}
	}
	public int booleanToInt(boolean value) {
		return value ? 1 : 0;
	}


}
