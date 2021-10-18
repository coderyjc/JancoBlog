<template>
  <div class="personal-settings">
    <el-row :gutter="10">
      <el-col
        :span="18"
        :offset="3"
      >
        <!-- 展示信息: 头像、用户名、签名 -->
        <el-card class="show-info">
          <!-- 用户头像 -->
          <el-col :span="5">
            <el-avatar
              :size="120"
              :src="userAvatar"
            ></el-avatar>
          </el-col>
          <!-- 用户信息 -->
          <el-col :span="19">
            <el-form :disabled="!editBtn.user">
              <el-form-item label="用户名">
                <el-input v-model="user.userName"></el-input>
              </el-form-item>
              <el-form-item label="签名">
                <el-input v-model="user.userSignature"></el-input>
              </el-form-item>
            </el-form>
            <el-button
              type="primary"
              @click="submit_user"
              v-show="editBtn.user"
            >提交</el-button>
            <el-button
              type="primary"
              v-show="editBtn.user"
              @click="changeAvatarVisiable = true"
            >修改头像</el-button>
            <el-button
              v-show="editBtn.user"
              @click="editBtn.user = false;get_user_info()"
            >取消</el-button>
            <el-button
              class="edit-btn"
              v-show="!editBtn.user"
              @click="editBtn.user = true"
            >编辑</el-button>
          </el-col>
        </el-card>
        <!-- 个人信息：真实姓名、性别、邮箱、电话、地区、生日 -->
        <el-card>
          <h4>个人信息</h4>
          <el-form
            ref="form"
            :model="userInfo"
            label-width="80px"
            :disabled="!editBtn.personal"
          >
            <el-form-item label="真实姓名">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userRealName"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="性别">
              <el-select
                v-model="userInfo.userSex"
                placeholder="选择"
              >
                <el-option
                  label="男"
                  value="1"
                ></el-option>
                <el-option
                  label="女"
                  value="0"
                ></el-option>
                <el-option
                  label="未知"
                  value="-1"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userEmail"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="电话">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userTelephone"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="地区">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userRegion"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="生日">
              <el-col :span="11">
                <el-date-picker
                  type="date"
                  placeholder="选择日期"
                  v-model="userInfo.userBirthdate"
                ></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item>
            </el-form-item>
          </el-form>
          <el-button
            type="primary"
            @click="submit_user_info"
            v-show="editBtn.personal"
          >提交</el-button>
          <el-button
            v-show="editBtn.personal"
            @click="editBtn.personal = false;get_user_info()"
          >取消</el-button>
          <el-button
            class="edit-btn"
            v-show="!editBtn.personal"
            @click="editBtn.personal = true"
          >编辑</el-button>
        </el-card>
        <!-- 教育信息：学校、学位、入学时间、最高学历 -->
        <el-card>
          <h4>教育信息</h4>
          <el-form
            ref="form"
            :model="userInfo"
            label-width="80px"
            :disabled="!editBtn.education"
          >
            <el-form-item label="毕业院校">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userSchool"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="专业">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userMajor"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="入学时间">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userEnterSchoolDate"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="最高学历">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userAcademicDegree"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item>
            </el-form-item>
          </el-form>
          <el-button
            type="primary"
            @click="submit_user_info"
            v-if="editBtn.education"
          >提交</el-button>
          <el-button
            v-if="editBtn.education"
            @click="editBtn.education = false;get_user_info()"
          >取消</el-button>
          <el-button
            class="edit-btn"
            v-if="!editBtn.education"
            @click="editBtn.education = true"
          >编辑</el-button>
        </el-card>
        <!-- 工作信息：公司、职位、领域 -->
        <el-card>
          <h4>工作信息</h4>
          <el-form
            ref="form"
            :model="userInfo"
            label-width="80px"
            :disabled="!editBtn.work"
          >
            <el-form-item label="公司">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userCompany"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="职位">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userPosition"></el-input>
              </el-col>
            </el-form-item>
            <el-form-item label="领域">
              <el-col
                :span="structure.formSpan"
                :offset="structure.formOffset"
              >
                <el-input v-model="userInfo.userField"></el-input>
              </el-col>
            </el-form-item>
          </el-form>
          <el-button
            type="primary"
            @click="submit_user_info"
            v-if="editBtn.work"
          >提交</el-button>
          <el-button
            v-if="editBtn.work"
            @click="editBtn.work = false;get_user_info()"
          >取消</el-button>
          <el-button
            class="edit-btn"
            v-if="!editBtn.work"
            @click="editBtn.work = true"
          >编辑</el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog
      title="裁切封面"
      :visible.sync="changeAvatarVisiable"
      width="950px"
      center
    >
      <cropper-image
        :Name="cropperName"
        @uploadImgSuccess="handleUploadSuccess"
        ref="child"
      >
      </cropper-image>
    </el-dialog>
    <!--查看大封面-->
    <el-dialog
      title="查看大封面"
      :visible.sync="imgVisible"
      center
    >
      <img
        :src="imgName"
        v-if="imgVisible"
        style="width: 100%"
        alt="查看"
      >
    </el-dialog>
  </div>
</template>

<script>
import CropperImage from './component/CropperImage'
import {
  getUserDetailInfo,
  getUserInfo,
  updateUser,
  updateUserInfo,
} from '@/api/user'

export default {

  data() {
    return {
      structure: {
        formSpan: 10,
        formOffset: 0,
      },
      editBtn: {
        sign: false,
        education: false,
        personal: false,
        work: false,
        user: false,
      },
      option: {
        img: 'http://localhost:8080/avatar/10800.jpg',
      },
      changeAvatarVisiable: true,
      user: {},
      userInfo: {},
      userAvatar: '',
      //裁切图片参数
      cropperModel: false,
      cropperName: 'CropperImage',
      imgName: '',
      imgVisible: false,
    }
  },
  components: {
    'cropper-image': CropperImage,
  },
  created() {
    this.get_user_info()
  },
  methods: {
    get_user_info() {
      var _this = this
      getUserDetailInfo().then((res) => {
        _this.userInfo = res.extend.info
        _this.userAvatar =
          'http://localhost:8080/avatar/' + _this.userInfo.userId + '.jpg'
      })
      getUserInfo().then((res) => {
        _this.user = res.extend.user
      })
    },
    submit_user_info() {
      updateUserInfo(this.userInfo).then((res) => {
        if (res.extend.suc) {
          // 成功
          // 修改成功
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 3000,
          })
          this.editBtn.personal = false
          this.editBtn.education = false
          this.editBtn.work = false
          this.get_user_info()
        } else {
          // 修改成功
          this.$message({
            message: '修改失败',
            type: 'fail',
            duration: 3000,
          })
        }
      })
    },
    submit_user() {
      updateUser(this.user.userName, this.user.userSignature).then((res) => {
        if (res.extend.suc) {
          // 成功
          // 修改成功
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 3000,
          })
          this.editBtn.personal = false
          this.editBtn.education = false
          this.editBtn.work = false
          this.editBtn.user = false
          this.get_user_info()
        } else {
          // 修改成功
          this.$message({
            message: '修改失败',
            type: 'fail',
            duration: 3000,
          })
        }
      })
    },
    //封面设置
    uploadPicture(name) {
      this.cropperName = name
      this.cropperModel = true
    },
    //图片上传成功后
    handleUploadSuccess(data) {
      console.log(data)
      switch (data.name) {
        case 'flagImg':
          this.formValidate.mainImage = ''
          console.log('最终输出' + data.name)
          break
      }
      this.cropperModel = false
    },
  },
}
</script>

<style lang="scss" scoped>
.personal-settings {
  padding: 10px;

  .el-card {
    margin-top: 10px;
  }

  // 展示头像、用户名、签名
  .show-info {
    padding-bottom: 15px;
    .user-name {
      font-size: 25px;
      font-weight: 600;
      margin: 20px;
    }

    .signature {
      font-size: 18px;
      margin: 20px;
    }
  }

  // 编辑按钮
  .edit-btn {
    float: right;
    margin-bottom: 8px;
  }
}
///////////////////////////////////////////

.upload-list-cover {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  padding: 0 40px;
  align-items: center;
  background: rgba(0, 0, 0, 0.6);
  opacity: 0;
  transition: opacity 1s;
}
.cover_icon {
  font-size: 30px;
}
.upload-btn {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  border: 1px solid #cccccc;
  border-radius: 5px;
  overflow: hidden;
  box-shadow: 0 0 1px #cccccc;
}
.upload-btn:hover {
  border: 1px solid #69b7ed;
}
.upload-btn i {
  margin: 5px;
}
</style>