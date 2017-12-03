package light.mvc.model.productfield;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_product_field_name")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tproductfield extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private String name;
	private int type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
