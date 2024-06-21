package com.zzsj.dm.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.exception.BusinessException;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.base.utils.enums.ResultEnum;
import com.zzsj.dm.manage.mapper.mysql.CatalogMapper;
import com.zzsj.dm.manage.mapper.oracle.TabInfoMapper;
import com.zzsj.dm.manage.pojo.query.CatalogQuery;
import com.zzsj.dm.manage.pojo.query.TabDataOprQuery;
import com.zzsj.dm.manage.pojo.vo.CatalogVo;
import com.zzsj.dm.manage.pojo.vo.TabInfoVo;
import com.zzsj.dm.manage.service.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/14 14:19
 * @description：数据目录管理
 */

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    CatalogMapper catalogMapper;
    @Autowired
    TabInfoMapper tabInfoMapper;

    /**
     * Description: 获取数据目录结构信息
     *
     * @param pageQuery
     * @Date: 2020/9/14 14:36
     * @Author: zbya
     * @return: PageInfo
     */
    @Override
    public PageInfo getCatalog(PageQuery<CatalogQuery> pageQuery) throws Exception {
        return new PageInfo(PageHelper.startPage(pageQuery.getPageIndex(), pageQuery.getPageSize()).doSelectPage(() -> catalogMapper.getCatalog(pageQuery.getQueryParams())).getResult());
    }

    /**
     * Description: 获取所有数据目录结构信息
     *
     * @param
     * @Date: 2020/9/14 15:28
     * @Author: zbya
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.CatalogVo>
     */
    @Override
    public List<CatalogVo> getAllCatalog(String type) throws Exception {
        // 获取所有的菜单数据
         List<CatalogVo> catalogVos = new ArrayList<>();

        //所有表目录
        if (type.equals("0")) {
            catalogVos = catalogMapper.getCatalogCh();
        }
        //自建表目录
        else if (type.equals("1")) {
            catalogVos = catalogMapper.getAllCatalog();
        }
        // 新建一个菜单，存放处理后的结果
        List<CatalogVo> tree = new ArrayList<>();
        // 先找到所有的一级菜单
        for (int i = 0; i < catalogVos.size(); i++) {
            // 一级菜单没有parentId
            if (StringUtils.isBlank(catalogVos.get(i).getParentId())) {
                tree.add(catalogVos.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (CatalogVo catalogVo : catalogVos) {
            catalogVo.setChildren(getChild(catalogVo.getId(), catalogVos));
        }
        return tree;
    }


    private List<CatalogVo> getChild(String id, List<CatalogVo> catalogVos) {
        // 子菜单
        List<CatalogVo> childList = new ArrayList<>();
        for (CatalogVo catalogVo : catalogVos) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(catalogVo.getParentId())) {
                if (catalogVo.getParentId().equals(id)) {
                    childList.add(catalogVo);
                }
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    /**
     * Description: 删除目录信息
     *
     * @param id
     * @Date: 2020/9/14 15:55
     * @Author: zbya
     * @return: int
     */
    @Override
    public int deleteData(String id) throws Exception {
        return catalogMapper.deleteData(id);
    }

    /**
     * Description: 更新目录信息
     *
     * @param catalogVo
     * @Date: 2020/9/14 15:56
     * @Author: zbya
     * @return: int
     */
    @Override
    public int updateData(CatalogVo catalogVo) throws Exception {
        return catalogMapper.updateData(catalogVo);
    }

    /**
     * Description: 插入目录信息
     *
     * @param
     * @Date: 2020/9/14 15:57
     * @Author: zbya
     * @return: int
     */
    @Override
    public int insertData(CatalogVo catalogVo) throws Exception {
        return catalogMapper.insertData(catalogVo);
    }

    /**
     * Description: 查询未插入数据目录的表
     *
     * @param
     * @Date: 2020/9/15 17:36
     * @Author: zbya
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.TabInfoVo>
     */
    @Override
    public List<TabInfoVo> getTabList() throws Exception {
        //查询所有表
        List<TabInfoVo> tabInfoVos = tabInfoMapper.getTabList();
        //查询已有目录表
        List<String> datas = catalogMapper.selectTab();
        //要删除的表
        List<TabInfoVo> delTabInfoVos = new ArrayList<>();
        for (int i = 0; i < tabInfoVos.size(); i++) {
            if (datas.contains(tabInfoVos.get(i).getTableName())) {
                delTabInfoVos.add(tabInfoVos.get(i));
            }
        }
        if (!delTabInfoVos.isEmpty()) {
            tabInfoVos.removeAll(delTabInfoVos);
        }
        return tabInfoVos;
    }
    
    /**
     * Description: 自建表数据删除 
     * @Date: 2020/12/24 14:03 
     * @Author: zbya 
     * 
     * @param dataCountQuery
     * @return: int
     */ 
    @Override
    public int deleteByDate(TabDataOprQuery dataCountQuery) throws Exception{

        List<String> tabList=tabInfoMapper.getUseTabList().stream().map(tab ->tab.getTableName()).collect(Collectors.toList());
        if(!tabList.isEmpty()){
            if(tabList.contains(dataCountQuery.getTabEnName())){
                return tabInfoMapper.deleteByDate(dataCountQuery);
            }
            else {
                throw new BusinessException(ResultEnum.ILLEGAL_OPRATION.getValue());
            }
        }
        return 0;
    }
}
