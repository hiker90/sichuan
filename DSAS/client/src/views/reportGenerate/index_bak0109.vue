<template>
  <div class="app-container">
    <div v-if="!editView" class="table-view">
      <div class="filter-container">
        <el-form ref="form" :inline="true" :model="form">
          <el-form-item label="拟稿时间">
            <el-date-picker
              v-model="form.time"
              size="small"
              class="filter-item"
              type="daterange"
              range-separator="至"
              value-format="yyyy-MM-dd"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-form-item>
          <el-form-item label="模板名称">
            <el-input
              v-model="form.name"
              placeholder="输入模板名称"
              size="small"
              clearable
            />
          </el-form-item>
          <div class="filter-btn">
            <el-button class="filter-item" type="primary" icon="el-icon-search" size="small" @click="handleSearch">查询</el-button>
            <el-button class="filter-item" type="success" icon="el-icon-plus" size="small" @click="editView = true">新增模板</el-button>
          </div>
        </el-form>
      </div>
      <div style="height:calc(100vh - 268px)">
        <el-table
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          height="100%"
          style="width: 100%;"
        >
          <el-table-column label="序号" type="index" :index="i => (page - 1) * limit + i + 1" width="80" align="center" />
          <el-table-column label="模板名称" prop="modelName" align="center" show-overflow-tooltip />
          <el-table-column label="拟稿人" prop="draftUser" align="center" width="80" />
          <el-table-column label="拟稿单位" prop="draftUnit" align="center" show-overflow-tooltip />
          <el-table-column label="拟稿时间" prop="createTime" align="center" width="150">
            <template slot-scope="{row}">
              <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="审核人" prop="checkUser" align="center" />
          <el-table-column label="审核时间" prop="checkDate" align="center" />
          <el-table-column label="审核单位" prop="checkUnit" align="center" /> -->
          <el-table-column label="版本号" prop="version" align="center" width="130" />
          <!-- <el-table-column label="报告状态" prop="reportStatus" align="center" /> -->
          <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
            <template slot-scope="{row}">
              <el-button size="mini" type="primary" @click="view(row)">
                编辑
              </el-button>
              <el-button :disabled="row.reportStatus !== 0" size="mini" type="success" @click="generate(row)">
                生成
              </el-button>
              <el-button size="mini" type="danger" @click="handleDel(row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
    </div>
    <div v-else class="moudle-view">
      <back-header title="模板编辑" @back="back">
        <el-button type="warning" size="small" icon="el-icon-check" @click="saveMoudle">保存</el-button>
      </back-header>
      <div class="filter-container">
        <el-form ref="moudleForm" :key="'moudleForm'" :inline="true" :model="moudleForm" :rules="addRules">
          <el-form-item label="模板名称" prop="modelName">
            <el-input
              v-model="moudleForm.modelName"
              placeholder="输入模板名称"
              size="small"
              style="width: 400px;"
            />
          </el-form-item>
          <el-form-item label="版本号" prop="version">
            <el-input
              v-model="moudleForm.version"
              placeholder="例：v 1.0.0"
              size="small"
            />
          </el-form-item>
        </el-form>
      </div>
      <split-pane split="vertical" :default-percent="20">
        <template slot="paneL">
          <div class="params-bar">
            <h5 class="params-title">报告指标</h5>
            <i class="el-icon-d-arrow-right arrow" />
            <div class="params-con">
              <el-button v-for="item in params" :key="item" size="mini" type="primary" plain @click="addParam(item)">{{ item }}</el-button>
            </div>
          </div>
        </template>
        <template slot="paneR">
          <div class="edit-con">
            <editor ref="editor" v-model="detail" :is-clear="isClear" @change="change" />
          </div>
        </template>
      </split-pane>
    </div>
    <el-dialog :visible.sync="viewDialog" :show-close="false" append-to-body fullscreen>
      <div class="html-wrapper">
        <div class="html-content" v-html="detail" />
      </div>
    </el-dialog>
    <!-- <el-dialog :visible.sync="generateDialog" title="生成报告" width="600px" append-to-body>
      <el-form ref="generateForm" :model="generateForm" label-width="90px">
        <el-form-item label="报告类型">
          <el-radio-group v-model="generateForm.type" size="small">
            <el-radio label="1">年报</el-radio>
            <el-radio label="2">季报</el-radio>
            <el-radio label="3">月报</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="统计范围">
          <el-date-picker
            v-if="generateForm.type !== '3'"
            v-model="generateForm.year"
            size="small"
            type="year"
            value-format="yyyy"
            placeholder="选择年份"
            style="width: 200px;"
          />
          <el-date-picker
            v-if="generateForm.type === '3'"
            v-model="generateForm.month"
            size="small"
            type="month"
            value-format="yyyyM"
            placeholder="选择月份"
            style="width: 200px"
          />
          <el-select v-if="generateForm.type === '2'" v-model="generateForm.season" size="small" placeholder="选择季度" style="width: 200px;margin-left: 10px;">
            <el-option
              v-for="item in seasonOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="指定审核人">
          <el-select v-model="generateForm.checkUser" size="small" placeholder="选择审核人">
            <el-option
              v-for="item in userList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-alert
          v-if="reportTitle !== ''"
          :title="reportTitle"
          type="success"
          :closable="false"
        />
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="generateDialog = false">取 消</el-button>
        <el-button size="small" type="primary" @click="generate">生 成</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>

<script>
import splitPane from 'vue-splitpane'
import Pagination from '@/components/Pagination'
import BackHeader from '@/components/BackHeader'
import Editor from './Editor'
import { getModelList, getModel, delModel, addModel, updateModel, generateReport } from '@/api/apis'
import { clearObj } from '@/utils'

export default {
  name: 'ReportGenerate',
  components: {
    Pagination,
    BackHeader,
    Editor,
    splitPane
  },
  data() {
    return {
      viewDialog: false, // 预览弹窗
      form: {
        time: null,
        name: ''
      }, // 查询表单
      listLoading: false, // 表格遮罩
      list: [], // 表格数据
      total: 0, // 分页 总数
      page: 1, // 分页 页码
      limit: 20, // 分页 每页条数
      editView: false, // 新增 编辑页面
      isEdit: false, // 区分新增 和 编辑
      curModelId: null, // 当前编辑模板id
      isClear: false,
      detail: '', // 模板html
      moudleForm: {
        modelName: '',
        version: ''
      }, // 新增表单
      params: [
        `%建设投资完成额%`,
        `%建设投资完成同比%`,
        `%建设投资计划额%`,
        `%建设投资完成占比%`,
        `%高速公路完成额%`,
        `%高速公路完成同比%`,
        `%高速公路计划额%`,
        `%高速公路完成占比%`,
        `%国省干线完成额%`,
        `%国省干线完成同比%`,
        `%国省干线计划额%`,
        `%国省干线完成占比%`,
        `%农村公路完成额%`,
        `%农村公路完成同比%`,
        `%农村公路计划额%`,
        `%农村公路完成占比%`,
        `%内河水运完成额%`,
        `%内河水运完成同比%`,
        `%内河水运计划额%`,
        `%内河水运完成占比%`,
        `%站点建设完成额%`,
        `%站点建设完成同比%`,
        `%站点建设计划额%`,
        `%站点建设完成占比%`,
        `%养护其他完成额%`,
        `%养护其他完成同比%`,
        `%养护其他计划额%`,
        `%养护其他完成占比%`
      ], // 模板参数列表
      generateDialog: false,
      // generateForm: {
      //   type: '1',
      //   year: '',
      //   season: '',
      //   month: ''
      //   checkUser: ''
      // },
      // seasonOptions: ['第一季度', '第二季度', '第三季度', '第四季度'],
      // curMoudle: '',
      // userList: ['黄齐盛'],
      addRules: { // 表单验证
        modelName: [
          { required: true, message: '请填写模板名称', trigger: 'blur' }
        ],
        version: [
          { required: true, message: '请填写版本号', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    // reportTitle() {
    //   const { type, year, season, month } = this.generateForm
    //   let tit = ''
    //   switch (type) {
    //     case '1':
    //       tit = year + '年' + this.curMoudle
    //       break
    //     case '2':
    //       tit = year + '年' + season + this.curMoudle
    //       break
    //     case '3':
    //       tit = month.substr(0, 4) + '年' + month.substr(4, 5) + '月' + this.curMoudle
    //       break
    //     default:
    //       break
    //   }
    //   return tit
    // }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取模板列表
    getList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {
          modelName: this.form.name,
          beginDate: this.form.time !== null ? this.form.time[0] : '',
          endDate: this.form.time !== null ? this.form.time[1] : ''
        }
      }
      getModelList(params).then((res) => {
        this.total = res.data.total
        this.list = res.data.list
        this.listLoading = false
      })
    },
    // 查询按钮
    handleSearch() {
      this.getList()
    },
    // 编辑模板
    view(row) {
      const { id } = row
      getModel(id).then((res) => {
        this.detail = res.data
        this.editView = true
        this.isEdit = true
        this.curModelId = row.id
        this.moudleForm.modelName = row.modelName
        this.moudleForm.version = row.version
      })
    },
    // 删除模板
    handleDel(row) {
      const { id } = row
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delModel(id).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getList()
        })
      })
    },
    // 保存按钮
    saveMoudle() {
      if (this.detail === '') {
        this.$message({
          type: 'warning',
          message: '请填写模板正文'
        })
        return
      }
      this.$refs.moudleForm.validate(valid => {
        if (valid) {
          const params = {
            modelName: this.moudleForm.modelName,
            version: this.moudleForm.version,
            model: this.detail
          }
          if (this.isEdit) {
            params.id = this.curModelId
            updateModel(params).then((res) => {
              this.$message({
                message: res.msg,
                type: 'success'
              })
              clearObj(this.moudleForm)
              this.detail = ''
              this.editView = false
              this.isEdit = false
              this.curModelId = null
              this.getList()
            })
          } else {
            addModel(params).then((res) => {
              this.$message({
                message: res.msg,
                type: 'success'
              })
              clearObj(this.moudleForm)
              this.detail = ''
              this.editView = false
              this.isEdit = false
              this.getList()
            })
          }
        }
      })
    },
    // 返回按钮
    back() {
      this.editView = false
      this.isEdit = false
      clearObj(this.moudleForm)
      this.detail = ''
      this.curModelId = null
    },
    // 富文本编辑器 内容变化事件
    change(val) {
    },
    // 富文本 插入参数
    addParam(val) {
      this.$refs.editor.editor.$textElem.focus()
      const selection = window.getSelection()
      const range = selection.getRangeAt(0)
      const newNode = document.createElement('span')
      newNode.appendChild(document.createTextNode(val))
      range.setStart(range.endContainer, range.endOffset) // 设置开始插入位置
      range.insertNode(newNode) // 插入元素，node节点
      range.setEnd(range.endContainer, range.endOffset)
    },
    // 生成报告 按钮
    // handleGenerate(row) {
    //   this.generateDialog = true
    //   this.curModelId = row.id
    //   this.generateForm.checkUser = this.userList[0]
    // },
    generate(row) {
      this.curModelId = row.id
      const params = {
        id: this.curModelId,
        reportStatus: 0
      }
      generateReport(params).then((res) => {
        this.$message({
          message: res.msg,
          type: 'success'
        })
        this.generateDialog = false
        this.getList()
        this.curModelId = null
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  padding: 0 20px;
  .table-view{
    padding-top: 20px;
  }
  .params-bar{
    border: 1px solid #C9D8DB;
    position: relative;
    padding-top: 42px;
    .params-title{
      position: absolute;
      display: block;
      top: .5px;
      left: .5px;
      height: 42px;
      line-height: 42px;
      margin: 0;
      right: .5px;
      border-bottom: 1px solid #eee;
      text-indent: 10px;
      background: #fff;
    }
    .arrow{
      position: absolute;
      top: 42px;
      right: 15px;
      bottom: 0;
      width: 10px;
      line-height: calc(100vh - 264px);
      color: #999;
    }
    .params-con{
      height: calc(100vh - 264px);
      overflow: auto scroll;
      padding-top: 10px;
      .el-button{
        margin: 0 0 10px 10px;
      }
    }
  }
  .edit-con{
    height: calc(100vh - 220px);
  }
}
.html-wrapper{
  width: 600px;
  margin: 0 auto;
}
</style>
