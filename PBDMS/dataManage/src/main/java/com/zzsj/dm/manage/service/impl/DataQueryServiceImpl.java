package com.zzsj.dm.manage.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.mapper.oracle.DataQueryMapper;
import com.zzsj.dm.manage.mapper.oracle.TabInfoMapper;
import com.zzsj.dm.manage.pojo.query.DataQueryQuery;
import com.zzsj.dm.manage.pojo.query.TabQuery;
import com.zzsj.dm.manage.pojo.vo.ColListVo;
import com.zzsj.dm.manage.pojo.vo.DataQueryParamVo;
import com.zzsj.dm.manage.service.DataQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/18 23:14
 * @description：数据查询
 */
@DS("oracle")
@Service
public class DataQueryServiceImpl implements DataQueryService {
    @Autowired
    DataQueryMapper dataQueryMapper;
    @Autowired
    TabInfoMapper tabInfoMapper;
    /**
     * Description: 数据查询 
     * @Date: 2020/9/22 22:28
     * @Author: zbya
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    @Override
    public PageInfo getData(PageQuery<DataQueryQuery> pageQuery) throws Exception{
        //查询列
        StringBuffer str = new StringBuffer() ;
        tabInfoMapper.getColList(pageQuery.getQueryParams().getTabName()).stream().forEach(col->{
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
//                    else if(exp.equals("like")){
//                        queryParam.append("'%"+params.get(i).getValue()+"%'");
//                    }
                    else if(exp.equals("between")||exp.equals("<=")||exp.equals(">=")){
                        queryParam.append(params.get(i).getValue());
                    }
                    else {
                        throw new BusinessException(ResultEnum.ILLEGAL_OPRATION.getValue());
                    }
                }
               else{
                    queryParam.append("regexp_like"+"("+params.get(i).getColName()+","+"'"+params.get(i).getValue()+"','i')");
                }

            }
            pageQuery.getQueryParams().setQueryParam(queryParam.toString());
        }
        else{
            pageQuery.getQueryParams().setQueryParam("1=1");
        }
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataQueryMapper.getData(pageQuery.getQueryParams())).getResult());
    }
    
    /**
     * Description: 获取表信息 
     * @Date: 2020/9/23 15:26 
     * @Author: zbya 
     * 
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */ 
    @Override
    public PageInfo getTable(PageQuery<TabQuery> pageQuery) throws Exception{
        //查询传参小写转大写
        pageQuery.getQueryParams().setTableQueryParam(pageQuery.getQueryParams().getTableQueryParam().toUpperCase());
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(),pageQuery.getPageSize()).doSelectPage(()->dataQueryMapper.getTable(pageQuery.getQueryParams())).getResult());
    }

    /**
     * Description:  获取指定表列信息
     * @Date: 2020/9/23 16:09
     * @Author: zbya
     *
     * @param tableEnName
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.ColListVo>
     */
    @Override
    public List<ColListVo> getColumn(String tableEnName) throws Exception {
        return dataQueryMapper.getColumn(tableEnName);
    }
    public static void main(String []args){
        System.out.println("sf赵@$#$GGG".toUpperCase());
    }
}
