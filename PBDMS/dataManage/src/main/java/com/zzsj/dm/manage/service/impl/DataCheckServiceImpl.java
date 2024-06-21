package com.zzsj.dm.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuwenze.poi.util.BeanUtil;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.mapper.mysql.DataCheckMapper;
import com.zzsj.dm.manage.pojo.query.CheckQuery;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.DmCheckMainVo;
import com.zzsj.dm.manage.pojo.vo.DmCheckVo;
import com.zzsj.dm.manage.service.DataCheckService;
import com.zzsj.dm.manage.utils.NullUtil;
import com.zzsj.dm.manage.utils.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 9:41
 * @description：数据对标
 */

@DS("dm")
@Service
@Transactional(rollbackFor = Exception.class)
public class DataCheckServiceImpl implements DataCheckService {
    @Autowired
    DataCheckMapper dataCheckMapper;

    /**
     * Description: 对标主表分页查询
     *
     * @param pageQuery
     * @Date: 2020/9/7 23:35
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo getCheckList(PageQuery<CheckQuery> pageQuery) throws Exception {
        return new PageInfo<>(PageHelper.startPage(pageQuery.getPageIndex(), pageQuery.getPageSize()).doSelectPage(() -> dataCheckMapper.getCheckList(pageQuery.getQueryParams())).getResult());
    }

    /**
     * Description: 对标主表更新
     *
     * @param dmCheckVo
     * @Date: 2020/9/7 23:35
     * @Author: zbya
     * @return: int
     */
    @Override
    public void updateData(DmCheckVo dmCheckVo) throws Exception {
        DmCheckMainVo dmCheckMainVo = new DmCheckMainVo();
        BeanUtils.copyProperties(dmCheckVo, dmCheckMainVo, NullUtil.getNullPropertyNames(dmCheckVo));
        //更新主表
        dataCheckMapper.updateMain(dmCheckMainVo);
        //删除字段表信息
        dataCheckMapper.deleColByid(dmCheckVo.getId());
        //插入字段信息
        if (!dmCheckVo.getDetailVoList().isEmpty()) {
            dataCheckMapper.insertColList(dmCheckVo.getDetailVoList());
        }
    }

    /**
     * Description: 根据ID删除表和字段信息
     *
     * @param id
     * @Date: 2020/9/7 22:50
     * @Author: zbya
     * @return: int
     */
    @Override
    public int deleteData(String id) {
        //删除主表信息
        dataCheckMapper.deleteById(id);
        //删除字段表信息
        return dataCheckMapper.deleColByid(id);
    }

    /**
     * Description: 插入表预警信息
     *
     * @param dmCheckVo
     * @Date: 2020/9/7 18:16
     * @Author: zbya
     * @return: int
     */
    @Override
    public int insertData(DmCheckVo dmCheckVo) throws Exception {
        DmCheckMainVo dmCheckMainVo = new DmCheckMainVo(UuidUtil.get());
        BeanUtil.copyProperties(dmCheckMainVo, dmCheckVo);
        //插入字段信息
        if (!dmCheckVo.getDetailVoList().isEmpty()) {
            dataCheckMapper.insertColList(dmCheckVo.getDetailVoList());
        }

        //插入表字段信息
        return dataCheckMapper.insertData(dmCheckMainVo);

    }

    /**
     * Description: 修改预警状态
     *
     * @param statusQuery
     * @Date: 2020/9/8 16:48
     * @Author: zbya
     * @return: int
     */
    @Override
    public int changeStatus(StatusQuery statusQuery) throws Exception {
        return dataCheckMapper.changeStatus(statusQuery);
    }

    /**
     * Description: 根据表ID查询列集合信息
     *
     * @param id
     * @Date: 2020/9/7 23:01
     * @Author: zbya
     * @return: java.util.List
     */
    @Override
    public List getColList(String id) throws Exception {
        return dataCheckMapper.getColList(id);
    }


}
