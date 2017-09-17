package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

import cn.taotao.commom.pojo.EasyUIDateGridResource;
import cn.taotao.commom.pojo.TaotaoResult;

@Controller
public class ItemContoller {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDateGridResource ItemList(int page,int rows) throws Exception{
		EasyUIDateGridResource itemList = itemService.getItemList(page, rows);
		return itemList;
		
	}
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult AddItem(TbItem item, String desc) throws Exception{    
		TaotaoResult result = itemService.addItem(item, desc);
		return result;
	}
}
