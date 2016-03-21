package com.zfer.kit.excel.importor;

import com.zfer.kit.excel.importor.ExcelVOAnnotation;

public class StudentVO {
	@ExcelVOAnnotation(name = "序号", column = "A")
	private int id;

	@ExcelVOAnnotation(name = "姓名", column = "B", isExport = true)
	private String name;

	@ExcelVOAnnotation(name = "年龄", column = "C", prompt = "年龄保密哦!", isExport = false)
	private int age;

	@ExcelVOAnnotation(name = "班级", column = "D", combo = { "五期提高班", "六期提高班",
			"七期提高班" })
	private String clazz;

	@ExcelVOAnnotation(name = "公司", column = "F")
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
