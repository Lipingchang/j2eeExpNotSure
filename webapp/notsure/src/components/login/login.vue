<template>
    <el-container style="width:600px; margin:auto;">
        <el-main >


            <!-- <el-button style="width:100%;" type="primary" :disabled="validateinput">登陆</el-button> -->
            
            <el-form :label-position="'left'" label-width="80px" :rules="rules" :model="uploadform" ref="uploadform">
                <el-form-item label="用户名" prop="username">
                    <el-input
                        v-model="uploadform.username"
                        placeholder="请输用户名"
                        clearable>
                    </el-input>
                </el-form-item>
                <el-form-item  label="密码" prop="password">
                    <el-input 
                        v-model="uploadform.password"
                        placeholder="请输入密码" 
                        show-password>
                    </el-input>
                </el-form-item>
                <el-form-item  label="重输密码" prop="repassword" v-if="isRegister">
                    <el-input 
                        v-model="uploadform.repassword"
                        placeholder="再次输入密码" 
                        show-password>
                    </el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm">{{!isRegister?'登陆':'注册'}}</el-button>
                    <el-checkbox class="registercheckbox" v-model="isRegister">注册</el-checkbox>
                </el-form-item>
            </el-form>

        </el-main>
    </el-container>
</template>

<script>
export default {
    data: function(){
        return {
            uploadform: {
                username: 'laoA',
                password: 'aaaa',
                repassword: '',
            },
            isRegister: false,
        }
    },
    computed:{
        rules: function(){
            if ( this.isRegister) {
                return {
                    repassword: [
                        { validator: (rule,value,callback) => {
                            if ( value === '' ){
                                callback(new Error("不能为空"))
                            }else if ( value !== this.uploadform.password ){
                                callback(new Error("两次密码不一样"))
                            } else {
                                callback();
                            }
                        }}
                    ],
                    password: [
                        { validator: (rule,value,callback) => {
                            if ( value === '' ){
                                callback(new Error("不能为空"))
                            } else if (value.length<6){
                                callback(new Error("密码大于6位"))
                            }else {
                                if (this.uploadform.repassword !== '') {
                                    this.$refs.uploadform.validateField('repassword');
                                }
                                callback();                            
                            }
                        }}
                    ],
                    username: [
                        { validator: (rules,value,callback) => {
                            if ( value === '' ){
                                callback(new Error("不能为空"))
                            } else {
                                callback();
                            }
                        }}
                    ]
                }
            }else{
                return {
                    password: [
                        { validator: (rule,value,callback) => {
                            if ( value === '' ){
                                callback(new Error("不能为空"))
                            } else {
                                if (this.uploadform.repassword !== '') {
                                    this.$refs.uploadform.validateField('repassword');
                                }
                                callback();                            
                            }
                        }}
                    ],
                    username: [
                        { validator: (rules,value,callback) => {
                            if ( value === '' ){
                                callback(new Error("不能为空"))
                            } else {
                                callback();
                            }
                        }}
                    ]
                }
            }
        }
    },
    methods: {
        submitForm: function(){
            let that = this;
            this.$refs.uploadform.validate((valid) => {
                if (valid) {
                    if ( this.isRegister ){
                        this.$axios.post('/register',{
                            username: this.uploadform.username,
                            password: this.uploadform.password
                        })
                        .then(function (res) {
                            if ( res['data']['statusCode'] == 200 ){
                                that.$message({
                                    message: res['data']['msg'],
                                    type: 'success'
                                })
                                that.uploadform.password = '';
                                that.uploadform.repassword = '';
                                that.isRegister = false;
                            }else{
                                that.$message({
                                    message: res['data']['msg'],
                                    type: 'warning'
                                })
                            }
                        })
                        .catch(function (err) {
                            console.error(err)
                            that.$message({
                                message: "未知错误"+err,
                                type: 'error'
                            })                            
                        })



                    }else{
                        this.$axios.post('/login',{
                            username: this.uploadform.username,
                            password: this.uploadform.password
                        })
                        .then(function (res) {
                            if ( res['data']['statusCode'] == 200 ){
                                that.$message({
                                    message: '登陆成功',
                                    type: 'success'
                                });
                                console.log(res['data']['data'])
                                that.$store.commit('userlogin', res['data']['data'])
                                setTimeout(()=>{that.$router.push("/")},500);
                            } else {
                                that.$message({
                                    message: res['data']['msg'],
                                    type: 'warning'
                                })
                            }
                        })
                        .catch(function (error) {
                            console.error(error)
                            that.$message({
                                message: "未知错误",
                                type: 'error'
                            })
                        });

                    }
                } else {


                }
            });
        }
    }
}
</script>

<style>
.registercheckbox {
    float: right;
}
</style>
