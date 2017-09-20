package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

import cn.taotao.commom.pojo.EasyUIDateGridResource;
import cn.taotao.commom.pojo.TaotaoResult;

@Controller
public class ContentContoller {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult contentSave(TbContent tbContent){
		return contentService.InsertContent(tbContent);
	}
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDateGridResource showContent(Long categoryId,int page,int rows){
		EasyUIDateGridResource showContent = contentService.showContent(categoryId, page, rows);
		return showContent;
	}
}
