package com.zfer.kit;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathKitTest {

	@Test
	public void testGetPathClass() {
		String path = PathKit.getPath(PathKitTest.class);
		System.out.println(path);
		//assertEquals("", path);
	}

	@Test
	public void testGetPathObject() {
	}

	@Test
	public void testGetRootClassPath() {
	}

	@Test
	public void testSetRootClassPath() {
	}

	@Test
	public void testGetPackagePath() {
	}

	@Test
	public void testGetFileFromJar() {
	}

	@Test
	public void testGetWebRootPath() {
	}

	@Test
	public void testSetWebRootPath() {
	}

}
