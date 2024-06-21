package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.mapper.mysql.DataImportMapper;
import com.zzsj.dm.manage.mapper.mysql.DataTabOprateMapper;
import com.zzsj.dm.manage.mapper.oracle.DbOracleUpdateMapper;
import com.zzsj.dm.manage.mapper.oracle.TabOperateOacleMapper;
import com.zzsj.dm.manage.pojo.query.StatusQuery;
import com.zzsj.dm.manage.pojo.vo.*;
import com.zzsj.dm.manage.service.DataImportService;
import com.zzsj.dm.manage.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/8 21:55
 * @description：数据导入
 */
@Service
@Slf4j
public class DataImportServiceImpl implements DataImportService {
    @Autowired
    DataTabOprateMapper dataTabOprateMapper;
    @Autowired
    TabOperateOacleMapper tabOperateOacleMapper;
    @Autowired
    DataImportMapper dataImportMapper;
    @Autowired
    DbOracleUpdateMapper dbOracleUpdateMapper;

    /**
     * Description: 导入JSON格式EXCEL
     *
     * @param tabListVo
     * @param tabListVo
     * @Date: 2020/9/10 10:44
     * @Author: zbya
     * @return: int
     */
    @Override
    @Async
    public void impJsonExcel(TabListVo tabListVo) throws Exception {
        //主表UUID
        String id = UuidUtil.get();
        //excel数据
        List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) tabListVo.getExcel();
        //插入日志
        dataImportMapper.newLog(new ImportLogVo(id, datas.size(), tabListVo.getTableCnName(), tabListVo.getId()));
        //获取列集合
        List<String> cols = dataTabOprateMapper.selectColsByTabId(tabListVo.getId());
        //拼接SQL
        StringBuffer buffer = new StringBuffer("insert into " + tabListVo.getTableEnName() + "(");
        if (cols.isEmpty()) {
            log.error("数据库中不存在表或表字段为空");
            return;
        }
        cols.stream().forEach(col -> {
            buffer.append(col + ",");
        });
        buffer.append("ID ) values (");
        //将SQl拼接为单条插入
        List<WrongSqlVo> wrongSqls = new ArrayList<>();
        for (HashMap<String, Object> map : datas) {
            StringBuffer sql = new StringBuffer(buffer);
            StringBuffer wrongSql = new StringBuffer();
            for (Map.Entry<String, Object> data : map.entrySet()) {
                if(data.getValue() instanceof Double){
                    sql.append("'" + String.valueOf(new BigDecimal(data.getValue()+"")) + "',");
                }
                else{
                    sql.append("'" + String.valueOf(data.getValue()) + "',");
                }

                wrongSql.append(String.valueOf(data.getValue()) + ",");
            }
            sql.append("'" + UuidUtil.get() + "')");
            wrongSql.replace(wrongSql.length() - 1, wrongSql.length(), "");
            try {
                tabOperateOacleMapper.insertData(sql.toString());
            } catch (Exception e) {
                wrongSqls.add(new WrongSqlVo(UuidUtil.get(), id, wrongSql.toString(), e.toString()));
            }
        }
        //插入错误信息
        if (!wrongSqls.isEmpty()) {
            dataImportMapper.insertWrongMes(wrongSqls);
        }
        //更新日志表
        int status = 1;
        if (wrongSqls.size() == 0) {
            status = 3;
        }
        dataImportMapper.updateLog(new ImportLogVo(id, status, datas.size() - wrongSqls.size(), wrongSqls.size()));
    }

    /**
     * Description: 查询导入表日志
     *
     * @param pageQuery
     * @Date: 2020/9/10 17:48
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo getImpLog(PageQuery<StatusQuery> pageQuery) throws Exception {
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(), pageQuery.getPageSize()).doSelectPage(() -> dataImportMapper.getImpLog(pageQuery.getQueryParams())).getResult());
    }

    /**
     * Description: 更新处理状态
     *
     * @param statusQuery
     * @Date: 2020/9/10 18:52
     * @Author: zbya
     * @return: int
     */
    @Override
    public int changeStatus(StatusQuery statusQuery) throws Exception {
        return dataImportMapper.changeStatus(statusQuery);
    }

    /**
     * Description: 查询错误信息
     *
     * @param id
     * @Date: 2020/9/11 10:26
     * @Author: zbya
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.WrongSqlVo>
     */
    @Override
    public WrongMesVo getWrongMes(String id) throws Exception {
        //根据日志Id获取表Id
        String tabId = dataImportMapper.getTabIdById(id);
        //根据表Id获取列字段
        List<String> col = dataTabOprateMapper.getColList(tabId).stream().map(ColListVo::getColCnName).collect(Collectors.toList());
        //错误数据信息
        List<WrongSqlVo> dataList = dataImportMapper.getWrongMes(id);
        return new WrongMesVo(dataList, col);

    }

    /**
     * Description: 导入文件格式excel,暂不用
     *
     * @param file
     * @param tabListVo
     * @Date: 2020/9/10 10:42
     * @Author: zbya
     * @return: int
     */
    @Override
    public int importData(MultipartFile file, TabListVo tabListVo) throws Exception {
        HSSFWorkbook wb = null;
        wb = new HSSFWorkbook(file.getInputStream());
        // 获得第一个工作薄
        HSSFSheet sheet = wb.getSheetAt(0);
        //获得总列数
        int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
        //获得总行数
        int rowNum = sheet.getPhysicalNumberOfRows();
        //获取列集合
        List<String> cols = dataTabOprateMapper.selectColsByTabId(tabListVo.getId());

        StringBuffer buffer = new StringBuffer("insert into " + tabListVo.getTableEnName() + "(");
        cols.stream().forEach(col -> {
            if (!col.equals(cols.get(cols.size() - 1))) {
                buffer.append(col + ",");
            } else {
                buffer.append(col + ") values(");
            }
        });
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < coloumNum; j++) {
                if (j != coloumNum - 1) {
                    if (row.getCell(j).toString() != "") {
                        buffer.append("'" + row.getCell(j).toString() + "',");
                    } else {
                        buffer.append("'',");
                    }
                } else {
                    if (row.getCell(j).toString() != "") {
                        buffer.append("'" + row.getCell(j).toString() + "')");
                    } else {
                        buffer.append("'')");
                    }
                }
            }

        }
        //将stringbuffer分批插入，用另一种方法暂不写
        return 0;
    }
}
