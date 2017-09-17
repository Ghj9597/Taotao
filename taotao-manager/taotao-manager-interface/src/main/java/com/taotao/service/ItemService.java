package com.taotao.service;

import com.taotao.pojo.TbItem;

import cn.taotao.commom.pojo.EasyUIDateGridResource;
import cn.taotao.commom.pojo.TaotaoResult;

//服务端接口
public interface ItemService {
	EasyUIDateGridResource getItemList(int page,int rows)throws Exception;
	TaotaoResult addItem(TbItem item,String desc)throws Exception;
}
