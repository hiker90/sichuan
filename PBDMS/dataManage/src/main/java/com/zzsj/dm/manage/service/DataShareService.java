package com.zzsj.dm.manage.service;

import com.github.pagehelper.PageInfo;
import com.zzsj.dm.base.pojo.query.PageQuery;
import com.zzsj.dm.manage.pojo.query.DataApplyQuery;
import com.zzsj.dm.manage.pojo.vo.DataShareApplyVo; /**
 * @author ：zbya
 * @date ：Created in 2020/10/15 14:04
 * @description：数据共享
 */
public interface DataShareService {

    /**
     * Description: 共享交换-申请
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param dataShareApplyVo
     * @return: java.lang.String
     */
    void dataApply(DataShareApplyVo dataShareApplyVo) throws Exception;

    /**
     * Description: 共享交换-申请详情
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param pageQuery
     * @return: com.github.pagehelper.PageInfo
     */
    PageInfo applyList(PageQuery<DataApplyQuery> pageQuery) throws Exception;

    /**
     * Description:  共享交换-审核
     * @Date: 2020/10/15 15:53
     * @Author: zbya
     *
     * @param dataShareApplyVo
     * @return: java.lang.String
     */
    void applyCheck(DataShareApplyVo dataShareApplyVo) throws Exception;
}
