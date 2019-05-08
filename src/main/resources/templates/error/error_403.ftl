<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">

</style>
<body>
<div id="app">
    <h1>没权限</h1>
</div>
<#include "../common/scripts.ftl">
<script>
    Vue.prototype.$http = axios.create({
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        }
    });
    new Vue({
        el: '#app',
        data() {
            return {
                listLoading: true
            }
        },
        created() {
            this.fetchData();
        },
        methods: {

        }
    });
</script>
</body>
</html>