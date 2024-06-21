<template>
  <div class="app-container">
    <div v-if="dialogAdd === false" class="table-view">
      <div class="filter-container">
        <el-form ref="form" :inline="true" :model="form">
          <el-form-item label="审核状态">
            <el-select v-model="form.applyStatus" placeholder="请选择" size="small" style="width: 180px;" clearable>
              <el-option
                v-for="(item, i) in StatusOptions"
                :key="'status' + i"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="申请数据名称">
            <el-input v-model="form.applyDataName" size="small" placeholder="请输入名称" style="width: 180px;" clearable />
          </el-form-item>
          <el-form-item label="申请时间">
            <el-date-picker
              v-model="form.applyDate"
              size="small"
              class="filter-item"
              type="daterange"
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :clearable="true"
              style="width: 310px;"
            />
          </el-form-item>
          <div class="filter-btn">
            <el-button class="filter-item" type="primary" icon="el-icon-search" size="small" @click="handleSearch">查询</el-button>
            <el-button class="filter-item" type="success" icon="el-icon-plus" size="small" @click="handleAdd">申请</el-button>
          </div>
        </el-form>
      </div>
      <div style="height:calc(100vh - 268px);">
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
          <el-table-column label="申请数据名称" prop="applyDataName" align="center" show-overflow-tooltip />
          <el-table-column label="申请人" prop="applyUser" align="center" width="80" />
          <el-table-column label="申请单位" prop="applyUnit" align="center" width="140" show-overflow-tooltip />
          <el-table-column label="申请时间" prop="applyDate" align="center" width="140">
            <template slot-scope="{row}">
              <span>{{ row.applyDate | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="申请类型" prop="applyType" align="center" width="80" />
          <el-table-column label="审核状态" prop="applyStatus" align="center" width="80">
            <template slot-scope="{row}">{{ row.applyStatus | transShareCheckStatus }}</template>
          </el-table-column>
          <el-table-column label="审核人" prop="checkUser" align="center" width="80" />
          <el-table-column label="审核时间" prop="checkDate" align="center" width="140">
            <template slot-scope="{row}">
              <span>{{ row.checkDate | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
            <template slot-scope="{row,$index}">
              <!-- <el-button size="mini" type="primary" @click="viewDetail(row,$index)">详情</el-button> -->
              <el-button v-if="row.applyStatus === 1" v-permission="['pbdms:share:checkapply']" size="mini" type="success" @click="handleCheck(row,$index)">审核</el-button>
              <!-- <el-button size="mini" type="danger" @click="handleDel(row,$index)">删除</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />
    </div>
    <div v-else class="form-view">
      <back-header title="数据申请" @back="back">
        <el-button type="primary" size="small" icon="el-icon-check" @click="applyData">提交</el-button>
      </back-header>
      <el-form ref="addForm" :model="addForm" label-width="80px" :rules="rules">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="数据名称" prop="applyDataName">
              <el-select v-model="addForm.applyDataName" placeholder="请选择" size="small" style="width: 100%;" filterable>
                <el-option
                  v-for="(item, i) in tableOptions"
                  :key="'name' + i"
                  :label="item.comments"
                  :value="item.comments"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请类型" prop="applyType">
              <el-radio-group v-model="addForm.applyType">
                <el-radio label="共享" />
                <el-radio label="交换" />
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <!-- <el-col :span="8">
            <el-form-item label="申请时间">
              <el-date-picker
                v-model="addForm.applyDate"
                size="small"
                type="date"
                placeholder="请选择"
                value-format="yyyy-MM-dd"
                disabled
                style="width: 100%;"
              />
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="申请人" prop="applyUser">
              <el-input v-model="addForm.applyUser" size="small" placeholder="请输入申请人姓名" style="width: 100%;" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请单位" prop="applyUnit">
              <el-input v-model="addForm.applyUnit" size="small" placeholder="请输入申请单位名称" style="width: 100%;" disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-dialog :visible.sync="dialogCheck" title="审核意见" width="400px">
      <el-form ref="checkForm" :model="checkForm">
        <el-form-item label="审核结果">
          <el-radio-group v-model="checkForm.applyStatus">
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
        <el-button size="small" type="primary" @click="check">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getTables, getShares, applyShare, checkApply } from '@/api/apis'
import Pagination from '@/components/Pagination'
import BackHeader from '@/components/BackHeader'
import { clearObj } from '@/utils'
import permission from '@/directive/permission/index.js' // use clipboard by v-directive

export default {
  name: 'DataExchange',
  directives: {
    permission
  },
  components: { Pagination, BackHeader },
  data() {
    return {
      form: {
        applyDate: null,
        applyStatus: '',
        applyDataName: ''
      }, // 查询表单
      StatusOptions: [
        {
          label: '待审核',
          value: '1'
        },
        {
          label: '已通过',
          value: '2'
        },
        {
          label: '已驳回',
          value: '3'
        }
      ],
      tableOptions: [], // 数据表集合
      list: [], // 申请清单表格
      total: 0, // 分页 总条数
      page: 1, // 分页 页码
      limit: 20, // 分页 每页条数
      listLoading: false, // 表格加载遮罩
      dialogCheck: false, // 审核弹窗
      checkForm: {
        id: '',
        applyStatus: '2'
        // checkOpinion: ''
      }, // 审核弹窗表单
      dialogAdd: false, // 新增页面
      addForm: {
        applyUser: '',
        applyUnit: '',
        applyType: '',
        applyDataName: ''
      }, // 新增表单
      rules: { // 表单验证
        applyUser: [
          { required: true, message: '请填写申请人', trigger: 'blur' }
        ],
        applyUnit: [
          { required: true, message: '请填写申请单位', trigger: 'blur' }
        ],
        applyType: [
          { required: true, message: '请选择申请类型', trigger: 'blur' }
        ],
        applyDataName: [
          { required: true, message: '请选择数据表', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    name() {
      return this.$store.getters.name
    },
    dept() {
      return this.$store.getters.dept
    }
  },
  watch: {
    // 自动填充审核意见
    // 'checkForm.check': {
    //   handler: function(val) {
    //     if (val === '1') {
    //       this.checkForm.checkOpinion = '同意'
    //     } else {
    //       this.checkForm.checkOpinion = '不同意'
    //     }
    //   },
    //   immediate: true
    // }
  },
  created() {
    this.getList()
    this.getTables()
    this.addForm.applyUser = this.name
    this.addForm.applyUnit = this.dept
    // this.initTime()
  },
  methods: {
    // 初始化时间 当天
    // initTime() {
    //   const date = new Date()
    //   const today = date.getFullYear() + '' + ('0' + (date.getMonth() + 1)).slice(-2) + '' + ('0' + date.getDate()).slice(-2)
    //   this.addForm.applyDate = today
    // },
    // 获取数据库表列表
    getTables() {
      getTables().then((res) => {
        this.tableOptions = res.data
      })
    },
    // 查询方法
    getList() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {
          beginDate: this.form.applyDate !== null ? this.form.applyDate[0] : '',
          endDate: this.form.applyDate !== null ? this.form.applyDate[1] : '',
          applyDataName: this.form.applyDataName,
          applyStatus: this.form.applyStatus
        }
      }
      getShares(params).then((res) => {
        this.list = res.data.list
        this.total = res.data.total
        this.listLoading = false
      })
    },
    // 查询按钮
    handleSearch() {
      this.page = 1
      this.getList()
    },
    // 审核按钮
    handleCheck(row) {
      this.checkForm.id = row.id
      this.dialogCheck = true
    },
    // 审核弹窗 确定按钮
    check() {
      const params = this.checkForm
      checkApply(params).then((res) => {
        this.$message({
          type: 'success',
          message: '提交成功'
        })
        this.dialogCheck = false
        this.getList()
      })
    },
    // handleDel(row) {
    //   this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     console.log(row)
    //     this.$message({
    //       type: 'success',
    //       message: '删除成功'
    //     })
    //   })
    // },
    // viewDetail(row) {},
    // 申请按钮
    handleAdd() {
      this.dialogAdd = true
    },
    // 返回按钮
    back() {
      this.dialogAdd = false
      clearObj(this.addForm)
    },
    // 提交按钮
    applyData() {
      const params = this.addForm
      this.$refs.addForm.validate(valid => {
        if (valid) {
          applyShare(params).then((res) => {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            clearObj(this.addForm)
            this.dialogAdd = false
            this.getList()
          })
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  padding: 0;
  .table-view{
    padding: 20px;
    padding-bottom: 0;
    .filter-container{
      padding: 0 160px 10px 10px;
    }
  }
  .form-view{
    padding: 0 20px 20px;
  }
}
</style>
