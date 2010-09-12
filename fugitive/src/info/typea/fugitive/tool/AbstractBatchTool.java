package info.typea.fugitive.tool;

import java.util.Properties;

/**
 * �R�}���h���C����AAnt���痘�p����c�[���̒��ۊ��N���X
 * 
 * @author totoec yagi
 */
public abstract class AbstractBatchTool {
    
    /**
     * �g�p�@�^�C�g��
     * Comment for <code>USAGE_TITLE</code>
     */
    public static final String USAGE_TITLE = "usage : ";
    
    /**
     * �o�b�`���������s����<br>
     * @param args
     * @throws Exception
     */
    abstract public void execute(String[] args) throws Exception;
    
    /**
     * ���̃N���X�̎g�p�@��Ԃ�
     */
    abstract public String getUsage();
    
    /**
     * �g�p�@���o�͂��AABEND���� 
     */
    public void exitWithUsage() {
      System.err.println(getUsage());
      System.exit(0);
    }
    
    /**
     * ���s���̃p�����[�^����͂�Map�Ɋi�[���ĕԂ�
     * @param startIndex
     * @param flags
     * @param args
     * @return ���s���̃p�����[�^���i�[���ꂽ�o������������������
     */
    public Properties parseArguments(int startIndex, String[] flags, String[] args) {
      if (flags == null || args == null) return null;
      
      Properties result = new Properties();
      for (int i = startIndex; i < args.length; i++) {
        for (int j=0; j<flags.length; j++) {
          if (flags[j].equalsIgnoreCase(args[i])) {
            try {
              result.put(flags[j], args[i + 1]);
            } catch (ArrayIndexOutOfBoundsException e) {
              result.put(flags[j], "true");
            }
          }
        }
      }
      return result;
    }
}
