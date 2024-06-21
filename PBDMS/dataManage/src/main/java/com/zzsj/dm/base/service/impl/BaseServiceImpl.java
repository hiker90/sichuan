package com.zzsj.dm.base.service.impl;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.pojo.vo.BaseVo;
import com.zzsj.dm.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 16:07
 * @description：
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BaseServiceImpl implements BaseService {
    @Override
    public PageInfo getList(PageQuery<BaseVo> pageQuery) throws Exception{
        return null;
    }

    @Override
    public int insertData() throws Exception {
        return 0;
    }

    @Override
    public int deleteData(String id) throws Exception {
        return 0;
    }

    @Override
    public int updateData() throws Exception {
        return 0;
    }
}
