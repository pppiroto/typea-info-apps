package libra.ab01.model;

public class NullBook extends Book {
	private static final long serialVersionUID = 1L;
	public NullBook() {
		this("");
	}
	public NullBook(String asin) {
		this.setAsin(asin);
		this.setTitle("該当データはありません。");
		this.setPage_url("http://www.amazon.co.jp/b?node=289754011&tag=typea09-22&camp=647&creative=3479&linkCode=ur1&adid=0PVD54K5NE5V9N1F45CY&");  
	}
}
