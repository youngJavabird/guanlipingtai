package light.mvc.model.ricktext;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_richtext")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trichtext extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private String name;
	private String richcontext;
	private Date createdate;
	private Date upddate;
	private String remark;
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
}
