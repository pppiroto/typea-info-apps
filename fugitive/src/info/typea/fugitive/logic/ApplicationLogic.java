package info.typea.fugitive.logic;

/**
 * 業務ロジックを記述する
 * <br/>
 * 基本的に業務ロジックは、Struts　API や　J2EE APIに依存するものではないので、
 * 再利用性の点からも、Struts の Actionクラスに記述すべきではない。
 * <br/>
 * このクラスの継承クラスに業務ロジックを実装し、Struts の Action クラスからは、そのクラスを呼び出すような
 * 構造とする。
 * <br/>
 * 同様に、Struts Actionクラスにデータアクセスは実装せず、Daoクラスに実装する。
 * 
 * @see info.typea.fugitive.database.Dao
 * @author totec yagi
 */
public class ApplicationLogic {
}
