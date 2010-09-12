package info.typea.fugitive.action;

import info.typea.fugitive.csv.CsvRecordable;
import info.typea.fugitive.logging.LogUtil;
import info.typea.fugitive.logic.ApplicationLogic;
import info.typea.fugitive.logic.Message;
import info.typea.fugitive.logic.Messages;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


/**
 * �Ɩ��A�v���P�[�V�������Action�N���X
 * <br/>
 * Struts��Action�N���X(DispatchAction)���g���B
 * �Ɩ��A�v���P�[�V�����ŋ��ʎg�p����@�\����������B
 * <br/>
 * �Ɩ��A�v���P�[�V�����́A���̃N���X���p�������N���X���g�p���āA��ʑJ�ڂ⏈���̃R���g���[�����s���B
 * �������A�Ɩ����W�b�N�́A���̃N���X�Ɏ��������ɁA{@link ApplicationLogic}���p�������N���X���쐬���A�����ɋL�q����B
 * ���̃N���X����́A{@link ApplicationLogic}�ɏ������Ϗ�����B
 * <br/>
 * Struts �� DispatchAction�N���X���p�����Ă��邽�߁A�w�肳�ꂽ���N�G�X�g�p�����[�^�Ɉ�v���郁�\�b�h��Submit���Ɏ����ŌĂяo�����B
 * ���̂��Ƃɂ��Aexecute()���\�b�h�ŁA������U�蕪������A�������Ƃɕʂ�Action�N���X���쐬����K�v���Ȃ��B
 * ���N�G�X�g�p�����[�^���w�肳��Ȃ��ꍇ(����URL���u���E�U�ɓ��͂����ꍇ��)�Aunspecified()���\�b�h���Ă΂��B
 * �p���N���X�ł́Aunspecified()���\�b�h���I�[�o�[���C�h���A�����\���p�̏������s���K�v������B
 * <br/>
 * ���N�G�X�g�p�����[�^���ɂ͔C�ӂ̒l��ݒ�ł��邪�A<span style="color:red">"trxId" �Ƃ��邱��</span>�B �΂ɂȂ�{@link BaseActionForm} �ł́A"trxId" ��O��Ƃ��Ă���B
 * �ݒ�́Astruts-config.xml �� action �v�f�ŁAparemeter="trxId" �Ƃ���B 
 * <pre style="color:blue">
 *   &lt;action-mappings&gt;
 *     &lt;action path="/nz01_01" 
 *                name="NZ01_01Form" 
 *                type="nwf.nz01.ZZ01_01Action"
 *                parameter="trxId"&gt;
 *       &lt;forward name="success"  path="/pages/nz01/nz01_01.jsp" /&gt;
 *       &lt;forward name="editform" path="/pages/nz01/nz01_02.jsp" /&gt;
 *       &lt;forward name="fail"     path="welcome" /&gt;
 *     &lt;/action&gt;
 *          �F
 * </pre>
 * �������A���ۂɂ́A�Ɩ����Ƃ� struts-config.xml �𕪊����邽�߁AWEB-INF/struts-config.xml �ɂł͂Ȃ��A
 * ������̐ݒ�t�@�C���ɋL�q���邱�ƂɂȂ�B
 * �����̐ݒ�́AWEB-INF/web.xml �̈ȉ��̃Z�N�V�����́Aparam-value �ɁA�J���}��؂Ŏw�肷��B
 * <pre style="color:blue">
 *   &lt;servlet&gt;
 *    &lt;servlet-name&gt;action&lt;/servlet-name&gt;
 *    &lt;servlet-class&gt;org.apache.struts.action.ActionServlet&lt;/servlet-class&gt;
 *    &lt;init-param&gt;
 *      &lt;param-name&gt;config&lt;/param-name&gt;
 *      &lt;!-- �Ɩ�App���̐ݒ�t�@�C�����w�� --&gt;
 *      &lt;param-value&gt;
 *          /WEB-INF/struts-config.xml,
 *          /WEB-INF/config/nz01-config.xml
 *      &lt;/param-value&gt;
 *    &lt;/init-param&gt;
 *    &lt;load-on-startup&gt;2&lt;/load-on-startup&gt;
 *  &lt;/servlet&gt;
 * </pre>
 * ���̐ݒ�t�@�C���̕����́A�_���I�ɂ͕�������Ȃ�(���s���Ƀ}�[�W�����)�ׁA���O�̏Փ˂Ȃǂɂ͒��ӂ���K�v������B
 * <br/>
 * @see DispatchAction
 * @author totec yagi
 */
public abstract class BaseAction extends DispatchAction {
	
	/**
	 * ��^ ActionForward �L�[ ��������
	 */
	public static final String SUCCESS = "success";
	/**
	 * ��^ ActionForward �L�[ �������s
	 */
	public static final String FAILURE = "failure";
	/**
	 * ��^ ActionForward �L�[ Welcome�y�[�W��
	 */
	public static final String WELCOME = "weclome";
	
	/**
	 * �Ɩ��A�v���P�[�V����Action�N���X�Ń��O���o�͂������ꍇ�Ɏg�p����B
     * <ul>
     * <li>BaseAction�N���X�ł́A���O�o�͂̂��߂ɁAdebug()�Ainfo()�Awarn()�Aerror()���\�b�h��񋟂���B</li>
     * <li>���O�̐ݒ�́Alog4j.properties �t�@�C���ōs���B</li>
     * </ul>
     * @see BaseAction#debug(Object)
     * @see BaseAction#info(Object)
     * @see BaseAction#warn(Object)
     * @see BaseAction#error(Object)
	 */
	protected Logger log = LogUtil.getLogger(BaseAction.class);
	
    /** 
     * �Ɩ��A�v���P�[�V����Action�N���X��ID��Ԃ��B
     */
    public abstract String getActionId();
    
	/**
     * ����(trxId)���w�肳�ꂸ�ɁA�Ɩ��A�v���P�[�V�����`�����������N���X���Ăяo���ꂽ�ꍇ�̏����B 
	 * Submit���ꂽForm�f�[�^�ɁA���N�G�X�g�p�����[�^("trxId")�����݂��Ȃ��ꍇ�ɁA�Ăяo�����B
	 */
    public abstract ActionForward unspecified(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception;
    
    /**
     * �Ɩ��A�v���P�[�V�����N���X�Ŕ��������A�Ɩ����x���̃G���[����舵�����ʏ����B
     * �ȉ��̂悤�Ɏg�p����B
     * <pre style="color:blue;">
     *    Messages errors = new Messages();
     *    if (isErrorOccured) { // �Ɩ����x���G���[������
     *       err.add(new Message("errors.keyname"));
     *    }
     *    handleErrors(request, errors);
     * </pre>
     * <ul>
     * <li>�G���[�̃L�[(��L�ł�"errors.keyname")�ɑΉ����郁�b�Z�[�W�́AMessageResources.native �t�@�C���Ɏw�肷��B</li>
     * <li>MessageResources.native �t�@�C���́A���j�R�[�h�G�X�P�[�v(native2ascii)���s���AMessageResources.properties �Ƃ���B</li>
     * </ul>
     * <br/>
     * �ۑ����ꂽ�G���[���́AJSP���ŁA�ȉ��̂悤�Ɏ擾���邱�Ƃ��ł���
     * <pre style="color:blue;">
     * &lt;%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"  %&gt;
     *             :
     *     &lt;html:messages id="errors" message="true" 
     *                                header="errors.header" 
     *                                footer="errors.footer"&gt;
     *       &lt;li&gt;&lt;bean:write name="errors"/&gt;&lt;/li&gt;
     *     &lt;/html:messages&gt;
     * </pre>
     * @param request 
     * @param errors �G���[���b�Z�[�W����ێ����� Messages �N���X
     * @see Messages
     */
    protected void handleErrors(HttpServletRequest request, Messages errors) {
        if (errors == null || errors.isEmpty()) {
            return;
        }
        ActionMessages actionMsgs = new ActionMessages();
        for (Message err : errors) {
            String key = err.getKey();
            Object[] params = err.getParameters();
            actionMsgs.add(ActionMessages.GLOBAL_MESSAGE, 
                    new ActionMessage(key, params));
            
            String msg = "action error has occurred, key[" + key + "]," 
                       + "params" + ((params == null)?"":Arrays.toString(params));
            error(msg);
        }
        saveMessages(request, actionMsgs);
    }
    
    /**
     * CSV�t�@�C���̃_�E�����[�h���s���B
     * <ul>
     * <li>CsvRecordable�C���^�[�t�F�[�X�����������N���X�̃C���X�^���X��1�s�Ƃ���CSV�f�[�^���o�͂��܂�</li>
     * <li>CsvRecordable�C���^�[�t�F�[�X�����������N���X�̃��X�g�������Ƃ��ēn���Ă�������</li>
     * <li>Action#execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)�Ɠ��������������\�b�h����Ăяo���Ă��������B</li>
     * <li>���̂Ƃ��A���\�b�h�̖ߒl(ActionForward)��null�Ƃ��Ă��������B</li>
     * </ul>
     * @param filename �t�@�C����
     * @param extention �g���q
     * @param csvRecList CsvRecordable �����������N���X�̃��X�g 
     * @param req 
     * @param res
     * @throws IOException
     * @see CsvRecordable
     * @see Action#execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)
     * @see DispatchAction
     */
    protected void downloadCsvFile(String filename, String extention,
    		                       List csvRecList,
    		                       HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.reset();
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition","attachment;filename=" + filename + "." + extention);

		OutputStream writer = res.getOutputStream();
		if (csvRecList != null) {
			for (int i=0; i<csvRecList.size(); i++) {
				CsvRecordable csvRec = (CsvRecordable)csvRecList.get(i);
				writer.write(csvRec.toCsvRecord().getBytes());
				writer.write("\r\n".getBytes());
			}
		}
		writer.flush();    	
		writer.close();
    }

    /**
     * ���O�o�͂̃v���t�B�b�N�X���w�肷��B
     * �I�[�o�[���C�h���Ȃ��ꍇ�A�Ɩ��A�v���P�[�V����ID��Ԃ��B
     * @return ���O�o�͂̃v���t�B�b�N�X
     */
    protected String getLogPrefix() {
        return "[" + getActionId() + "] ";
    }
    /**
     * �f�o�b�O���O�̏o��
     * @param msg �o�̓��b�Z�[�W
     * @param exception �Ή���O
     */
    protected void debug(Object msg, Throwable exception) {
        log.debug(getLogPrefix() + msg, exception);
    }
    /**
     * �f�o�b�O���O�̏o��
     * @param msg �o�̓��b�Z�[�W
     */
    protected void debug(Object msg) {
        log.debug(getLogPrefix() + msg);
    }
    /**
     * ��񃍃O�̏o��
     * @param msg �o�̓��b�Z�[�W
     * @param exception �Ή���O
     */
    protected void info(Object msg, Throwable exception) {
        log.info(getLogPrefix() + msg, exception);
    }
    /**
     * ��񃍃O�̏o��
     * @param msg �o�̓��b�Z�[�W
     */
    protected void info(Object msg) {
        log.info(getLogPrefix() + msg);
    }
    /**
     * �x�����O�̏o��
     * @param msg �o�̓��b�Z�[�W
     * @param exception �Ή���O
     */
    protected void warn(Object msg, Throwable exception) {
        log.warn(getLogPrefix() + msg, exception);
    }
    /**
     * �x�����O�̏o��
     * @param msg �o�̓��b�Z�[�W
     */
    protected void warn(Object msg) {
        log.warn(getLogPrefix() + msg);
    }
    /**
     * �G���[���O�̏o��
     * @param msg �o�̓��b�Z�[�W
     * @param exception �Ή���O
     */
    protected void error(Object msg, Throwable exception) {
        log.error(getLogPrefix() + msg, exception);
    }
    /**
     * �G���[���O�̏o��
     * @param msg �o�̓��b�Z�[�W
     */
    protected void error(Object msg) {
        log.error(getLogPrefix() + msg);
    }
}
