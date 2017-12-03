package light.mvc.model.purchase;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_product_purchase")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tpurchase extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private int product_id;
	private String shelfnum;
	private String shelfbegintime;
	private String shelfendtime;
	private String update;
	private String scale;
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getShelfnum() {
		return shelfnum;
	}
	public void setShelfnum(String shelfnum) {
		this.shelfnum = shelfnum;
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
