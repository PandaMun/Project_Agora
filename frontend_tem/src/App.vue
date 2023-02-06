<template>
  <div id="app">
    <loader v-if="!status"></loader>
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'App',
  data: function() { return { status: false } },
}
</script>


