package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuwenze.poi.util.BeanUtil;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.mapper.mysql.CatalogMapper;
import com.zzsj.dm.manage.mapper.mysql.DataTabOprateMapper;
import com.zzsj.dm.manage.mapper.oracle.TabOperateOacleMapper;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.pojo.vo.ColListVo;
import com.zzsj.dm.manage.pojo.vo.ColTypesVo;
import com.zzsj.dm.manage.pojo.vo.TabListVo;
import com.zzsj.dm.manage.pojo.vo.TabVo;
import com.zzsj.dm.manage.service.DataTabOprateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/9 14:45
 * @description：数据表增删改查
 */

@Service
public class DataTabOprateServiceImpl implements DataTabOprateService {
    @Autowired
    DataTabOprateMapper dataTabOprateMapper;
    @Autowired
    TabOperateOacleMapper tabOperateOacleMapper;
    @Autowired
    CatalogMapper catalogMapper;
    /**
     * Description: 查询自定义表集合
     *
     * @param pageQuery
     * @Date: 2020/9/9 15:34
     * @Author: zbya
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo getList(PageQuery pageQuery) throws Exception {
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(), pageQuery.getPageSize()).doSelectPage(() -> dataTabOprateMapper.getList()).getResult());
    }

    /**
     * Description: 新建表
     *
     * @param tabVo
     * @Date: 2020/9/10 17:49
     * @Author: zbya
     * @return: int
     */
    @Override
    public String insertData(TabVo tabVo) throws Exception {
        TabListVo tabListVo = new TabListVo();
        BeanUtil.copyProperties(tabListVo, tabVo);
        List<String> createTab=new ArrayList<>();
        //拼接建表SQL
        StringBuffer createSql;
        if (!tabVo.getColList().isEmpty()) {
            createSql = new StringBuffer("CREATE TABLE " + tabVo.getTableEnName() + "( ID VARCHAR(32) default SYS_GUID() not null,");
            //获取字段排序
            List<ColListVo> colList = tabVo.getColList().stream().sorted(Comparator.comparing(ColListVo::getSort)).collect(Collectors.toList());
            //拼字段
            colList.stream().forEach(vo -> {
                createSql.append(vo.getColEnName() + " "+vo.getDataType());
                //数据类型不可选长度
                if(vo.getLength()==null){
                    createSql.append(",");
                }
                else{
                    createSql.append("("+vo.getLength());
                    //有小数位
                    if(vo.getDecimalLength()!=null){
                        createSql.append(","+vo.getDecimalLength());
                    }
                    createSql.append("),");
                }
            });
            createSql.append("SYNCHRONIZE_TIME DATE DEFAULT SYSDATE, constraint PK_"+tabVo.getTableEnName()+" primary key (ID))");
            createTab.add(createSql.toString());
            //拼注释
            if(!tabVo.getTableCnName().isEmpty()){
                createTab.add("comment on table "+tabVo.getTableEnName()+" is '"+tabVo.getTableCnName()+"'");
            }
            //拼接列注释
            colList.stream().forEach(vo -> {
                createTab.add("comment on column "+tabVo.getTableEnName()+"."+vo.getColEnName()+" is '"+vo.getColCnName()+"'");
            });
            createTab.add("comment on column "+tabVo.getTableEnName()+".ID is 'ID'");
            createTab.add("comment on column "+tabVo.getTableEnName()+".SYNCHRONIZE_TIME is '同步时间'");
            //库中创建表
            createTab.stream().forEach(sql->{
                tabOperateOacleMapper.createTab(sql);
            });

            //插入列集合
            dataTabOprateMapper.insertColList(colList);
            //插入表信息
            dataTabOprateMapper.insert(tabListVo);

            return ResultEnum.SAVE_SUCCESS.getValue();
        }
        return "新建数据表字段不能为空";

    }

    /**
     * Description: 删除表信息
     *
     * @param tabQuery
     * @Date: 2020/9/10 11:02
     * @Author: zbya
     * @return: void
     */
    @Override
    public void deleteData(TabQuery tabQuery) throws Exception {
        //删除列集合表
        dataTabOprateMapper.delColById(tabQuery.getId());
        // 删除表集合对应数据
        dataTabOprateMapper.delById(tabQuery.getId());
        //根据表名删除目录信息
        catalogMapper.deleteByEnName(tabQuery.getTableEnName());
        //删除实际表
        tabOperateOacleMapper.deleteTab("DROP TABLE " + tabQuery.getTableEnName());
    }

    /**
     * Description: 查询数据列可选类型
     *
     * @param
     * @Date: 2020/9/10 19:11
     * @Author: zbya
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColTypesVo>
     */
    @Override
    public List<ColTypesVo> getColTypes() {
        return dataTabOprateMapper.getColTypes();
    }

    /**
     * Description: 根据表ID查询列信息
     *
     * @param id
     * @Date: 2020/9/10 19:11
     * @Author: zbya
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColListVo>
     */
    @Override
    public List<ColListVo> getColList(String id) {
        return dataTabOprateMapper.getColList(id);
    }

}
