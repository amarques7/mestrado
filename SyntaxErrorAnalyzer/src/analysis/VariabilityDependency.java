package analysis;

import java.util.ArrayList;
import java.util.List;

public class VariabilityDependency {
	public List<Link> dependencies = new ArrayList<Link>();
	
	public void add(Link link){
		this.dependencies.add(link);
	}
	
	public List<Link> getAllLinksFromDps(){
		return this.dependencies;
	}
}
