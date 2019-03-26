<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style rel="stylesheet/scss" lang="scss">
 .filter-container{
     margin: 30px 10px 30px 0;
 }
</style>
<body>
<div id="app">

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
                filterText: '',
                treeData: []
            }
        },
        filters: {
            statusFilter(status) {
                const statusMap = {
                    1: '启用',
                    0: '禁用'
                };
                return statusMap[status]
            }
        },
        watch: {
            filterText(val) {
                this.$refs.menuTree.filter(val);
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