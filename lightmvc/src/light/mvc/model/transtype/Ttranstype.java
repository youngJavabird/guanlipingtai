package light.mvc.model.transtype;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_transtype")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Ttranstype extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private String transtype;
	private String transtypename;
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getTranstypename() {
		return transtypename;
	}
	public void setTranstypename(String transtypename) {
		this.transtypename = transtypename;
	}
	
}
