<template>
  <div>
    <div>
      <el-card class="box-card">
        <div class="card-header">
          <span>文章管理</span>
          <el-button type="primary" @click="addArticle">发布文章</el-button>
        </div>
        <hr>
        <div>
          <el-form :inline="true" :model="queryForm" class="el-form">
            <el-form-item label="分类">
              <el-select v-model="queryForm.categoryId" clearable placeholder="分类id">
                <el-option label="人文" value="17"/>
                <el-option label="科学" value="16"/>
              </el-select>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="queryForm.state" clearable placeholder="状态">
                <el-option label="已发布" value="已发布"/>
                <el-option label="草稿" value="草稿"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button :icon="Search" type="primary" @click="queryArticle"/>
              <el-button :icon="Loading" type="warning" @click="resetForm"/>
            </el-form-item>
          </el-form>
        </div>

        <div>
          <el-table :data="objLs" row-class-name="success-row" width="100%">
            <el-table-column label="标题" prop="title"/>
            <el-table-column label="分类" prop="categoryId"/>
            <el-table-column label="发表时间" prop="createBy"/>
            <el-table-column label="状态" prop="state"/>
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-row>
                  <!-- <el-col :span="6"> -->
                  <el-button :icon="Edit" circle disabled type="primary" @click="editArticle(row)"/>
                  <!-- </el-col> -->
                  <!-- <el-col :span="16"> -->
                  <el-button :icon="Delete" circle disabled type="danger" @click="deleteArticle(row)"/>
                  <!-- </el-col> -->
                </el-row>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 分页 -->
        <div class="el-pag">
          <el-pagination v-model:current-page="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
                         :background="background" :disabled="disabled" :page-sizes="[2, 5, 10, 20]" :small="small"
                         :total="total" layout="total,sizes, prev, pager, next,jumper" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"/>
        </div>
      </el-card>
    </div>

  </div>
</template>

<script setup>

import {onMounted, ref} from 'vue'
import {Delete, Edit, Loading, Search} from '@element-plus/icons-vue'
import user from '@/api/user/index.js'
import article from '@/api/article/index.js'


/**
 * 分页配置
 */
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const total = ref(0)

const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
}

/**
 * 表单配置
 */
const queryForm = ref({
  "pageNum": 1,
  "pageSize": 5,
  "categoryId": null,
  "state": null,
})
/**
 * 重置表单
 */
const resetForm = () => {
  queryForm.value = {
    "pageNum": 1,
    "pageSize": 2,
    "categoryId": null,
    "state": null,
  }
  getAllArticle()
}
/**
 * 文章列表
 */
const objLs = ref([
  {
    "id": 1,
    "title": "标题1",
    "categoryId": 1,
    "createBy": "admin",
    "state": 1
  },
  {
    "id": 2,
    "title": "标题2",
    "categoryId": 2,
    "createBy": "admin",
    "state": 1
  },

])

// 请求函数配置
const getAllArticle = () => {
  article.articleGetByPage(queryForm.value, res => {
    total.value = res.data.data.total
    objLs.value = res.data.data.items
  }, err => {
    if (err.status === 401) {
      alert('登录失效，请重新登录')
    }
  })
}

const login = () => {
  user.login(
      {
        username: 'admin',
        password: '123123'
      }
  )
}

const queryArticle = () => {
  article.articleGetByPage(queryForm.value, res => {
    objLs.value = res.data.data.items
  }, err => {
    if (err.status === 401) {
      alert('登录失效，请重新登录')
    }
  })
}

onMounted(() => {
  // login()
  getAllArticle()
})
</script>

<style scoped>
.card-header {
  margin: 10px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.el-form {
  margin: 20px 0;
}

.el-pag {
  margin: 20px 0;
  display: flex;
  justify-content: flex-end;
}

.el-table .success-row {
  margin: 20px;
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>