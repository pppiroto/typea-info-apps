package info.typea.fugitive.action;

import info.typea.fugitive.model.ValueBean;

/**
 * 任意のBeanを1つ保持することができる、業務アプリケーションActionFormクラス
 * <br/>
 * JSPで、Strutsタグを利用した記述において、以下の例の様に、"bean.fieldName" で値を操作することを可能にする。
 * <pre style="color:blue;">
 *   &lt;html:form styleId="zz01_02form" action="/zz01_02" method="POST" .... &gt
 *    &lt;html:text property="bean.employee_id" /&gt
 *    &lt;html:text property="bean.first_name"  /&gt
 *    &lt;html:text property="bean.last_name"   /&gt
 * </pre>
 * <br/>
 * Beanの情報を操作するために、ActionFormクラス自体ににアクセッサメソッドを定義する必要がないため、
 * 業務固有のBeanを、Strutsに依存したActionFormから分離し、独立したクラスとして管理できる。
 * 
 * @see info.typea.fugitive.model.ValueBean
 * @author totec yagi
 */
public class BeanActionForm extends BaseActionForm {
    private static final long serialVersionUID = 1L;
	
    /**
     * Formと結びつけて管理するBeanクラス
     */
    private ValueBean bean;

    /**
     * 
     * @return 管理しているBeanを返す
     */
    public ValueBean getBean() {
        return bean;
    }

    /**
     * @param bean 設定する Bean
     */
    public void setBean(ValueBean bean) {
        this.bean = bean;
    }
    
}
