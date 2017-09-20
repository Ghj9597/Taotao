package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import com.taotao.utils.JsonUtils;

@Controller
public class IndexContoller {
	@Value("${AD1_CONTENT_CID}")
	private Long AD1_CONTENT_CID;//从配置文件中读取组ID
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Autowired
	private ContentService contentService;
	@RequestMapping("/index")
	public String showIndex(Model model){
		List<TbContent> list = contentService.getContentList(AD1_CONTENT_CID);//注入service进行查询
		List<Ad1Node> nodes=new ArrayList<Ad1Node>();
		for (TbContent tbContent : list) {
		  Ad1Node node = new Ad1Node();
		  node.setAlt(tbContent.getTitle());
		  node.setHref(tbContent.getUrl());
		  node.setSrc(tbContent.getPic());
		  node.setSrcB(tbContent.getPic2());
		  node.setHeight(AD1_HEIGHT);
		  node.setHeightB(AD1_HEIGHT_B);
		  node.setWidth(AD1_WIDTH);
		  node.setWidthB(AD1_WIDTH_B);
		  nodes.add(node);
		}
		String json = JsonUtils.objectToJson(nodes);
		model.addAttribute("ad1",json);
		return "index";
	}
}
