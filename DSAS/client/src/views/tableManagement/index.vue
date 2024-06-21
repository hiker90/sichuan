<template>
  <div class="app-container">
    <split-pane v-if="!dialogCalc" split="vertical" :default-percent="20">
      <template slot="paneL">
        <div class="left-cont">
          <h4 class="panel-title">报表目录</h4>
          <el-input
            v-model="filterText"
            placeholder="输入关键字进行过滤"
            size="small"
            style="margin-top: 4px;"
          />
          <el-divider />
          <el-tree
            ref="tree"
            :data="treeData"
            node-key="tableEnName"
            default-expand-all
            :props="{ label: 'tableName' }"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            style="font-size: 14px;overflowX: hidden;"
          >
            <span slot-scope="{ node, data }" class="custom-tree-node">
              <span @click="() => selectNode(node, data)">{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
      </template>
      <template slot="paneR">
        <div class="right-cont">
          <div class="view-panel">
            <back-header :title="title" :show-back="false">
              <el-button type="success" icon="el-icon-data-analysis" size="small" @click="showCalc">计算</el-button>
            </back-header>
            <div>
              <div class="filter-btn">
                <el-button v-loading="exLoading" type="warning" icon="el-icon-download" size="small" :disabled="tableData.length === 0" @click="getFullData">导出Excel</el-button>
                <el-button class="filter-item" type="primary" icon="el-icon-search" size="small" @click="handleSearch">查询</el-button>
                <el-button class="filter-folder" type="default" :icon="isQuery ? 'el-icon-arrow-down' : 'el-icon-arrow-up'" size="small" @click="isQuery = !isQuery">高级检索</el-button>
              </div>
              <div v-if="isQuery" class="data-filter">
                <div v-for="(formItem, i) in filterForm" :key="'filter' + i" class="filter-item">
                  <template>
                    <el-select v-model="formItem.link" size="small" :disabled="i === 0" style="width: 100px;margin-right: 10px;">
                      <el-option
                        v-for="item in linkOptions"
                        :key="item"
                        :label="item"
                        :value="item"
                      />
                    </el-select>
                    <el-select v-model="formItem.param" placeholder="请选择字段" size="small" style="width: 300px;margin-right: 10px;">
                      <el-option
                        v-for="item in fieldsList"
                        :key="item.value"
                        :label="item.colCnName"
                        :value="item.colEnName"
                      />
                    </el-select>
                    <el-input v-if="checkType(formItem.param) === 'str'" v-model="formItem.val" placeholder="请输入检索条件" size="small" style="width: 300px;margin-right: 10px;" />
                    <el-input v-if="checkType(formItem.param) === 'num'" v-model="formItem.min" placeholder="请输入最小值" size="small" style="width: 195px;margin-right: 10px;" />
                    <el-input v-if="checkType(formItem.param) === 'num'" v-model="formItem.max" placeholder="请输入最大值" size="small" style="width: 195px;margin-right: 10px;" />
                    <el-date-picker
                      v-if="checkType(formItem.param) === 'date'"
                      v-model="formItem.date"
                      size="small"
                      type="datetimerange"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      :clearable="true"
                      style="width: 300px;margin-right: 10px;"
                    />
                    <el-button v-if="i !== 0" type="primary" plain icon="el-icon-minus" size="small" @click="handleParamdelete(i)" />
                    <el-button v-if="i === filterForm.length - 1" type="primary" plain icon="el-icon-plus" size="small" @click="handleParamAdd" />
                  </template>
                </div>
              </div>
            </div>
            <div style="height:calc(100vh - 290px);">
              <el-table
                id="ex-table"
                ref="table"
                v-loading="listLoading"
                :data="tableData"
                border
                fit
                highlight-current-row
                height="100%"
                style="width: 100%;"
              >
                <template v-for="(item, i) in tableHeader">
                  <el-table-column :key="i" :label="item.colCnName" :prop="item.colEnName" :fixed="i === 0" align="center" :min-width="calcTableWidth(item.colCnName)">
                    <template slot-scope="scope">
                      <span :class="judgeNormalData(item.colEnName, scope.row[item.colEnName], warnRules)">
                        {{ transTableDataView(scope.row[item.colEnName], tableViewRules, item.colEnName) }}
                      </span>
                    </template>
                  </el-table-column>
                </template>
              </el-table>
            </div>
            <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getData" />
          </div>
        </div>
      </template>
    </split-pane>
    <div v-else class="calc-container">
      <back-header title="报表计算" @back="dialogCalc = false" />
      <el-alert
        title="请选择报表对应的计算时间并点击“执行计算”按钮。"
        type="warning"
        closable
        show-icon
        style="margin-bottom: 10px;"
      />
      <div style="height:calc(100vh - 216px);">
        <el-table
          v-loading="listLoading"
          :data="calcParamsTable"
          border
          highlight-current-row
          height="100%"
          style="width: 100%;"
        >
          <el-table-column label="表名" prop="tableName" align="center" show-overflow-tooltip />
          <el-table-column label="年份" prop="year" align="center" width="150">
            <template slot-scope="{row}">
              <el-date-picker
                v-model="row.year"
                size="small"
                value-format="yyyy"
                class="filter-item"
                type="year"
                placeholder="选择年份"
                style="width:120px;"
              />
            </template>
          </el-table-column>
          <el-table-column label="季度" prop="quarter" align="center" width="140">
            <template slot-scope="{row}">
              <el-select v-model="row.quarter" placeholder="选择季度" size="small" :disabled="row.date !== 1">
                <el-option
                  v-for="item in 4"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="月份" prop="month" align="center" width="140">
            <template slot-scope="{row}">
              <el-select v-model="row.month" placeholder="选择月份" size="small" :disabled="row.date !== 0">
                <el-option
                  v-for="item in 12"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" prop="month" align="center" width="100">
            <template slot-scope="{row}">
              <el-button size="small" type="success" @click="handleCalc(row)">
                计算
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import splitPane from 'vue-splitpane'
import { getReportList, handleCalc, getReportCols, getReport } from '@/api/apis'
import BackHeader from '@/components/BackHeader'
import { judgeNormalData, transTableDataView } from '@/filters'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'TableManagement',
  components: { splitPane, Pagination, BackHeader },
  data() {
    return {
      listLoading: false, // 表格遮罩
      exLoading: false, // 导出遮罩
      filterText: '', // 目录筛选关键字
      treeData: [], // 目录
      title: '报表管理', // 报表title
      tabName: '', // 报表英文名称
      isQuery: false, // 是否高级检索
      filterForm: [
        {
          param: '',
          val: '',
          min: '',
          max: '',
          date: null,
          link: ''
        }
      ], // 高级检索表单
      linkOptions: ['and', 'or'],
      queryParams: null, // 上次高级检索条件缓存
      fieldsList: [], // 字段信息
      tableHeader: [], // 表头
      tableData: [], // 表格数据
      warnRules: null, // 预警规则
      tableViewRules: null, // 表格显示规则
      total: 0, // 首页分页 总条数
      page: 1, // 首页分页 页码
      limit: 20, // 每页条数
      dialogCalc: false, // 是否计算页面
      calcParamsTable: [] // 计算执行参数表单
    }
  },
  watch: {
    // 监听目录检索关键词
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getTree()
  },
  methods: {
    // 数据表格宽度调整
    calcTableWidth(label) {
      const width = label && label.length * 14 + 24
      return width > 100 ? (width < 300 ? width : 300) : 100
    },
    // 预警高亮方法
    judgeNormalData,
    // 转义表格显示格式，例如，加%
    transTableDataView,
    // 获取目录树
    getTree() {
      getReportList().then((res) => {
        this.treeData = res.data
      })
    },
    // 节点点击事件
    selectNode(node, data) {
      if (this.tabName === data.tableEnName) return
      this.filterForm = this.$options.data().filterForm
      this.isQuery = false
      this.tabName = data.tableEnName
      this.title = data.tableName
      this.tableHeader = []
      this.tableData = []
      this.getColList()
    },
    // 判断高级检索字段类型
    checkType(name) {
      if (!name) return 'str'
      const { dataType } = this.fieldsList.find(val => val.colEnName === name)
      const type = dataType.split('(')[0].toUpperCase()
      switch (type) {
        case 'DATE':
        case 'DATETIME':
        case 'TIMESTAMP':
          return 'date'
        case 'CHAR':
        case 'VARCHAR':
        case 'BLOB':
          return 'str'
        case 'DOUBLE':
        case 'FLOAT':
        case 'BIGINT':
        case 'INT':
        case 'DECIMAL':
        case 'INTEGER':
          return 'num'
        default:
          return null
      }
    },
    // 数据检索条件增加按钮
    handleParamAdd() {
      this.filterForm.push({
        param: '',
        val: '',
        min: '',
        max: '',
        date: null,
        link: 'and'
      })
    },
    // 数据检索条件删减按钮
    handleParamdelete(i) {
      this.filterForm.splice(i, 1)
    },
    // 查询
    handleSearch() {
      if (this.tabName === '') {
        this.$message({
          type: 'warning',
          message: '请点选报表名称'
        })
      } else {
        this.getData()
      }
    },
    // 获取表字段信息
    getColList() {
      // 匹配预警规则
      const tableEnName = this.tabName
      import('./rules.js').then(data => {
        this.warnRules = data.warnRules[tableEnName] || null
        this.tableViewRules = data.tableViewRules[tableEnName] || null
      })
      getReportCols(tableEnName).then(res => {
        const colList = res.data.filter(val => val.colEnName !== 'ID' && val.colEnName !== 'SYNCHRONIZE_TIME')
        this.fieldsList = JSON.parse(JSON.stringify(colList))
        this.tableHeader = JSON.parse(JSON.stringify(colList))
        this.$nextTick(() => {
          this.$refs.table.doLayout()
        })
      })
    },
    // 获取输出表数据
    getData() {
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {
          tabName: this.tabName
        }
      }
      if (this.isQuery) {
        // 组装查询参数
        const param = this.generateParams()
        if (param) {
          this.queryParams = param
          params.queryParams.params = param
        } else {
          this.$message({
            message: '检索条件请填写完整',
            type: 'warning'
          })
          return false
        }
      } else {
        this.queryParams = null
      }
      this.listLoading = true
      getReport(params).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      })
    },
    // 组装高级查询参数
    generateParams() {
      const form = this.filterForm
      const numReg = /^[0-9]+(\.[0-9]+)?$/
      const valid = form.some(val => {
        if (val.param === null || val.param === '') {
          return true
        } else {
          const type = this.checkType(val.param)
          switch (type) {
            case 'str':
              if (val.val === '') return true
              break
            case 'num':
              if (val.min === '' && val.max === '') {
                return true
              } else if (val.min !== '' && !numReg.test(val.min)) {
                return true
              } else if (val.max !== '' && !numReg.test(val.max)) {
                return true
              }
              break
            case 'date':
              if (val.date === null) return true
              break
          }
        }
      })
      if (valid) return false
      return form.map(val => {
        const temp = {
          colName: val.param,
          expression: '',
          value: '',
          link: val.link
        }
        const type = this.checkType(val.param)
        switch (type) {
          case 'str':
            temp.value = val.val
            temp.expression = 'like'
            break
          case 'num':
            if (val.min !== '' && val.max !== '') {
              temp.value = val.min + ' and ' + val.max
              temp.expression = 'between'
            } else {
              if (val.min === '') {
                temp.value = val.max
                temp.expression = '<='
              } else if (val.max === '') {
                temp.value = val.min
                temp.expression = '>='
              }
            }
            break
          case 'date':
            temp.value = `TO_DATE('${val.date[0]}','YYYY-MM-DD HH24:MI:SS') and TO_DATE('${val.date[1]}','YYYY-MM-DD HH24:MI:SS')`
            temp.expression = 'between'
            break
        }
        return temp
      })
    },
    // 获取全部数据 用于导出
    getFullData(row) {
      const params = {
        pageIndex: 1,
        pageSize: 0,
        queryParams: {
          tabName: this.tabName
        }
      }
      if (this.queryParams !== null) {
        params.queryParams.params = this.queryParams
      }
      getReport(params).then((res) => {
        const dataList = res.data.list
        const header = this.fieldsList.map(k => k.colEnName)
        const cnHeader = this.fieldsList.map(k => k.colCnName || k.colEnName)
        const data = dataList.map(item => {
          const temp = []
          header.forEach(val => {
            const data = item[val] || null
            temp.push(data)
          })
          return temp
        })
        this.exportExcel(this.title, cnHeader, data)
      })
    },
    exportExcel(title, header, list) {
      this.exLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = header
        const data = list
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: title,
          autoWidth: true,
          bookType: 'xlsx'
        })
        this.exLoading = false
      })
    },
    // 计算按钮
    showCalc() {
      this.dialogCalc = true
      this.calcParamsTable = JSON.parse(JSON.stringify(this.treeData))
    },
    // 执行计算
    handleCalc(row) {
      const param = [row]
      const valid = param && param.every(val => {
        const { date, year, quarter, month } = val
        if (date === 1) {
          return !!year && !!quarter
        } else if (date === 0) {
          return !!year && !!month
        } else {
          return !!year
        }
      })
      // 检查参数完整性
      if (!valid) {
        this.$message({
          type: 'warning',
          message: '请确认是否正确填选参数！'
        })
      } else {
        handleCalc(param).then(res => {
          if (res && res.msg) {
            this.$message({
              type: 'success',
              message: res.msg
            })
            this.dialogCalc = false
            this.selectNode(null, ...param)
            this.isQuery = false
            this.$nextTick(() => {
              this.handleSearch()
            })
          }
        })
      }
    },
    // 目录过滤方法
    filterNode(value, data) {
      if (!value) return true
      return data.tableName.indexOf(value) !== -1
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 0;
  height: calc(100vh - 90px);
  .left-cont {
    height: 100%;
    margin-right: -4px;
    padding: 0 20px 20px;
    overflow-x: auto;
    overflow-y: scroll;
    h4{
      text-indent: .5em;
      border-left: 4px solid #63C9C7;
    }
  }
  .right-cont {
    height: 100%;
    padding: 0 20px;
    overflow-x: auto;
    overflow-y: scroll;
    .filter-btn {
      text-align: right;
      .filter-folder {
        float: left;
        margin-left: 0;
      }
    }
    .data-filter {
      position: relative;
      border-left: 4px solid #63C9C7;
      padding-left: 10px;
      margin-bottom: 10px;
    }
    .filter-item {
      margin-bottom: 10px;
    }
    .high-light {
      color: #F56C6C;
      font-weight: bold;
    }
  }

  .calc-container {
    padding: 0 20px;
    overflow: hidden;
  }
}
</style>
