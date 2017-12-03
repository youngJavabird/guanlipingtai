package light.mvc.pageModel.producttype;

public class Producttype implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private Long id;
	private String typename;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
}
