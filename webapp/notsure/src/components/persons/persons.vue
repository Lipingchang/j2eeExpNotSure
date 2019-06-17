<template>
    <div>
        <ul>
            <li class="person" v-for="(person,personindex) in persons" :key="personindex" :class="{'on':person.haschanged}">
                <div class="baseinfo">
                    <span class="personid">{{person.personId}}</span>
                    <span class="personname">{{person.personName}}</span>
                    <span class="rolename" :style="{'background-color':getRoleColor(person.roleName)}">{{person.roleName}}</span>

                    <div class="rolenamemodify">
                        <el-radio 
                            @change="handleRolenameChange(personindex)"
                            v-for="(name,index) in roleNameList" 
                            v-model="person.newrolename" 
                            :label="index" 
                            border 
                            :key="index"></el-radio>
                        <el-radio 
                            @change="handleRolenameChange(personindex)"
                            class="myradioinput" 
                            v-model="person.newrolename" 
                            :label="person.temprolename" 
                            border>
                            <input v-model="person.temprolename"/>
                        </el-radio>
                    </div>

                </div>
                <div class="accessmodify">
                    <div class="checkboxs">
                        <el-checkbox-group v-model="person.newaccesses" @change="handleAccessChange(personindex)">
                            <el-checkbox 
                                v-for="(item,index) in access" 
                                :label="item.accessId" 
                                :key="index" 
                                :disabled="roleNameList[person.newrolename]!=undefined" 
                                >
                                {{item.accessId | formatAccessName}}<!--当用户选择的角色是预定的时候，就不能自由地选择用户权限 -->
                            </el-checkbox>
                        </el-checkbox-group>
                    </div>
                </div>
                <transition name="fade">
                    <div v-show="person.haschanged" class="btnbox">
                        <el-button type="primary" @click="handleSubmit(personindex)">修改</el-button>
                        <el-button type="danger" @click="handleReset(personindex)">复原</el-button>
                    </div>
                </transition>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    data() {
        return {
            persons: [],
            access: [],
            roleNameList:{
                '游客':  {color:'#909399',defaultAccess:[5]},
                '管理员':{color:'#F56C6C',defaultAccess:[1,2,3,4,5,6]},
                '编辑':  {color:'#E6A23C',defaultAccess:[2,3,4,5]},
                '质检员':{color:'#67C23A',defaultAccess:[3,5]},
            },
            freeName:{
                '自定义': '#303133'
            }
        }
    },
    filters: {
        formatAccessName: function(nn,c){
            switch ( nn ){
                case 1:
                    return "人员管理"
                case 2:
                    return "添加文章"
                case 3:
                    return "编辑文章"
                case 4:
                    return "删除文章"
                case 5:
                    return "浏览文章"
                case 6:
                    return "管理栏目"
            }
        }
    },
    methods: {
        handleSubmit: function(index){
            // console.log(this.persons[index])
            let that = this
            let pp = this.persons[index]
            this.$axios.post('/persons',{
                userid: pp['personId'],
                rolename: pp['newrolename'],
                accessList: pp['newaccesses']
            })
            .then( (res)=>{
                if ( res.data.statusCode == 200 ){
                    let person = res.data.data
                    that.initPerson(person)
                    that.persons.splice(index,1,person)
                    that.$notify({
                        title: '用户修改成功',
                        message: '用户:'+person.personName,
                        type:'success' 
                    })
                } else {
                    console.log('用户修改失败',res.data)
                    that.$notify({
                        title: '用户修改失败',
                        message: res.data.msg,
                        type:'warning' 
                    })
                }
            })
            .catch( (err)=>{
                console.log(err)
                that.$notify.error({
                    title: '用户修改失败',
                    message: err,
                })
            })
        },
        getRoleColor: function(roleName){
            for( let name in this.roleNameList ){
                if ( name == roleName )
                    return this.roleNameList[name].color
            }
            return this.freeName['自定义']
        },
        handleReset: function(index){
            let pp = this.persons[index]
            pp['haschanged'] = false
            pp['newrolename'] = pp['roleName']
            if ( this.roleNameList[pp['roleName']] == undefined ){
                pp['temprolename'] = pp['roleName']
            }

            while( pp['newaccesses'].length>0){
                pp['newaccesses'].pop()
            }
            for( let i in pp['accesses']){
                pp['newaccesses'].push(pp['accesses'][i]['accessId'])
            }

        },
        handleAccessChange: function(index){
            let before = this.persons[index]['accesses']
            let after = this.persons[index]['newaccesses']
            if ( before.length != after.length ){
                this.persons[index]['haschanged'] = true
                return;
            }
            for( let k=0; k<before.length; k++ ){
                if ( ! after.includes(before[k]['accessId'])){
                    this.persons[index]['haschanged'] = true
                    return;
                }
            }
            this.persons[index]['haschanged'] = false
        },
        handleRolenameChange: function(index) {
            let before = this.persons[index]['roleName']
            let after = this.persons[index]['newrolename']
            if ( before != after )
                this.persons[index]['haschanged'] = true
            else 
                this.persons[index]['haschanged'] = false

            // 如果是预设的rolename的话，就要设置role对应的权限列表
            if ( this.roleNameList[after] != undefined ){
                this.persons[index]['newaccesses'] = this.roleNameList[after].defaultAccess
            } else {
                this.persons[index]['newaccesses'] = [] // TODO 有个bug 从预设值转选到自定义的时候 会忘记触发changed的按钮
            }
        },
        initPerson: function(person){ // 把后台传过来的person对象，改变成persons列表中能用的
            person['newaccesses'] = []// 记录 改动后的权限列表值
            person['haschanged'] = false// 记录 前后是否改动了
            person['temprolename'] = '自定义'// 用户自定义的用户名
            person['newrolename'] = person['roleName']
            if ( this.roleNameList[ person['roleName'] ] == undefined ){
                person['temprolename'] = person['roleName']// 用户自定义的用户名
            }
            for( let k in person['accesses'] ){
                person['newaccesses'].push(person['accesses'][k].accessId)
            }
        },
        getPersons: function(){
            this.$axios.get("/persons")
            .then( (res)=>{
                for( let i = 0; i<res.data.data.length; i++ ){
                    this.initPerson(res.data.data[i])
                }
                this.persons = res.data.data
            })
            .catch( (res)=>{

            })
        },
        getAccess: function(){
            this.$axios.get('/access')
            .then( (res)=>{
                this.access = res.data.data
                console.log('accesslist:',this.access)
            })
            .catch( (err)=>{

            })
        }
    },
    mounted() {
        this.getPersons()
        this.getAccess()
    },
}
</script>

<style scoped>
.person.on {
    border-color: #E6A23C;
}
.person.on::before {
    content: '没保存';
    position: absolute;
    left: -65px;
    background-color: #E6A23C;
    border-radius: 5px;
    color: white;
    padding: 3px 5px;
}
.person {
    position: relative;
    display: flex;
    flex-wrap: nowrap;
    justify-content: flex-end;
    border: 1px solid rgba(64, 158, 255,0.2);
    border-radius: 10px;
    margin: 10px;
    padding: 10px;
    transition: ease 0.5s;
    height: 30px;
}
.person:hover {
    border: 1px solid rgba(64, 158, 255);
    height: 240px;
        overflow: hidden;
}
.baseinfo {
    flex-grow: 1;
}
.baseinfo .rolename {
    padding: 5px 10px;
    border-radius: 4px;
    color: white;
    font-weight: 600;
}
.baseinfo .personname {
    font-size: 30px;
    padding: 10px;
    margin: 0px 10px;
}
.baseinfo .personid {
    background-color: #409EFF;
    color: white;
    border-radius: 50%;
    padding: 10px;
}
.baseinfo {
    display: flex;
    align-items: center;
}
.accessmodify:hover {
    border: 1px solid rgba(64, 158, 255);
}

/* 用户名管理 */
.person:hover .rolenamemodify {
    display: block;
}
.rolenamemodify {
    display: none;
    margin: 10px;
    width: 100px;
}
.rolenamemodify>label {
    margin: 5px 10px;
}
.rolenamemodify input {
    border: 1px solid #EBEEF5;
    height: 25px;
    padding-left:  5px;
}
.rolenamemodify .myradioinput {
    padding-top: 5px;
}

/* 权限管理 */
.person:hover .accessmodify {
    display: flex;
    height: max-content;
}
.accessmodify {
    border: 1px solid rgba(64, 158, 255,0.2);
    border-radius: 10px;
    margin: 10px;
    padding: 10px;
    display: none;
    transition: ease 0.5s;
}
.accessmodify .checkboxs {
    margin: 10px;
    display: flex;
    width: 100px;
    flex-direction: column;
}

/* 修改按钮 */
.person:hover .btnbox {
    display: flex;
}
.btnbox {
    display: none;
    flex-direction: column;
    justify-content: center;
}
.btnbox>button {
    box-sizing: border-box;
    margin: 10px 0px;
    width: 80px;
}
.fade-enter-active {
    animation: fadein 1s;
}
.fade-leave-active {
    animation: fadein 1s reverse;
}
@keyframes fadein {
    0% {
        width: 0px;
        opacity: 0;
    }
    100% {
        width: 80px;
        opacity: 1;    
    }
}

</style>
