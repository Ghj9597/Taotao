package com.taotao.search.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.taotao.commom.pojo.SearchResult;

import com.taotao.search.service.SearchService;

@Controller
public class SearchContoller {
	//注入SearchService
	@Autowired
	private SearchService searchService;
	//注入每页记录数;
	@Value("${ITEM_ROWS}")
	private int ITEM_ROWS;
	@RequestMapping("/search")
	public String searchItem(@RequestParam("q")String queryString,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception{
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		SearchResult searchResult = searchService.search(queryString, page, ITEM_ROWS);
		model.addAttribute("query",queryString);
		model.addAttribute("page",searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page",page);
		return "search";
	}
}
