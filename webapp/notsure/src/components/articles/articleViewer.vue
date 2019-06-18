<template>
    <div>
        <div>
            <ul>
                <li class="title">
                    <div><span>文章ID</span></div>
                    <div><span>文章作者</span></div>
                    <div><span>文章标题</span></div>
                    <div><span>添加人员名字</span></div>
                </li>
                <li v-for="(article,index) in articles" 
                    :key="article.article_id" 
                    class="article" 
                    @click="showViewer(article.article_id,index)" >
                    <div class="articleid"><span>{{article.article_id}}</span></div>
                    <div class="articleeditor"><span>{{article.article_editor}}</span></div>
                    <div class="articletitle"><span>{{article.article_title}}</span></div>
                    <div class="articlepersonname"><span>{{article.person_name}}</span></div>
                </li>
            </ul>
        </div>

        <el-dialog :title="'文章内容'" :visible.sync="showArticleContent" width="100%">
            <p class="showtitle">{{showTitle}}</p>
            <span>作者：{{showEditor}}</span>
            <span class="showchannel" v-for="channelid in showChannels" :key="channelid">{{getChannelName(channelid)}}</span>
            <pre class="showcontent">{{showContent}}</pre>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data: function () {
        return {
            articles: [ ],
            showArticleContent: false,
            showTitle: '',
            showEditor: '',
            showChannels: [],
            showContent: '',
            channels: [],
        }
    },
    methods: {
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
        getChannels: function () {
            let that = this
            this.$axios.get("/channels")
            .then((res) => {
                that.channels = res.data.data
            })
            .catch((err) => {
                that.$message({message:err.data,type:'warning'})
                console.log(err)
            })
        },
    },
    mounted: function(){
        this.getArticles()
        this.getChannels()
    }
}
</script>

<style scoped>
li {
    display: flex;
    justify-content: space-around;
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
