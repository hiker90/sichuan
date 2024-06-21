package com.zzsj.analysis.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.analysis.base.exception.BusinessException;
import com.zzsj.analysis.base.pojo.query.PageQuery;
import com.zzsj.analysis.base.utils.enums.ResultEnum;
import com.zzsj.analysis.manage.entity.CulRule;
import com.zzsj.analysis.manage.mapper.calculate.QueryDataMapper;
import com.zzsj.analysis.manage.mapper.mysql.ReportTabMapper;
import com.zzsj.analysis.manage.mapper.oracle.ReportMapper;
import com.zzsj.analysis.manage.pojo.query.DataQueryQuery;
import com.zzsj.analysis.manage.pojo.query.DateQuery;
import com.zzsj.analysis.manage.pojo.vo.ColListVo;
import com.zzsj.analysis.manage.pojo.vo.DataQueryParamVo;
import com.zzsj.analysis.manage.pojo.vo.TabListVo;
import com.zzsj.analysis.manage.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/16 10:24
 * @description：报表管理
 */

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportTabMapper reportTabMapper;
    @Autowired
    ReportMapper reportMapper;

    /**
     * Description: 获取结果表集合
     * @Date: 2020/10/16 14:46
     * @Author: zbya
     *
     * @param
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.TabListVo>
     */
    @Override
    public List<CulRule> getTabList() {
        return reportTabMapper.getTabList();
    }

    /**
     * Description: 数据查询
     * @Date: 2020/10/16 15:16
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    @Override
    public PageInfo getData(PageQuery<DataQueryQuery> pageQuery) throws Exception{
        //查询列
        StringBuffer str = new StringBuffer() ;
        reportMapper.getColList(pageQuery.getQueryParams().getTabName()).stream().forEach(col->{
            str.append(col.getColumnName()+",");
        });

        pageQuery.getQueryParams().setColName(str.replace(str.length()-1,str.length(),"").toString());
        List<DataQueryParamVo> params=pageQuery.getQueryParams().getParams();
        //查询条件
        if(params!=null&&!params.isEmpty()){

            StringBuffer queryParam=new StringBuffer();
            for(int i=0;i<params.size();i++){
                if(i!=0){
                    queryParam.append(" "+params.get(i).getLink()+" ");
                }
                if(!params.get(i).getExpression().equals("like")){
                    queryParam.append(params.get(i).getColName()+" "+params.get(i).getExpression()+" ");
                    String exp=params.get(i).getExpression();
                    if(exp.equals("=")){
                        queryParam.append("'"+params.get(i).getValue()+"'");
                    }
                    else if(exp.equals("between")||exp.equals("<=")||exp.equals(">=")){
                        queryParam.append(params.get(i).getValue());
                    }
                    else {
                        throw new BusinessException(ResultEnum.ILLEGAL_OPRATION.getValue());
                    }
                }
                else{
                    queryParam.append(params.get(i).getColName()+" REGEXP "+"'"+params.get(i).getValue()+"'");
                }
            }
            pageQuery.getQueryParams().setQueryParam(queryParam.toString());
        }
        else{
            pageQuery.getQueryParams().setQueryParam("1=1");
        }
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->reportMapper.getData(pageQuery.getQueryParams())).getResult());
    }

    /**
     * Description:  获取指定表列信息
     * @Date: 2020/9/23 16:09
     * @Author: zbya
     *
     * @param tableEnName
     * @return: java.util.List<com.zzsj.analysis.manage.pojo.vo.ColListVo>
     */
    @Override
    public List<ColListVo> getColumn(String tableEnName) throws Exception {
        List<ColListVo> datas=reportMapper.getColumn(tableEnName);
        datas.stream().forEach(data->{
            if(data.getDataType().contains("(")){
                if(data.getDataType().contains(",")){
                    data.setDecimalLength(Integer.parseInt(data.getDataType().substring(data.getDataType().indexOf(",")+1,data.getDataType().indexOf(")"))));
                    data.setLength(Integer.parseInt(data.getDataType().substring(data.getDataType().indexOf("(")+1,data.getDataType().indexOf(","))));
                }
                else{
                    data.setLength(Integer.parseInt(data.getDataType().substring(data.getDataType().indexOf("(")+1,data.getDataType().indexOf(")"))));
                }

                data.setDataType(data.getDataType().substring(0,data.getDataType().indexOf("(")));
           }
        });
        return datas;
    }


}
