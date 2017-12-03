package light.mvc.model.productpicture;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_product_picture")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tproductpicture extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private String guid;
	private String product_guid;
	private String picture;
	private Date createdate;	
	private int state;
	private String remark;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getProduct_guid() {
		return product_guid;
	}
	public void setProduct_guid(String product_guid) {
		this.product_guid = product_guid;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
