package priv.bajdcc.grammar.semantic;

import priv.bajdcc.grammar.error.SemanticError;

/**
 * 【语义分析】语义错误记录接口
 *
 * @author bajdcc
 */
public interface ISemanticRecorder {

	/**
	 * 记录一个错误
	 * @param error 语义分析错误
	 */
	public void add(SemanticError error);
}
