<template>
  <div class="app-container">
    <div style="height:calc(100vh - 206px);">
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
        <el-table-column label="模块" prop="module" align="center" />
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="{row}">
            <span>{{ row.operate | transOperation }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作人" prop="operateUser" width="120px" align="center" />
        <el-table-column label="IP" prop="ip" width="120" align="center" />
        <el-table-column label="操作单位" prop="unit" align="center" />
        <el-table-column label="操作时间" width="160" align="center">
          <template slot-scope="{row}">
            <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100px" align="center">
          <template slot-scope="{row}">
            <el-button type="primary" size="mini" @click="view(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
    <el-dialog :visible.sync="showDialog" title="日志详情" width="80%">
      <el-table
        :data="detail"
        border
        fit
        :show-header="false"
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column prop="prop" align="right" width="200px">
          <template slot-scope="{row}">
            <span style="font-weight: bold;">{{ row.prop }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="val" align="left">
          <template slot-scope="{row}">
            <span v-if="row.prop === '操作'">{{ row.val | transOperation }}</span>
            <span v-else>{{ row.val }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { getLogs } from '@/api/apis'
import Pagination from '@/components/Pagination'

export default {
  name: 'OperateLogs',
  components: { Pagination },
  data() {
    return {
      showDialog: false, // 日志详情弹窗
      list: [], // 日志列表
      total: 0, // 分页 总条数
      page: 1, // 分页 页码
      limit: 20, // 分页 每页条数
      listLoading: true, // 表格加载遮罩
      detail: '' // 日志详情
    }
  },
  computed: {
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取日志
    getList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit
      }
      getLogs(params).then(response => {
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
      })
    },
    // 查看详情
    view(row) {
      const arr = {
        operate: '操作',
        operateUser: '操作人',
        unit: '所属单位',
        ip: '地址',
        queryParam: '请求参数',
        module: '模块',
        queryMethod: '请求方式',
        browser: '浏览器',
        browserVersion: '浏览器版本',
        operatingSystem: '操作系统',
        returnMes: '返回结果',
        createTime: '操作时间'
      }
      const result = []
      Object.keys(row).forEach(val => {
        if (arr[val] !== undefined) {
          result.push({
            'prop': arr[val],
            'val': row[val]
          })
        }
      })
      this.detail = result
      this.showDialog = true
    }
  }
}
</script>
