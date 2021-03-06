package suzhiliang.springBatchDemo.itemReaderXml;

import java.io.Serializable;

public class Customer implements Serializable {


	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6423759958572094582L;
    
	private Long id;

	private String firstName;

	private String lastName;

	private String birthday;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthday=" + birthday
				+ "]";
	}

}
