package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.utils.FastDFSClient;
import com.taotao.utils.JsonUtils;

@Controller
public class PictureContoller {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		String json=null;
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try {
		//1.接收上传上来的文件;
		byte[] bytes = uploadFile.getBytes();
		//2.获取文件名称;
		String filename = uploadFile.getOriginalFilename();
		//3.从文件名中获取文件的扩展名称;
		//由于FastDfs要求的上传文件的后缀名不带.所以+1将点去掉
		String ext = filename.substring(filename.lastIndexOf(".")+1);
		//将图片上传到服务器;
		//使用工具类上传并且加载配置文件;
		FastDFSClient fastDFSClient=new FastDFSClient("classpath:upload/fast_dfs.conf");
		//返回结果对象;
		//返回的结果对象为拼接之后的地址名;
		String fileUrl = fastDFSClient.uploadFile(bytes, ext);
		//创建返回结果对象;
		hashMap.put("error", 0);
		hashMap.put("url", IMAGE_SERVER_URL+"/"+fileUrl);
		json = JsonUtils.objectToJson(hashMap);
		} catch (Exception e) {
			hashMap.put("error",1);
			hashMap.put("message","发生了错误");
			e.printStackTrace();
		json = JsonUtils.objectToJson(hashMap);
		}
		return json;
	}
}
