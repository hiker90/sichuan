<template>
  <div class="app-container">
    <div v-if="!showProcess" class="import-container">
      <split-pane split="vertical" :default-percent="20">
        <template slot="paneL">
          <div class="left-cont">
            <h4 class="panel-title">数据目录</h4>
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
              node-key="id"
              accordion
              :props="{ label: 'menuName' }"
              :expand-on-click-node="true"
              :filter-node-method="filterNode"
              style="font-size: 14px;"
              @node-click="selectNode"
            >
              <span slot-scope="{ node, data }" class="custom-tree-node">
                <span :style="{color: (data.type !== 0) ? '#409EFF' : ''}">{{ node.label }}</span>
                <span>
                  <el-button
                    v-if="data.type === 2"
                    type="text"
                    size="mini"
                    icon="el-icon-delete"
                    style="color: #F56C6C; margin-left: 5px;"
                    @click.stop="() => showDelete(node, data)"
                  />
                </span>
              </span>
            </el-tree>
          </div>
        </template>
        <template slot="paneR">
          <div class="right-cont">
            <div v-if="tableAdd === false" class="import-panel">
              <back-header title="数据导入" :show-back="false">
                <el-button size="small" icon="el-icon-notebook-2" @click="viewImportHis">历史导入任务</el-button>
              </back-header>
              <el-form :ref="'importform'" :key="'importform'" :inline="true" :model="importForm">
                <el-form-item key="tableEnName1" label="目标表">
                  <el-input v-model="importForm.tableEnName" size="small" disabled />
                </el-form-item>
                <el-form-item key="tableCnName1">
                  <el-input v-model="importForm.tableCnName" size="small" disabled />
                </el-form-item>
                <el-form-item>
                  <el-button type="warning" size="small" icon="el-icon-upload2" @click="importData">确认导入</el-button>
                </el-form-item>
              </el-form>
              <div class="upload-table">
                <upload-excel-component :on-success="handleSuccess" :before-upload="beforeUpload" />
                <div style="height: calc(100vh - 430px);">
                  <el-table
                    :data="tableData.slice((page-1)*limit,page*limit)"
                    border
                    highlight-current-row
                    height="100%"
                    style="width: 100%;margin-top:20px;"
                  >
                    <el-table-column
                      v-for="item of tableHeader"
                      :key="item"
                      :prop="item"
                      :label="item"
                    />
                  </el-table>
                  <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" />
                </div>
              </div>
            </div>
            <div v-else class="add-panel">
              <back-header title="新增数据表" @back="tableAdd = false">
                <el-button type="success" size="small" icon="el-icon-check" @click="addTable">创建</el-button>
              </back-header>
              <el-form :ref="'addform'" :key="'importform'" :inline="true" :model="addForm" :rules="addRules">
                <el-form-item key="parentId" label="上级目录" prop="parentId">
                  <el-cascader
                    v-model="addForm.parentId"
                    size="small"
                    placeholder="搜索节点"
                    :options="treeData"
                    :props="pNodeProps"
                    :show-all-levels="false"
                    disabled
                  />
                </el-form-item>
                <el-form-item key="tableCnName" label="表中文名称" prop="tableCnName">
                  <el-input
                    v-model="addForm.tableCnName"
                    size="small"
                    placeholder="请输入名称"
                    maxlength="50"
                    @change="updateCnName"
                  />
                </el-form-item>
                <el-form-item key="tableEnName" label="表名称">
                  <el-input v-model="addForm.tableEnName" size="small" disabled />
                </el-form-item>
              </el-form>
              <el-alert
                type="info"
                description="注：备注处填写表名称中文关键字，如表名称、更新频率、统计时间等信息，例：“路线概况(国道)-年报-2019”"
                :closable="false"
              />
              <h5>字段信息</h5>
              <div style="height: calc(100vh - 356px);">
                <el-table
                  :data="colList"
                  border
                  highlight-current-row
                  height="100%"
                  style="width: 100%;margin-top:20px;"
                >
                  <el-table-column label="序号" type="index" width="50" align="center" fixed="left" />
                  <!-- <el-table-column align="center" label="排序" min-width="160" fixed="left">
                    <template slot-scope="{row}">
                      <template v-if="row.edit">
                        <el-input-number
                          v-model="row.sort"
                          :min="1"
                          size="small"
                        />
                      </template>
                      <span v-else>{{ row.sort }}</span>
                    </template>
                  </el-table-column> -->
                  <el-table-column align="center" label="字段英文名称" min-width="160" fixed="left">
                    <template slot-scope="{row}">
                      <span>{{ row.colEnName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="字段中文名称" min-width="160" fixed="left">
                    <template slot-scope="{row}">
                      <template v-if="row.edit">
                        <el-input v-model="row.colCnName" class="edit-input" size="small" maxlength="100" />
                      </template>
                      <span v-else>{{ row.colCnName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="字段类型" width="140">
                    <template slot-scope="{row}">
                      <template v-if="row.edit">
                        <el-select
                          v-model="row.dataType"
                          size="small"
                          placeholder="请选择"
                          @change="data => updateColType(data,row)"
                        >
                          <el-option
                            v-for="item in typeOption"
                            :key="item.id"
                            :label="item.dataType"
                            :value="item.dataType"
                          />
                        </el-select>
                      </template>
                      <span v-else>{{ row.dataType }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="字段长度" width="160">
                    <template slot-scope="{row}">
                      <template v-if="row.edit">
                        <el-input-number
                          v-model="row.length"
                          :min="0"
                          :max="row.maxLength"
                          size="small"
                          :disabled="row.exLength !== 0"
                        />
                      </template>
                      <span v-else>{{ row.length }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="小数精度" width="160">
                    <template slot-scope="{row}">
                      <template v-if="row.edit">
                        <el-input-number
                          v-model="row.decimalLength"
                          :min="row.minDecimal"
                          :max="row.maxDecimal"
                          size="small"
                          :disabled="row.exDecimal !== 0"
                        />
                      </template>
                      <span v-else>{{ row.decimalLength }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="日期格式" width="120">
                    <template slot-scope="{row}">
                      <template v-if="row.edit">
                        <el-input
                          v-model="row.dateType"
                          class="edit-input"
                          size="small"
                          :disabled="row.dataType !== 'DATE'"
                        />
                      </template>
                      <span v-else>{{ row.dateType }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="操作" width="160" fixed="right">
                    <template slot-scope="{row, $index}">
                      <el-button
                        v-if="row.edit"
                        type="success"
                        size="mini"
                        @click="confirmEdit(row)"
                      >确认</el-button>
                      <el-button
                        v-if="!row.edit"
                        type="primary"
                        size="mini"
                        @click="editCol(row)"
                      >编辑</el-button>
                      <el-button
                        v-if="!row.edit"
                        type="danger"
                        size="mini"
                        @click="deleteRow(row, $index)"
                      >删除</el-button>
                    </template>
                  </el-table-column>
                  <el-table-column align="center" label="移动" width="100" fixed="right">
                    <template slot-scope="{$index}">
                      <i
                        v-show="$index+1 !== colList.length"
                        class="el-icon-caret-bottom"
                        size="mini"
                        style="cursor: pointer;"
                        @click="moveRow($index, true)"
                      />
                      <i
                        v-show="$index !== 0"
                        class="el-icon-caret-top"
                        size="mini"
                        style="cursor: pointer;"
                        @click="moveRow($index, false)"
                      />
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <el-alert
                v-if="dataTypeComments.length > 0"
                type="warning"
                :description="dataTypeComments"
                :closable="false"
              />
              <div class="add-btn">
                <el-button
                  size="mini"
                  icon="el-icon-plus"
                  style="width: 100%;margin-top: 5px;padding:4px 0;"
                  @click="addRow"
                >增加一行</el-button>
              </div>
            </div>
          </div>
        </template>
      </split-pane>
      <el-dialog
        title="删除"
        :visible.sync="dialogDelete"
        width="30%"
      >
        <el-form ref="form" :model="tableToDelete" label-width="80px">
          <el-form-item label="删除选项">
            <el-radio-group v-model="isDeleteAll">
              <el-radio label="0" border size="small">删除部分数据</el-radio>
              <el-radio label="1" border size="small">删除数据表</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="isDeleteAll === '0'" label="数据年份">
            <el-date-picker
              v-model="tableToDelete.year"
              size="small"
              value-format="yyyy"
              class="filter-item"
              type="year"
              placeholder="选择年份"
              style="width:200px;"
            />
          </el-form-item>
          <el-form-item v-if="isDeleteAll === '0'" label="数据月份">
            <el-select v-model="tableToDelete.month" placeholder="选择月份" size="small" style="width:200px;">
              <el-option
                v-for="item in 12"
                :key="item"
                :label="item"
                :value="item"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="dialogDelete = false">取 消</el-button>
          <el-button size="small" type="primary" @click="handleDelete">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div v-else class="process-container">
      <back-header title="历史导入任务" @back="backToImport">
        <el-button
          :loading="refreshLoading"
          size="small"
          icon="el-icon-refresh"
          @click="refreshProcess"
        >刷新状态</el-button>
      </back-header>
      <div style="height:calc(100vh - 248px);">
        <el-table
          v-loading="listLoading"
          :data="processList"
          border
          highlight-current-row
          height="100%"
          style="width: 100%;"
        >
          <el-table-column
            label="序号"
            type="index"
            :index="i => (page - 1) * limit + i + 1"
            width="50"
            align="center"
          />
          <el-table-column label="表名" prop="tableName" align="center" show-overflow-tooltip />
          <el-table-column label="导入时间" prop="updateTime" align="center" width="140">
            <template slot-scope="{row}">
              <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="状态"
            prop="status"
            :filters="statusfilter"
            :filter-method="filterHandler"
          >
            <template slot-scope="{row}">
              <span style="color: #F56C6C;">{{ row.status | transImportStatus }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="总条数" prop="totalNum" width="100" />
          <el-table-column align="center" label="成功条数" prop="successNum" width="100" />
          <el-table-column align="center" label="错误条数" prop="failNum" width="100" />
          <el-table-column
            label="错误记录"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="{row}">
              <el-button :disabled="row.status !== 1 && row.status !== 2 " size="mini" type="primary" @click="getErrorLogs(row)">下载</el-button>
              <el-button
                :disabled="row.status !== 1"
                type="success"
                size="mini"
                @click="handleError(row)"
              >处理</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="page"
        :limit.sync="limit"
        @pagination="getProcessList"
      />
    </div>
  </div>
</template>

<script>
import splitPane from 'vue-splitpane'
import UploadExcelComponent from '@/components/UploadExcel/index.vue'
import Pagination from '@/components/Pagination'
import BackHeader from '@/components/BackHeader'
import {
  importData,
  getProcessLogs,
  getImportErrors,
  handleImportError,
  getCatalogTree,
  getFieldOptions,
  addTable,
  delTable,
  delTableData,
  addCatalogs,
  getTables
} from '@/api/apis'
import { createId32, clearObj, transCNFirstWord, treeQuery, checkSameName, debonce } from '@/utils'

export default {
  name: 'DataImport',
  components: { UploadExcelComponent, Pagination, splitPane, BackHeader },
  data() {
    return {
      refreshLoading: false, // 刷新遮罩
      downloadLoading: false, // 全局遮罩
      filterText: '', // 目录模糊检索
      showProcess: false, // 历史导入任务页面
      processList: [], // 历史导入任务列表
      treeData: [], // 数据目录树
      tableOptions: [], // 所有数据库表集合
      dialogDelete: false, // 删除选项弹窗
      tableToDelete: {
        id: null,
        tabEnName: null,
        year: null,
        month: null
      }, // 待删除表信息
      isDeleteAll: '0', // 删除选项
      tableAdd: false, // 新增表页面
      addForm: {
        id: '',
        parentId: '',
        tableEnName: '',
        tableCnName: ''
      }, // 新增数据表表单
      colList: [],
      importForm: {
        id: '',
        tableEnName: '',
        tableCnName: '',
        excel: []
      }, // 数据导入表单
      tableData: [], // 导入数据预览
      tableHeader: [], // 导入数据表头
      total: 0, // 分页总条数
      page: 1, // 分页页码
      limit: 20, // 分页每页条数
      typeOption: [], // 字段类型选项列表
      dataTypeComments: '', // 字段类型填报提示
      addRules: {
        tableCnName: [
          { required: true, message: '请填写新建表名称', trigger: 'blur' },
          { max: 50, message: '长度在50个字符以内', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '请选择上级目录', trigger: 'blur' }
        ]
      }, // 新增表表单验证
      pNodeProps: {
        expandTrigger: 'hover',
        value: 'id',
        label: 'menuName',
        emitPath: false,
        checkStrictly: true
      }, // 上级目录级联选择器选项
      statusfilter: [
        {
          value: 0,
          text: '导入中'
        },
        {
          value: 1,
          text: '待处理'
        },
        {
          value: 2,
          text: '处理完成'
        },
        {
          value: 3,
          text: '成功'
        }
      ] // 历史任务 状态筛选选项
    }
  },
  computed: {
  },
  watch: {
    // 监听目录检索关键词
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getTree()
    this.getFieldOptions()
    // 刷新事件防抖
    this.refreshProcess = debonce(this.refreshProcess, 2000)
  },
  methods: {
    // 获取数据库表目录结构
    getTree() {
      getCatalogTree(0).then((res) => {
        this.treeData = res.data
      })
      getTables().then((response) => {
        this.tableOptions = response.data
      })
    },
    // 获取新建表字段属性配置项
    getFieldOptions() {
      getFieldOptions().then((res) => {
        this.typeOption = res.data
      })
    },
    // 节点选中事件
    selectNode(data, node) {
      if (data.type === 2) {
        // 选中数据表，导入功能
        clearObj(this.addForm)
        this.tableAdd = false
        this.importForm.id = data.id
        this.importForm.tableEnName = data.enName
        this.importForm.tableCnName = data.menuName
      } else if (data.type === 0) {
        // 选中目录结构，建表功能
        clearObj(this.importForm)
        this.tableHeader = []
        this.tableData = []
        this.total = 0
        this.page = 1
        this.tableAdd = true
        this.addForm.parentId = data.id
        this.addForm.id = createId32()
      } else {
        this.$message({
          message: '当前表不允许导入数据！',
          type: 'warning'
        })
      }
    },
    // 自建表删除弹窗
    showDelete(node, data) {
      clearObj(this.tableToDelete)
      this.dialogDelete = true
      this.tableToDelete.id = data.id
      this.tableToDelete.tabEnName = data.enName
    },
    // 执行删除
    handleDelete() {
      if (this.isDeleteAll === '0') {
        this.removeData()
      } else if (this.isDeleteAll === '1') {
        this.removeNode()
      }
    },
    // 删除自建表部分数据
    removeData() {
      const params = JSON.parse(JSON.stringify(this.tableToDelete))
      if (!params.year) {
        this.$message({
          message: '请填选要删除的数据时间选项！',
          type: 'warning'
        })
        return
      }
      delTableData(params).then(res => {
        this.dialogDelete = false
        this.$message({
          message: res.msg,
          type: 'success'
        })
        clearObj(this.tableToDelete)
      })
    },
    // 删除自建的数据表
    removeNode() {
      this.$confirm('此操作将永久删除此表并清空表内数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {
          id: this.tableToDelete.id,
          tableEnName: this.tableToDelete.tabEnName
        }
        delTable(params).then((res) => {
          clearObj(this.tableToDelete)
          this.dialogDelete = false
          this.$message({
            message: res.msg,
            type: 'success'
          })
          this.getTree()
        })
      })
    },
    // 导入前验证
    beforeUpload(file) {
      const isLt10M = file.size / 1024 / 1024 < 10
      if (isLt10M) {
        return true
      }
      this.$message({
        message: '上传文件大小不能超过10Mb！.',
        type: 'warning'
      })
      return false
    },
    // 读取导入数据
    handleSuccess({ results, header }) {
      this.tableData = results
      this.total = results.length
      this.tableHeader = header
    },
    // 确认导入
    importData() {
      const params = this.importForm
      if (this.tableData.length < 1) {
        this.$message({
          message: '请选择要上传的数据！',
          type: 'warning'
        })
        return
      }
      params.excel = this.tableData
      if (this.importForm.tableEnName === '' || this.importForm.tableCnName === '') {
        this.$message({
          message: '请点选目标数据表！',
          type: 'warning'
        })
      } else {
        importData(params).then((res) => {
          this.$message({
            message: res.msg,
            type: 'success'
          })
          clearObj(this.importForm)
          this.tableData = []
          this.tableHeader = []
          this.viewImportHis()
        })
      }
    },
    // 自动更新英文表名
    updateCnName(val) {
      if (val.trim() !== '') {
        const name = 'T10_' + transCNFirstWord(val)
        this.addForm.tableEnName = checkSameName(name, this.tableOptions, 'tableName')
      } else {
        this.addForm.tableEnName = ''
      }
    },
    // 新建表 增加一行
    addRow() {
      const newRow = {
        id: createId32(),
        tableId: this.addForm.id,
        colCnName: '',
        colEnName: '',
        dataType: '',
        exLength: 1,
        length: null,
        maxLength: 0,
        exDecimal: 1,
        decimalLength: null,
        maxDecimal: 0,
        minDecimal: 0,
        dateType: '',
        edit: false
      }
      this.colList.push(newRow)
    },
    // 根据数据类型选中情况 更新字段其他属性限制条件
    updateColType(data, row) {
      const {
        exLength,
        maxLength,
        exDecimal,
        maxDecimal,
        minDecimal,
        comments
      } = this.typeOption.find((val) => val.dataType === data)
      row.exLength = exLength === null ? 1 : exLength
      row.maxLength = maxLength === null ? 0 : maxLength
      row.exDecimal = exDecimal === null ? 1 : exDecimal
      row.maxDecimal = maxDecimal === null ? 0 : maxDecimal
      row.minDecimal = minDecimal === null ? 0 : minDecimal
      this.dataTypeComments = '字段类型说明：' + comments
    },
    editCol(row) {
      row.edit = !row.edit
      row.colEnName = ''
    },
    // 行编辑 确认
    confirmEdit(row) {
      let flag = true
      if (
        row.colCnName === '' ||
        row.dataType === '' ||
        (row.dataType === 'DATE' && row.dateType === '')
      ) {
        this.$message({
          message: '请将信息填写完整！',
          type: 'warning'
        })
        flag = false
        return
      }
      const enName = checkSameName(transCNFirstWord(row.colCnName), this.colList, 'colEnName')
      if (enName === 'ID' || enName === 'SYNCHRONIZE_TIME') {
        this.$message({
          message: '非法字段名称，请更改！',
          type: 'warning'
        })
        flag = false
        return
      }
      if (flag) {
        row.edit = false
        row.colEnName = enName
        row.length = row.exLength === 1 ? '' : row.length
        row.decimalLength = row.exDecimal === 1 ? '' : row.decimalLength
        this.dataTypeComments = ''
      }
    },
    // 新建表 删除行
    deleteRow(row, i) {
      this.colList.splice(i, 1)
    },
    // 创建节点
    addCatalogs(obj) {
      const params = {
        id: obj.id,
        menuName: obj.tableCnName,
        enName: obj.tableEnName,
        parentId: obj.parentId,
        type: 2,
        sort: 0
      }
      const pNode = treeQuery(this.treeData, obj.parentId)
      params.sort = pNode.children ? pNode.children[pNode.children.length - 1].sort + 1 : 0
      addCatalogs(params).then((res) => {
        this.getTree()
      })
    },
    // 新建表 创建
    addTable() {
      const params = this.addForm
      params.colList = this.colList.map((item, i) => {
        return Object.assign(item, {
          sort: i
        })
      })
      this.$refs['addform'].validate((valid) => {
        if (valid) {
          const allConfirm = params.colList.every(val => !val.edit)
          if (params.colList.length === 0 || !allConfirm) {
            this.$message({
              message: '请确认字段信息填写完整！',
              type: 'warning'
            })
          } else {
            addTable(params).then((res) => {
              this.$message({
                message: res.msg,
                type: 'success'
              })
              this.addCatalogs(params)
              clearObj(this.addForm)
              this.colList = []
            })
          }
        }
      })
    },
    // 获取导入日志
    getProcessList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit
      }
      getProcessLogs(params).then((res) => {
        this.processList = res.data.list
        this.total = res.data.total
        this.listLoading = false
        this.refreshLoading = false
      })
    },
    refreshProcess() {
      this.refreshLoading = true
      this.getProcessList()
    },
    // 查看导入日志
    viewImportHis() {
      this.showProcess = true
      this.page = 1
      this.getProcessList()
    },
    // 退出日志页面
    backToImport() {
      this.showProcess = false
      this.total = this.tableData.length
    },
    // 处理导入错误
    handleError(row) {
      const params = {
        id: row.id,
        status: 2
      }
      handleImportError(params).then((res) => {
        this.$message({
          message: res.msg,
          type: 'success'
        })
        this.getProcessList()
      })
    },
    // 获取错误信息
    getErrorLogs(row) {
      getImportErrors(row.id).then((res) => {
        const { col, dataList } = res.data
        const dataLen = dataList[0].data.split(',').length
        let len = dataLen - col.length
        while (len > 0) {
          col.push('非法字段')
          len--
        }
        const header = [...res.data.col, '错误信息']
        const data = dataList.map(item => {
          const temp = item.data.split(',')
          temp.push(item.exceptionMessage)
          return temp
        })
        this.exportExcel(row.tableName, header, data)
      })
    },
    exportExcel(title, header, list) {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = header
        const data = list
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: title + '错误日志',
          autoWidth: true,
          bookType: 'xlsx'
        })
        this.downloadLoading = false
      })
    },
    // 目录过滤方法
    filterNode(value, data) {
      if (!value) return true
      return data.menuName.indexOf(value) !== -1
    },
    // 历史任务 状态过滤
    filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    moveRow(i, isDown) {
      const temp = this.colList[i]
      if (isDown) {
        this.colList[i] = this.colList[i + 1]
        this.$set(this.colList, i + 1, temp)
      } else {
        this.colList[i] = this.colList[i - 1]
        this.$set(this.colList, i - 1, temp)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 0;
  .import-container {
    height: calc(100vh - 90px);
    .left-cont {
      height: 100%;
      margin-right: -4px;
      padding: 0 20px 20px;
      overflow-x: auto;
      overflow-y: scroll;
    }
    .right-cont {
      height: 100%;
      padding: 0 20px;
      overflow-x: auto;
      overflow-y: scroll;
    }
  }
  .process-container {
    padding: 0 20px;
  }
}
</style>
