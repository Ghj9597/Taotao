package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentCategoryService;

import cn.taotao.commom.pojo.EasyUITreeNode;
import cn.taotao.commom.pojo.TaotaoResult;

@Controller
public class ContentCategoryContoller {
	//注入Service代理对象;
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> showContentCategory(@RequestParam(defaultValue="0")Long id){
		List<EasyUITreeNode> list = contentCategoryService.showContentCategory(id);
		return list;
		
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult AddContentCategory(Long parentId,String name){
		return contentCategoryService.addContentCategory(parentId, name);
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public TaotaoResult contentDelete(Long id){
		TaotaoResult taotaoResult = contentCategoryService.contentDelete(id);
		return taotaoResult;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public void updateCategory(Long id, String name){
		contentCategoryService.update(id, name);
	}
}
