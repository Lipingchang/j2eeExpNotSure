<template>
    <div class="page-component__scroll">
        <div class="editor">
            <el-form ref="editorFrom" :model="editorFrom" label-width="80px" :rules="editorFrom.rules">
                <el-form-item label="文章标题" prop="title">
                    <el-input v-model="editorFrom.title"/>
                </el-form-item>
                <el-form-item label="作者名字" prop="editorName">
                    <el-input v-model="editorFrom.editorName"/>
                </el-form-item>
                <el-form-item label="栏目名称" prop="channelIds">
                    <el-checkbox-group 
                        v-model="editorFrom.channelIds"
                        :max="3">
                        <el-checkbox v-for="channel in channels" :label="channel.channelId" :key="channel.channelId">{{channel.channelTitle}}</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item label="文章内容" prop="content">
                    <el-input
                        type="textarea"
                        v-model="editorFrom.content"
                        :autosize="{ minRows: 10, maxRows: 20}"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addArticle" v-if="!editorInChangerMode">立即创建</el-button>
                    <el-button type="primary" @click="changeArticle" v-else>修改！</el-button>
                    <el-button @click="resetForm">重置</el-button>

                </el-form-item>
            </el-form>
        </div>
        <el-divider></el-divider>
        <div class="viewer">
            <div>
                <ul>
                    <li class="title">
                        <div><span>文章ID</span></div>
                        <div><span>文章作者</span></div>
                        <div><span>文章标题</span></div>
                        <div><span>添加人员名字</span></div>
                        <div><span>一些操作</span></div>
                        
                    </li>
                    <li v-for="(article,index) in articles" 
                        :key="article.article_id" 
                        class="article" 
                        >
                        <div class="articleid"><span>{{article.article_id}}</span></div>
                        <div class="articleeditor"><span>{{article.article_editor}}</span></div>
                        <div class="articletitle"><span>{{article.article_title}}</span></div>
                        <div class="articlepersonname"><span>{{article.person_name}}</span></div>
                        <el-button-group>
                            <el-button plain  
                                @click="showViewer(article.article_id,index)"
                                icon="el-icon-reading"
                                ></el-button>
                            <el-button type="primary"  icon="el-icon-edit" @click="loadArticle(article.article_id,index)"></el-button>
                            <el-button type="danger"  icon="el-icon-delete" @click="delArticle(article.article_id,index)"></el-button>
                        </el-button-group>

                    </li>
                </ul>
            </div>

            <el-dialog :title="'文章内容'" :visible.sync="showArticleContent" width="99%">
                <p class="showtitle">{{showTitle}}</p>
                <span>作者：{{showEditor}}</span>
                <span v-for="channelid in showChannels" :key="channelid" class="showchannel">{{getChannelName(channelid)}}</span>
                <pre class="showcontent">{{showContent}}</pre>
            </el-dialog>
        </div>
    </div>
</template>

<script>

export default {
    data: function () {
        return {
            channels: [],
            articles: [],
            editorFrom: {
                title: '',
                editorName: '',
                content: '',
                channelIds: [],
                personId: this.$store.state.userinfo.id,
                changeArticleId: '',
                rules: {
                    title: [{required:true,message:'标题不能为空',trigger:'blur'}],
                    editorName: [{required:true,message:'作者名字不能为空',trigger:'blur'}],
                    content: [{required:true,message:'文章内容不能为空',trigger:'blur'}],
                    channelIds: [{type:'array',required:true,message:'至少选择一个栏目',trigger:'change'}]
                }
            },

            showArticleContent: false,
            showTitle: '',
            showEditor: '',
            showChannels: [],
            showContent: '',

            editorInChangerMode: false, // 默认添加 文章， true的时候修改文章
        }
    },
    methods: {
        loadArticle: function( articleId , index ){
            this.editorInChangerMode = true
            this.$axios.get('/articles/'+articleId)
            .then( (res) => {
                let from = this.editorFrom
                this.editorFrom.changeArticleId = articleId

                from.title = this.articles[index].article_title
                from.editorName = this.articles[index].article_editor
                from.channelIds = this.articles[index].channelIds    
                from.content = res.data.data.articleContent
                // 生硬的滚动
                document.body.scrollTop = 0
                document.documentElement.scrollTop = 0

            })
        },
        addArticle: function(){
            this.$refs['editorFrom'].validate( (valid) => {
                if ( valid ){
                    let newtitile = this.editorFrom.title
                    this.$axios.post('/articles',this.editorFrom)
                    .then( (res) => {
                        console.log(res)
                        this.$notify({
                            title: '文章添加成功',
                            message: "已添加:"+newtitile,
                            type:'success' 
                        })
                        this.getArticles() // TODO 改成push this.articles
                        this.resetForm()
                    })
                    .catch( (err) => {
                        this.$notify({
                            title: '文章添加失败',
                            type:'error' 
                        })
                    })
                } else {
                    return false
                }
            })
        },
        changeArticle: function () {
            this.$refs['editorFrom'].validate( (valid) => {
                if ( valid ){
                    let newtitile = this.editorFrom.title
                    this.$axios.patch('/articles',this.editorFrom)
                    .then( (res) => {
                        console.log(res)
                        this.$notify({
                            title: '文章修改成功',
                            message: "已添加:"+newtitile,
                            type:'success' 
                        })
                        this.getArticles() // TODO 改成push this.articles
                        this.resetForm()
                    })
                    .catch( (err) => {
                        this.$notify({
                            title: '文章修改失败',
                            type:'error' 
                        })                    
                    })
                } else {
                    return false
                }
            })       
        },
        delArticle: function(articleid,index){
            this.$axios.delete('/articles/'+articleid)
            .then( (res)=>{
                if ( res.data.statusCode == 200 ){
                    let newtitile = this.articles[index].article_title
                    this.$notify({
                        title: '文章删除成功',
                        message: "已删除:"+newtitile,
                        type:'success' 
                    })
                    this.articles.splice(index,1)
                }else {
                    this.$notify({
                        title: '文章删除失败',
                        type:'error' 
                    })    
                }
            })
        },
        resetForm() {
            this.$refs['editorFrom'].resetFields()
            this.editorInChangerMode = false
        },
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

        getArticles: function() {
            this.$axios.get('/articles')
            .then( (res)=> {
                this.articles = res.data.data
            })
            .catch( (err)=> {
                console.log('get articles', err)
            })
        },
        getChannelName: function( channelid ){
            for( let i in this.channels ){
                if ( this.channels[i].channelId == channelid ){
                    return this.channels[i].channelTitle
                }
            }
            return 'channel Not Found!'
        },
        showViewer: function( articleId , index ){
            this.showArticleContent = true
            this.$axios.get('/articles/'+articleId)
            .then( (res) => {
                this.showTitle = this.articles[index].article_title
                this.showEditor = this.articles[index].article_editor
                this.showChannels = this.articles[index].channelIds    
                this.showContent = res.data.data.articleContent
            })
        },
    },
    mounted: function () {
        this.getArticles()
        this.getChannels()    
    }
}
</script>

<style scoped>
li {
    display: flex;
    justify-content: space-between;
    text-align: center;
    margin: 20px 0px;
    align-items: center;
    padding: 15px;

}
.title div{
    border: 1px solid #409EFF; 
    padding: 10px;
    background-color: #409EFF;
    color: white;
    font-weight: 600;
    border-radius: 10px;
    margin: 0px 5px;
}
.article {
    border: 1px solid rgba(64, 158, 255,0.2);
    border-radius: 10px;
    margin: 10px;
    transition: ease 0.5s;
}
.article:hover {
    border: 1px solid rgba(64, 158, 255);
    overflow: hidden;
}
.articleid {
    background: #409EFF;
    border-radius: 50%;
    padding: 5px 10px;
    color: white;
}
pre {
    background-color: aliceblue;
    padding: 10px;
    width: min-content;
}
.showtitle {
    font-size: 25px;
    font-weight: 800;
}
.showchannel {
    background: #E6A23C;
    color: white;
    border-radius: 5px;
    padding: 2px 5px;
    margin: 0 5px;
}
</style>
