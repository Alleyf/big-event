<script setup>

import {onMounted, onUpdated, reactive, ref} from 'vue'
import {Delete, Edit, Refresh, Search, Upload} from '@element-plus/icons-vue'
import article from '@/api/article'
import {QuillEditor} from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.prod.css'
import {useTokenStore} from "@/stores/token.js";
import {ElMessage, ElMessageBox} from "element-plus";

//****************************************//
const tokenStore = useTokenStore()

/**
 * 分页配置
 */
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const total = ref(0)

const handleSizeChange = (val) => {
  queryForm.value.pageSize = val
  getAllArticle()
}
const handleCurrentChange = (val) => {
  queryForm.value.pageNum = val
  getAllArticle()
}

/**
 * 查询表单配置
 */
const queryForm = ref({
  pageNum: 1,
  pageSize: 5,
  categoryId: null,
  state: null,
})
/**
 * 重置查询表单
 */
const resetQueryForm = () => {
  queryForm.value.categoryId = null
  queryForm.value.state = null
  getAllArticle()
}
/**
 * 文章列表
 */
const articleLs = ref([])
const categorys = ref([])
// 请求函数配置
const getAllArticle = () => {
  article.articleGetByPage(queryForm.value, res => {
    total.value = res.data.total
    articleLs.value = res.data.items

    // 处理数据对分类id进行转化为分类名称
    for (let i = 0; i < articleLs.value.length; i++) {
      for (let j = 0; j < categorys.value.length; j++) {
        if (articleLs.value[i].categoryId === categorys.value[j].id) {
          articleLs.value[i].categoryName = categorys.value[j].categoryName
        }
      }
    }

  }, err => {
    if (err.status === 401) {
      alert('登录失效，请重新登录')
    }
  })
}


const getCategory = () => {
  article.categoryGetAll(res => {
    categorys.value = res.data
  }, err => {
    console.log(err)
  })
}

/*
 * 添加文章相关配置
 */
//控制抽屉是否显示
const drawerTitle = ref('添加文章')
const visibleDrawer = ref(false)
//添加表单数据模型
const defaultAvatar = ref("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")
const articleRef = ref()
const mdRef = ref(null)
const articleModel = ref({
  title: null,
  categoryId: null,
  coverImg: null,
  content: null,
  state: null
})


const resetAddForm = () => {
  articleModel.value = {
    title: null,
    categoryId: null,
    coverImg: null,
    content: null,
    state: null
  }
}

const addArticle = () => {
  resetAddForm()
  console.log(mdRef.value)
  visibleDrawer.value = true
}

const articleRules = reactive({
  title: [
    {required: true, message: '请输入文章标题', trigger: 'blur'},
    {min: 2, max: 20, message: '标题长度在 2 到 20 个字符', trigger: 'blur'}
  ],
  categoryId: [
    {required: true, message: '请选择文章分类', trigger: 'change'}
  ],
  coverImg: [
    {required: true, message: '请上传文章封面', trigger: 'change'}
  ],
  content: [
    {required: true, message: '请输入文章内容', trigger: 'blur'}
  ]
})


const publishArticle = (state) => {
  articleModel.value.state = state

  // 用户未上传头像则使用默认头像
  if (articleModel.value.coverImg === null) {
    articleModel.value.coverImg = defaultAvatar.value
  }

  articleRef.value.validate((valid) => {
    if (valid) {
      if (drawerTitle.value === '添加文章') {
        article.articleAdd(articleModel.value, res => {
          visibleDrawer.value = false
          resetQueryForm()
          ElMessage.success('发布成功')
        }, err => {
          console.log(err)
        })
      } else {
        ElMessage.error('功能尚在研发中~')
        // article.articleUpdate(articleModel.value, res => {
        //   visibleDrawer.value = false
        //   resetQueryForm()
        //   ElMessage.success('修改成功')
        // }, err => {
        //   console.log(err)
        // })
      }
    }
    return false
  })
}

const editArticle = (paper) => {
  articleModel.value = paper
  drawerTitle.value = '编辑文章'
  visibleDrawer.value = true
}
const deleteArticle = (paper) => {
  ElMessageBox.confirm('确定删除该文章吗', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.error('功能尚在研发中~')
    // article.articleDelete(paper.id, res => {
    //   ElMessage.success('删除成功')
    //   resetQueryForm()
    // }, err => {
    //   console.log(err)
    // })
  }).catch(() => {
  })
}


//图片上传回调函数
const handleSuccess = (res) => {
  //返回的是一个数组，数组中包含所有上传的图片对象，对象包含一个键（源文件名称）和一个值（上传后的文件url）
  const imgObj = res.data[0]
  articleModel.value.coverImg = imgObj[Object.keys(imgObj)[0]]
  console.log(articleModel.value.coverImg)
}

onMounted(() => {
  getCategory()
  getAllArticle()
})


onUpdated(() => {
  getCategory()
  getAllArticle()
})

</script>


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
              <el-select v-model="queryForm.categoryId" clearable placeholder="分类">
                <el-option v-for="item in categorys" :key="item.id" :label="item.categoryName" :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="queryForm.state" clearable placeholder="状态">
                <el-option label="已发布" value="已发布"/>
                <el-option label="草稿" value="草稿"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button :icon="Search" type="primary" @click="getAllArticle"/>
              <el-button :icon="Refresh" type="warning" @click="resetQueryForm"/>
            </el-form-item>
          </el-form>
        </div>

        <div>
          <el-table :data="articleLs" :total="total" row-class-name="success-row"
                    width="100%">
            <el-table-column label="标题">
              <template #default="{ row }">
                <a :href="row.coverImg" target="_blank">{{ row.title }}</a>
              </template>
            </el-table-column>
            <el-table-column label="分类" prop="categoryName"/>
            <el-table-column label="发表时间" prop="createTime"/>
            <el-table-column label="状态" prop="state"/>
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-row>
                  <el-button :icon="Edit" circle type="primary" @click="editArticle(row)"/>
                  <el-button :icon="Delete" circle type="danger" @click="deleteArticle(row)"/>
                </el-row>
              </template>
            </el-table-column>
            <template #empty>
              <el-empty/>
            </template>
          </el-table>
        </div>

        <!-- 分页 -->
        <div class="el-pag">
          <el-pagination v-model:current-page="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
                         :background="background" :disabled="disabled" :page-sizes="[2, 5, 10, 20]" :small="small"
                         :total="total" layout="total,sizes, prev, pager, next,jumper" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"/>
        </div>

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="drawerTitle" direction="rtl" size="50%">
          <!-- 添加文章表单 -->
          <el-form ref="articleRef" :model="articleModel" :rules="articleRules" label-width="100px">
            <el-form-item label="文章标题" prop="title">
              <el-input v-model="articleModel.title" maxlength="20" minlength="2" placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item label="文章分类" prop="categoryId">
              <el-select v-model="articleModel.categoryId" placeholder="请选择">
                <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="文章封面" prop="coverImg">
              <el-upload :auto-upload="true" :headers="{'Authorization': tokenStore.token}"
                         :on-success="handleSuccess"
                         :show-file-list="false"
                         action="/api/file/uploadToQiniu"
                         class="avatar-uploader"
                         name="file"
              >
                <el-image v-if="articleModel.coverImg" :src="articleModel.coverImg" circle class="avatar el-upload"/>
                <el-avatar v-else :src="defaultAvatar"
                           class="avatar-uploader-icon"
                />
              </el-upload>
            </el-form-item>
            <el-form-item label="文章内容" prop="content">
              <div class="editor">
                <quill-editor
                    ref="mdRef"
                    v-model:content="articleModel.content"
                    contentType="html"
                    theme="snow"
                >
                </quill-editor>
              </div>
            </el-form-item>

            <!--            <el-form-item label="文章简介" prop="summary">-->
            <!--              <div id="tinyMd">-->
            <!--                <Editor-->
            <!--                    :init="{-->
            <!--        toolbar_mode: 'sliding',-->
            <!--        plugins: 'ai tinycomments mentions anchor autolink charmap codesample emoticons image link lists media searchreplace table visualblocks wordcount checklist mediaembed casechange export formatpainter pageembed permanentpen footnotes advtemplate advtable advcode editimage tableofcontents mergetags powerpaste tinymcespellchecker autocorrect a11ychecker typography inlinecss',-->
            <!--        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | align lineheight | tinycomments | checklist numlist bullist indent outdent | emoticons charmap | removeformat',-->
            <!--        tinycomments_mode: 'embedded',-->
            <!--        tinycomments_author: 'Author name',-->
            <!--        mergetags_list: [-->
            <!--          { value: 'First.Name', title: 'First Name' },-->
            <!--          { value: 'Email', title: 'Email' },-->
            <!--        ],-->
            <!--        ai_request: (request, respondWith) => respondWith.string(() => Promise.reject(`See docs to implement AI Assistant`)),-->
            <!--                }"-->
            <!--                    api-key="ty5kr6krl8lyrozh1cawy1x20kvnv9f5p2r4ww1khiwe4oji"-->
            <!--                    initial-value="Welcome to TinyMCE!"-->
            <!--                />-->
            <!--              </div>-->
            <!--            </el-form-item>-->

            <el-form-item>
              <el-button :icon="Upload" type="primary" @click="publishArticle(`已发布`)">发布</el-button>
              <el-button :icon="Upload" type="info" @click="publishArticle(`草稿`)">草稿</el-button>
            </el-form-item>
          </el-form>
        </el-drawer>
      </el-card>
    </div>

  </div>
</template>


<style lang="scss" scoped>

@media (min-width: 1024px) {
  #tinyMd {
    display: flex;
    flex-direction: column;
    place-items: center;
    width: 100%;
  }
}

.card-header {
  margin: 10px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 200px;
  }
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

/* 抽屉样式 */
.avatar-uploader {
  :deep(.el-upload) {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
  }

  .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .el-upload:hover {
    border-color: var(--el-color-primary);
  }

}


</style>