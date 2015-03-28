package priv.bajdcc.lexer.algorithm.filter;

import priv.bajdcc.lexer.error.RegexException;
import priv.bajdcc.lexer.error.RegexException.RegexError;
import priv.bajdcc.lexer.regex.IRegexStringFilter;
import priv.bajdcc.lexer.regex.IRegexStringFilterMeta;
import priv.bajdcc.lexer.regex.IRegexStringIterator;
import priv.bajdcc.lexer.regex.RegexStringIteratorData;
import priv.bajdcc.lexer.regex.RegexStringUtility;
import priv.bajdcc.lexer.token.MetaType;

/**
 * �ַ������͹��ˣ���β�ַ���ͬ��
 * 
 * @author bajdcc
 *
 */
public class StringPairFilter implements IRegexStringFilter,
		IRegexStringFilterMeta {

	/**
	 * �ַ����׵��ս��
	 */
	private MetaType m_kMetaBegin = MetaType.NULL;

	/**
	 * �ַ���β���ս��
	 */
	private MetaType m_kMetaEnd = MetaType.NULL;

	public StringPairFilter(MetaType begin, MetaType end) {
		m_kMetaBegin = begin;
		m_kMetaEnd = end;
	}

	@Override
	public RegexStringIteratorData filter(IRegexStringIterator iterator) {
		RegexStringUtility utility = iterator.utility();// ��ȡ�������
		RegexStringIteratorData data = new RegexStringIteratorData();
		try {
			if (!iterator.available()) {
				data.m_kMeta = MetaType.END;
				data.m_chCurrent = MetaType.END.getChar();
			} else {
				data.m_kMeta = iterator.meta();
				data.m_chCurrent = iterator.current();
				iterator.next();
				if (data.m_kMeta == m_kMetaBegin || data.m_kMeta == m_kMetaEnd) {// �����ս��
					data.m_kMeta = MetaType.NULL;
				} else if (data.m_kMeta == MetaType.ESCAPE) {// ����ת��
					data.m_chCurrent = iterator.current();
					iterator.next();
					data.m_kMeta = MetaType.MUST_SAVE;
					data.m_chCurrent = utility.fromEscape(data.m_chCurrent,
							RegexError.ESCAPE);
				}
			}
		} catch (RegexException e) {
			System.err.println(e.getPosition() + " : " + e.getMessage());
			data.m_kMeta = MetaType.ERROR;
			data.m_chCurrent = MetaType.ERROR.getChar();
		}
		return data;
	}

	@Override
	public IRegexStringFilterMeta getFilterMeta() {
		return this;
	}

	@Override
	public MetaType[] getMetaTypes() {
		return new MetaType[] { m_kMetaBegin, m_kMetaEnd, MetaType.ESCAPE };
	}
}