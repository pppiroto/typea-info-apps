package libra.ab01;

import java.sql.Connection;
import java.util.List;

import libra.ab01.model.Book;
import libra.ab01.model.BookCacheManager;
import libra.ab01.model.Comment;

public class AB01CacheDao extends AB01Dao {

	@Override
	public Book findBook(String asin) {
		BookCacheManager cache = BookCacheManager.getInstance();
		Book book = cache.findBook(asin);
		if (book == null) {
			book = super.findBook(asin);
			cache.entry(book);
		} else {
			log.debug("hit in cache [" + asin +"]");
		}
		return book;
	}

	/**
	 * 最近アクセスされたcnt件数分のBookを取得する
	 * @param cnt 取得する件数
	 * @return 最近アクセスされたcnt件数分のBook情報
	 */
	@SuppressWarnings("unchecked")
	public List<Book> findRecentlyAccessBooks(int cnt) {
		
		List<Book> books = BookCacheManager.getInstance().getRecentlyAccessBooks(cnt);
		int loaded = 0;
		if (books != null) {
			loaded = books.size();
		} 
		if (cnt > loaded) {
			List<Book> addbooks = super.findRecentlyEntryBooks(cnt - loaded);
			if (books == null ) {
				books = addbooks;
			} else {
				for (Book b : addbooks) {
					books.add(b);
				}
			}
		}
		return books;
	}

	@Override
	public int insertBook(Connection conn, Book book) {
		int ret = super.insertBook(conn, book);
	
		BookCacheManager.getInstance().entry(book);
		
		return ret;
	}

	@Override
	public int insertComment(Connection conn, Comment comment) {
		int ret = super.insertComment(conn, comment);
		
		Book book = super.findBook(comment.getAsin());
		BookCacheManager.getInstance().entry(book);
		
		return ret;
	}
}
