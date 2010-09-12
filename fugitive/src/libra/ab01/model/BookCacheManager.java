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

	/**�Q�Ƃ��Ǘ�����L���[*/
	private Queue<String>     que   = null;
	/**Book���L���b�V��*/
	private Map<String, Book> cache = null; 
	/**
	 * �R���X�g���N�^ 
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
	 * �V���O���g���̃C���X�^���X���擾
	 * @return
	 */
	public static BookCacheManager getInstance() {
		if (me == null) {
			me = new BookCacheManager();
		}
		return me;
	}

	/**
	 * �L���b�V������Book��T��
	 * @param asin
	 * @return �������Book�A������Ȃ����null
	 */
	public Book findBook(String asin) {
		Book book = cache.get(asin);
		if (book != null) {
			referenced(asin);
		}
		return book;
	}

	/**
	 * �L���b�V����Book��o�^
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
	 * �ŋ߃A�N�Z�X���ꂽBook���w�茏���擾����
	 * @param cnt �擾����
	 * @return �ŋ߃A�N�Z�X���ꂽBook
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
	 * �Q�Ƃ��ꂽ�ꍇ�ɃL���[�̏��������ւ���
	 * @param asin
	 */
	private void referenced(String asin) {
		synchronized (que) {
			que.remove(asin);
			que.add(asin);
		}
	}
}
