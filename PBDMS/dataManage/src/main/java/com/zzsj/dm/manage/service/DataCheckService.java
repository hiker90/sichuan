package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.CheckQuery;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.DmCheckVo;
import com.zzsj.dm.manage.pojo.vo.StatusVo;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 9:41
 * @description：数据对标
 */
public interface DataCheckService {
    /**
     * Description: 获取所有表信息
     * @Date: 2020/9/7 23:18
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getCheckList(PageQuery<CheckQuery> pageQuery) throws Exception;
    /**
     * Description: 获取所有列信息
     * @Date: 2020/9/7 23:18
     * @Author: zbya
     *
     * @param id
     * @return: java.util.List
     */
    List getColList(String id) throws Exception;
    /**
     * Description: 更新表预警信息，字段信息
     * @Date: 2020/9/7 23:19
     * @Author: zbya
     *
     * @param dmCheckVo
     * @return: int
     */
    void updateData(DmCheckVo dmCheckVo) throws Exception;

    /**
     * Description: 删除表预警信息，字段信息
     * @Date: 2020/9/7 23:18
     * @Author: zbya
     *
     * @param id
     * @return: int
     */
    int deleteData(String id) throws Exception;
    
    /**
     * Description: 插入表预警信息  
     * @Date: 2020/9/7 22:23
     * @Author: zbya 
     * 
     * @param dmCheckVo
     * @return: int
     */ 
    int insertData(DmCheckVo dmCheckVo) throws Exception;

    /**
     * Description: 修改预警状态
     * @Date: 2020/9/8 16:46
     * @Author: zbya
     *
     * @param statusVo
     * @return: int
     */
    int changeStatus(StatusQuery statusQuery)throws Exception;
}
