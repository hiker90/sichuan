package com.zzsj.dm.base.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.pojo.vo.BaseVo; /**
 * @author ：zbya
 * @date ：Created in 2020/9/4 16:06
 * @description：
 */
public interface BaseService {

    PageInfo getList(PageQuery<BaseVo> pageQuery) throws Exception;

    int insertData() throws Exception;

    int deleteData(String id) throws Exception;

    int updateData() throws Exception;
}
