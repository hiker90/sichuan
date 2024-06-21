package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.ImportLogVo;
import com.zzsj.dm.manage.pojo.vo.WrongSqlVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/8 21:56
 * @description：数据导入
 */

@Repository
public interface DataImportMapper {
    /**
     * Description: 插入日志信息
     * @Date: 2020/9/10 17:01
     * @Author: zbya
     *
     * @param importLogVo
     * @return: int
     */
    int newLog(ImportLogVo importLogVo);

    /**
     * Description: 插入错误信息
     * @Date: 2020/9/10 17:11
     * @Author: zbya
     *
     * @param list
     * @return: int
     */
    int insertWrongMes(List<WrongSqlVo> list);

    /**
     * Description: 更新日志
     * @Date: 2020/9/10 17:11
     * @Author: zbya
     *
     * @param importLogVo
     * @return: int
     */
    int updateLog(ImportLogVo importLogVo);

    /**
     * Description: 查询导入日志
     * @Date: 2020/9/10 17:40
     * @Author: zbya
     *
     * @param queryParams
     * @return: void
     */
    List<ImportLogVo> getImpLog(StatusQuery queryParams);

    /**
     * Description: 更新处理状态
     * @Date: 2020/9/10 18:52
     * @Author: zbya
     *
     * @param statusQuery
     * @return: int
     */
    int changeStatus(StatusQuery statusQuery);

    /**
     * Description: 查询错误信息
     * @Date: 2020/9/11 10:28
     * @Author: zbya
     *
     * @param id
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.WrongSqlVo>
     */
    List<WrongSqlVo> getWrongMes(String id);

    /**
     * Description: 根据日志ID查询表ID
     * @Date: 2020/9/23 14:28
     * @Author: zbya
     *
     * @param id
     * @return: java.lang.String
     */
    String getTabIdById(String id);
}
