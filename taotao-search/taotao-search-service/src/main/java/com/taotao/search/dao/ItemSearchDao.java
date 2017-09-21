package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.taotao.commom.pojo.SearchItem;
import cn.taotao.commom.pojo.SearchResult;

//1创建用于读写Solr的Dao层方法;
@Repository
public class ItemSearchDao {
	@Autowired
	private SolrServer solrServer;//注入SolrServer已访问数据库
	
	public SearchResult search(SolrQuery solrQuery) throws Exception{
		//使用SolrServer进行查询
		QueryResponse response = solrServer.query(solrQuery);
		//获取结果Document对象
		SolrDocumentList solrDocumentList = response.getResults();
		//定义SearchResult取得查询结果
		SearchResult searchResult = new SearchResult();
		//取得查询总记录数;
		searchResult.setRecordCount(solrDocumentList.getNumFound());
		List<SearchItem> itemList=new ArrayList<SearchItem>();
		//遍历结果集封装结果
		for (SolrDocument solrDocument : solrDocumentList) {
			//创建SearchItem封装Item结果
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name(solrDocument.get("item_category_name").toString());
			searchItem.setId(Long.parseLong(solrDocument.get("id").toString()));
			searchItem.setImage(solrDocument.get("item_image").toString());
			searchItem.setPrice((Long)solrDocument.get("item_price"));
			//封装高亮显示
			String itemTitle=null;
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
			List<String> list = map.get("item_title");
			if(list != null&&list.size()>0){
				itemTitle=list.get(0);
			}else{
				itemTitle=solrDocument.get("item_title").toString();
			}
			searchItem.setTitle(itemTitle);
			searchItem.setSell_point(solrDocument.get("item_sell_point").toString());
			itemList.add(searchItem);
		}
		searchResult.setItemList(itemList);
		return searchResult;
	}
}
