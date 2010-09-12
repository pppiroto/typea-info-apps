package libra.ab01.model;

import info.typea.fugitive.model.ValueBean;
import info.typea.fugitive.util.StringUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book extends ValueBean {
	private static final long serialVersionUID = 1L;
	private String asin;
	private String title;
	private String page_url;
	private String small_image;
	private String medium_image ;
	private String large_image;
	private Timestamp   create_date;
	private String create_user;
	private String create_host;
	
	private List<String> keywords = new ArrayList<String>();
	private List<Comment> comments = new ArrayList<Comment>();
	
	/**
	 * @param index
	 * @param keyword
	 */
	public void addKeyword(int index, String keyword) {
		keywords.add(index, StringUtil.nvl(keyword));
	}
	/**
	 * @param index
	 * @return
	 */
	public String getKeyword(int index) {
		String keyword = "";
		try {
			keyword = keywords.get(index);
		}catch(IndexOutOfBoundsException e) {}
		return StringUtil.nvl(keyword);
	}
	
	/**
	 * @param comment
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	/**
	 * @param index
	 */
	public Comment getComment(int index) {
		Comment comment = null;
		try {
			comment = comments.get(index);
		} catch(IndexOutOfBoundsException e){}
		return comment;
	}
	
	/**
	 * @return
	 */
	public List<Comment> getCommentList() {
		return comments;
	}
	/**
	 * @param comments
	 */
	public void setCommentList(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * @return
	 */
	public List getKeywordList() {
		return Collections.unmodifiableList(keywords);
	}
	
	public String getKeyword_1() {
		return getKeyword(0);
	}
	public String getKeyword_2() {
		return getKeyword(1);
	}
	public String getKeyword_3() {
		return getKeyword(2);
	}
	public String getKeyword_4() {
		return getKeyword(3);
	}
	public String getKeyword_5() {
		return getKeyword(4);
	}
	public void setKeyword_1(String keyword) {
		addKeyword(0, keyword);
	}
	public void setKeyword_2(String keyword) {
		addKeyword(1, keyword);
	}
	public void setKeyword_3(String keyword) {
		addKeyword(2, keyword);
	}
	public void setKeyword_4(String keyword) {
		addKeyword(3, keyword);
	}
	public void setKeyword_5(String keyword) {
		addKeyword(4, keyword);
	}
	
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = StringUtil.nvl(asin).trim();
	}
	public Timestamp getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	public String getCreate_host() {
		return create_host;
	}
	public void setCreate_host(String create_host) {
		this.create_host = create_host;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getLarge_image() {
		String img = large_image;
		if (StringUtil.isBlank(img)) {
			img = this.getMedium_image();
		}
		return img;
	}
	public void setLarge_image(String large_image) {
		this.large_image = large_image;
	}
	public String getMedium_image() {
		String img = medium_image;
		if (StringUtil.isBlank(img)) {
			img = this.getSmall_image();
		}
		return img;
	}
	public void setMedium_image(String medium_image) {
		this.medium_image = medium_image;
	}
	public String getPage_url() {
		return page_url;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public String getSmall_image() {
		return small_image;
	}
	public void setSmall_image(String small_image) {
		this.small_image = small_image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortTitle() {
		String title = StringUtil.nvl(this.getTitle());
		int size = Math.min(title.length(), 6);
		return title.substring(0, size) + "...";
	}
}
