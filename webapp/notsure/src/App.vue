<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted:function(){
    // console.log(this.$store.state)
    if ( this.$store.state.useinfo == undefined ){
      console.log('not login..');
      this.$router.push('/login')
    }else{
      console.log('has login..');
    }
  }
}
</script>

<style>
</style>
