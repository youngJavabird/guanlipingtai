package light.mvc.pageModel.purchase;

import java.util.Date;


public class Purchase implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private Long id; 
	private int product_id;
	private String shelfnum;
	private String shelfbegintime;
	private String shelfendtime;
	private String update;
	private String scale;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getShelfnum() {
		return shelfnum;
	}

	public String getShelfbegintime() {
		return shelfbegintime;
	}
	public void setShelfbegintime(String shelfbegintime) {
		this.shelfbegintime = shelfbegintime;
	}
	public String getShelfendtime() {
		return shelfendtime;
	}
	public void setShelfendtime(String shelfendtime) {
		this.shelfendtime = shelfendtime;
	}
	public void setShelfnum(String shelfnum) {
		this.shelfnum = shelfnum;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}

	
	
}
