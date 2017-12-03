package light.mvc.model.card;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;










import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;
import light.mvc.model.sys.Trole;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "wnf_card_type")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcardtype extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1867623281523381449L;


	private String typename;//类型名称
	
	private String type ;//类型编号
	
	private String remark;//
	
	private String num;//
	
	private String price;//金额
	
	private String total; //总金额
	
	
	
	
	public  Tcardtype() {
		super();
	}
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}



	public String getNum() {
		return num;
	}



	public void setNum(String num) {
		this.num = num;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}

	
	
	
	
//	private Set<Tcard> cards = new HashSet<Tcard>(0);



	/*@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "wnf_card", joinColumns = { @JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<Tcard> getCards() {
		return cards;
	}



	public void setCards(Set<Tcard> cards) {
		this.cards = cards;
	}*/
	

}