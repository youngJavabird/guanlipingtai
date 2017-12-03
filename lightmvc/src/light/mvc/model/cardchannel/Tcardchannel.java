package light.mvc.model.cardchannel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_card_channel")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcardchannel extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = -570098299191874913L;
	private String guid;
	private String channel;
	private String picture;
	private String bannerpicture;
//	private int sort;
	private int state;
	private Date createdate;
	private String remark;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getBannerpicture() {
		return bannerpicture;
	}
	public void setBannerpicture(String bannerpicture) {
		this.bannerpicture = bannerpicture;
	}
//	public int getSort() {
//		return sort;
//	}
//	public void setSort(int sort) {
//		this.sort = sort;
//	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
