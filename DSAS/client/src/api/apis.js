import request from '@/utils/request'

/* 报表管理*/
// 获取报表目录
export function getReportList() {
  return request({
    url: '/report/all-table',
    method: 'post'
  })
}
// 计算输出表
export function handleCalc(params) {
  return request({
    url: '/data-cal/calculate',
    method: 'post',
    data: params
  })
}
// 获取报表表头
export function getReportCols(tableEnName) {
  return request({
    url: '/report/get-col',
    method: 'post',
    params: { tableEnName }
  })
}
// 获取报表内容
export function getReport(params) {
  return request({
    url: '/report/get-data',
    method: 'post',
    data: params
  })
}
/* 模板管理*/
// 获取模板列表
export function getModelList(params) {
  return request({
    url: '/report-manage/get-model',
    method: 'post',
    data: params
  })
}
// 生成报告
export function generateReport(params) {
  return request({
    url: '/report-manage/new-report',
    method: 'post',
    data: params
  })
}
/* 报告管理*/
// 获取报告列表
export function fetchReportList(params) {
  return request({
    url: '/report-manage/get-report',
    method: 'post',
    data: params
  })
}
// 删除内容
export function delReport(id) {
  return request({
    url: '/report-manage/delete-reportdata',
    method: 'post',
    params: { id }
  })
}
// 审核
export function checkReport(params) {
  return request({
    url: 'report-manage/check-data',
    method: 'post',
    data: params
  })
}
// 归档
export function archiveReport(params) {
  return request({
    url: 'report-manage/archive-data',
    method: 'post',
    data: params
  })
}
/* 操作日志*/
// 获取日志列表
export function getLogs(params) {
  return request({
    url: '/sys-log/log-list',
    method: 'post',
    data: params
  })
}
