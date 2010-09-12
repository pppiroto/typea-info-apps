package info.typea.fugitive.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * �Ɩ����b�Z�[�W���Ǘ�����N���X
 * <br/>
 * @see info.typea.fugitive.logic.Message
 * @author totec yagi
 */
public class Messages implements Iterable<Message> {
    /**
     * �Ɩ����b�Z�[�W�̃��X�g
     */
    private List<Message> messages;
    
    /**
     * �Ɩ����b�Z�[�W�̃��X�g���擾����B
     * @return �Ɩ����b�Z�[�W�̃��X�g
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
     * �L���ȋƖ����b�Z�[�W��ێ����Ă��邩
     * @return �L���ȋƖ����b�Z�[�W��ێ����Ă��Ȃ��ꍇtrue
     */
    public boolean isEmpty() {
        if (this.messages == null) {
            return true;
        }
        return (getMessages().size() == 0);
    }
    
    /**
     * �Ɩ����b�Z�[�W��ǉ�����
     * @param msg �Ɩ����b�Z�[�W
     */
    public void add(Message msg) {
        getMessages().add(msg);
    }
    /**
     * �C���f�b�N�X���w�肵�āA�Ɩ����b�Z�[�W��ǉ�����
     * @param index �C���f�b�N�X
     * @param msg �Ɩ����b�Z�[�W
     */
    public void add(int index, Message msg) {
        getMessages().add(index, msg);
    }
    /**
     * �C���f�b�N�X���w�肵�āA�Ɩ����b�Z�[�W���擾����B
     * @param index�@�C���f�b�N�X
     * @return �Ɩ����b�Z�[�W
     */
    public Message get(int index) {
        return getMessages().get(index);
    }
}
