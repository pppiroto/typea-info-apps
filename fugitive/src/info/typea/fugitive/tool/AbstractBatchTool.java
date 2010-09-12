package info.typea.fugitive.tool;

import java.util.Properties;

/**
 * コマンドラインや、Antから利用するツールの抽象基底クラス
 * 
 * @author totoec yagi
 */
public abstract class AbstractBatchTool {
    
    /**
     * 使用法タイトル
     * Comment for <code>USAGE_TITLE</code>
     */
    public static final String USAGE_TITLE = "usage : ";
    
    /**
     * バッチ処理を実行する<br>
     * @param args
     * @throws Exception
     */
    abstract public void execute(String[] args) throws Exception;
    
    /**
     * このクラスの使用法を返す
     */
    abstract public String getUsage();
    
    /**
     * 使用法を出力し、ABENDする 
     */
    public void exitWithUsage() {
      System.err.println(getUsage());
      System.exit(0);
    }
    
    /**
     * 実行時のパラメータを解析しMapに格納して返す
     * @param startIndex
     * @param flags
     * @param args
     * @return 実行時のパラメータが格納されたＰｒｏｐｅｒｔｉｅｓ
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
