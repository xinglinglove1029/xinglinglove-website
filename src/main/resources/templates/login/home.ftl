<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">
    .el-row {
        margin-bottom: 15px;}
    .el-row:last-child {
        margin-bottom: 0px;
    }

    .el-card__header{
        padding: 10px;
        color: #333333;
        font-size: 16px;
    }
    .el-card__body {
        padding: 15px;
    }
    .op-icon{
        font-size: 16px;
        float: right;
        cursor: pointer;
        color:#000000;
    }
    .op-icon i{
        margin-left: 5px;
        margin-right: 5px;
    }
    #row1 .el-card__body{
        max-height: 337px;
        overflow-y: auto;
        height: 300px;
    }
    #row1 .el-collapse-item__header{
        height: 35px;
        line-height: 35px;
    }
    #row1 .el-collapse-item__arrow{
        line-height: 35px;
    }
    #row1 .el-collapse{
        border-top:0px;
        border-bottom: 0px;
    }
    #row2 .el-card__body{
        height: 450px;
    }
    #row3 .div_card_blue{
        width: 150px;
        height: 99px;
        background: #20A0FF;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        line-height: 17px;
        border: 1px solid #ebeef5;
        -webkit-transition: .3s;
        transition: .3s;
        border-radius: 4px;
        overflow: hidden;
        margin-right: 10px;
        float: left;
    }
    .div_card_green{
        width: 150px;
        height: 99px;
        background: #50E3C2;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        line-height: 17px;
        border: 1px solid #ebeef5;
        -webkit-transition: .3s;
        transition: .3s;
        border-radius: 4px;
        overflow: hidden;
        margin-right: 10px;
        float: left;
    }
    .div_card_yellow{
        width: 150px;
        height: 99px;
        background: #F5A623;
        color: #FFFFFF;
        letter-spacing: 0;
        text-align: center;
        line-height: 17px;
        border: 1px solid #ebeef5;
        -webkit-transition: .3s;
        transition: .3s;
        border-radius: 4px;
        overflow: hidden;
        margin-right: 10px;
        float: left;
    }
    .span_notice{
        font-size: 33px;
        display: block;
        position: relative;
        height: 65px;
        line-height: 65px;
        text-decoration: none;
        color:#ffffff;
    }
    .span_notice:hover{
        text-decoration: none;
        color:#ffffff;
    }
    .p_notice{
        font-size: 14px;
        display: contents;
        position: absolute;
    }

</style>
<body>
<div id="app">
    <el-row :gutter="10" id="row1">
        <el-col :span="12">
            <el-card class="box-card" v-loading="noticeLoading">
                <div slot="header" class="clearfix">
                    <span>公告</span>
                    <span class="op-icon">
                        <a :href="faqEnvDomain" target="_blank" ><i class="el-icon-more"></i></a>
                        <i class="el-icon-refresh" @click="refreshNotice()"></i>
                    </span>
                </div>
                <el-collapse>
                    <el-collapse-item v-for="(obj,index) in noticeLists" :key="obj.id">
                        <template slot="title" style="font-size: 14px;color: #333333;">
                            <div style="float: left;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;width: 300px;" v-text="index+1+'、'+obj.title"></div>
                            <a style="float: right;font-size: 12px;text-decoration:none;color: #999999;" target="_blank" :href="obj.linkUrl" v-text="obj.createTime"></a>
                        </template>
                        <div v-if="obj.content" style="color:#666666;font-size: 12px;" v-html="obj.content"></div>
                    </el-collapse-item>
                </el-collapse>
            </el-card>
        </el-col>
        <el-col :span="12">
            <el-card class="box-card" v-loading="commonQuestionLoading">
                <div slot="header" class="clearfix">
                    <span>常见问题</span>
                    <span class="op-icon">
                        <span class="op-icon">
                            <a :href="faqEnvDomain" target="_blank" ><i class="el-icon-more"></i></a>
                            <i class="el-icon-refresh" @click="refreshQuestion()"></i>
                        </span>
                    </span>
                </div>
                <div v-for="(obj,index) in questionList" :key="obj.id" style="color: #333333;cursor:pointer;font-size: 14px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;line-height: 30px;">
                    <a style="text-decoration:none;color: #333333;" :href="obj.linkUrl" target="_blank"   v-text="index+1+'、'+obj.title"></a>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row :gutter="10" id="row2">
        <el-col :span="24">
            <el-card class="box-card" v-loading="countLoading">
                <div slot="header" class="clearfix">
                    <span>数据统计</span>
                    <span class="op-icon">
                        <i class="el-icon-refresh" @click="refreshCountData()"></i>
                    </span>
                    <ve-histogram :data="chartData" :settings="chartSettings"></ve-histogram>
                </div>
            </el-card>
        </el-col>
    </el-row>
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
            this.chartSettings = {
                showLine: ['在线用户']
            };
            return {
                faqEnvDomain: '1',
                countLoading: false,
                noticeLoading: false,
                commonQuestionLoading: false,
                queryNotice: {
                    type: 1,
                    systemKey : '1',
                    pageNumber: 1,
                    pageSize: 1000
                },
                queryQuestion: {
                    type: 2,
                    systemKey : '1',
                    pageNumber: 1,
                    pageSize: 1000
                },
                noticeLists: [],
                questionList: [],
                chartData: {
                    columns: ['日期', '访问用户', '在线用户', '总人数'],
                    rows: [
                        { '日期': '2019/1/1', '访问用户': 1393, '在线用户': 1093, '总人数': 1000 },
                        { '日期': '2019/1/2', '访问用户': 3530, '在线用户': 3230, '总人数': 1000 },
                        { '日期': '2019/1/3', '访问用户': 2923, '在线用户': 2623, '总人数': 2000 },
                        { '日期': '2019/1/4', '访问用户': 1723, '在线用户': 1423, '总人数': 1900 },
                        { '日期': '2019/1/5', '访问用户': 3792, '在线用户': 3492, '总人数': 1003 },
                        { '日期': '2019/1/6', '访问用户': 4593, '在线用户': 4293, '总人数': 1000 },
                        { '日期': '2019/1/7', '访问用户': 4593, '在线用户': 4293, '总人数': 1500 },
                        { '日期': '2019/1/8', '访问用户': 4593, '在线用户': 4293, '总人数': 1700 },
                        { '日期': '2019/1/9', '访问用户': 4593, '在线用户': 4293, '总人数': 1800 }
                    ]
                }
            }
        },
        mounted(){
            this.refreshNotice();
            this.refreshQuestion();
        },
        methods: {
            refreshNotice() {
                let _this = this;
                _this.noticeLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/notice/listPage',
                    data: _this.queryNotice,
                }).then(function (response) {
                    console.log(response);
                    _this.noticeLists = response.data.result.list;
                    _this.noticeLoading = false;
                }).catch(function (response) {
                    console.log(response);
                });
            },
            refreshQuestion(){
                let _this = this;
                _this.commonQuestionLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/notice/listPage',
                    data: _this.queryQuestion,
                }).then(function (response) {
                    console.log(response);
                    _this.questionList = response.data.result.list;
                    _this.commonQuestionLoading = false;
                }).catch(function (response) {
                    console.log(response);
                });
            },
            refreshCountData() {

            }
        }
    });
</script>
</body>
</html>