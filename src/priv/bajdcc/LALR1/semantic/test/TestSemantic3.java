package priv.bajdcc.LALR1.semantic.test;

import java.util.Scanner;

import priv.bajdcc.LALR1.grammar.symbol.IQuerySymbol;
import priv.bajdcc.LALR1.semantic.Semantic;
import priv.bajdcc.LALR1.semantic.token.IIndexedData;
import priv.bajdcc.LALR1.semantic.token.ISemanticAnalyzier;
import priv.bajdcc.LALR1.syntax.Syntax;
import priv.bajdcc.LALR1.syntax.handler.SyntaxException;
import priv.bajdcc.util.lexer.error.RegexException;
import priv.bajdcc.util.lexer.token.OperatorType;
import priv.bajdcc.util.lexer.token.Token;
import priv.bajdcc.util.lexer.token.TokenType;

@SuppressWarnings("unused")
public class TestSemantic3 {

	public static void main(String[] args) {
		// System.out.println("Z -> `a`<,> | B | [`a` `b` Z B]");
		try {
			// Scanner scanner = new Scanner(System.in);
			// String expr = "( i )";
			String expr = "a a a a";
			Semantic semantic = new Semantic(expr);
			semantic.addTerminal("a", TokenType.ID, "a");
			semantic.addNonTerminal("START");
			semantic.addNonTerminal("Z");
			semantic.addErrorHandler("sample", null);
			ISemanticAnalyzier handleValue = new ISemanticAnalyzier() {
				@Override
				public Object handle(IIndexedData indexed, IQuerySymbol factoryj) {
					return 1;
				}
			};
			ISemanticAnalyzier handleCopy = new ISemanticAnalyzier() {
				@Override
				public Object handle(IIndexedData indexed, IQuerySymbol factory) {
					return indexed.get(0).object;
				}
			};
			ISemanticAnalyzier handleRec = new ISemanticAnalyzier() {
				@Override
				public Object handle(IIndexedData indexed, IQuerySymbol factory) {
					int lop = Integer
							.parseInt(indexed.get(0).object.toString());
					return lop + 1;
				}
			};
			// syntax.infer("E -> T `PLUS`<+> E | T `MINUS`<-> E | T");
			// syntax.infer("T -> F `TIMES`<*> T | F `DIVIDE`</> T | F");
			// syntax.infer("F -> `LPA`<(> E `RPA`<)>  | `SYMBOL`<i>");
			semantic.infer(handleCopy, "START -> Z[0]");
			semantic.infer(handleRec, "Z -> Z[0] @a[1]");
			semantic.infer(handleValue, "Z -> @a[0]");
			semantic.initialize("START");
			System.out.println(semantic.toString());
			System.out.println(semantic.getNGAString());
			System.out.println(semantic.getNPAString());
			System.out.println(semantic.getInst());
			System.out.println(semantic.getError());
			System.out.println(semantic.getTokenList());
			System.out.println(semantic.getObject());
			// scanner.close();
		} catch (RegexException e) {
			System.err.println(e.getPosition() + "," + e.getMessage());
			e.printStackTrace();
		} catch (SyntaxException e) {
			System.err.println(e.getPosition() + "," + e.getMessage() + " "
					+ e.getInfo());
			e.printStackTrace();
		}
	}
}