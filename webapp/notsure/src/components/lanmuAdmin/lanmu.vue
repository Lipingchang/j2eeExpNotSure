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
        <el-button >新建</el-button>
        
        <div>
            <ul>
                <li v-for="(channel,id) in channels" :key="id">{{channel}}</li>
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
                console.log(res.data)
            })
            .catch((err) => {
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
</style>
