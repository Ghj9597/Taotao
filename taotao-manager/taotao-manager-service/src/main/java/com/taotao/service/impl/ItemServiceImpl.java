package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taotao.commom.pojo.EasyUIDateGridResource;
import cn.taotao.commom.pojo.TaotaoResult;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
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
	@Override
	public TaotaoResult addItem(TbItem item, String desc) throws Exception {
		//1.补全Item对象;
		long id = IDUtils.genItemId();
		item.setId(id);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte) 1);
		//2.插入Item信息
		tbItemMapper.insert(item);
		//3.插入商品描述信息;
		TbItemDesc tbItemDesc2 = new TbItemDesc();
		tbItemDesc2.setItemId(id);
		tbItemDesc2.setCreated(date);
		tbItemDesc2.setUpdated(date);
		tbItemDesc2.setItemDesc(desc);
		//保存
		tbItemDescMapper.insert(tbItemDesc2);
		return TaotaoResult.ok();
	}

}
