package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.search.service.SearchItemService;

import cn.taotao.commom.pojo.TaotaoResult;

@Controller
public class IndexManagerController {
	@Autowired
	private SearchItemService searchItemService;
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult indexImport() throws Exception{
		TaotaoResult taotaoResult = searchItemService.importAllItemToIndex();
		return taotaoResult;	
	}
}
