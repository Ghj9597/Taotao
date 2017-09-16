package com.taotao.service;

import cn.taotao.commom.pojo.EasyUIDateGridResource;

//服务端接口
public interface ItemService {
	EasyUIDateGridResource getItemList(int page,int rows)throws Exception;
}
