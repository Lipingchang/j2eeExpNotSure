<template>
<el-container>
  <el-aside width="200px">

    <el-menu :default-active="subMenus[defaultSubMenue]" >
    <div class="userinfo">
      <i class="el-icon-apple"></i>
      <span>{{this.$store.state.userinfo.username}}<i>{{this.$store.state.userinfo.rolename}}</i></span>
    </div>

      <el-submenu index="1" v-if="hasAuthority('channelAdmin')">
        <template slot="title"><i class="el-icon-takeaway-box"></i>栏目管理</template>          
        <router-link to="/channels">
          <el-menu-item index="1-1">栏目管理</el-menu-item>
        </router-link>
      </el-submenu>
      <el-submenu index="2" v-if="hasAuthority('articleViewer')">
        <template slot="title"><i class="el-icon-files"></i>文章</template>
        <router-link to="/articleadmin" index="2-1" v-if="hasAuthority('articleAdmin')"><el-menu-item index="2-1">文章管理</el-menu-item></router-link>
        <router-link to="/articleviewer" index="2-2" v-if="hasAuthority('articleViewer')"><el-menu-item index="2-2">文章浏览</el-menu-item></router-link>
      </el-submenu>
      <el-submenu index="3" v-if="hasAuthority('personAdmin')">
        <template slot="title"><i class="el-icon-user"></i>人员管理</template>
        <router-link to="/persons"><el-menu-item index="3-1">人员管理</el-menu-item></router-link>
      </el-submenu>
    </el-menu>
  </el-aside>
  
  <el-container>    
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</el-container>

</template>

<script>

export default {
    data() {
      return {
        auths: {
          'channelAdmin': [
            'ROLE_管理栏目'
          ],
          'articleAdmin': [
            'ROLE_浏览文章',
            'ROLE_删除文章',
            'ROLE_编辑文章',
            'ROLE_添加文章'
          ],
          'articleViewer': [
            'ROLE_浏览文章',
          ],
          'personAdmin': [
            'ROLE_人员管理'
          ]
        },
        subMenus: {
          'channelAdmin': '1-1',
          'articleAdmin': '2-1',
          'articleViewer': '2-2',
          'personAdmin': '3-1'
        },
        subMenus2: {
          'channelAdmin': 'channels',
          'articleAdmin': 'articleadmin',
          'articleViewer': 'articleviewer',
          'personAdmin': 'persons'
        },
        defaultSubMenue: 'channels'
      }
    },
    mounted: function (){
      for( let name in this.auths ){
        if ( this.hasAuthority(name) ){
          this.defaultSubMenue = name
          this.$router.replace(  '/' + this.subMenus2[name] )
          return
        }
      }
    },
    methods: {
      hasAuthority: function ( menueName ){
        // console.log('menue name:', menueName )
        let ret = true
        let userAuth = this.$store.state.userinfo.authorities
        let hadauth = []
        for( let i in userAuth ){
          hadauth.push( userAuth[i]['authority'])
        }
        console.log(hadauth)
        for( let needAuth in this.auths[menueName] ){
          console.log( needAuth)
          ret = ret && hadauth.includes(this.auths[menueName][needAuth])
        }
        // for( let i in userAuth ){
        //   console.log( this.auths[menueName], userAuth[i]['authority'])
        //   ret = ret && this.auths[menueName].includes(userAuth[i]['authority']) 
        
        // }
        return ret
      }
    }
}
</script>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }
  a {
    text-decoration: none;
  }
  .userinfo
  .userinfo {
    margin: 0 auto;
  }
  .userinfo>i{
    display: block;
    width: 100px;
    height: 100px;
    font-size: 100px;
    background-color: antiquewhite;
    border-radius: 55px;
    padding: 10px;
    margin: 10px;
  }
  .userinfo {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 10px;
  }
  .userinfo>span>i{
    background: yellowgreen;
    color: white;
    font-style: normal;
    padding: 0px 5px;
    border-radius: 5px;
    margin: 0px 10px;
  }
  
</style>
