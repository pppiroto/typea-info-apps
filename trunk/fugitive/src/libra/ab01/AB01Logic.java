package libra.ab01;

import info.typea.fugitive.logic.ApplicationLogic;
import info.typea.fugitive.util.StringUtil;
import info.typea.jungler.operation.ItemLookupOperation;
import info.typea.jungler.operation.ItemLookupOperation.ITEMLOOKUP_RESPONSEGROUP;
import info.typea.jungler.xml.XmlUtil;

import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import libra.ab01.model.Book;
import libra.ab01.model.Comment;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
	
public class AB01Logic extends ApplicationLogic {
    /**
     * 更新か、新規登録かを区分する
     */
    public enum UPDATE_TYPE {INSERT, UPDATE};

    /**
     * XPath式格納用Map
     */
    private static Map<EXP_TYPE, XPathExpression> xpathExperMap;
    private enum EXP_TYPE {
    	 EXP_IS_VALID
    	,EXP_ERROR_CODE
    	,EXP_ASIN
    	,EXP_TITLE
    	,EXP_PAGE_URL
    	,EXP_SMALL_IMG
    	,EXP_MEDIUM_IMG
    	,EXP_LARGE_IMG
    	,EXP_KEYWORDS
    };
	static {
		try {
			XPath xpath = XmlUtil.getInstance().getXPath();
			xpathExperMap = new HashMap<EXP_TYPE, XPathExpression>();
			String itemBasePath = "/ItemLookupResponse/Items/";
			xpathExperMap.put(EXP_TYPE.EXP_IS_VALID,   xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Request/IsValid")));
			xpathExperMap.put(EXP_TYPE.EXP_ERROR_CODE, xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Request/Errors/Error/Code")));
			xpathExperMap.put(EXP_TYPE.EXP_ASIN,       xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/ASIN")));
			xpathExperMap.put(EXP_TYPE.EXP_TITLE,      xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/ItemAttributes/Title")));
			xpathExperMap.put(EXP_TYPE.EXP_PAGE_URL,   xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/DetailPageURL")));
			xpathExperMap.put(EXP_TYPE.EXP_SMALL_IMG,  xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/SmallImage/URL")));
			xpathExperMap.put(EXP_TYPE.EXP_MEDIUM_IMG, xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/MediumImage/URL")));
			xpathExperMap.put(EXP_TYPE.EXP_LARGE_IMG,  xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/LargeImage/URL")));
			xpathExperMap.put(EXP_TYPE.EXP_KEYWORDS,   xpath.compile(XmlUtil.toXPathExper(itemBasePath + "Item/BrowseNodes/BrowseNode")));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ASIN を元に、Amazonにアクセスし、Bookインスタンスを作成する
	 * @param asin
	 * @param user
	 * @param addr
	 * @return
	 * @throws Exception
	 */
	public Book createBook(String asin, String user, String addr) throws Exception {
		Book book = createBook(asin);
		if (book != null) {
			book.setCreate_user(user);
			book.setCreate_host(addr);
		}
		return book;
	}
	/**
	 * @param asin
	 * @param comment
	 * @param user
	 * @param addr
	 * @return
	 */
	public Comment createComment(String asin, 
			                     String comment, 
			                     Integer pos_x, 
			                     Integer pos_y, 
			                     String user, String addr) {
		Comment cmt = new Comment();
		
		cmt.setAsin(asin);
		cmt.setBook_comment(comment);
		cmt.setPos_x(pos_x);
		cmt.setPos_y(pos_y);
		cmt.setCreate_date(new Timestamp(System.currentTimeMillis()));
		cmt.setCreate_user(user);
		cmt.setCreate_host(addr);
		
		return cmt;
	}
	/**
	 * ASIN を元に、Amazonにアクセスし、Bookインスタンスを作成する
	 * @param asin
	 * @return
	 * @throws Exception
	 */
	public Book createBook(String asin) throws Exception {
		String url = getItemLookupRequest(asin);
		Document doc = XmlUtil.getInstance().getDocument(new URL(url));
		
		Book book = null;
		
		if (Boolean.parseBoolean(xpathExperMap.get(EXP_TYPE.EXP_IS_VALID).evaluate(doc))
		  && StringUtil.isBlank(xpathExperMap.get(EXP_TYPE.EXP_ERROR_CODE).evaluate(doc))) { 		
			book = new Book();
			book.setAsin(         xpathExperMap.get(EXP_TYPE.EXP_ASIN).evaluate(doc)      );
			book.setTitle(        xpathExperMap.get(EXP_TYPE.EXP_TITLE).evaluate(doc)     );
			book.setPage_url(     xpathExperMap.get(EXP_TYPE.EXP_PAGE_URL).evaluate(doc)  );
			book.setSmall_image(  xpathExperMap.get(EXP_TYPE.EXP_SMALL_IMG).evaluate(doc) );
			book.setMedium_image( xpathExperMap.get(EXP_TYPE.EXP_MEDIUM_IMG).evaluate(doc));
			book.setLarge_image(  xpathExperMap.get(EXP_TYPE.EXP_LARGE_IMG).evaluate(doc) );
			
			NodeList nodes = (NodeList)xpathExperMap.get(EXP_TYPE.EXP_KEYWORDS).evaluate(doc, XPathConstants.NODESET);
			for (int i=0; i<nodes.getLength(); i++) {
				NodeList childs = nodes.item(i).getChildNodes();
				String keyword  = childs.item(1).getTextContent();
				book.addKeyword(i, keyword);
			}			
			book.setCreate_date(new Timestamp(System.currentTimeMillis()));
		} 
		return book;
	}
	
	/**
	 * @param asin
	 * @return
	 */
	public String getItemLookupRequest(String asin) {
		ItemLookupOperation op = new ItemLookupOperation();
		op.setItemId(asin);
		op.setResponseGroup(ITEMLOOKUP_RESPONSEGROUP.Large);
		String url = op.toRestRequest();
		return url;
	}

	/**
     * Bookを更新か新規登録かを判定し、処理を呼び出す
     * @param type
     * @param book
     * @return 対象件数
     */
    public int insertOrUpdateBook(UPDATE_TYPE type, Book book) {
        AB01Dao dao = new AB01CacheDao();
        
        int ret = -1;
        switch (type) {
        case INSERT:
            ret = dao.insertBook(book);
            break;
        case UPDATE:
            // ret = dao.update(book);
        	// break;
        	throw new UnsupportedOperationException();
        default:
            throw new IllegalArgumentException();
        }
        return ret;
    }
    
	/**
	 * Bookを検索する
	 * @param asin
	 * @return Book
	 */
	public Book findBook(String asin) {
		AB01Dao dao = new AB01CacheDao();
		
		Book book = dao.findBook(asin);
		
		return book;
	}   

	/**
     * コメント更新か新規登録かを判定し、処理を呼び出す
     * @param type
     * @param book
     * @return 対象件数
     */
    public int insertOrUpdateComment(UPDATE_TYPE type, Comment comment) {
        AB01Dao dao = new AB01CacheDao();
        
        int ret = -1;
        switch (type) {
        case INSERT:
            ret = dao.insertComment(comment);
            break;
        case UPDATE:
            // ret = dao.update(book);
        	// break;
        	throw new UnsupportedOperationException();
        default:
            throw new IllegalArgumentException();
        }
        return ret;
    }
    
	/**
	 * @param cnt
	 * @return
	 */
	public List<Book> findRecentlyAccessBooks(int cnt) {
		AB01CacheDao dao = new AB01CacheDao();
		
		return dao.findRecentlyAccessBooks(cnt);
	}
}
