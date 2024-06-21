<template>
  <div class="check-container">
    <div v-if="!dialogConfig" class="table-container">
      <div class="filter-container">
        <div class="filter-btn">
          <el-button class="filter-item" type="success" icon="el-icon-plus" size="small" @click="openDetail(null,'add')">新增规则</el-button>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          height="100%"
          style="width: 100%;"
        >
          <el-table-column label="序号" type="index" :index="i => (page - 1) * limit + i + 1" width="50" align="center" />
          <el-table-column label="方案名称" prop="name" align="center" show-overflow-tooltip />
          <el-table-column label="表英文名称" prop="tabEnName" align="center" show-overflow-tooltip />
          <el-table-column label="表中文名称" prop="tabCnName" align="center" show-overflow-tooltip />
          <el-table-column label="备注" prop="comment" align="center" width="140" show-overflow-tooltip />
          <el-table-column label="更新时间" prop="updateTime" align="center" width="140">
            <template slot-scope="{row}">
              <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
            <template slot-scope="{row}">
              <el-button size="mini" type="primary" @click="openDetail(row,'edit')">
                查看
              </el-button>
              <el-button v-if="row.status" size="mini" type="success" @click="applyRules(row)">
                启用
              </el-button>
              <el-button v-if="!row.status" size="mini" type="info" @click="applyRules(row)">
                停用
              </el-button>
              <el-button size="mini" type="danger" @click="delAlertRules(row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getAlertRules" />
      </div>
    </div>
    <div v-else :key="isEdit" class="config-container">
      <back-header title="预警规则配置" @back="cancelRules">
        <el-button size="small" type="success" icon="el-icon-check" @click="saveRules()">
          保存
        </el-button>
      </back-header>
      <div class="panel-top">
        <el-form ref="form" :model="form" label-width="80px" :rules="rules">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="表中文名" prop="tabCnName">
                <el-select v-model="form.tabCnName" placeholder="请选择" size="small" style="width: 100%;" :disabled="isEdit === 'edit'" filterable @change="handleTableChange">
                  <el-option
                    v-for="(item, i) in tableOptions"
                    :key="'name' + i"
                    :label="item.comments"
                    :value="item.comments"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="表名称">
                <el-input v-model="form.tabEnName" size="small" style="width: 100%;" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="方案名称" prop="name">
                <el-input v-model="form.name" size="small" maxlength="20" placeholder="请输入名称" style="width: 100%;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="备注" prop="comment">
                <el-input
                  v-model="form.comment"
                  type="textarea"
                  size="small"
                  placeholder="请填写方案说明"
                  maxlength="100"
                  show-word-limit
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="panel-bottom">
        <el-table :data="form.detailVoList" border highlight-current-row style="width: 100%;" height="100%">
          <el-table-column label="序号" type="index" width="50" align="center" />
          <el-table-column align="center" label="对标字段英文名称">
            <template slot-scope="{row,$index}">
              <template v-if="row.edit">
                <el-select v-model="row.colEnName" size="small" placeholder="请选择" style="width: 100%;" @change="val => handleFieldChange(val,$index)">
                  <el-option
                    v-for="item in colOptions"
                    :key="item.columnName"
                    :label="item.columnName"
                    :value="item.columnName"
                  />
                </el-select>
              </template>
              <span v-else>{{ row.colEnName }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="对标字段中文名称" prop="colCnName" />
          <el-table-column align="center" label="预警规则" min-width="80">
            <template slot-scope="{row}">
              <template v-if="row.edit">
                <el-select v-model="row.rule" size="small" placeholder="请选择" style="width: 100%;">
                  <el-option
                    v-for="item in ruleOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </template>
              <span v-else>{{ row.rule | transRules }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作" width="160">
            <template slot-scope="{row, $index}">
              <el-button
                v-if="row.edit"
                type="success"
                size="mini"
                @click="confirmEdit(row)"
              >
                确认
              </el-button>
              <el-button
                v-if="!row.edit"
                type="primary"
                size="mini"
                @click="row.edit=!row.edit"
              >
                编辑
              </el-button>
              <el-button
                v-if="!row.edit"
                type="danger"
                size="mini"
                @click="deleteRow(row, $index)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="add-btn">
          <el-button size="mini" icon="el-icon-plus" :disabled="colOptions.length === 0" style="width: 100%;margin-top: 5px;padding:4px 0;" @click="addRow">增加一行</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getAlertRules, delAlertRules, applyRules, addAlertRules, updateAlertRules, getRulesDetail, getTables, getTableCols } from '@/api/apis'
import Pagination from '@/components/Pagination'
import BackHeader from '@/components/BackHeader'
import { createId32, clearObj } from '@/utils'

export default {
  name: 'DataCheckConfig',
  components: { Pagination, BackHeader },
  data() {
    return {
      list: [], // 规则列表
      isEdit: '', // 区分新增 编辑
      tableOptions: [], // 数据库表列表
      total: 0, // 规则总数
      page: 1, // 规则页码
      limit: 20, // 规则每页条数
      listLoading: false, // 规则表格遮罩
      dialogConfig: false, // 详情页面 显示
      form: { // 详情页面表单
        id: '',
        name: '',
        tabCnName: '',
        tabEnName: '',
        comment: '',
        detailVoList: []
      },
      colOptions: [], // 字段下拉选项
      ruleOptions: [ // 规则下拉选项
        {
          label: '检查字段完整性',
          value: '1'
        }
      ],
      rules: { // 表单验证
        tabCnName: [
          { required: true, message: '请选择数据表', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请填写方案名称', trigger: 'blur' },
          { max: 20, message: '长度在20个字符以内', trigger: 'blur' }
        ],
        comment: [
          { required: true, message: '请填写方案备注', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
  },
  created() {
    this.getAlertRules()
    this.getTables()
  },
  methods: {
    // 获取数据库表列表
    getTables() {
      getTables().then((response) => {
        this.tableOptions = response.data
      })
    },
    // 获取预警规则列表
    getAlertRules() {
      this.listLoading = true
      const params = {
        pageIndex: this.page,
        pageSize: this.limit,
        queryParams: {}
      }
      getAlertRules(params).then((response) => {
        this.total = response.data.total
        this.page = response.data.pageNum
        this.list = response.data.list
        this.listLoading = false
      })
    },
    // 删除规则
    delAlertRules(row) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delAlertRules(row.id).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getAlertRules()
        })
      })
    },
    // 停用 启用 规则
    applyRules(row) {
      applyRules({ id: row.id, status: Number(!row.status) }).then((res) => {
        this.$message({
          message: res.msg,
          type: 'success'
        })
        this.getAlertRules()
      })
    },
    // 打开详情页面
    openDetail(row, flag) {
      this.isEdit = flag
      this.dialogConfig = true
      if (flag === 'edit') {
        Object.keys(this.form).forEach(val => {
          if (val !== 'detailVoList') {
            this.form[val] = row[val]
          }
        })
        getRulesDetail(row.id).then((res) => {
          this.form.detailVoList = res.data.map(val => {
            return Object.assign(val, {
              edit: false
            })
          })
        })
        this.handleTableChange(this.form.tabCnName)
      } else if (flag === 'add') {
        // 用作新增时 新建预警方案id
        this.form.id = createId32()
      }
    },
    // 详情页保存 新增 更新规则
    saveRules() {
      const params = this.form
      // 为各字段规则添加排序标记
      params.detailVoList = this.form.detailVoList.map((item, i) => {
        return Object.assign(item, {
          sort: i
        })
      })
      this.$refs.form.validate(valid => {
        if (valid) {
          if (params.detailVoList.length === 0) {
            this.$message({
              message: '字段校验规则不能为空！',
              type: 'warning'
            })
          } else {
            if (this.isEdit === 'add') {
              addAlertRules(params).then((res) => {
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
                this.clearForm()
                this.dialogConfig = false
                this.getAlertRules()
              })
            } else if (this.isEdit === 'edit') {
              updateAlertRules(params).then((res) => {
                this.$message({
                  message: res.msg,
                  type: 'success'
                })
                this.clearForm()
                this.dialogConfig = false
                this.getAlertRules()
              })
            }
          }
        }
      })
    },
    // 详情页 返回
    cancelRules() {
      this.dialogConfig = false
      this.clearForm()
    },
    // 清空详情页表单
    clearForm() {
      clearObj(this.form)
      this.colOptions = []
    },
    // 表选中事件
    handleTableChange(name) {
      const { tableName } = this.tableOptions.find(val => val.comments === name)
      this.form.tabEnName = tableName
      // 获取表对应的字段信息
      getTableCols(tableName).then((response) => {
        this.colOptions = response.data
      })
      if (this.isEdit === 'add') {
        this.form.detailVoList = []
      }
    },
    // 字段配置 增加一行
    addRow() {
      const newRow = {
        id: createId32(),
        rule: this.ruleOptions[0].value,
        colEnName: this.colOptions[0].columnName,
        colCnName: this.colOptions[0].comments,
        checkMainId: this.form.id,
        edit: false // 用于表格内编辑功能标记
      }
      this.form.detailVoList.push(newRow)
    },
    // 对表字段选中事件
    handleFieldChange(name, i) {
      const { comments } = this.colOptions.find(val => val.columnName === name)
      this.form.detailVoList[i].colCnName = comments
    },
    // 字段规则编辑确认
    confirmEdit(row) {
      let flag = true
      if (row.colEnName === null && row.rule === null) {
        this.$message({
          message: '请将信息填写完整！',
          type: 'warning'
        })
        flag = false
      }
      if (flag) row.edit = false
    },
    // 字段规则删除行
    deleteRow(row, i) {
      this.form.detailVoList.splice(i, 1)
    }
  }
}
</script>
<style lang="scss" scoped>
.check {
  &-container {
    margin: 20px;
    padding-bottom: 20px;
    .table-wrapper{
      height: calc(100vh - 256px);
    }
    .config-container{
      .panel-bottom{
        height: calc(100vh - 330px);
        position: relative;
      }
    }
  }
}
</style>
