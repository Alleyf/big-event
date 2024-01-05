<script setup>
import {Delete, Edit,} from '@element-plus/icons-vue'
import {onMounted, onUpdated, reactive, ref} from 'vue'
import article from "@/api/article";
import {ElMessage} from "element-plus";

const category = ref([])
const form = ref()
const diaTitle = ref('添加分类')
const dialogVisible = ref(false)
const categoryModel = ref({
  categoryName: '',
  categoryAlias: ''
})
const rules = reactive({
  categoryName: [
    {required: true, message: '请输入分类名称', trigger: 'blur'},
    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
  ],
  categoryAlias: [
    {required: true, message: '请输入分类别名', trigger: 'blur'},
    {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
  ]
})

// 定义事件
const handleEdit = (row) => {
  diaTitle.value = '编辑分类'
  categoryModel.value = row
  dialogVisible.value = true
}
const handleDelete = (row) => {

  article.categoryDelete(row, res => {
    ElMessage.success('删除成功')
    // 重新获取分类列表
    getCategory()
  }, err => {
    console.log(err)
  })
}
const handleAdd = () => {
  diaTitle.value = '添加分类'
  categoryModel.value = {
    categoryName: '',
    categoryAlias: ''
  }
  dialogVisible.value = true
}

const getCategory = () => {
  article.categoryGetAll(res => {
    category.value = res.data
  }, err => {
    console.log(err)
  })
}

const submitForm = () => {
  form.value.validate((valid) => {
    if (valid) {
      if (diaTitle.value === '添加分类') {
        article.categoryAdd(categoryModel.value, res => {
          getCategory();
          ElMessage.success('添加成功')
        }, err => {
          console.log(err)
        })
      } else {
        article.categoryUpdate(categoryModel.value, res => {
          getCategory();
          ElMessage.success('修改成功')
        }, err => {
          console.log(err)
        })
      }
      dialogVisible.value = false
    }
    return false
  })
}


// 定义生命周期方法
onMounted(() => {
  getCategory()
})

onUpdated(() => {
  getCategory()
})

</script>
<template>
  <!--  添加和编辑对话框-->
  <el-dialog v-model="dialogVisible" :title="diaTitle" width="30%">
    <el-form ref="form" :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="categoryModel.categoryName" maxlength="10" minlength="2"></el-input>
      </el-form-item>
      <el-form-item label="分类别名" prop="categoryAlias">
        <el-input v-model="categoryModel.categoryAlias" maxlength="10" minlength="1"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitForm"> 确认 </el-button>
        </span>
    </template>
  </el-dialog>
  <!--  分类列表-->
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章分类</span>
        <div class="extra">
          <el-button type="primary" @click="handleAdd">添加分类</el-button>
        </div>
      </div>
    </template>
    <el-table :data="category" style="width: 100%">
      <el-table-column label="序号" type="index" width="100"></el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
      <el-table-column label="创建人" prop="createBy"></el-table-column>
      <el-table-column label="更新人" prop="updateBy"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="handleEdit(row)"></el-button>
          <el-popconfirm title="">
            <template #reference>
            </template>
          </el-popconfirm>
          <el-popconfirm
              cancel-button-text="No"
              confirm-button-text="Yes"
              icon-color="#626AEF"
              title="确定要删除该条数据吗?"
              @confirm="handleDelete(row)"
          >
            <template #reference>
              <el-button :icon="Delete" circle plain type="danger"></el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据"/>
      </template>
    </el-table>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  //max-width: 80%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>