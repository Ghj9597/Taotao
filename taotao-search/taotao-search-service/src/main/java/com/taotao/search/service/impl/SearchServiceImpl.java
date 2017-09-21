package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.SearchResult;

import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	//注入Dao层;
	@Autowired
	private ItemSearchDao itemSearchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		SolrQuery query = new SolrQuery();//创建查询对象
		query.setQuery(queryString);//设置查询对象
		if(page<1){
			page=1;//确保当前页的正确性
		}
		query.setStart((page-1)*rows);//设置开始页
		query.setRows(rows);//设置每页记录数
		query.set("df", "item_title");//设置默认搜索域
		query.setHighlight(true);//开启高亮显示
		query.addHighlightField("item_title");//设置高亮域
		query.setHighlightSimplePre("");//设置高亮前缀
		query.setHighlightSimplePost("");//设置高亮后缀
		SearchResult searchResult = itemSearchDao.search(query);//进行查询
		long count = searchResult.getRecordCount();//获取总记录数
		long pageCount=count/rows;
		if(pageCount%rows>0){
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		return searchResult;
	}

}
