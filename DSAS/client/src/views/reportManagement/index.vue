<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="form" :inline="true" :model="form">
        <el-form-item label="报告名称">
          <el-input
            v-model="form.reportName"
            placeholder="请输入报告名称"
            size="small"
            clearable
            style="width: 200px;"
          />
        </el-form-item>
        <el-form-item label="拟稿单位">
          <!-- <el-select v-model="form.unit" placeholder="请选择" size="small">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select> -->
          <el-input
            v-model="form.unit"
            placeholder="请输入单位名称"
            size="small"
            clearable
            style="width: 200px;"
          />
        </el-form-item>
        <el-form-item label="创建日期">
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
        <el-form-item label="报告状态">
          <el-select v-model="form.status" placeholder="请选择" size="small" clearable>
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <div class="filter-btn">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleSearch">查询</el-button>
        </div>
      </el-form>
    </div>
    <div style="height:calc(100vh - 320px);">
      <el-table
        v-loading="listLoading"
        :data="list"
        border
        fit
        highlight-current-row
        height="100%"
        style="width: 100%;"
      >
        <el-table-column label="序号" type="index" :index="i => (page - 1) * limit + i + 1" width="60" align="center" />
        <el-table-column label="报告名称" prop="reportName" align="center" show-overflow-tooltip />
        <el-table-column label="拟稿人" prop="draftUser" align="center" width="80" />
        <el-table-column label="拟稿时间" prop="createTime" align="center" width="140">
          <template slot-scope="{row}">
            {{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}
          </template>
        </el-table-column>
        <el-table-column label="拟稿单位" prop="draftUnit" align="center" show-overflow-tooltip />
        <el-table-column label="审核人" prop="checkUser" align="center" width="90" />
        <el-table-column label="审核时间" prop="checkDate" align="center" width="140">
          <template slot-scope="{row}">
            {{ row.checkDate | parseTime('{y}-{m}-{d} {h}:{i}') }}
          </template>
        </el-table-column>
        <el-table-column label="审核单位" prop="checkUnit" align="center" show-overflow-tooltip />
        <el-table-column label="报告状态" prop="reportStatus" align="center" width="100">
          <template slot-scope="{row}">
            {{ row.reportStatus | transReportStatus }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
          <template slot-scope="{row}">
            <!-- <el-button size="mini" type="primary" @click="handleView(row)">
              查看
            </el-button> -->
            <el-button size="mini" type="primary" @click="download(row)">
              下载
            </el-button>
            <el-button v-if="row.reportStatus === 2" v-permission="['dsas:reports:archivereport']" size="mini" type="warning" @click="archive(row)">
              归档
            </el-button>
            <el-button v-if="row.reportStatus === 1" v-permission="['dsas:reports:checkreport']" size="mini" type="success" @click="handleCheck(row)">
              审核
            </el-button>
            <el-button size="mini" type="danger" @click="handleDel(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
    <el-dialog :visible.sync="viewDialog" :show-close="false" append-to-body fullscreen>
      <div class="html-wrapper">
        <div class="html-content" v-html="detail" />
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogCheck" title="审核意见" width="400px">
      <el-form ref="checkForm" :model="checkForm">
        <el-form-item label="审核结果">
          <el-radio-group v-model="checkForm.status">
            <el-radio label="3">不同意</el-radio>
            <el-radio label="2">同意</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- <el-form-item label="审核意见">
          <el-input
            v-model="checkForm.checkOpinion"
            type="textarea"
            size="small"
            placeholder="请填写审核意见"
            maxlength="100"
            show-word-limit
          />
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogCheck = false">取 消</el-button>
        <el-button size="small" type="primary" @click="check(checkForm)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { fetchReportList, archiveReport, delReport, checkReport } from '@/api/apis'
import Docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'
import permission from '@/directive/permission/index.js' // use clipboard by v-directive

export default {
  name: 'ReportManagement',
  directives: {
    permission
  },
  components: { Pagination },
  data() {
    return {
      viewDialog: false, // 预览弹窗
      form: {
        time: null,
        status: '',
        unit: '',
        reportName: ''
      }, // 查询表单
      listLoading: false, // 表格遮罩
      list: [
        {}
      ], // 表格数据
      total: 0, // 分页 总条数
      page: 1, // 分页 页码
      limit: 20, // 分页 每页条数
      detail: '', // 预览 正文
      statusOptions: [
        // {
        //   label: '未生成',
        //   value: 0
        // },
        {
          label: '审核中',
          value: 1
        },
        {
          label: '审核通过',
          value: 2
        },
        {
          label: '审核驳回',
          value: 3
        },
        {
          label: '已归档',
          value: 4
        }
      ], // 报告状态选项
      dialogCheck: false, // 审核弹窗
      checkForm: {
        id: '',
        status: '2'
      } // 审核表单
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 查询报告列表
    getList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {
          reportName: this.form.reportName,
          beginDate: this.form.time !== null ? this.form.time[0] : '',
          endDate: this.form.time !== null ? this.form.time[1] : '',
          checkUnit: this.form.unit,
          reportStatus: this.form.status
        }
      }
      fetchReportList(params).then((res) => {
        this.total = res.data.total
        this.list = res.data.list
        this.listLoading = false
      })
    },
    // 查询按钮
    handleSearch() {
      this.getList()
    },
    // 预览报告
    // handleView(row) {
    //   const { id } = row
    //   fetchReport(id).then((res) => {
    //     this.detail = res.data
    //     this.viewDialog = true
    //     setTimeout(() => {
    //       this.$message({
    //         type: 'info',
    //         message: '按Esc键返回'
    //       })
    //     }, 2000)
    //   })
    // },
    // 下载报告
    download(row) {
      // fetchReport(row.id).then((res) => {
      //   this.detail = res.data
      //   if (!res.data) {
      //     return
      //   }
      this.exportWord(row)
      // })
    },
    exportWord(row) {
      const docData = JSON.parse(row.model)
      // 读取并获得模板文件的二进制内容
      JSZipUtils.getBinaryContent(`http\:\/\/${window.location.host}\/DSAS\/report\/${row.modelName}\.docx`, function(error, content) {
        // model.docx是模板。我们在导出的时候，会根据此模板来导出对应的数据
        // 抛出异常
        if (error) {
          throw error
        }
        // 创建一个PizZip实例，内容为模板的内容
        const zip = new PizZip(content)
        // 设置angular解析器
        var expressions = require('angular-expressions')
        var assign = require('lodash/assign')
        expressions.filters.lower = function(input) {
          if (!input) return input
          return input.toLowerCase()
        }
        function angularParser(tag) {
          if (tag === '.') {
            return {
              get: function(s) { return s }
            }
          }
          const expr = expressions.compile(
            tag.replace(/(’|‘)/g, "'").replace(/(“|”)/g, '"')
          )
          return {
            get: function(scope, context) {
              let obj = {}
              const scopeList = context.scopeList
              const num = context.num
              for (let i = 0, len = num + 1; i < len; i++) {
                obj = assign(obj, scopeList[i])
              }
              return expr(scope, obj)
            }
          }
        }
        // 创建并加载docxtemplater实例对象
        const doc = new Docxtemplater(zip, { parser: angularParser })
        // 设置模板变量的值
        doc.setData({
          ...docData
        })
        try {
          // 用模板变量的值替换所有模板变量
          doc.render()
        } catch (error) {
          // 抛出异常
          const e = {
            message: error.message,
            name: error.name,
            stack: error.stack,
            properties: error.properties
          }
          console.log(JSON.stringify({ error: e }))
          throw error
        }
        // 生成一个代表docxtemplater对象的zip文件（不是一个真实的文件，而是在内存中的表示）
        const out = doc.getZip().generate({
          type: 'blob',
          mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
        })
        // 将目标文件对象保存为目标类型的文件，并命名
        saveAs(out, `${row.reportName}.docx`)
      })
    },
    // 审核按钮
    handleCheck(row) {
      this.dialogCheck = true
      this.checkForm.id = row.id
    },
    // 审核 归档
    check(row) {
      const params = {
        id: row.id,
        reportStatus: this.checkForm.status
      }
      checkReport(params).then((res) => {
        this.$message({
          type: 'success',
          message: res.msg
        })
        this.getList()
        this.dialogCheck = false
      })
    },
    // 归档
    archive(row) {
      const params = {
        id: row.id,
        reportStatus: 4
      }
      archiveReport(params).then((res) => {
        this.$message({
          type: 'success',
          message: res.msg
        })
        this.getList()
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
        delReport(id).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getList()
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.html-wrapper{
  width: 600px;
  margin: 0 auto;
}
</style>
