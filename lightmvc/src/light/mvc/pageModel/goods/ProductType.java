package light.mvc.pageModel.goods;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import light.mvc.model.sys.Torganization;

public class ProductType implements java.io.Serializable{
	private Long id;
	private String name;//分类名
	private String picture;//图片名称
	private Date createdate;//创建时间
	private int state;//状态 
	private String remark;//备注
	public ProductType() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
