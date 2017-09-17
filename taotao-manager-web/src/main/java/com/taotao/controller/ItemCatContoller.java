package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemCatService;

import cn.taotao.commom.pojo.EasyUITreeNode;

@Controller
public class ItemCatContoller {
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> showNode(@RequestParam(value="id",defaultValue="0")Long parentid) throws Exception{
		List<EasyUITreeNode> findNode = itemCatService.findNode(parentid);
		return findNode;
	}
}
