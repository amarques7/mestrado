package de.fosd.typechef;

import java.util.List;

import de.fosd.typechef.lexer.Main;
import de.fosd.typechef.parser.TokenReader;
import de.fosd.typechef.parser.c.CLexer;
import de.fosd.typechef.parser.c.CToken;
import de.fosd.typechef.parser.c.CTypeContext;

public final class Lex {

	public static TokenReader<CToken, CTypeContext> lex(FrontendOptions opt) {
		List<LexerToken> tokens;
		try {
			Main obj = new Main();
			
			tokens = obj.run(opt, opt.parse);
			TokenReader<CToken, CTypeContext> in = CLexer.prepareTokens(tokens);

			return in;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}