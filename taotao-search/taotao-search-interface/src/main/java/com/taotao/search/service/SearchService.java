package com.taotao.search.service;

import cn.taotao.commom.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString,int page,int rows)throws Exception;
}
