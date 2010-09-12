package libra.ab01.test;

import libra.ab01.AB01Logic;
import libra.ab01.model.Book;

public class AB01LogicTest {
	public static void main(String[] args) {
		AB01LogicTest me = new AB01LogicTest();
		me.test();
	}
	
	public void test() {
		String asin = "4797327014";
		// String asin = "ERRORASINNO";
		AB01Logic logic = new AB01Logic();
		try {
			System.out.println(logic.getItemLookupRequest(asin));
			Book book = logic.createBook(asin);
			book.setCreate_user("piroto");
			book.setCreate_host("127.0.0.1");

			System.out.println(book.getAsin());
			System.out.println(book.getTitle());
			System.out.println(book.getPage_url());
			System.out.println(book.getKeyword_1());
			System.out.println(book.getKeyword_2());
			System.out.println(book.getKeyword_3());
			System.out.println(book.getKeyword_4());
			System.out.println(book.getKeyword_5());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
