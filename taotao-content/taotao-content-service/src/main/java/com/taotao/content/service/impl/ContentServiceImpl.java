package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.TaotaoResult;

import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
@Service
public class ContentServiceImpl implements ContentService {
	//注入Mapper映射
	@Autowired
	private TbContentMapper tbContentMapper;
	@Override
	public TaotaoResult InsertContent(TbContent content) {
		//补全信息;
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//向表中插入数据;
		tbContentMapper.insert(content);
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> getContentList(Long cid) {
		TbContentExample contentExample=new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExample(contentExample);
		return list;
	}

}
