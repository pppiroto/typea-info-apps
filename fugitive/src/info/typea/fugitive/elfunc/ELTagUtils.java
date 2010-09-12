package info.typea.fugitive.elfunc;

import java.io.UnsupportedEncodingException;

/**
 * JSP�Ŏg�p����AEL�֐��Q
 * @author totec yagi
 */
public class ELTagUtils {
	private static final String AMAZON_ASOCIATE_TAG = "typea09-22";
	/**
	 * �w�茅����&lt;wbr&gt;�^�O��}������B
	 * HTML�^�O�ň͂܂�Ă��镶���̓J�E���g���Ȃ��BHTML���ꕶ���̓G�X�P�[�v����Ă��邱�ƁB
	 * 
	 * @param value
	 * @param column
	 * @return
	 */
	public static String blockComment(String value, int column) {
		final String WORD_BREAK_STR = "<wbr>";

		char[] c = value.toCharArray();
		StringBuilder buf = new StringBuilder(c.length + 20);
		boolean isInTag = false;
		boolean isEscChar = false;
		int charCnt = 0;

		for(int i=0; i<c.length; i++) {
		    switch (c[i]) {
		    case '<': isInTag   = true;  break;
		    case '>': isInTag   = false; break;
		    case '&': isEscChar = true;  break;
		    case ';': isEscChar = false; break;
		    default:
		    }
		    if (!isInTag) {
		        try {
					charCnt += String.valueOf(c[i]).getBytes("Shift_JIS").length;
				} catch (UnsupportedEncodingException e) {}
		    }
		    if (isEscChar) {
		    	if (c[i]=='&') {
		    		charCnt++;
		    	} 
		    }
		    buf.append(c[i]);
		    if (charCnt >= column) {
		        buf.append(WORD_BREAK_STR);
		        charCnt = 0;
		    }
		}		
		return buf.toString();
	}
	
	/**
	 * ������̂Ȃ���URL��������n�C�p�[�����N�ɐݒ肷��
	 * @param value �Ώە�����
	 * @return
	 */
	public static String addLink(String value) {
		return ELTagUtils.addLink(value, true); 
	}
	/**
	 * ������̂Ȃ���URL��������n�C�p�[�����N�ɐݒ肷��
	 * @param value �Ώە�����
	 * @param isFilter HTML���ꕶ������������ꍇtrue
	 * @return
	 */
	public static String addLink(String value, boolean isFilter) {
		if (isFilter) {
			value = ELTagUtils.filter(value);
		}
		String regex = "((https?|ftp)://[0-9a-zA-Z,;:~&=@_'%?+\\-/$.!*()]+)";
		String replacement = "<a href='$1' target='_blank'>$1</a>";
		
		return value.replaceAll(regex, replacement);
	}
	
	/**
	 * HTML���ꕶ�����G�X�P�[�v����
	 * @param value
	 * @return
	 */
	public static String filter(String value) {
        if(value == null || value.length() == 0) {
        	return value;
        }
        StringBuilder result = null;
        String filtered = null;
        for(int i = 0; i < value.length(); i++) {
            filtered = null;
            switch(value.charAt(i)) {
            case 60: // '<'
                filtered = "&lt;";
                break;
            case 62: // '>'
                filtered = "&gt;";
                break;
            case 38: // '&'
                filtered = "&amp;";
                break;
            case 34: // '"'
                filtered = "&quot;";
                break;
            case 39: // '\''
                filtered = "&#39;";
                break;
            }
            if(result == null) {
                if(filtered == null) {
                    continue;
                }
                result = new StringBuilder(value.length() + 50);
                if(i > 0) {
                    result.append(value.substring(0, i));
                }
                result.append(filtered);
                continue;
            }
            if(filtered == null) {
                result.append(value.charAt(i));
            } else {
                result.append(filtered);
            }
        }
        return result != null ? result.toString() : value;
	}
	
	/**
	 * �A�}�]���̖{�C���[�W�����N
	 * @param asin10
	 * @return
	 */
	public static String amazon(String asin10) {
		return "<a href='http://www.amazon.co.jp/exec/obidos/ASIN/"
		       + asin10 + "/" +  AMAZON_ASOCIATE_TAG + "'>" 
		       + "<img src='http://images-jp.amazon.com/images/P/" 
		       + asin10 + ".01.MZZZZZZZ.jpg' border='0'></a>";
	}
}
