export { parseTime, formatTime } from '@/utils'

// 转义预警信息处理状态
export function transHandleResult(num) {
  if (num === undefined || num === null) return '-'
  const dict = ['待处理', '已处理']
  return dict[num]
}

// 转义预警信息处理状态
export function transWarningType(num) {
  if (num === undefined || num === null) return '-'
  const dict = ['接口', '数据表']
  return dict[num - 1]
}

// 转义预警规则
export function transRules(num) {
  if (num === undefined || num === null) return '-'
  const dict = ['检查字段完整性']
  return dict[num - 1]
}

// 转义导入日志状态
export function transImportStatus(num) {
  if (num === undefined || num === null) return '-'
  const dict = ['导入中', '待处理', '处理完成', '成功']
  return dict[num]
}

// 转义共享审核状态
export function transShareCheckStatus(num) {
  if (num === undefined || num === null) return '-'
  const dict = ['待审核', '已通过', '已驳回']
  return dict[num - 1]
}

// 转义日志操作
export function transOperation(op) {
  if (op === undefined || op === null) return '-'
  const dict = ['其它', '插入', '删除', '修改', '查询', '审核']
  return dict[op]
}
