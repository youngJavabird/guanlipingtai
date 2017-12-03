package light.mvc.pageModel.headlelines;

import java.util.Date;

public class Headlelines implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private Long id;
	private String guid;
	private String href;
	private int sort;
	private Date createtime;
	private Date upddate;
	private String describes;
	private String title;
	private int clicktimes;
	private String picture;
	private int virclicktime;
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
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getClicktimes() {
		return clicktimes;
	}
	public void setClicktimes(int clicktimes) {
		this.clicktimes = clicktimes;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getVirclicktime() {
		return virclicktime;
	}
	public void setVirclicktime(int virclicktime) {
		this.virclicktime = virclicktime;
	}
	
}
