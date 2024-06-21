package com.zzsj.dm.manage.mapper.mysql;

import com.zzsj.dm.manage.pojo.query.DataApplyQuery;
import com.zzsj.dm.manage.pojo.vo.DataShareApplyVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：zbya
 * @date ：Created in 2020/10/15 14:05
 * @description：数据共享
 */

@Repository
public interface DataShareMapper {

    /**
     * Description: 共享交换-申请
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param dataShareApplyVo
     * @return: int
     */
    int dataApply(DataShareApplyVo dataShareApplyVo);

    /**
     * Description: 共享交换-申请详情
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param dataApplyQuery
     * @return: java.util.List<com.zzsj.dm.manage.pojo.vo.DataShareApplyVo>
     */
    List<DataShareApplyVo> applyList(DataApplyQuery dataApplyQuery);

    /**
     * Description: 共享交换-审核
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param id
     * @return: java.lang.String
     */
    int applyCheck(DataShareApplyVo dataShareApplyVo);
}
