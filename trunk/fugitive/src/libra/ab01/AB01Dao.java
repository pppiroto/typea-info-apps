package libra.ab01;

import info.typea.fugitive.database.DefaultDao;
import info.typea.fugitive.database.Parameter;

import java.sql.Connection;
import java.util.List;

import libra.ab01.model.Book;
import libra.ab01.model.Comment;

public class AB01Dao extends DefaultDao {
	public AB01Dao() {
		super("jdbc.datasource.fugitive");
	}
	
	/**
	 * Bookを新規登録する
	 * 
	 * @param book
	 * @return
	 */
	public int insertBook(Book book) {
		return insertBook(null, book);
	}
    /**
     * Bookを新規登録する
     * (トランザクション管理も行う)
     * 
     * @param conn トランザクションを開始したConnection
     * @param book
     * @return 対象件数
     */
    public int insertBook(Connection conn, Book book) {
        Parameter params = new Parameter(
        		book.getAsin(),
        		book.getTitle(),
        		book.getPage_url(),
        		book.getSmall_image(),
        		book.getMedium_image(),
        		book.getLarge_image(),
        		book.getKeyword_1(),
        		book.getKeyword_2(),
        		book.getKeyword_3(),
        		book.getKeyword_4(),
        		book.getKeyword_5(),
        		book.getCreate_date(),
        		book.getCreate_user(),
        		book.getCreate_host()
            );
        return update(conn, getSql("ab01.01"), params.toObjectArray());
    }
	/**
	 * Bookを検索する
	 * @param asin
	 * @return Book
	 */
	public Book findBook(String asin) {
		Book book =  (Book) getBean(Book.class, getSql("ab01.02"), 
							Parameter.makeObjectArray(asin));
		if (book != null) {
			List<Comment> comments = findComments(asin);
			book.setCommentList(comments);
		}
		return book;
	}    

	/**
	 * 最近登録されたcnt件数分のBookを取得する
	 * @param cnt 取得する件数
	 * @return 最近登録されたcnt件数分のBook情報
	 */
	@SuppressWarnings("unchecked")
	public List<Book> findRecentlyEntryBooks(int cnt) {
		List<Book> books = (List<Book>)getBeanList(
					Book.class, getSql("ab01.05"), Parameter.makeObjectArray(cnt));
		
		for (Book book : books) {
			List<Comment> comments = findComments(book.getAsin());
			book.setCommentList(comments);
		}
		return books;
	}
    
	/**
	 * コメントを新規登録する
	 * 
	 * @param book
	 * @return
	 */
	public int insertComment(Comment comment) {
		return insertComment(null, comment);
	}
    /**
     * コメントを新規登録する
     * (トランザクション管理も行う)
     * 
     * @param conn トランザクションを開始したConnection
     * @param book
     * @return 対象件数
     */
    public int insertComment(Connection conn, Comment comment) {
        Parameter params = new Parameter(
        		comment.getAsin(),
        		comment.getAsin(), // SEQ is evaluated by SQL
        		comment.getBook_comment(),
        		comment.getPos_x(),
        		comment.getPos_y(),
        		comment.getCreate_date(),
        		comment.getCreate_user(),
        		comment.getCreate_host()
            );
        return update(conn, getSql("ab01.03"), params.toObjectArray());
    }
    
	/**
	 * コメントを検索する
	 * @param asin
	 * @return Book
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> findComments(String asin) {
		return (List<Comment>)getBeanList(
					Comment.class, getSql("ab01.04"), Parameter.makeObjectArray(asin)); 
	}
}
