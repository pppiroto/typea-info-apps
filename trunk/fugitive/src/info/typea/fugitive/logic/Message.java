package info.typea.fugitive.logic;

/**
 * 業務メッセージを管理する
 * @author totec yagi
 */
public class Message {
    /**
     * メッセージキー
     */
    private String key;
    /**
     * メッセージのプレースフォルダを置き換えるパラメータ
     */
    private Object[] parameters;
    
    /**
     * コンストラクタ
     * @param key メッセージキー
     * @param parameters メッセージのプレースフォルダを置き換えるパラメータ
     */
    public Message(String key, Object...parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    /**
     * コンストラクタ
     * @param key メッセージキー
     */
    public Message(String key) {
        this(key, new Object[]{});
    }
    
    /**
     * メッセージキーを取得する
     * @return メッセージキー
     */
    public String getKey() {
        return key;
    }
    
    /**
     * メッセージキーをセットする
     * @param key メッセージキー
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * パラメータを取得する
     * @return パラメータ
     */
    public Object[] getParameters() {
        return parameters;
    }
    /**
     * パラメータをセットする
     * @param parameters パラメータ
     */
    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
