package info.typea.fugitive.action;

import info.typea.fugitive.model.ValueBean;

/**
 * �C�ӂ�Bean��1�ێ����邱�Ƃ��ł���A�Ɩ��A�v���P�[�V����ActionForm�N���X
 * <br/>
 * JSP�ŁAStruts�^�O�𗘗p�����L�q�ɂ����āA�ȉ��̗�̗l�ɁA"bean.fieldName" �Œl�𑀍삷�邱�Ƃ��\�ɂ���B
 * <pre style="color:blue;">
 *   &lt;html:form styleId="zz01_02form" action="/zz01_02" method="POST" .... &gt
 *    &lt;html:text property="bean.employee_id" /&gt
 *    &lt;html:text property="bean.first_name"  /&gt
 *    &lt;html:text property="bean.last_name"   /&gt
 * </pre>
 * <br/>
 * Bean�̏��𑀍삷�邽�߂ɁAActionForm�N���X���̂ɂɃA�N�Z�b�T���\�b�h���`����K�v���Ȃ����߁A
 * �Ɩ��ŗL��Bean���AStruts�Ɉˑ�����ActionForm���番�����A�Ɨ������N���X�Ƃ��ĊǗ��ł���B
 * 
 * @see info.typea.fugitive.model.ValueBean
 * @author totec yagi
 */
public class BeanActionForm extends BaseActionForm {
    private static final long serialVersionUID = 1L;
	
    /**
     * Form�ƌ��т��ĊǗ�����Bean�N���X
     */
    private ValueBean bean;

    /**
     * 
     * @return �Ǘ����Ă���Bean��Ԃ�
     */
    public ValueBean getBean() {
        return bean;
    }

    /**
     * @param bean �ݒ肷�� Bean
     */
    public void setBean(ValueBean bean) {
        this.bean = bean;
    }
    
}
