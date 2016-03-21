package com.zfer.kit.excel.importor;

public class StudentNullAnnoVO {
	private int id;

	private String name;

	private int age;

	private String clazz;

	private String company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "StudentVO [id=" + id + ", name=" + name + ", company="
				+ company + ", age=" + age + ", clazz=" + clazz + "]";
	}

}
