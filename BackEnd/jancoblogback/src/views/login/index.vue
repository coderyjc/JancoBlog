<template>
  <div class="login">

    <div class="container">
      <el-tabs
        :value="currPanel"
        :stretch="true"
        type="border-card"
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
import { setToken } from '@/utils/auth'
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
        username: 'admin',
        password: '333',
      },
      register: {
        username: '',
        password: '',
        password2: '',
      },
      rules: {
        username: [
          { required: true, validator: checkUserName, trigger: 'blur' },
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        password2: [
          { required: true, validator: checkPassAgain, trigger: 'blur' },
        ],
      },
      loading: false,
      registerVisiable: false,
    }
  },
  methods: {
    handleLogin() {
      this.$router.push({ path: '/' })
      this.loading = true
      this.$store
        .dispatch('user/login', this.login)
        .then((res) => {
          // 登录成功之后显示登录成功，然后跳转到首页
          this.$message({
            message: '登录成功',
            type: 'success',
          })
          this.$router.push({ path: '/' })
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    },
    handleRegister() {
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
              _this.currPanel = 'first'
            } else {
              // 失败
              this.$message.error('注册失败')
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
.login {
  width: 100%;
  height: 100%;
  background-image: url('../../assets/imgs/bg.jpg');
}
.container {
  width: 500px;
  margin: 10% 5%;
  float: right;

  .el-button {
    width: 80%;
    margin: 0 17%;
  }
}
</style>