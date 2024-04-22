package com.garden.entities;



import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "G_Booking")
public class G_customer {

	@Id
	private int B_ID;
	
	
	@Column
	private String name;
	
	
	@Column
	private String phone;
	

	@Column
	private int No_Of_Person;
	
	
	@Column
	private int price;
	
	@Column
	private Date creationDate;
	
	public int getB_ID() {
		return B_ID;
	}

	public void setB_ID(int b_ID) {
		B_ID = b_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getNo_Of_Person() {
		return No_Of_Person;
	}

	public void setNo_Of_Person(int no_Of_Person) {
		No_Of_Person = no_Of_Person;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public G_customer() {
		super();
		
	}
	

	public G_customer(int b_ID, String name, String phone, int no_Of_Person, int price, Date creationDate) {
		super();
		B_ID = b_ID;
		this.name = name;
		this.phone = phone;
		No_Of_Person = no_Of_Person;
		this.price = price;
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "G_customer [B_ID=" + B_ID + ", name=" + name + ", phone=" + phone + ", No_Of_Person=" + No_Of_Person
				+ ", price=" + price + ", creationDate=" + creationDate + "]";
	}


	

}
