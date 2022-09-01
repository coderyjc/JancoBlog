<template>

  <div class="change-password">

    <el-row>

      <el-col
        :span="12"
        :offset="6"
      >
        <el-form
          label-width="80px"
          :model="password"
          class="password"
          :rules="rules"
          :ref="password"
        >
          <el-form-item
            label="旧密码"
            prop="old"
          >
            <el-input
              type="password"
              v-model="password.old"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="新密码"
            prop="password"
          >
            <el-input
              type="password"
              v-model="password.password"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="确认密码"
            prop="password2"
          >
            <el-input
              type="password"
              v-model="password.password2"
            ></el-input>
          </el-form-item>
          <el-button
            type="primary"
            @click="changePwd"
          >修改密码</el-button>
        </el-form>
      </el-col>

    </el-row>

  </div>

</template>

<script>
import { changePassword,logout } from '@/api/user'

export default {
  data() {
    var checkPassAgain = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入密码'))
      }
      if (value !== this.password.password) {
        return callback(new Error('两次密码不一致'))
      }
      callback()
    }
    return {
      password: {
        old: '',
        password: '',
        password2: '',
      },
      rules: {
        old: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        password2: [
          { required: true, validator: checkPassAgain, trigger: 'blur' },
        ],
      },
    }
  },
  methods: {
    changePwd() {
      var _this = this
      this.$refs[this.password].validate((valid) => {
        if (valid) {
          changePassword(
            '',
            _this.password.old,
            _this.password.password2
          ).then((res) => {
            let suc = res.extend.suc
            if(suc){
              // 修改成功
              this.$message({
                message: '修改成功，请重新登陆',
                type: 'success',
                duration: 3000
              })
              // 先退出登录，跳转登录页面
              logout().then(res => {
                if(res.extend.suc){
                  // 退出成功，跳转登录页面
                  _this.$router.push('/login')
                }
              })

            } else {
              this.$message.error('旧密码不正确')
            }
          })
        } else {
          // 告知注册失败
          return false
        }
      })
    },
  },
}
</script>

<style scoped>
.change-password {
  padding: 20px;
}
</style>