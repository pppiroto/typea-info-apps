package libra.ab01.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import libra.ab01.AB01Dao;

public class BookCacheManager {
	private static BookCacheManager me;
	private final int INITIAL_CACHE_SIZE = 32;
	private final int MAX_CACHE_SIZE     = 100;

	/**参照を管理するキュー*/
	private Queue<String>     que   = null;
	/**Bookをキャッシュ*/
	private Map<String, Book> cache = null; 
	/**
	 * コンストラクタ 
	 */
	private BookCacheManager() {
		AB01Dao dao = new AB01Dao();
		List<Book> books = dao.findRecentlyEntryBooks(INITIAL_CACHE_SIZE);
		
		que   = new LinkedList<String>();
		cache = new TreeMap<String, Book>();
		
		for (Book book : books) {
			que.add(book.getAsin());
			cache.put(book.getAsin(), book);
		}
	}

	/**
	 * シングルトンのインスタンスを取得
	 * @return
	 */
	public static BookCacheManager getInstance() {
		if (me == null) {
			me = new BookCacheManager();
		}
		return me;
	}

	/**
	 * キャッシュからBookを探す
	 * @param asin
	 * @return 見つかればBook、見つからなければnull
	 */
	public Book findBook(String asin) {
		Book book = cache.get(asin);
		if (book != null) {
			referenced(asin);
		}
		return book;
	}

	/**
	 * キャッシュにBookを登録
	 * @param book
	 */
	public void entry(Book book) {
		if (book == null) return;
		synchronized (cache) {
			if (cache.size() > MAX_CACHE_SIZE) {
				String removeAsin = null;
				synchronized (que) {
					removeAsin = que.poll();
				}
				cache.remove(removeAsin);
			}
			cache.put(book.getAsin(), book);
			referenced(book.getAsin());
		}
	}

	/**
	 * 最近アクセスされたBookを指定件数取得する
	 * @param cnt 取得件数
	 * @return 最近アクセスされたBook
	 */
	public List<Book> getRecentlyAccessBooks(int cnt) {
		List queList     = (List)que;
		List<Book> books = null;
		
		if (queList.size() > 0 ) {
			for (int i=queList.size()-1; i>=0; i--){
				if (books == null) {
					books = new ArrayList<Book>();
				}
				books.add(cache.get((String)queList.get(i)));
				cnt--;
				if (cnt <= 0) {
					break;
				}
			}
		}
		return books;
	}

	/**
	 * 参照された場合にキューの順序を入れ替える
	 * @param asin
	 */
	private void referenced(String asin) {
		synchronized (que) {
			que.remove(asin);
			que.add(asin);
		}
	}
}
