package com.zzsj.dm.manage.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/9/4 16:48
 * @description：数据对标
 */
@Data
public class DmCheckVo extends DmCheckMainVo {
    /**
     * Description:  对标字段表信息
     * @Date: 2020/9/7 15:25
     * @Author: zbya
     *
     * @param null
     * @return:
     */
    private List<DmCheckDetailVo> detailVoList;

}
