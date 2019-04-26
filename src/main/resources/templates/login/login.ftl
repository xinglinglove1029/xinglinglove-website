<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">
  body{
      margin: 0;
      padding: 0;
  }
  .login-container{
      position: fixed;
      height: 100%;
      width: 100%;
      background:url(../../static/image/login.png) no-repeat;
      background-size: cover;
      -moz-background-size: cover;
      -webkit-background-size: cover
  }
  .login-container .title {
      font-size: 26px;
      font-weight: 400;
      color: #8c5c5c;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
  }
  .login-container .login-form {
      position: absolute;
      left: 0;
      right: 0;
      width: 400px;
      padding: 35px 35px 15px 35px;
      margin: 120px auto;
  }
</style>
<body>
<div id="app">
    <div class="login-container">
        <el-form autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left" label-width="0px"
                 class="card-box login-form">
            <h3 class="title">spring-boot-admin</h3>
            <el-form-item prop="username">
                <el-input name="username" type="text" v-model="loginForm.username" autoComplete="on" placeholder="用户名" />
            </el-form-item>
            <el-form-item prop="password">
                <el-input name="password" :type="pwdType" @keyup.enter.native="handleLogin" v-model="loginForm.password" autoComplete="on"
                          placeholder="密码"></el-input>
                <span class="show-pwd" @click="showPwd"></span>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
                    登录
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</div>
<#include "../common/scripts.ftl">
<script>
    Vue.prototype.$http = axios.create({
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        }
    });
    new Vue({
        el: '#app',
        data() {
            const validateUsername = (rule, value, callback) => {
                const usernameRegex = /^[A-Za-z0-9]{5,16}$/;
                if (!usernameRegex.test(value)) {
                    callback(new Error('请输入正确的用户名'));
                } else {
                    callback();
                }
            };
            const validatePass = (rule, value, callback) => {
                if (value.length < 3) {
                    callback(new Error('密码不能小于3位'));
                } else {
                    callback();
                }
            };
            return {
                listLoading: true,
                loginForm: {
                    username: 'yangwensheng',
                    password: '123456'
                },
                loginRules: {
                    username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePass }]
                },
                loading: false,
                pwdType: 'password'
            };
        },
        created() {

        },
        methods: {
            showPwd() {
                if (this.pwdType === 'password') {
                    this.pwdType = '';
                } else {
                    this.pwdType = 'password';
                }
            },
            handleLogin() {
                let _this = this;
                _this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        _this.loading = true;
                        _this.$http({
                            method: 'POST',
                            url: '/userLogin',
                            data: _this.loginForm
                        }).then(function (res) {
                            _this.loading = false;
                            if(res.data.code === 200){
                                window.location.href="/index";
                            }else{
                                _this.$message.error(res.data.message);
                            }
                        }).catch(function (response) {
                            console.log(response);
                            window.location.href="/userLogin";
                        });
                    } else {
                        console.log('error submit!!');
                        return false
                    }
                })
            }
        }
    });
</script>
</body>
</html>