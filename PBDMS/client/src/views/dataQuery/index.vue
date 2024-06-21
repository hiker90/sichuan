<template>
  <div class="query-container" v-loading="bodyLoading">
    <div v-if="!showDetail" class="search-container">
      <el-input v-model="queryText" placeholder="请输入检索关键字" class="search-bar" clearable @keyup.enter.native="handleSearch">
        <!-- <el-button slot="prepend" class="green-bg" :icon="showAddition ? 'el-icon-arrow-down' : 'el-icon-arrow-right'" @click="showAddition = !showAddition">高级搜索</el-button> -->
        <el-button slot="append" class="green-bg" icon="el-icon-search" @click="handleSearch" />
      </el-input>
      <!-- <div v-if="showAddition" class="addition-filter">
        <el-form ref="form" :inline="true" :model="form">
          <el-form-item label="领域">
            <el-cascader
              v-model="form.unit"
              size="small"
              placeholder="搜索节点"
              :options="treeData"
              :props="{ expandTrigger: 'hover' }"
              filterable
              clearable
              :show-all-levels="false"
            />
          </el-form-item>
        </el-form>
      </div> -->
    </div>
    <div v-if="!showDetail" class="result-list">
      <split-pane split="vertical" :default-percent="20">
        <template slot="paneL">
          <div class="left-cont">
            <el-input
              v-model="filterText"
              placeholder="输入关键字进行过滤"
              size="small"
              clearable
              style="margin-top: 4px;"
            />
            <el-divider />
            <el-tree
              ref="tree"
              :data="treeData"
              :default-expanded-keys="expandedList"
              node-key="id"
              accordion
              :props="{ label: 'menuName' }"
              :expand-on-click-node="true"
              :filter-node-method="filterNode"
              style="font-size: 14px;"
              @node-click="selectNode"
              @node-expand="treeExpand"
              @node-collapse="treecollapse"
            >
              <span slot-scope="{ node, data }" class="custom-tree-node">
                <span :style="{color: (data.type !== 0) ? '#409EFF' : ''}">{{ node.label }}</span>
              </span>
            </el-tree>
          </div>
        </template>
        <template slot="paneR">
          <div class="right-cont">
            <el-table
              v-loading="listLoading"
              :data="list"
              :show-header="false"
              highlight-current-row
              style="width: 100%;"
            >
              <el-table-column align="center">
                <template slot-scope="{row,$index}">
                  <div class="list-left-cont">
                    <h4 class="list-title">
                      <span class="list-index">{{ (page - 1) * limit + $index + 1 }}</span>{{ row.comments }}
                    </h4>
                    <p>表名称：{{ row.tableName }}</p>
                    <!-- <div class="list-keywords">
                      <el-tag v-for="(item, i) in row.keywords" :key="i" size="mini">{{ item }}</el-tag>
                    </div>
                    <el-tooltip class="list-desc" effect="dark" :content="row.desc" placement="top-start">
                      <p>描述：{{ row.desc }}</p>
                    </el-tooltip> -->
                  </div>
                  <div class="list-right-cont">
                    <el-button type="primary" size="mini" @click="getDetail(row)">查看详情</el-button>
                    <!-- <p>{{ row.unit }}</p>
                    <p>{{ row.time }}</p> -->
                  </div>
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
          </div>
        </template>
      </split-pane>
    </div>
    <div v-if="showDetail" class="detail-container">
      <back-header :title="curTable" @back="back">
        <el-button v-show="activeTab === 'data'" class="export-btn" type="warning" size="small" :disabled="dataList.length === 0" icon="el-icon-download" @click="getFullData">导出Excel</el-button>
      </back-header>
      <el-tabs v-model="activeTab" class="detail-tab" @tab-click="handleTabClick">
        <el-tab-pane label="字段信息" name="fields">
          <div style="height:calc(100vh - 226px);">
            <el-table
              ref="colTable"
              v-loading="listLoading"
              :data="fieldsList"
              border
              highlight-current-row
              height="100%"
              style="width: 100%;"
            >
              <el-table-column
                type="index"
                label="序号"
                width="50"
                align="center"
              />
              <el-table-column prop="colEnName" label="字段英文名称" align="center" />
              <el-table-column prop="colCnName" label="字段中文名称" align="center" />
              <el-table-column prop="dataType" label="字段类型" align="center" />
              <el-table-column prop="length" label="长度" align="center" />
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane label="数据信息" style="padding-top: 45px;" name="data">
          <el-button class="filter-folder" type="default" :icon="isQuery ? 'el-icon-arrow-down' : 'el-icon-arrow-up'" size="small" @click="isQuery = !isQuery">高级检索</el-button>
          <el-button class="search-btn" type="primary" icon="el-icon-search" size="small" @click="searchData">查询</el-button>
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
                    v-for="item in shortFieldsList"
                    :key="item.value"
                    :label="item.colCnName"
                    :value="item.colEnName"
                  />
                </el-select>
                <el-input v-if="checkType(formItem.param) === 'str'" v-model="formItem.val" placeholder="请输入检索条件" size="small" style="width: 400px;margin-right: 10px;" />
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
                  style="width: 400px;margin-right: 10px;"
                />
                <el-button v-if="i !== 0" type="primary" plain icon="el-icon-minus" size="small" @click="handleParamdelete(i)" />
                <el-button v-if="i === filterForm.length - 1" type="primary" plain icon="el-icon-plus" size="small" @click="handleParamAdd" />
              </template>
            </div>
          </div>
          <div :class="{ 'table-wrapper': total1>0, 'no-data-table': !total1 }">
            <el-table
              ref="dataTable"
              v-loading="listLoading"
              :data="dataList"
              border
              highlight-current-row
              height="100%"
              style="width: 100%;"
            >
              <el-table-column
                fixed
                type="index"
                label="序号"
                :index="i => (page1 - 1) * limit + i + 1"
                width="50"
                align="center"
              />
              <template v-for="item in shortFieldsList">
                <el-table-column v-if="!item.colCnName" :key="item.colEnName" :prop="item.colEnName" :label="item.colEnName" align="center" :min-width="calcTableWidth(item.colEnName)">
                  <!-- <template slot="header" slot-scope="scope">
                    <el-tooltip effect="dark" content="" placement="top">
                      <span>良好率</span>
                    </el-tooltip>
                  </template> -->
                </el-table-column>
                <el-table-column v-else :key="item.colEnName" :prop="item.colEnName" align="center" :min-width="calcTableWidth(item.colCnName)">
                  <template slot="header">
                    <el-tooltip effect="dark" :content="colFullName(item.colEnName)" placement="top">
                      <span>{{ item.colCnName }}</span>
                    </el-tooltip>
                  </template>
                </el-table-column>
              </template>
            </el-table>
          </div>
          <pagination v-show="total1>0" :total="total1" :page.sync="page1" :limit.sync="limit" @pagination="getData" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import splitPane from 'vue-splitpane'
import { getCatalogTree, queryTable, queryCols, queryData } from '@/api/apis'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import BackHeader from '@/components/BackHeader'

export default {
  name: 'DataQuery',
  components: { splitPane, Pagination, BackHeader },
  data() {
    return {
      queryText: '', // 查询关键字
      showAddition: false, // 高级搜索显示
      listLoading: false, // 表格加载遮罩
      filterText: '', // 目录模糊检索
      treeData: [], // 目录树数据
      expandedList: [], // 缓存展开的节点
      list: [], // 搜索结果
      total: 0, // 首页分页 总条数
      page: 1, // 首页分页 页码
      limit: 20, // 每页条数
      curTable: '', // 当前表名
      curTableName: '', // 当前表名英文名
      showDetail: false, // 详情页显示
      fieldsList: [], // 字段信息
      dataList: [], // 数据信息
      total1: 0, // 数据信息 总条数
      page1: 1, // 数据信息 页码
      activeTab: 'data', // 详情页 tab
      bodyLoading: false, // 导出遮罩
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
      queryParams: null // 上次高级检索条件缓存
    }
  },
  computed: {
    // 处理字段过长问题，截取括号之前的名称
    shortFieldsList() {
      const temp = JSON.parse(JSON.stringify(this.fieldsList))
      if (temp === 'null' && temp === 'undefined') return temp
      return temp.map(val => {
        if (val.colCnName && (val.colCnName.indexOf('（') > -1)) {
          return Object.assign(val, {
            colCnName: val.colCnName.split('（')[0]
          })
        } else if (val.colCnName && (val.colCnName.indexOf('(') > -1)) {
          return Object.assign(val, {
            colCnName: val.colCnName.split('(')[0]
          })
        } else {
          return val
        }
      })
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
    this.getList()
  },
  methods: {
    // 数据详情表格宽度调整
    calcTableWidth(label) {
      const width = label && label.length * 14 + 24
      // return width > 100 ? (width < 300 ? width : 300) : 100
      return width
    },
    // 获取表头全称，用于tooltips
    colFullName(key) {
      const item = this.fieldsList.find(val => val.colEnName === key)
      return item && item.colCnName
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
        case 'NVARCHAR2':
        case 'VARCHAR2':
        case 'CLOB':
        case 'BLOB':
        case 'RAW':
          return 'str'
        case 'LONG':
        case 'FLOAT':
        case 'NUMBER':
        case 'INT':
        case 'DECIMAL':
        case 'INTEGER':
          return 'num'
        default:
          return null
      }
    },
    // 获取目录树
    getTree() {
      getCatalogTree(1).then((res) => {
        this.treeData = res.data
      })
    },
    // 获取表集合
    getList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {
          tableQueryParam: this.queryText.trim()
        }
      }
      queryTable(params).then((response) => {
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
      })
    },
    // 节点点击事件
    selectNode(data) {
      if (data.type !== 0) {
        const { menuName: comments, enName: tableName } = data
        this.getDetail({ comments, tableName })
      }
    },
    // 搜索
    handleSearch() {
      this.page = 1
      this.getList()
    },
    // tab点击事件
    handleTabClick() {
      this.$nextTick(() => {
        if (this.activeTab === 'data') {
          this.$refs.dataTable.doLayout()
        } else {
          this.$refs.colTable.doLayout()
        }
      })
    },
    // 获取表详情
    getDetail(row) {
      this.curTable = row.comments
      this.showDetail = true
      this.curTableName = row.tableName
      queryCols(row.tableName).then((res) => {
        this.fieldsList = res.data.filter(val => val.colEnName !== 'ID' && val.colEnName !== 'SYNCHRONIZE_TIME')
      })
      this.getData()
    },
    // 查询按钮
    searchData() {
      this.page1 = 1
      this.getData()
    },
    // 获取表数据
    getData() {
      const params = {
        pageIndex: this.page1,
        pageSize: this.limit,
        queryParams: {
          tabName: this.curTableName
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
            message: '请正确填写检索条件，并确认填写完整！',
            type: 'warning'
          })
          return false
        }
      } else {
        this.queryParams = null
      }
      this.listLoading = true
      queryData(params).then((res) => {
        this.dataList = res.data.list
        this.total1 = res.data.total
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
      return form.map((val, i, arr) => {
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
    // 返回搜索页
    back() {
      this.isQuery = false
      this.filterForm = [
        {
          param: '',
          val: '',
          min: '',
          max: '',
          date: null
        }
      ]
      this.curTable = ''
      this.curTableName = ''
      this.showDetail = false
      this.fieldsList = []
      this.dataList = []
      this.page1 = 1
      this.total1 = 0
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
    // 获取全部数据 用于导出
    getFullData(row) {
      if (this.total1 > 10000) {
        this.$message({
          message: '数据量过大，请联系管理员！',
          type: 'warning'
        })
        return false
      }
      this.bodyLoading = true
      const params = {
        pageIndex: 1,
        pageSize: 0,
        queryParams: {
          tabName: this.curTableName
        }
      }
      if (this.queryParams !== null) {
        params.queryParams.params = this.queryParams
      }
      queryData(params).then((res) => {
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
        this.exportExcel(this.curTable, cnHeader, data)
      }).finally(() => {
        this.bodyLoading = false
      })
    },
    exportExcel(title, header, list) {
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
      })
    },
    // 目录过滤方法
    filterNode(value, data) {
      if (!value) return true
      return data.menuName.indexOf(value) !== -1
    },
    // 树节点展开事件
    treeExpand(data) {
      this.expandedList.push(data.id)
    },
    // 树节点闭合事件
    treecollapse(data) {
      this.expandedList.splice(this.expandedList.indexOf(data.id), 1)
    }
  }
}
</script>

<style lang="scss" scoped>
.query {
  &-container {
    overflow: hidden;
    .search-container {
      position: relative;
      z-index: 100;
      padding: 10px 0;
      // margin-bottom: 4px;
      background: #fff;
      text-align: center;
      // border-bottom: 1px solid #ddd;
      box-shadow: 0 1px 4px rgba(0,21,41,.08);
      .search-bar, .addition-filter{
        max-width: 1000px;
      }
      .addition-filter{
        text-align: left;
        margin: 10px auto 0;
        .el-form-item{
          margin-bottom: 0;
        }
      }
    }
    .green-bg {
      background-color: #63c9c6;
      border: none;
      border-radius: 0;
      color: #fff;
    }
    .result-list {
      height: calc(100vh - 151px);
      p {
        line-height: 26px;
      }
      .left-cont {
        height: 100%;
        margin-right: -4px;
        padding: 20px 20px 20px;
        overflow-x: auto;
        overflow-y: scroll;
      }
      .right-cont {
        height: 100%;
        padding: 0 20px 20px;
        overflow-x: auto;
        overflow-y: scroll;
      }
      .list-left-cont {
        display: inline-block;
        width: 70%;
        text-align: left;
        padding-left: 10px;
        .list-title {
          font-size: 16px;
          border: none;
          text-indent: 0;
          .list-index {
            display: inline-block;
            background: #e6a23c;
            color: #fff;
            min-width: 22px;
            height: 22px;
            text-align: center;
            margin-right: 10px;
          }
        }
        .el-tag{
          margin-right: 5px;
        }
        .list-desc{
          height: 26px;
          line-height: 26px;
          overflow: hidden;
          transform: translateY(8px);
        }
      }
      .list-right-cont {
        display: inline-block;
        vertical-align: top;
        width: 30%;
        text-align: right;
        padding: 40px 10px 0 0;
        p {
          margin-right: 4px;
        }
      }
    }
    .detail-container{
      padding: 0 20px;
      position: relative;

      .filter-folder {
        position: absolute;
        top: 0;
        left: 0;
        // padding: 0;
      }
      .search-btn {
        position: absolute;
        right: 0;
        top: 0;
      }
      .filter-item {
        margin-bottom: 10px;
      }
      .data-filter {
        position: relative;
        border-left: 4px solid #63C9C7;
        padding-left: 10px;
      }
      .table-wrapper{
        height: calc(100vh - 346px);
      }
      .no-data-table{
        height: 120px;
      }
    }
  }
}
</style>
