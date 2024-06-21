<template>
  <div class="app-container">
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
                  v-if="data.type === 0 && node.isLeaf"
                  type="text"
                  size="mini"
                  icon="el-icon-delete"
                  style="color: #F56C6C; margin-left: 5px;"
                  @click.stop="() => removeNode(node, data)"
                />
              </span>
            </span>
          </el-tree>
        </div>
      </template>
      <template slot="paneR">
        <div class="right-cont">
          <div class="view-panel">
            <back-header v-if="!isEdit && !isAdd" :title="title" :show-back="false">
              <el-button type="primary" size="small" :disabled="isRoot" icon="el-icon-edit-outline" @click="isEdit = true">编辑节点</el-button>
              <el-button type="success" size="small" :disabled="isTable" icon="el-icon-plus" @click="addCata">新建节点</el-button>
            </back-header>
            <back-header v-else :title="title" @back="goback">
              <el-button v-if="isAdd" type="success" size="small" icon="el-icon-check" @click="addCatalogs">创建</el-button>
              <el-button v-if="!isAdd" type="success" size="small" icon="el-icon-check" @click="updateCatalogs">保存</el-button>
            </back-header>
            <el-form v-if="!isAdd" ref="editform" :model="nodeDetail" label-width="100px">
              <el-form-item label="目录中文名称">
                <el-input v-model="nodeDetail.menuName" maxlength="50" size="small" :disabled="!isEdit || isTable" style="width: 500px" />
              </el-form-item>
              <el-form-item v-if="!!nodeDetail.enName" label="目录名称">
                <el-input v-model="nodeDetail.enName" size="small" disabled style="width: 500px" />
              </el-form-item>
              <el-form-item label="上级目录">
                <el-cascader
                  v-model="nodeDetail.parentId"
                  size="small"
                  placeholder="搜索节点"
                  :options="treeData"
                  :props="pNodeProps"
                  filterable
                  :show-all-levels="false"
                  :disabled="!isEdit"
                  style="width: 500px"
                  @change="checkCata"
                />
              </el-form-item>
              <el-form-item label="目录类型">
                <el-radio-group v-model="nodeDetail.type" disabled>
                  <el-radio label="0">目录</el-radio>
                  <el-radio v-if="nodeDetail.type === '1'" label="1">数据表</el-radio>
                  <el-radio v-else label="2">数据表</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="创建时间">
                <span>{{ nodeDetail.createTime }}</span>
              </el-form-item>
            </el-form>
            <el-form v-else ref="addform" :model="addForm" label-width="100px">
              <el-form-item label="目录中文名称">
                <el-input v-if="addForm.type === '0'" v-model="addForm.menuName" maxlength="50" size="small" style="width: 500px" />
                <el-select v-else v-model="addForm.menuName" placeholder="请选择" size="small" style="width: 500px" @change="updateEnName">
                  <el-option
                    v-for="(item, i) in noCataTables"
                    :key="'name' + i"
                    :label="item.comments"
                    :value="item.comments"
                  />
                </el-select>
              </el-form-item>
              <el-form-item v-if="addForm.type !== '0'" label="目录名称">
                <el-input v-model="addForm.enName" size="small" disabled style="width: 500px" />
              </el-form-item>
              <el-form-item label="上级目录">
                <el-cascader
                  v-model="addForm.parentId"
                  size="small"
                  placeholder="搜索节点"
                  :options="treeData"
                  :props="pNodeProps"
                  filterable
                  :show-all-levels="false"
                  style="width: 500px"
                  @change="checkCata"
                />
              </el-form-item>
              <el-form-item label="目录类型">
                <el-radio-group v-model="addForm.type" @change="typeChange">
                  <el-radio label="0">目录</el-radio>
                  <el-radio label="1">数据表</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </template>
    </split-pane>
  </div>
</template>

<script>
import splitPane from 'vue-splitpane'
import { getCatalogTree, getNoCataTables, delCatalogs, updateCatalogs, addCatalogs } from '@/api/apis'
import BackHeader from '@/components/BackHeader'
import { createId32, treeQuery, clearObj } from '@/utils'

export default {
  name: 'DataCatalog',
  components: { splitPane, BackHeader },
  data() {
    return {
      isRoot: true,
      filterText: '', // 目录模糊检索
      isEdit: false, // 是否编辑状态
      isAdd: false, // 是否新增状态
      treeData: [], // 目录树数据
      noCataTables: [], // 未分配目录数据表集合
      nodeDetail: {
        menuName: '',
        id: '',
        enName: '',
        parentId: '',
        type: '',
        sort: '',
        createTime: ''
      }, // 当前节点详情
      addForm: {
        menuName: '',
        id: '',
        enName: '',
        parentId: '',
        type: '0',
        sort: ''
      }, // 新增目录表单
      pNodeProps: {
        expandTrigger: 'hover',
        value: 'id',
        label: 'menuName',
        emitPath: false,
        checkStrictly: true
      } // 上级目录级联选择器选项
    }
  },
  computed: {
    // 右侧标题
    title() {
      return this.isAdd ? '新增目录' : this.nodeDetail.menuName
    },
    // 当前节点是否数据表类型
    isTable() {
      return this.nodeDetail.type === '1' || this.nodeDetail.type === '2'
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
  },
  mounted() {
  },
  methods: {
    // 获取目录树
    getTree() {
      getCatalogTree(1).then((res) => {
        this.treeData = res.data
        this.selectNode(this.treeData[0], null)
      })
      this.getNoCataTables()
    },
    // 节点点击事件
    selectNode(data, node) {
      this.isAdd = false
      this.isEdit = false
      Object.keys(this.nodeDetail).forEach(k => {
        this.nodeDetail[k] = data[k] !== undefined && data[k] !== null ? data[k] + '' : ''
      })
      if (!node || node.level === 1) {
        this.isRoot = true
      } else {
        this.isRoot = false
      }
    },
    // 获取未分配目录的数据表集合
    getNoCataTables() {
      getNoCataTables().then((res) => {
        this.noCataTables = res.data
      })
    },
    // 删除目录
    removeNode(node, data) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delCatalogs(data.id).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getTree()
        })
      })
    },
    // 编辑节点
    updateCatalogs() {
      const params = this.nodeDetail
      if (params.menuName === '' || params.parentId === '') {
        this.$message({
          type: 'warning',
          message: '目录名称不能为空！'
        })
        return
      } else {
        const pNode = treeQuery(this.treeData, params.parentId)
        params.sort = pNode.children ? pNode.children[pNode.children.length - 1].sort + 1 : 0
        updateCatalogs(params).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.isAdd = false
          this.isEdit = false
          this.getTree()
        })
      }
    },
    // 新增按钮点击
    addCata() {
      this.isAdd = true
      this.addForm.parentId = this.nodeDetail.id
    },
    // 返回按钮点击
    goback() {
      this.isEdit = false
      this.isAdd = false
    },
    // 新增时目录类型切换
    typeChange(val) {
      this.addForm.menuName = ''
      this.addForm.enName = ''
    },
    // 新增时更新数据表名称
    updateEnName(val) {
      this.addForm.enName = this.noCataTables.find(item => item.comments === val).tableName
    },
    // 创建节点
    addCatalogs() {
      const params = this.addForm
      if (params.menuName === '' || params.parentId === '') {
        this.$message({
          type: 'warning',
          message: '目录名称不能为空！'
        })
        return
      } else {
        const pNode = treeQuery(this.treeData, params.parentId)
        params.sort = pNode.children ? pNode.children[pNode.children.length - 1].sort + 1 : 0
        params.id = createId32()
        addCatalogs(params).then((res) => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          clearObj(this.addForm)
          this.addForm.type = '0'
          this.isAdd = false
          this.isEdit = false
          this.getTree()
        })
      }
    },
    // 上级目录选择 避免数据表作为上级目录节点
    checkCata(val) {
      const node = treeQuery(this.treeData, val)
      if (node.type !== 0) {
        this.$message({
          type: 'warning',
          message: '数据表不可作为上级目录！'
        })
        this.$nextTick(() => {
          this.nodeDetail.parentId = ''
          this.addForm.parentId = ''
        })
      }
    },
    // 目录过滤方法
    filterNode(value, data) {
      if (!value) return true
      return data.menuName.indexOf(value) !== -1
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 0;
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
    padding: 0 20px 20px;
    overflow-x: auto;
    overflow-y: scroll;
  }
}
</style>
