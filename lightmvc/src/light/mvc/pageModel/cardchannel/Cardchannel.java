package light.mvc.pageModel.cardchannel;

import java.util.Date;

public class Cardchannel implements java.io.Serializable{
	
	private static final long serialVersionUID = 6159615613952892233L;
	private Long id;
	private String guid;
	private String channel;
	private String picture;
	private String bannerpicture;
//	private int sort;
    private int state;
    private Date createdate;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
