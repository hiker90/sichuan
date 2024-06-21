package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.CheckQuery;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.DmCheckDetailVo;
import com.zzsj.dm.manage.pojo.vo.DmCheckMainVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 9:42
 * @description：数据对标
 */

@Repository
public interface DataCheckMapper {
    /**
     * Description: 对标主表
     * @Date: 2020/9/7 20:51
     * @Author: zbya
     *
     * @param checkQuery
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DmCheckMainVo>
     */
    List<DmCheckMainVo> getCheckList(CheckQuery checkQuery);
    /**
     * Description: 对标字段查询
     * @Date: 2020/9/7 20:52
     * @Author: zbya
     *
     * @param id
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DmCheckDetailVo>
     */
    List<DmCheckDetailVo> getColList(String id);
    /**
     * Description:  插入对标主表
     * @Date: 2020/9/7 20:52
     * @Author: zbya
     *
     * @param dmCheckMainVo
     * @return:
     */
    int insertData(DmCheckMainVo dmCheckMainVo);
    /**
     * Description: 插入对标字段表
     * @Date: 2020/9/7 22:51 
     * @Author: zbya 
     * 
     * @param list
     * @return: int
     */ 
    int insertColList(List<DmCheckDetailVo> list) throws Exception;
    /**
     * Description: 根据ID删除表和字段信息
     * @Date: 2020/9/7 22:51
     * @Author: zbya
     *
     * @param id
     * @return: int
     */
    int deleteById(String id);

    /**
     * Description: 根据ID查询ID列集合
     * @Date: 2020/9/7 23:53
     * @Author: zbya
     *
     * @param id
     * @return:
     */
    List<String> getIdsById(String id);

    /**
     * Description: 更新主表
     * @Date: 2020/9/8 9:32
     * @Author: zbya
     *
     * @param dmCheckMainVo
     * @return: int
     */
    int updateMain(DmCheckMainVo dmCheckMainVo) throws Exception;
    /**
     * Description: 根据id删除列信息 
     * @Date: 2020/9/8 14:28 
     * @Author: zbya 
     * 
     * @param id
     * @return: int
     */ 
    int deleColByid(String id);

    /**
     * Description: 修改预警状态
     * @Date: 2020/9/8 16:50
     * @Author: zbya
     *
     * @param statusQuery
     * @return: int
     */
    int changeStatus(StatusQuery statusQuery);
}
