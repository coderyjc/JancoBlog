<template>
  <div class="login">
    <vue-particles
      class="particles"
      color="#fff"
      linesColor="#fff"
      shapeType="polygon"
      :hoverEffect="true"
      :clickEffect="false"
      :linesWidth="2"
      :lineOpacity="0.6"
    ></vue-particles>
    <div class="container">
      <el-tabs
        :value="currPanel"
        :stretch="true"
        type="border-card"
        v-loading="loading"
        element-loading-text="登陆中"
      >
        <!-- 登录界面 -->
        <el-tab-pane
          label="登录"
          name="first"
        >
          <el-form
            label-width="80px"
            :model="login"
            class="login-form"
          >
            <el-form-item label="用户名">
              <el-input v-model="login.username"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input
                type="password"
                v-model="login.password"
                @keyup.enter.native="handleLogin"
              ></el-input>
            </el-form-item>
            <el-button
              type="primary"
              @click="handleLogin"
            >登录</el-button>
          </el-form>
        </el-tab-pane>
        <!-- 注册界面 -->
        <el-tab-pane
          label="注册"
          name="second"
        >
          <el-form
            label-width="80px"
            :model="register"
            class="register"
            :rules="rules"
            :ref="register"
          >
            <el-form-item
              label="用户名"
              prop="username"
            >
              <el-input
                type="name"
                v-model="register.username"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="密码"
              prop="password"
            >
              <el-input
                type="password"
                v-model="register.password"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="确认密码"
              prop="password2"
            >
              <el-input
                type="password"
                v-model="register.password2"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="验证码"
              prop="code"
            >
              <el-col :span="12">
                <el-input
                  type="text"
                  v-model="register.code"
                  @keyup.enter.native="handleRegister"
                >
                </el-input>
              </el-col>
              <el-image
                :src="verifyCode"
                @click="generateVerifyCode"
              ></el-image>
            </el-form-item>
            <el-button
              type="primary"
              @click="handleRegister"
            >注册</el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { checkUserNameUnique, register } from '@/api/user'

export default {
  name: 'Login',
  data() {
    var checkUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名不能为空'))
      }
      checkUserNameUnique(value).then((res) => {
        if (res.extend.unique) {
          callback()
        } else {
          return callback(new Error('用户名已被注册'))
        }
      })
    }
    var checkPassAgain = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入密码'))
      }
      if (value !== this.register.password) {
        return callback(new Error('两次密码不一致'))
      }
      callback()
    }
    return {
      currPanel: 'first',
      login: {
        username: '',
        password: '',
      },
      register: {
        username: '',
        password: '',
        password2: '',
        code: '',
      },
      rules: {
        username: [
          { required: true, validator: checkUserName, trigger: 'blur' },
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        password2: [
          { required: true, validator: checkPassAgain, trigger: 'blur' },
        ],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
      },
      loading: false,
      registerVisiable: false,
      verifyCode: '',
    }
  },
  created() {
    this.generateVerifyCode()
  },
  methods: {
    generateVerifyCode() {
      var url = 'http://101.201.64.102:8000/user/getverifycode?' + Math.random()
      // var url = 'http://localhost:8000/user/getverifycode?' + Math.random()
      this.verifyCode = url
    },
    handleLogin() {
      this.loading = true
      this.$store
        .dispatch('user/login', this.login)
        .then((res) => {
          // 登录成功之后显示登录成功，然后跳转到首页
          this.$message({
            message: '登录成功',
            type: 'success',
          })
          setTimeout(() => {
            this.$router.push({ path: '/' })
          }, 1000)
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    async handleRegister() {
      var _this = this
      this.$refs[this.register].validate((valid) => {
        if (valid) {
          // 提交注册表单
          register(this.register).then((res) => {
            if (res.extend.success) {
              // 成功
              this.$message({
                message: '注册成功',
                type: 'success',
              })
              // 跳转登录
              _this.login.username = _this.register.username
              _this.login.password = _this.register.password
              _this.register.username = ''
              _this.register.password = ''
              _this.register.password2 = ''
              _this.register.code = ''
              _this.currPanel = 'first'
              this.generateVerifyCode()
            } else {
              // 失败
              this.generateVerifyCode()
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

<style lang="scss" scoped>
.particles {
  width: 100%;
  height: 90vh;
  position: absolute;
}
.login {
  width: 100vw;
  height: 100vh;
  background-image: linear-gradient(#0ed2f7, #0ed2f7);
  display: flex;
  justify-content: center;
  align-items: center;
}
.container {
  margin-bottom: 5%;
  width: 400px;
  float: right;

  .el-button {
    width: 80%;
    margin: 0 17%;
  }
}
</style>