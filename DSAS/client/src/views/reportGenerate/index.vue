<template>
  <div class="app-container">
    <div class="table-view">
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
          <el-table-column label="拟稿人" prop="draftUser" align="center" width="120" />
          <!-- <el-table-column label="拟稿单位" prop="draftUnit" align="center" show-overflow-tooltip /> -->
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
              <el-button size="mini" type="primary">
                <a :href="`/DSAS/model/${row.modelName}.docx`" :download="row.modelName">下载模板</a>
              </el-button>
              <el-button size="mini" type="success" @click="generate(row)">
                生成报告
              </el-button>
              <!-- <el-button size="mini" type="danger" @click="handleDel(row)">
                删除
              </el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
    </div>
    <!-- <el-dialog :visible.sync="viewDialog" :show-close="false" append-to-body fullscreen>
      <div class="html-wrapper">
        <div class="html-content" v-html="detail" />
      </div>
    </el-dialog> -->
    <el-dialog :visible.sync="generateDialog" title="报告选项" width="600px" append-to-body>
      <el-form ref="generateForm" :model="generateForm" label-width="90px">
        <el-form-item label="报告类型">
          <el-radio-group v-model="generateForm.date" size="small">
            <el-radio label="2">年报</el-radio>
            <el-radio label="1">季报</el-radio>
            <el-radio label="0">月报</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="统计范围">
          <el-date-picker
            v-model="generateForm.year"
            size="small"
            type="year"
            value-format="yyyy"
            placeholder="选择年份"
            style="width: 200px;"
            :clearable="false"
          />
          <el-select
            v-if="generateForm.date === '0'"
            v-model="generateForm.month"
            size="small"
            placeholder="选择月份"
            style="width: 200px;margin-left: 10px;"
          >
            <el-option
              v-for="item in 12"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
          <el-select
            v-if="generateForm.date === '1'"
            v-model="generateForm.quarter"
            size="small"
            placeholder="选择季度"
            style="width: 200px;margin-left: 10px;"
          >
            <el-option
              v-for="item in seasonOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="指定审核人">
          <el-select v-model="generateForm.checkUser" size="small" placeholder="选择审核人">
            <el-option
              v-for="item in userList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item> -->
        <el-alert
          v-if="reportTitle !== ''"
          :title="reportTitle"
          type="success"
          :closable="false"
        />
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="generateDialog = false">取 消</el-button>
        <el-button size="small" type="primary" @click="generateConfirm">生 成</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getModelList, generateReport } from '@/api/apis'

export default {
  name: 'ReportGenerate',
  components: {
    Pagination
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
      generateDialog: false,
      generateForm: {
        date: '2',
        year: '',
        quarter: '',
        month: ''
      },
      seasonOptions: [{
        label: '第1季度',
        value: 1
      }, {
        label: '第2季度',
        value: 2
      }, {
        label: '第3季度',
        value: 3
      }, {
        label: '第4季度',
        value: 4
      }],
      curMoudle: {}
    }
  },
  computed: {
    reportTitle() {
      const { date, year, quarter, month } = this.generateForm
      const season = parseInt(quarter) > 0 ? this.seasonOptions[quarter - 1].label : '季'

      let tit = ''
      switch (date) {
        case '2':
          tit = year + '年' + this.curMoudle.modelName
          break
        case '1':
          tit = year + '年' + season + this.curMoudle.modelName
          break
        case '0':
          tit = year + '年' + month + '月' + this.curMoudle.modelName
          break
        default:
          break
      }
      return tit
    }
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
    // 生成报告 按钮
    generate(row) {
      this.generateDialog = true
      this.curMoudle = row
    },
    // 生成 按钮
    generateConfirm() {
      let param = this.generateForm
      if (!param.year) {
        this.$message({
          type: 'warning',
          message: '请选择报告年份'
        })
        return
      }
      if (param.date === '0' && !param.month) {
        this.$message({
          type: 'warning',
          message: '请选择报告月份'
        })
        return
      } else if (param.date === '1' && !param.quarter) {
        this.$message({
          type: 'warning',
          message: '请选择报告季度'
        })
        return
      }
      param = Object.assign({}, this.curMoudle, param)
      generateReport(param).then(res => {
        this.$message({
          type: 'success',
          message: res.msg
        })
		this.generateDialog = false
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
