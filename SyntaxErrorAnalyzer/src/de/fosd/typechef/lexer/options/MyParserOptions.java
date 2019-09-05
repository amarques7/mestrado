package de.fosd.typechef.lexer.options;

import scala.Function3;
import de.fosd.typechef.featureexpr.FeatureExpr;
import de.fosd.typechef.parser.Position;
import de.fosd.typechef.parser.c.ParserOptions;

public class MyParserOptions implements ParserOptions {

	@Override
	public Function3<FeatureExpr, String, Position, Object> renderParserError() {
		return null;
	}
	
	@Override
	public boolean printParserStatistics() {
		return false;
	}
	
	@Override
	public boolean printParserResult() {
		return true;
	}
	
}
