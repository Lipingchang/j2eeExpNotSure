<template>
<el-container>
  <el-aside width="200px">

    <el-menu>
    <div class="userinfo">
      <i class="el-icon-apple"></i>
      <span>{{this.$store.state.userinfo.username}}<i>{{this.$store.state.userinfo.rolename}}</i></span>
    </div>

      <el-submenu index="1">
        <template slot="title"><i class="el-icon-takeaway-box"></i>栏目管理</template>          
        <router-link to="/channels">
          <el-menu-item index="1-1">栏目管理</el-menu-item>
        </router-link>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title"><i class="el-icon-files"></i>文章管理</template>
        <router-link to="/articles"><el-menu-item index="2-1">文章管理</el-menu-item></router-link>
      </el-submenu>
      <el-submenu index="3">
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
