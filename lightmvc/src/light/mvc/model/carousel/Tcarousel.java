package light.mvc.model.carousel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_carousel")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcarousel extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = -570098299191874913L;
	private String guid;
	private int type;
	private String name;
	private String detail;
	private String bannerpicture;
	private String picture;
//	private int sort;
	private String stime;
	private String etime;
	private Date createtime;
	private int state;
	private String href;
	private String remark;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getBannerpicture() {
		return bannerpicture;
	}
	public void setBannerpicture(String bannerpicture) {
		this.bannerpicture = bannerpicture;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
//	public int getSort() {
//		return sort;
//	}
//	public void setSort(int sort) {
//		this.sort = sort;
//	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
