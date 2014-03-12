package com.btten.vincenttools.selectImgDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImgDirectoryObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 文件夹路径
	public String Path;
	// 包含图片数量
	public int fileCum = 0;
	// 图片路径
	public List<String> lstFile = new ArrayList<String>();

}
