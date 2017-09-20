package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.SearchItem;
import cn.taotao.commom.pojo.TaotaoResult;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.SearchItemService;
@Service
public class SearchItemServiceImpl implements SearchItemService {
	//一注入HttpSolrServer对象
	@Autowired
	private SolrServer httpSolrServer;
	//注入查询Mapper代理对象
	@Autowired
	private ItemMapper itemMapper;
	@Override
	public TaotaoResult importAllItemToIndex() throws Exception {
		//查询所有数据库数据
		List<SearchItem> list = itemMapper.getItemList();
		//遍历
		for (SearchItem searchItem : list) {
			//创建Document对象;
			SolrInputDocument document=new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_des());
			//保存这个文档记录
			httpSolrServer.add(document);
			httpSolrServer.commit();
		}
		return TaotaoResult.ok();
	}

}
