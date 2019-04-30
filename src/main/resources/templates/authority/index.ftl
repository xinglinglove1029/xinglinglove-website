<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">

</style>
<body>
<div id="app">
    <div class="app-container calendar-list-container">
        <div class="filter-container">
            <el-form :inline="true" :model="listQuery" ref="listQuery">
                <el-form-item label="" prop="menuId">
                    <el-select @keyup.enter.native="searchFilter" v-model="listQuery.menuId" filterable clearable  placeholder="请选择菜单">
                        <el-option
                                v-for="item in menuList"
                                :key="item.id"
                                :label="item.menuName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label=""  prop="authorityName">
                    <el-input @keyup.enter.native="searchFilter"  style="width: 200px;" class="filter-item" placeholder="权限名" v-model="listQuery.authorityName">
                    </el-input>
                </el-form-item>
                <el-form-item label=""  prop="authorityCode">
                    <el-input @keyup.enter.native="searchFilter"  style="width: 200px;" class="filter-item" placeholder="权限编码" v-model="listQuery.authorityCode">
                    </el-input>
                </el-form-item>
                <el-form-item label=""  prop="status">
                    <el-select clearable style="width: 100px" class="filter-item" v-model="listQuery.status" placeholder="状态">
                        <el-option
                                v-for="item in statusArray"
                                :label="item.value"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-button class="filter-item" type="primary"  icon="el-icon-search" @click="searchFilter">搜索</el-button>
                <el-button class="filter-item" type="primary"   @click="resetForm('listQuery')">重置</el-button>
                <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="save" >添加</el-button>
            </el-form>
        </div>

        <el-table :data="tableData" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row
                  :default-sort = "{prop: 'createTime', order: 'descending'}">
            <el-table-column align="center" label='ID'>
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column label="菜单名称" align="center" prop="menuName">
                <template slot-scope="scope">
                    <span>{{scope.row.menuName}}</span>
                </template>
            </el-table-column>
            <el-table-column label="权限名称"  align="center" prop="authorityName">
                <template slot-scope="scope">
                    <span>{{scope.row.authorityName}}</span>
                </template>
            </el-table-column>
            <el-table-column label="权限编码" align="center" prop="authorityCode">
                <template slot-scope="scope">
                    <span>{{scope.row.authorityCode}}</span>
                </template>
            </el-table-column>
            <el-table-column class-name="status-col"  label="权限url"  align="center" prop="url">
                <template slot-scope="scope">
                    {{scope.row.url}}
                </template>
            </el-table-column>
            <el-table-column class-name="status-col"  label="状态"  align="center" prop="status">
                <template slot-scope="scope">
                    <span>{{scope.row.status | statusFilter}}</span>
                </template>
            </el-table-column>
            <el-table-column align="center"  prop="updater" label="修改人" width="120">
                <template slot-scope="scope">
                    <span>{{scope.row.updater}}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" sortable prop="updateTime" label="修改时间" width="180">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span>{{scope.row.updateTime}}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" fixed="right" label="操作" width="250" class-name="small-padding">
                <template slot-scope="scope">
                    <el-button type="primary" v-if="scope.row.status === '0'" size="mini" @click="enable(scope.row)">启用</el-button>
                    <el-button type="primary" v-if="scope.row.status === '1'" size="mini" @click="disable(scope.row)">禁用</el-button>
                    <el-button type="primary" size="mini" @click="edit(scope.row)">修改</el-button>
                    <el-button type="danger"  size="mini" @click="deleteAuthority(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div v-show="!listLoading" class="pagination-container">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.pageNum"
                           :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <!--新增模态框-->
        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
            <el-form :rules="rules" ref="ruleForm" :model="ruleForm" label-position="left" label-width="80px" style='width: 400px; margin-left:50px;'>
                <el-form-item label="菜单" prop="menuId">
                    <el-select v-model="ruleForm.menuId" filterable clearable  placeholder="请选择">
                        <el-option
                                v-for="item in menuList"
                                :key="item.id"
                                :label="item.menuName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="权限名称" prop="authorityName">
                    <el-input v-model.trim="ruleForm.authorityName"></el-input>
                </el-form-item>
                <el-form-item label="权限编码" v-if="dialogStatus=='create'" prop="authorityCode">
                    <el-input v-model.trim="ruleForm.authorityCode"/>
                </el-form-item>
                <el-form-item label="权限url"  prop="url">
                    <el-input v-model.trim="ruleForm.url"/>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model.trim="ruleForm.status" label="1">启用</el-radio>
                    <el-radio v-model.trim="ruleForm.status" label="0">禁用</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button v-if="dialogStatus=='create'" type="primary" @click="saveAuthorityData">确 定</el-button>
                <el-button v-else type="primary" @click="updateAuthority">确 定</el-button>
            </div>
        </el-dialog>

    </div>
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
            const validateAuthorityCode = (rule, value, callback) => {
                let _this = this;
                const authorityCodeRegex = /^[A-Za-z0-9:]{3,60}$/;
                if (!authorityCodeRegex.test(value)) {
                    callback(new Error('权限编码格式不合法'))
                } else {
                    const param = {};
                    param.authorityCode = value;
                    if (_this.dialogStatus === 'update') {
                        param.authorityId = _this.ruleForm.id
                    }
                    _this.$http({
                        method: 'POST',
                        url: '/authority/checkAuthorityCode',
                        data: param
                    }).then((res) => {
                        if(res.data.code === 200 && res.data.result === true){
                            callback(new Error('权限编码已存在'));
                        }else{
                            callback();
                        }
                    }).catch((err) => {
                        console.log(err);
                        callback(new Error('服务异常,请稍后重试'));
                    });
                }
            };
            const validateAuthorityName = (rule, value, callback) => {
                const authorityNameRegex = /^[\u4e00-\u9faf]+$/;
                if (!authorityNameRegex.test(value)) {
                    callback(new Error('权限名称格式不合法'));
                } else {
                    callback()
                }
            };
            return {
                tableData: null,
                listLoading: true,
                total: 0,
                listQuery: {
                    pageNum: 1,
                    pageSize: 20,
                    authorityName: '',
                    authorityCode: '',
                    status: '',
                    menuId: ''
                },
                menuList: [],
                statusArray: [
                    {
                        code: '1',
                        value: '启用'
                    },
                    {
                        code: '0',
                        value: '禁用'
                    }
                ],
                dialogFormVisible: false,
                dialogStatus: '',
                textMap: {
                    update: '修改',
                    create: '新增'
                },
                dialogPvVisible: false,
                downloadLoading: false,
                ruleForm: {
                    id: '',
                    authorityName: '',
                    authorityCode: '',
                    menuId: '',
                    status: '1',
                    url: ''
                },
                rules: {
                    authorityName: [
                        { required: true, message: '权限名称不能为空', trigger: 'blur' },
                        { min: 2, max: 40, message: '权限名称长度为2到40个字符', trigger: 'blur' },
                        { validator: validateAuthorityName, trigger: 'blur' }
                    ],
                    authorityCode: [
                        { required: true, message: '权限编码不能为空', trigger: 'blur' },
                        { validator: validateAuthorityCode, trigger: 'blur' }
                    ],
                    url: [
                        { required: true, message: 'URL不能为空', trigger: 'blur' },
                        { min: 2, max: 60, message: '权限名称长度为2到60个字符', trigger: 'blur' }
                    ],
                    menuId: [
                        { required: true, message: '菜单不能为空', trigger: 'blur' }
                    ]
                }
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
        created() {
            this.fetchData();
            this.getMenuList();
        },
        methods: {
            fetchData() {
                let _this = this;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/authority/listPage',
                    data: _this.listQuery
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.tableData = res.data.result.list;
                        _this.total = res.data.result.total;
                        _this.listLoading = false;
                    }else{
                        _this.$message({
                            type: 'warning',
                            message: res.data.message
                        })
                    }
                }).catch((err) => {
                    console.log(err);
                });
            },
            handleSizeChange(val) {
                let _this = this;
                _this.listQuery.pageSize = val;
                _this.fetchData();
            },
            handleCurrentChange(val) {
                let _this = this;
                _this.listQuery.pageNum = val;
                _this.fetchData();
            },
            getMenuList() {
                let _this = this;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/menu/select2MenuList',
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.menuList = res.data.result;
                        _this.listLoading = false;
                    }else{
                        _this.$message({
                            type: 'warning',
                            message: res.data.message
                        })
                    }
                }).catch((err) => {
                    console.log(err);
                });
            },
            save() {
                let _this = this;
                _this.resetAuthorityData();
                _this.dialogStatus = 'create';
                _this.dialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['ruleForm'].clearValidate()
                })
            },
            saveAuthorityData() {
                let _this = this;
                _this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/authority/saveAuthorityInfo',
                            data: _this.ruleForm
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.dialogFormVisible = false;
                                _this.resetAuthorityData();
                                _this.fetchData();
                                _this.$message({
                                    type: 'success',
                                    message: '保存成功!'
                                })
                            }else{
                                _this.$message({
                                    type: 'warning',
                                    message: res.data.message
                                })
                            }
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                })
            },
            resetAuthorityData() {
                let _this = this;
                _this.ruleForm = {
                    authorityName: '',
                    authorityCode: '',
                    status: '1',
                    url: '',
                    menuId: ''
                }
            },
            edit(row) {
                let _this = this;
                _this.ruleForm = Object.assign({}, row);
                _this.dialogStatus = 'update';
                _this.dialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['ruleForm'].clearValidate();
                })
            },
            updateAuthority() {
                let _this = this;
                _this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/authority/modifyAuthority',
                            data: _this.ruleForm
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.dialogFormVisible = false;
                                _this.resetAuthorityData();
                                _this.fetchData();
                                _this.$message({
                                    type: 'success',
                                    message: '更新成功!'
                                })
                            }else{
                                _this.$message({
                                    type: 'warning',
                                    message: res.data.message
                                })
                            }
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                })
            },
            deleteAuthority(row) {
                let _this = this;
                _this.$confirm(`确定删除权限`+row.authorityName+`吗？`, '删除提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/authority/delete',
                        data: row.id
                    }).then((res) => {
                        if(res.data.code === 200){
                            _this.fetchData();
                            _this.$message({
                                type: 'success',
                                message: '删除成功!'
                            })
                        }else{
                            _this.$message({
                                type: 'warning',
                                message: res.data.message
                            })
                        }
                    }).catch((err) => {
                        console.log(err);
                    });
                }).catch(() => {
                    _this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
            },
            searchFilter() {
                let _this = this;
                _this.listQuery.pageNum = 1;
                _this.fetchData();
            },
            resetForm(form) {
                let _this = this;
                _this.$refs[form].resetFields();
                _this.fetchData();
            },
            enable(row) {
                let _this = this;
                _this.$confirm(`确定启用权限`+row.authorityName+`吗？`, '启用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/authority/enable',
                        data: row.id
                    }).then((res) => {
                        if(res.data.code === 200){
                            _this.fetchData();
                            _this.$message({
                                type: 'success',
                                message: '启用成功!'
                            })
                        }else{
                            _this.$message({
                                type: 'warning',
                                message: res.data.message
                            })
                        }
                    }).catch((err) => {
                        console.log(err);
                    });
                }).catch(() => {
                    _this.$message({
                        type: 'info',
                        message: '已取消启用'
                    })
                });
            },
            disable(row) {
                let _this = this;
                _this.$confirm(`确定禁用权限`+row.authorityName+`吗？`, '禁用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/authority/disable',
                        data: row.id
                    }).then((res) => {
                        if(res.data.code === 200){
                            _this.fetchData();
                            _this.$message({
                                type: 'success',
                                message: '禁用成功!'
                            })
                        }else{
                            _this.$message({
                                type: 'warning',
                                message: res.data.message
                            })
                        }
                    }).catch((err) => {
                        console.log(err);
                    });
                }).catch(() => {
                    _this.$message({
                        type: 'info',
                        message: '已取消禁用'
                    })
                });
            }
        }
    });
</script>
</body>
</html>