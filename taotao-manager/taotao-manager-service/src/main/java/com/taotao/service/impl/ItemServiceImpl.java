package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.EasyUIDateGridResource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private TbItemMapper tbItemMapper;
	@Override
	public EasyUIDateGridResource getItemList(int page, int rows)
			throws Exception {
		//1.进行分页处理;
		PageHelper.startPage(page, rows);
		//执行查询;
		TbItemExample example=new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//封装结果集;
		EasyUIDateGridResource easyUIDateGridResource = new EasyUIDateGridResource();
		easyUIDateGridResource.setRows(list);
		PageInfo<TbItem> info = new PageInfo<TbItem>(list);
		easyUIDateGridResource.setTotal(info.getTotal());
		return easyUIDateGridResource;
	}

}
