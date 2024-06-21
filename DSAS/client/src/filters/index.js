export { parseTime, formatTime } from '@/utils'

// 转义预警信息处理状态
export function judgeNormalData(key, data, rules) {
  if (!rules) return
  if (data === undefined || data === null || data === '') return data
  const rule = rules.find(val => val.colEnName === key)
  if (!rule) {
    return
  } else {
    const { min, max } = rule
    if (data > max || data < min) {
      return 'high-light'
    }
  }
}
// 转义表格显示格式，例如，加%
export function transTableDataView(val, rule, colName) {
  if (!rule) return val
  if (val === null || val === undefined) return '--'
  switch (rule[colName]) {
    case 'percentage':
      val = Math.round(val * 10000) / 100.00 + '%'
      break
    default:
      break
  }
  return val
}
// 转义报告状态
export function transReportStatus(op) {
  if (op === undefined || op === null) return '-'
  const dict = ['审核中', '审核通过', '审核驳回', '已归档']
  return dict[op - 1]
}
// 转义日志操作
export function transOperation(op) {
  if (op === undefined || op === null) return '-'
  const dict = ['其它', '插入', '删除', '修改', '查询', '审核', '计算']
  return dict[op]
}
