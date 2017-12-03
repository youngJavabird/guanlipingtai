package light.mvc.pageModel.richtext;

import java.util.Date;

public class Richtext implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private String name;
	private String richcontext;
	private Date createdate;
	private Date upddate;
	private String remark;
	private Long id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRichcontext() {
		return richcontext;
	}
	public void setRichcontext(String richcontext) {
		this.richcontext = richcontext;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
