package info.typea.fugitive.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 業務メッセージを管理するクラス
 * <br/>
 * @see info.typea.fugitive.logic.Message
 * @author totec yagi
 */
public class Messages implements Iterable<Message> {
    /**
     * 業務メッセージのリスト
     */
    private List<Message> messages;
    
    /**
     * 業務メッセージのリストを取得する。
     * @return 業務メッセージのリスト
     */
    private List<Message> getMessages(){
        if (this.messages == null) {
            this.messages = new ArrayList<Message>();
        }
        return this.messages;
    }
    
    /* @see java.lang.Iterable#iterator() */
    public Iterator<Message> iterator() {
        return getMessages().iterator();
    }
    
    /**
     * 有効な業務メッセージを保持しているか
     * @return 有効な業務メッセージを保持していない場合true
     */
    public boolean isEmpty() {
        if (this.messages == null) {
            return true;
        }
        return (getMessages().size() == 0);
    }
    
    /**
     * 業務メッセージを追加する
     * @param msg 業務メッセージ
     */
    public void add(Message msg) {
        getMessages().add(msg);
    }
    /**
     * インデックスを指定して、業務メッセージを追加する
     * @param index インデックス
     * @param msg 業務メッセージ
     */
    public void add(int index, Message msg) {
        getMessages().add(index, msg);
    }
    /**
     * インデックスを指定して、業務メッセージを取得する。
     * @param index　インデックス
     * @return 業務メッセージ
     */
    public Message get(int index) {
        return getMessages().get(index);
    }
}
