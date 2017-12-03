package light.mvc.model.statistics;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "identification")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tidentification extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -570098299191874913L;

	private String name;
	private String type;
	private String identification;
	private String createtime;
	private String typestate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getTypestate() {
		return typestate;
	}
	public void setTypestate(String typestate) {
		this.typestate = typestate;
	}
	
	

}
