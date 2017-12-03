package light.mvc.model.producttype;

import javax.persistence.Entity;
import javax.persistence.Table;
import light.mvc.model.base.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_product_field_contrast")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tproducttypes extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private String typename;
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
}
