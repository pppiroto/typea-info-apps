package libra.ab01.model;

import info.typea.fugitive.model.ValueBean;

import java.sql.Timestamp;

public class Comment extends ValueBean {
	private static final long serialVersionUID = 1L;
	
	private String asin;
	private String book_comment;
	private Integer pos_x;
	private Integer pos_y;
	private Timestamp  create_date;
	private String create_user;
	private String create_host;

	
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getBook_comment() {
		return book_comment;
	}
	public void setBook_comment(String book_comment) {
		this.book_comment = book_comment;
	}
	public Integer getPos_x() {
		return pos_x;
	}
	public void setPos_x(Integer pos_x) {
		this.pos_x = pos_x;
	}
	public Integer getPos_y() {
		return pos_y;
	}
	public void setPos_y(Integer pos_y) {
		this.pos_y = pos_y;
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
	
}
