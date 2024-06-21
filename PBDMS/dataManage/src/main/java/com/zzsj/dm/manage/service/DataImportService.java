package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.pojo.vo.BaseVo;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.TabListVo;
import com.zzsj.dm.manage.pojo.vo.WrongMesVo;
import com.zzsj.dm.manage.pojo.vo.WrongSqlVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/8 21:55
 * @description：数据导入
 */
public interface DataImportService {
    /**
     * Description: 导入二进制格式EXCEL
     * @Date: 2020/9/10 19:22
     * @Author: zbya
     *
     * @param file
 * @param tabListVo
     * @return: int
     */
    int importData(MultipartFile file,TabListVo tabListVo) throws Exception;

    /**
     * Description: 导入JSON格式EXCEL
     * @Date: 2020/9/10 19:22
     * @Author: zbya
     *
     * @param tabListVo
     * @return: int
     */
    void impJsonExcel(TabListVo tabListVo) throws Exception ;

    /**
     * Description: 查询导入表日志
     * @Date: 2020/9/10 17:52
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo getImpLog(PageQuery<StatusQuery> pageQuery) throws Exception ;

    /**
     * Description: 修改导入状态
     * @Date: 2020/9/10 18:51
     * @Author: zbya
     *
     * @param statusQuery
     * @return: int
     */
    int changeStatus(StatusQuery statusQuery) throws Exception ;
    
    /**
     * Description: 查询错误信息 
     * @Date: 2020/9/11 10:23 
     * @Author: zbya 
     * 
     * @param id
     * @return: List<>
     */ 
    WrongMesVo getWrongMes(String id)  throws Exception ;
}
