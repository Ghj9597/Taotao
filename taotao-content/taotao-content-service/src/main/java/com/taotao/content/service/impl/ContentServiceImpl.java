package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.EasyUIDateGridResource;
import cn.taotao.commom.pojo.TaotaoResult;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {
	// 注入Mapper映射
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	// 方法中注入Redis接口进行操作
	@Autowired
	private JedisClient jedisClient;

	@Override
	public TaotaoResult InsertContent(TbContent content) {
		// 补全信息;
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 向表中插入数据;
		tbContentMapper.insert(content);
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentList(Long cid) {
		try {
			//从Json中取出值
			String json = jedisClient.hget(CONTENT_KEY, cid.toString());
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExample(contentExample);
		//将redis使用trycatch包起来为了让redis碟机时候不会影响正常运行
		try {
			//查询之后将值加入redis
			String json = JsonUtils.objectToJson(list);
			jedisClient.hset(CONTENT_KEY,cid.toString(), json);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public EasyUIDateGridResource showContent(Long categoryId, int page,int rows) {
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> list = tbContentMapper.selectByExample(contentExample);
		// 将结果集封装进入EasyUIDateGridResource
		EasyUIDateGridResource resource = new EasyUIDateGridResource();
		resource.setRows(list);
		PageInfo<TbContent> info = new PageInfo<TbContent>(list);
		resource.setTotal(info.getTotal());
		return resource;
	}
}
