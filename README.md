# kit-excel的使用
------
## 功能说明
将excel中的行数据，导入成List&lt;VO>
##目录说明
kit 模块：基础工具类
kit-excel 模块：excel导入导出工具类
## 使用
1.git clone https://git.oschina.net/duololo/epf.git
2.导入到IntelliJ Idea中
3.运行com.zfer.kit.excel.importor.ExcelTemplateImportorTest
相关测试方法

下面是一个测试示例：

##### 1.StudentNullAnnoVO.java
```java
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
    ....
```

##### 2.excel模板
标示哪一列代表哪个字段(可以到项目中寻找student_template.xls具体查看)
```java
| A  | B    | C   |   D   |    E   |
| id | name | age | clazz | company |
| 序号 | 姓名 | 年龄 | 班级 | 公司 |
| required | required | required | required |
```

##### 3.调用
```java
@Test
public void testImportExcel2003() throws Exception {
    ExcelAbstractImportor<StudentNullAnnoVO> util = new ExcelTemplateImportor<StudentNullAnnoVO>();// 创建excel导入工具类
    util.setExcelEntityClass(StudentNullAnnoVO.class);//设置导出的实体类型
    util.setTemplateExcelInputStream(templateFis);//设置模板
    
    util.importExcel("学生信息0", fis2003);// 导入
    List<StudentNullAnnoVO> list = util.getExcelRightDataList();//获取校验正确的数据
    List<StudentNullAnnoVO> allList = util.getExcelAllDataList();//获取所有的数据
    
    assertEquals(
            list.toString(),
            "[StudentVO [id=1, name=柳波, company=2009-10-09, age=18, clazz=五期提高班], StudentVO [id=2, name=柳波, company=2016-08-09, age=29, clazz=五期提高班]]");
    
    ExcelImportSheetErrorMsg error = util.getExcelImportSheetErrorMsg();
    assertEquals("{}", error.getExcelImportRowErrorMsgMap().toString());
}
```


