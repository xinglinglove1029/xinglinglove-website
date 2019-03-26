<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">
    .el-header {
        background-color: #002140;
        text-align: center;
        height: 60px;
        line-height: 60px;
    }

    .el-menu-demo {
        background-color: #002140;
        color: #FFFFFF;
        font-size: 15px;
        font-weight: bolder;
        text-align: center;
        height: 60px;
        line-height: 60px;
        border-right: none;
        border-bottom: solid 1px #e6e6e6;
        float: left;
    }

    .el-menu-demo .el-menu-item a:hover {
        color: #002140;
        background-color: #002140;
    }

    .el-menu-demo .el-menu-item:hover {
        color: #002140;
        background-color: #002140;
    }

    .el-menu--horizontal > .el-menu-item.is-active {
        border-bottom: 2px solid #409EFF;
        color: #FFFFFF;
    }

    /**左侧菜单样式**/
    .el-aside {
        background-color: #00152A;
        color: #FFFFFF;
        text-align: center;
        overflow-x: hidden;
        transition: .38s;
        height: 100%;
    }

    .el-menu-vertical-demo {
        background-color: #001529;
        width: 220px;
    }

    .el-menu-vertical-demo .el-menu-item [class^=el-icon-] {
        margin-right: 5px;
        width: 24px;
        text-align: center;
        font-size: 18px;
        vertical-align: middle;
    }
    .el-menu-vertical-demo .el-menu-item span{
        color: #FFFFFF;
    }

    .el-menu-vertical-demo .el-menu-item.is-active {
        color: #409EFF
    }

    .el-menu-vertical-demo .el-menu-item.is-active i {
        color: inherit
    }

    .el-menu-vertical-demo .el-submenu {
        list-style: none;
        margin: 0;
        padding-left: 0;
        width: 220px;
    }

    .el-menu-vertical-demo .el-submenu__title {
        padding: 0 20px !important;
        background-color: #001529 !important;
        font-size: 14px;
        color: #FFFFFF;
        text-align: left;
        line-height: 54px;
        height: 54px;
        cursor: pointer;
        -webkit-transition: border-color .3s, background-color .3s, color .3s;
        transition: border-color .3s, background-color .3s, color .3s;
        box-sizing: border-box

    }

    .el-menu-vertical-demo .el-submenu__title:focus, .el-submenu__title:hover {
        outline: 0;
        background-color: #002140
    }

    .el-menu-vertical-demo .el-submenu__title:hover {
        background-color: #002140;
        color: #758698;
    }

    .el-menu-vertical-demo .el-submenu.is-active .el-submenu__title {
        border-bottom-color: #409EFF
    }

    .el-menu-vertical-demo .el-submenu .el-menu-item {
        padding: 0 40px !important;
        background-color: #00152A !important;
        color: #758698 !important;
        text-align: left;
        line-height: 54px;
        height: 54px;
    }

    .el-menu-vertical-demo .el-menu-item:hover {
        background-color: #002140 !important;
        /*color: #758698 !important;*/
    }

    .el-main {
        background-color: #F1F2F6;
        color: #333;
        text-align: center;
        padding: 0px;
        overflow: auto;
        height: 100%;
    }
    .logo{
        float: left;
        width: 220px;
        height: 60px;
        text-align: center;
        margin-left: 10px;
    }
    body > .el-container {
        margin-bottom: 40px;
    }
    .el-dropdown {
        display: inline-block;
        position: relative;
        color: #606266;
        font-size: 14px;
        float: right;
    }
    .el-container {
        height: 100%;
    }

    .avatar-container .el-dropdown-menu{
        position: absolute;
        transform-origin: center top 0px;
        z-index: 2001;
        height: 150px;
    }
    .hamburger{
        padding-left: 20px;
        color: #fff;
        -webkit-transition: .38s;
        transition: .38s;
        height: 15px;
        cursor: pointer;
    }
    .hamburger.is-active {
        -webkit-transform: rotate(90deg);
        transform: rotate(90deg);
        -webkit-transition: .38s;
        transition: .38s;
        -webkit-transform-origin: 50% 50%;
        transform-origin: 50% 50%;
        margin-top: -20px;
    }
    .menu-is-collapse-left-width{
        width: 52px !important;
        transition: .38s;
    }

    .el-tabs--border-card>.el-tabs__header{
        border: 0px;
    }
    .el-tabs--border-card{
        border: 0px;
    }
    /* tab */
    .el-tabs__nav .el-tabs__item:first-child .el-icon-close{
        display: none;
    }
    .el-tabs--top.el-tabs--border-card .el-tabs__item:last-child{
        padding: 0px 10px;
    }
    .el-tabs--border-card>.el-tabs__content {
        padding: 0px;
        height: 100%;
    }

</style>
<body>
<div id="app">
    <el-container>
        <el-header>
            <div class="logo">
                <img style="vertical-align:middle;width: 32px;height:32px;margin-left: -60px;" src="../../static/image/logo.png">
                <span style="vertical-align:middle;line-height:60px;font-size: 18px;margin-left: 10px;color:#ffffff;cursor: pointer;" @click="reLoad()">后台管理系统</span>
                <img class="hamburger" :class="{'is-active':isCollapse}"  @click="isCollapse=!isCollapse" src="../../static/image/hamburger.png">
            </div>
            <el-dropdown class="avatar-container right-menu-item" trigger="click" style="color: #ffffff;cursor: pointer;">
                <span>{{currentUserName}}</span>
                <i class="el-icon-caret-bottom"></i>
                <el-dropdown-menu slot="dropdown" style="top: 65px;">
                    <el-dropdown-item >
                        <span style="display:block;width: 300px">角色:{{currentRoleName}}</span>
                    </el-dropdown-item>
                    <el-dropdown-item >
                        <span style="display:block;">电话：{{currentPhone}}</span>
                    </el-dropdown-item>
                    <el-dropdown-item >
                        <span  style="display:block;">邮箱：{{currentEmail}}</span>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <span @click="logout" style="display:block;">退出登录</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-header>

        <el-container id="container_left_menu">
            <el-aside width="220px" :class="{'menu-is-collapse-left-width':isCollapse}" >
                <el-menu :collapse="isCollapse"
                         default-active="1"
                         class="el-menu-vertical-demo"
                         @open="handleOpen"
                         @close="handleClose"
                         background-color="#001529"
                         text-color="#ffffff"
                         active-text-color="#002140"
                         :class="{'menu-is-collapse-left-width':isCollapse}">
                    <el-submenu :class="{'menu-is-collapse-left-width':isCollapse}" v-for="(item,index) in leftMenuTreeList" :key="'key_'+index" v-if="item.children" :index="item.url">
                        <template slot="title">
                            <i class="el-icon-menu"></i>
                            <span v-text="item.menuName"></span>
                        </template>
                        <el-menu-item  v-for="(item1,index1) in item.children" :key="'key_'+index1" v-if="item1.children" :index="item1.url" @click="addTab(item1)">
                            <i class="el-icon-document"></i>
                            <span v-text="item1.menuName"></span>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-main>
                <el-tabs id="container_right_main_tabs"  v-model="tabsIndex" type="border-card" closable @tab-remove="removeTab">
                    <el-tab-pane v-for="(item, index) in tabs" :key="'key_'+index" :label="item.title" :name="item.name"  style="height: 100%;">
                        <iframe v-if="item.href" frameborder="0" width="100%" height="100%" :src="item.href"></iframe>
                    </el-tab-pane>
                </el-tabs>
            </el-main>
        </el-container>
    </el-container>
</div>
<#include "../common/scripts.ftl">
<script>
    const homeJs={
        clientHeight:document.documentElement.clientHeight,
        clientWidth:document.documentElement.clientWidth,
        topHeight:60,
        leftWidth:220,
        fn_onresize(){
            if(!this.elRightMainTabsContent){
                this.elRightMainTabsContent=$(".el-tabs--border-card>.el-tabs__content")[0];
            }
            this.clientHeight = document.documentElement.clientHeight;
            this.clientWidth = document.documentElement.clientWidth;
            container_left_menu.style.maxHeight=this.clientHeight-this.topHeight+"px";
            container_left_menu.style.height=this.clientHeight-this.topHeight+"px";
            this.elRightMainTabsContent.style.height=this.clientHeight-this.topHeight-45+"px";
        }
    };
    window.onload=function(){
        homeJs.fn_onresize();
        var menu0timeout=window.setTimeout(function () {
            $("#container_right_header .el-menu-item:eq(0)").click();
            window.clearTimeout(menu0timeout);
        },500);

    };
    window.onresize = function () {
        homeJs.fn_onresize();
    };
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
                isCollapse: false,
                noticeLoading: false,
                commonQuestionLoading: false,
                dialogFormVisible: false,
                activeIndex: '80001',
                leftMenuTreeList: [],
                currentUserName: '',
                currentRoleName: '',
                currentPhone: '',
                currentEmail: '',
                mapTabs:{},
                tabsIndex: '/home',
                tabs: [{ title: '首页', name: '/home', href: '/home'}]
        };
        },
        watch: {
            "isCollapse"(val, oldVal) {
                console.log("this.isCollapse",this.isCollapse);
            }
        },
        created() {
        },
        mounted(){
            this.getLeftMenuList();
        },
        methods: {
            reLoad(){
                window.location.reload();
            },
            getLeftMenuList() {
                let _this = this;
                _this.$http({
                    method: 'POST',
                    url: "/menu/getMenuTree",
                    data: '1'
                }).then(function (response) {
                    console.log(response);
                    _this.leftMenuTreeList = response.data.result;
                }).catch(function (response) {
                    console.log(response);
                });
            },
            addTab(node) {
                console.log("-----",node);
                //如果有没有href,则退出
                if(!node.url)return;

                //新页签打开
                if(node.target==="_blank" && node.url.indexOf("http")>=0){
                    window.open(node.url);
                    return;
                }

                //如果已打开过，则退出
                let newTabName =  "menuId_"+node.url;
                let mapTabsItemValue=this.mapTabs[newTabName];
                console.log(newTabName,mapTabsItemValue);
                if(mapTabsItemValue){
                    this.tabsIndex = newTabName;//重新选中
                    return;
                }
                //新增
                this.tabs.push({ title: node.menuName, name: newTabName, href:node.url });
                this.mapTabs[newTabName]=true;
                this.tabsIndex = newTabName;
            },
            removeTab(targetName) {
                if(targetName=="/home")return;
                let tabs = this.tabs;
                let activeName = this.tabsIndex;
                if (activeName === targetName) {
                    tabs.forEach((tab, index) => {
                        if (tab.name === targetName) {
                            let nextTab = tabs[index + 1] || tabs[index - 1];
                            if (nextTab) {
                                activeName = nextTab.name;
                            }
                        }
                    });
                }

                this.mapTabs[targetName]=false;
                this.tabsIndex = activeName;
                this.tabs = tabs.filter(tab => tab.name !== targetName);
            },
            handleSelect(key, keyPath) {
                console.log(key +'_' + keyPath);
            },
            handleOpen(key, keyPath) {
                console.log(key +'_' + keyPath);
            },
            handleClose(key, keyPath) {
            },
            logout(){
                let _this = this;
                _this.$http({
                    method: 'POST',
                    url: '/logout',
                }).then(function (response) {
                    console.log(response);
                    window.location.href="/login";
                }).catch(function (response) {
                    console.log(response);
                });
            }
        }
    });
</script>
</body>
</html>