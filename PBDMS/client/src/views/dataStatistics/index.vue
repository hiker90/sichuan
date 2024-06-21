<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="form" :inline="true" :model="form">
        <el-form-item label="创建日期">
          <el-date-picker
            v-model="form.createTime"
            size="small"
            class="filter-item"
            type="daterange"
            value-format="yyyyMMdd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :clearable="false"
            :picker-options="pickerOptions"
          />
        </el-form-item>
        <div class="filter-btn">
          <el-button class="filter-item" type="primary" icon="el-icon-search" size="small" @click="handleSearch">查询</el-button>
        </div>
      </el-form>
    </div>
    <div style="height:calc(100vh - 266px);">
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
        <el-table-column
          label="数据来源单位"
          prop="unit"
          align="center"
        />
        <el-table-column label="数据总量（条）" prop="totalNum" width="160" align="center" />
        <el-table-column label="范围新增量（条）" prop="num" width="160" align="center" />
      </el-table>
    </div>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
  </div>
</template>

<script>
import { fetchStatistics } from '@/api/apis'
import Pagination from '@/components/Pagination'

export default {
  name: 'DataStatisticts',
  components: { Pagination },
  data() {
    return {
      form: {
        createTime: []
      }, // 查询表单
      list: [], // 统计表格
      total: 0, // 分页 总条数
      page: 1, // 分页 页码
      limit: 20, // 分页 每页条数
      listLoading: true, // 表格加载遮罩
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 3600 * 24 * 1000
        }
      }
    }
  },
  created() {
    this.initTime()
    this.getList()
  },
  methods: {
    // 初始化时间 当天
    initTime() {
      const date = new Date()
      date.setTime(date.getTime() - 24 * 60 * 60 * 1000)
      const today = date.getFullYear() + '' + ('0' + (date.getMonth() + 1)).slice(-2) + '' + ('0' + date.getDate()).slice(-2)
      this.form.createTime = [today, today]
    },
    // 查询方法
    getList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {
          beginTime: this.form.createTime[0],
          endTime: this.form.createTime[1]
        }
      }
      fetchStatistics(params).then((response) => {
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
      })
    },
    // 查询按钮
    handleSearch() {
      this.page = 1
      this.getList()
    }
  }
}
</script>
