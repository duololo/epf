package com.zfer.kit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class FileKit {
	
	/**
	 * @param args
	 * 使用apache的IO通用类即可 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//readLines
		InputStream input = new FileInputStream("D:\\aa.log");
		List<String> list = IOUtils.readLines(input);
		for(String str : list){
			System.out.println(str);
		}
		
		//在finally中关闭
		IOUtils.closeQuietly(input);
	}
	
	/*
	//文件1
	File file1;    
	//文件2
	File file2;    
	   
	//复制文件 copy one file into another    
	FileUtils.copyFile(file1, file2);    
	   
	//把文件内容转换为字符串  read a file into a String    
	String s1 = FileUtils.readFileToString(file1);    
	   
	//把文件内容转换为字符串集合，每行为一个字符串对象 read a file into a list of Strings, one item per line    
	List<String> l1 = FileUtils.readLines(file1);    
	   
	//获取目录下或者子目录下的文件，文件类型可以指定   return the list of xml and text files in the specified folder and any subfolders    
	Collection<File> c1 = FileUtils.listFiles(file1, { "xml", "txt" }, true);    
	   
	//复制目录 copy one folder and its contents into another    
	FileUtils.copyDirectoryToDirectory(file1, file2);    
	   
	//删除目录  delete one folder and its contents    
	FileUtils.deleteDirectory(file1);   
	 */

}
