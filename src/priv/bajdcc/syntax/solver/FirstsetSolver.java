package priv.bajdcc.syntax.solver;

import java.util.HashSet;

import priv.bajdcc.syntax.ISyntaxComponent;
import priv.bajdcc.syntax.ISyntaxComponentVisitor;
import priv.bajdcc.syntax.exp.BranchExp;
import priv.bajdcc.syntax.exp.OptionExp;
import priv.bajdcc.syntax.exp.PropertyExp;
import priv.bajdcc.syntax.exp.RuleExp;
import priv.bajdcc.syntax.exp.SequenceExp;
import priv.bajdcc.syntax.exp.TokenExp;
import priv.bajdcc.syntax.rule.RuleItem;
import priv.bajdcc.utility.VisitBag;

/**
 * 求解一个产生式的First集合
 *
 * @author bajdcc
 */
public class FirstsetSolver implements ISyntaxComponentVisitor {

	/**
	 * 终结符表
	 */
	private HashSet<TokenExp> setTokens = new HashSet<TokenExp>();

	/**
	 * 非终结符表
	 */
	private HashSet<RuleExp> setRules = new HashSet<RuleExp>();

	/**
	 * 产生式推导的串长度是否可能为零
	 */
	private boolean bZero = true;

	/**
	 * 求解
	 * 
	 * @param target
	 *            目标产生式对象
	 * @return 产生式是否合法
	 */
	public boolean solve(RuleItem target) {
		if (bZero) {
			return false;
		}
		target.setFirstSetTokens = new HashSet<TokenExp>(setTokens);
		target.setFirstSetRules = new HashSet<RuleExp>(setRules);
		return true;
	}

	@Override
	public void visitBegin(TokenExp node, VisitBag bag) {
		bag.bVisitChildren = false;
		bag.bVisitEnd = false;
		setTokens.add(node);
		if (bZero) {
			bZero = false;
		}
	}

	@Override
	public void visitBegin(RuleExp node, VisitBag bag) {
		bag.bVisitChildren = false;
		bag.bVisitEnd = false;
		setRules.add(node);
		if (bZero) {
			bZero = false;
		}
	}

	@Override
	public void visitBegin(SequenceExp node, VisitBag bag) {
		bag.bVisitChildren = false;
		bag.bVisitEnd = false;
		boolean zero = false;
		for (ISyntaxComponent exp : node.arrExpressions) {
			exp.visit(this);
			zero = bZero;
			if (!zero) {
				break;
			}
		}
		bZero = zero;
	}

	@Override
	public void visitBegin(BranchExp node, VisitBag bag) {
		bag.bVisitChildren = false;
		bag.bVisitEnd = false;
		boolean zero = false;
		for (ISyntaxComponent exp : node.arrExpressions) {
			exp.visit(this);
			if (bZero) {
				zero = bZero;
			}
		}
		bZero = zero;
	}

	@Override
	public void visitBegin(OptionExp node, VisitBag bag) {
		bag.bVisitChildren = false;
		bag.bVisitEnd = false;
		node.expression.visit(this);
		bZero = true;
	}

	@Override
	public void visitBegin(PropertyExp node, VisitBag bag) {
		bag.bVisitChildren = false;
		bag.bVisitEnd = false;
		node.expression.visit(this);
		bZero = false;
	}

	@Override
	public void visitEnd(TokenExp node) {

	}

	@Override
	public void visitEnd(RuleExp node) {

	}

	@Override
	public void visitEnd(SequenceExp node) {

	}

	@Override
	public void visitEnd(BranchExp node) {

	}

	@Override
	public void visitEnd(OptionExp node) {

	}

	@Override
	public void visitEnd(PropertyExp node) {

	}
}
