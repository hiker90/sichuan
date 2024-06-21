import request from '@/utils/request'
/* 数据质量*/
// kpi
export function getKpis() {
  return request({
    url: '/data-quality/access-num',
    method: 'post'
  })
}
// 预警信息
export function getAlertInfo(params) {
  return request({
    url: '/data-quality/warn-states',
    method: 'post',
    data: params
  })
}
// 处理结果
export function handleAlert(name) {
  return request({
    url: '/data-quality/warn-deal',
    method: 'post',
    params: { name }
  })
}
/* 数据质量*/
// 规则列表
export function getAlertRules(params) {
  return request({
    url: '/data-check/table-list',
    method: 'post',
    data: params
  })
}
// 删除规则
export function delAlertRules(id) {
  return request({
    url: '/data-check/delete-data',
    method: 'post',
    params: { id }
  })
}
// 启用 停用规则
export function applyRules(params) {
  return request({
    url: '/data-check/change-status',
    method: 'post',
    data: params
  })
}
// 新增规则保存
export function addAlertRules(params) {
  return request({
    url: '/data-check/insert-data',
    method: 'post',
    data: params
  })
}
// 获取数据库表
export function getTables() {
  return request({
    url: '/data-check/all-table',
    method: 'post'
  })
}
// 获取数据库表字段
export function getTableCols(tabName) {
  return request({
    url: '/data-check/col-list',
    method: 'post',
    params: { tabName }
  })
}
// 更新规则保存
export function updateAlertRules(params) {
  return request({
    url: '/data-check/update-data',
    method: 'post',
    data: params
  })
}
// 查询规则详情
export function getRulesDetail(id) {
  return request({
    url: '/data-check/check-col',
    method: 'post',
    params: { id }
  })
}
/* 数据导入 */
// 导入
export function importData(params) {
  return request({
    url: '/data-import/json-excel',
    method: 'post',
    data: params
  })
}
// 查询导入日志
export function getProcessLogs(params) {
  return request({
    url: '/data-import/imp-log',
    method: 'post',
    data: params
  })
}
// 查询导入错误信息
export function getImportErrors(id) {
  return request({
    url: '/data-import/wrong-mes',
    method: 'post',
    params: { id }
  })
}
// 修改日志处理状态
export function handleImportError(params) {
  return request({
    url: '/data-import/change-status',
    method: 'post',
    data: params
  })
}
// 获取数据库表列表
// export function getTableList(params) {
//   return request({
//     url: '/tab-operate/get-list',
//     method: 'post',
//     data: params
//   })
// }
// 查询新建表字段属性配置项
export function getFieldOptions() {
  return request({
    url: '/tab-operate/col-types',
    method: 'post'
  })
}
// 新建数据库表
export function addTable(params) {
  return request({
    url: '/tab-operate/insert-data',
    method: 'post',
    data: params
  })
}
// 删除数据库表
export function delTable(params) {
  return request({
    url: '/tab-operate/delete-data',
    method: 'post',
    data: params
  })
}
// 删除数据库表部分数据
export function delTableData(params) {
  return request({
    url: '/catalog-menu/del-data',
    method: 'post',
    data: params
  })
}
/* 数据目录 */
// 查询数据目录
export function fetchCatalogs(params) {
  return request({
    url: '/catalog-menu/get-catalog',
    method: 'post',
    data: params
  })
}
// 获取目录树
export function getCatalogTree(type) {
  return request({
    url: '/catalog-menu/all-catalog',
    method: 'post',
    params: { type }
  })
}
// 删除目录
export function delCatalogs(id) {
  return request({
    url: '/catalog-menu/delete-data',
    method: 'post',
    params: { id }
  })
}
// 更新数据目录
export function updateCatalogs(params) {
  return request({
    url: '/catalog-menu/update-data',
    method: 'post',
    data: params
  })
}
// 新增数据目录
export function addCatalogs(params) {
  return request({
    url: '/catalog-menu/insert-data',
    method: 'post',
    data: params
  })
}
// 查询未分配目录的数据表集合
export function getNoCataTables() {
  return request({
    url: '/catalog-menu/tab-list',
    method: 'post'
  })
}
/* 数据统计 */
export function fetchStatistics(params) {
  return request({
    url: '/data-count/get-data',
    method: 'post',
    data: params
  })
}
/* 数据查询 */
// 数据查询
export function queryTable(params) {
  return request({
    url: '/data-query/get-table',
    method: 'post',
    data: params
  })
}
// 获取表数据
export function queryData(params) {
  return request({
    url: '/data-query/get-data',
    method: 'post',
    data: params
  })
}
// 获取表字段信息
export function queryCols(tableEnName) {
  return request({
    url: '/data-query/get-col',
    method: 'post',
    params: { tableEnName }
  })
}
/* 共享交换 */
// 查询共享列表
export function getShares(params) {
  return request({
    url: '/data-share/get-list',
    method: 'post',
    data: params
  })
}
// 申请共享
export function applyShare(params) {
  return request({
    url: '/data-share/data-apply',
    method: 'post',
    data: params
  })
}
// 审核共享申请
export function checkApply(params) {
  return request({
    url: '/data-share/apply-check',
    method: 'post',
    data: params
  })
}
/* 操作日志 */
export function getLogs(params) {
  return request({
    url: '/sys-log/log-list',
    method: 'post',
    data: params
  })
}

