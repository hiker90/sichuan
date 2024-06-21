<template>
  <div class="app-container">
    <div class="head-board">
      <el-row :gutter="40" class="panel-group">
        <el-col :xs="24" :sm="8" :lg="8" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-amount">
              <svg-icon icon-class="chart" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">数据量</div>
              <count-to :start-val="0" :end-val="dataNum" :duration="2600" class="card-panel-num" />条
            </div>
          </div>
        </el-col>
        <el-col :xs="24" :sm="8" :lg="8" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-interface">
              <svg-icon icon-class="list" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">数据接口</div>
              <count-to
                :start-val="0"
                :end-val="interfaceNum"
                :duration="3200"
                class="card-panel-num"
              />
            </div>
          </div>
        </el-col>
        <el-col :xs="24" :sm="8" :lg="8" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-table">
              <svg-icon icon-class="table" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">数据表</div>
              <count-to :start-val="0" :end-val="tableNum" :duration="3600" class="card-panel-num" />
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <div class="table-board">
      <div class="link-btn">
        <router-link :to="{path: '/dataCheckConfig'}">
          <el-button v-permission="['pbdms:quality:getkpi']" size="small" icon="el-icon-setting">预警规则配置</el-button>
        </router-link>
        <el-button size="small" style="margin-left: 10px;" icon="el-icon-refresh" @click="refresh">刷新</el-button>
      </div>
      <el-tabs>
        <el-tab-pane label="预警情况">
          <div style="height:calc(100vh - 420px);">
            <el-table
              v-loading="listLoading"
              :data="alertList"
              fit
              highlight-current-row
              height="100%"
              style="width: 100%;"
            >
              <el-table-column
                type="index"
                :index="i => (page1 - 1) * limit + i + 1"
                width="60"
                align="center"
                label="序号"
              />
              <!-- <el-table-column label="预警情况" prop="warningState" align="center" /> -->
              <el-table-column label="预警名称" prop="name" align="center" min-width="300" show-overflow-tooltip />
              <el-table-column label="预警类型" align="center" width="80">
                <template slot-scope="{row}"><span>{{ row.warningType | transWarningType }}</span></template>
              </el-table-column>
              <el-table-column label="预警状态" align="center" width="80">
                <template slot-scope="{row}"><span style="color: #F56C6C;">{{ row.status | transHandleResult }}</span></template>
              </el-table-column>
              <el-table-column label="更新时间" prop="updateTime" align="center" width="140">
                <template slot-scope="{row}">
                  <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
                <template slot-scope="{row,$index}">
                  <el-button size="mini" type="success" @click="viewDetail(row,$index)">查看</el-button>
                  <el-button size="mini" type="primary" @click="handleAlert(row,$index)">处理</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <pagination v-show="total1>0" :total="total1" :page.sync="page1" :limit.sync="limit" @pagination="getAlertInfo" />
        </el-tab-pane>
        <el-tab-pane label="处理情况">
          <div style="height:calc(100vh - 420px);">
            <el-table
              v-loading="listLoading"
              :data="resultList"
              fit
              highlight-current-row
              height="100%"
              style="width: 100%;"
            >
              <el-table-column
                label="序号"
                type="index"
                :index="i => (page2 - 1) * limit + i + 1"
                width="60"
                align="center"
              />
              <el-table-column label="预警内容" prop="warningState" align="center" />
              <el-table-column label="预警名称" prop="name" align="center" width="300" show-overflow-tooltip />
              <el-table-column label="预警类型" align="center" width="80">
                <template slot-scope="{row}"><span>{{ row.warningType | transWarningType }}</span></template>
              </el-table-column>
              <el-table-column label="预警状态" align="center" width="80">
                <template slot-scope="{row}">{{ row.status | transHandleResult }}</template>
              </el-table-column>
              <el-table-column label="更新时间" prop="updateTime" align="center" width="140">
                <template slot-scope="{row}">
                  <span>{{ row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <pagination v-show="total2>0" :total="total2" :page.sync="page2" :limit.sync="limit" @pagination="getHandleResult" />
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog :visible.sync="dialogView" title="预警详情" width="600px">
      <div v-for="item in detail" :key="item.id">
        <p>
          <i class="el-icon-message-solid" style="color:#E6A23C" />
          {{ item.updateTime }} {{ item.name }}
        </p>
        <p style="text-indent: 18px;">{{ item.warningState }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getKpis, getAlertInfo, handleAlert } from '@/api/apis'
import CountTo from 'vue-count-to' // 数字增长效果
import Pagination from '@/components/Pagination' // 分页
import permission from '@/directive/permission/index.js' // use clipboard by v-directive
import { debonce } from '@/utils'

export default {
  name: 'DataQuality',
  directives: {
    permission
  },
  components: {
    CountTo,
    Pagination
  },
  data() {
    return {
      dataNum: 0, // 数据量
      interfaceNum: 0, // 数据接口
      tableNum: 0, // 数据表
      alertList: [], // 预警情况
      resultList: [], // 处理情况
      listLoading: false, // 表格加载遮罩
      dialogView: false, // 查看 弹窗
      detail: '', // 弹窗详情
      total1: 0, // 预警情况 总条数
      page1: 1, // 预警情况 页码
      total2: 0, // 处理情况 总条数
      page2: 1, // 处理情况 页码
      limit: 10 // 每页条数
    }
  },
  created() {
    this.getKpis()
    this.getAlertInfo()
    this.getHandleResult()
    // 刷新按钮防抖
    this.refresh = debonce(this.refresh, 2000)
  },
  methods: {
    // 获取数据窗三参数
    getKpis() {
      getKpis().then((response) => {
        this.dataNum = response.data.dataNum
        this.interfaceNum = response.data.interfaceNum
        this.tableNum = response.data.tableNum
      })
    },
    // 获取预警情况
    getAlertInfo() {
      this.listLoading = true
      const params = {
        pageIndex: this.page1,
        pageSize: this.limit,
        queryParams: {
          status: 0
        }
      }
      getAlertInfo(params).then((response) => {
        this.total1 = response.data.total
        this.page1 = response.data.pageNum
        this.alertList = response.data.list
        this.listLoading = false
      })
    },
    // 获取处理情况
    getHandleResult() {
      this.listLoading = true
      const params = {
        pageIndex: this.page2,
        pageSize: this.limit,
        queryParams: {
          status: 1
        }
      }
      getAlertInfo(params).then((response) => {
        this.total2 = response.data.total
        this.page2 = response.data.pageNum
        this.resultList = response.data.list
        this.listLoading = false
      })
    },
    // 处理按钮
    handleAlert(row, i) {
      handleAlert(row.name).then((res) => {
        if (res.success) {
          this.$message({
            message: res.msg,
            type: 'success'
          })
          this.getAlertInfo()
          this.getHandleResult()
        } else {
          this.$message({
            message: res.msg,
            type: 'danger'
          })
        }
      })
    },
    // 查看按钮
    viewDetail(row, i) {
      const params = {
        pageIndex: 1,
        pageSize: 0,
        queryParams: {
          status: 2,
          name: row.name
        }
      }
      getAlertInfo(params).then((response) => {
        this.detail = response.data.list
      })
      this.dialogView = true
    },
    // 刷新
    refresh() {
      this.getAlertInfo()
      this.getHandleResult()
    }
  }
}
</script>

<style lang="scss" scoped>
.app {
  &-container {
    margin: 20px 20px 0;
    .head-board {
      .panel-group {
        .card-panel-col {
          margin-bottom: 32px;
        }

        .card-panel {
          height: 108px;
          // cursor: pointer;
          font-size: 12px;
          position: relative;
          overflow: hidden;
          color: #666;
          background: #fff;
          box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
          border-color: rgba(0, 0, 0, 0.05);

          &:hover {
            .card-panel-icon-wrapper {
              color: #fff;
            }

            .icon-amount {
              background: #40c9c6;
            }

            .icon-interface {
              background: #36a3f7;
            }

            .icon-table {
              background: #f4516c;
            }
          }

          .icon-amount {
            color: #40c9c6;
          }

          .icon-interface {
            color: #36a3f7;
          }

          .icon-table {
            color: #f4516c;
          }
          .card-panel-icon-wrapper {
            float: left;
            margin: 14px 0 0 14px;
            padding: 16px;
            transition: all 0.38s ease-out;
            border-radius: 6px;
          }

          .card-panel-icon {
            float: left;
            font-size: 48px;
          }

          .card-panel-description {
            float: right;
            font-weight: bold;
            margin: 26px;
            margin-left: 0px;

            .card-panel-text {
              line-height: 18px;
              color: rgba(0, 0, 0, 0.45);
              font-size: 16px;
              margin-bottom: 12px;
            }

            .card-panel-num {
              font-size: 20px;
            }
          }
        }
      }

      @media (max-width: 550px) {
        .card-panel-description {
          display: none;
        }

        .card-panel-icon-wrapper {
          float: none !important;
          width: 100%;
          height: 100%;
          margin: 0 !important;

          .svg-icon {
            display: block;
            margin: 14px auto !important;
            float: none !important;
          }
        }
      }
    }
    .table-board{
      position: relative;
      .link-btn{
        position: absolute;
        right: 0;
        z-index: 1;
      }
    }
  }
}
</style>
