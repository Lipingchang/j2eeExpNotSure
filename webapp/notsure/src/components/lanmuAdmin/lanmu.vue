<template>
    <div>
        <el-input
            placeholder="栏目名字"
            v-model="channelTitle"
            maxlength="10"
            show-word-limit
            clearable>
        </el-input>
        <div class="diviv"></div>
        <el-input
            type="textarea"
            :rows="2"
            placeholder="栏目简介"
            v-model="channelDesc">
        </el-input>
        <div class="diviv"></div>
        <el-button @click="addChannel" >新建</el-button>
        
        <div>
            <ul>
                <li v-for="(channel,index) in channels" :key="index">
                    <div class="achannel">
                        <div>{{channel.channelId}}</div>
                        <div>{{channel.channelTitle}}</div>
                        <div>{{channel.channelDesc}}</div>
                        <div class="operate">
                            <el-button type="danger" @click="delchannel(channel.channelId,index,channelTitle)">删除栏目</el-button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    data: function(){
        return {
            channelTitle:'',
            channelDesc: '',
            channels: []
        }
    },
    methods: {
        getChannels: function () {
            this.$axios.get("/channels")
            .then((res) => {
                this.channels = res.data.data
            })
            .catch((err) => {
                this.$message({message:err.data,type:'warning'})
                console.log(err)
            })
        },
        addChannel: function () {
            let that = this
            let newtitile = this.channelTitle

            this.$axios.post("/channels",{
                title: that.channelTitle,
                desc: that.channelDesc
            })
            .then( (res) => {
                console.log(res.data)
                if ( res.data.statusCode == 200 ){
                    that.$notify({
                        title: '栏目添加成功',
                        message: "已添加:"+newtitile,
                        type:'success' 
                    })
                    that.channelTitle = ''
                    that.channelDesc = ''
                    that.channels.push(res.data.data)
                }else{
                    that.$message({
                        message: res.data.msg,
                        type: 'error'
                    })
                }
            })
            .catch( (err) => {
                console.log(err)
            })
        },
        delchannel: function (channelid,index,title) {
            let that = this;
            this.$axios.delete('channels',{
                data:{'channelid': channelid}
            })
            .then( (res) => {
                if ( res.data.statusCode == 200 ){
                    that.$notify({
                        title: '栏目删除成功',
                        message: "已删除:"+title,
                        type:'success' 
                    })
                    that.channels.splice(index,1)
                } else {
                    that.$notify({
                        title: '栏目删除失败',
                        message: "未删除:"+title,
                        type:'warning' 
                    })
                }
            })
            .catch( (err) => {
                console.log(err)
            })
        }
    },
    mounted: function () {
        this.getChannels();
    }
}
</script>

<style scoped>
.diviv {
    margin: 5px;
}
li {
    list-style: none;
}
.achannel>div {
    width: 30%;
    text-align: center;
    height: min-content;
}
.achannel:hover {
    border: 1px solid #409EFF;
}
.achannel {
    border: 1px solid rgba(64, 158, 255,0.2);
    transition: ease 0.5s;
    margin: 10px 0px;
    padding: 10px 0px;
    border-radius: 10px;
    display: flex;
    justify-content: space-around;
    align-items: center;
}
</style>
