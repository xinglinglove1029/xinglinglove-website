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
                <el-form-item label=""  prop="userName">
                    <el-input @keyup.enter.native="searchFilter"  style="width: 200px;" class="filter-item" placeholder="用户名" v-model="listQuery.userName">
                    </el-input>
                </el-form-item>
                <el-form-item label=""  prop="sex">
                    <el-select clearable style="width: 100px" class="filter-item" v-model="listQuery.sex" placeholder="性别">
                        <el-option
                                v-for="item in sexArray"
                                :label="item.value"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-button class="filter-item" type="primary"  icon="el-icon-search" @click="searchFilter">搜索</el-button>
                <el-button class="filter-item" type="primary"   @click="resetForm('listQuery')">重置</el-button>
                <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="save" >添加</el-button>
                <el-button class="filter-item" type="primary" :loading="downloadLoading"  icon="el-icon-download" @click="exportUserInfo">导出</el-button>
            </el-form>
        </div>

        <el-table :data="tableData" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row
                  :default-sort = "{prop: 'createTime', order: 'descending'}">
            <el-table-column align="center" label='ID' width="95">
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column label="用户名"  align="center" prop="userName">
                <template slot-scope="scope">
                    <span>{{scope.row.userName}}</span>
                </template>
            </el-table-column>
            <el-table-column label="真实姓名"  align="center" prop="realName" min-width="100">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.id === '1'" :type="scope.row.realName">{{scope.row.realName}}</el-tag>
                    <span v-else>{{scope.row.realName}}</span>
                </template>
            </el-table-column>
            <el-table-column label="部门"  align="center" prop="deptName">
                <template slot-scope="scope">
                    <span>{{scope.row.deptName}}</span>
                </template>
            </el-table-column>
            <el-table-column class-name="status-col"  label="性别"  align="center" prop="sex">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.sex">{{scope.row.sex | sexFilter}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column class-name="status-col" label="状态"align="center" prop="sex">
                <template slot-scope="scope">
                    <span>{{scope.row.status | statusFilter}}</span>
                </template>
            </el-table-column>
            <el-table-column label="出生日期" sortable width="120" align="center">
                <template slot-scope="scope">
                    {{scope.row.birthday}}
                </template>
            </el-table-column>
            <el-table-column align="center" prop="updater" label="修改人">
                <template slot-scope="scope">
                    <span>{{scope.row.updater}}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" sortable prop="updateTime" label="修改时间" min-width="180">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span>{{scope.row.updateTime}}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" fixed="right" label="操作" min-width="350" class-name="small-padding">
                <template slot-scope="scope">
                    <el-button type="primary" v-if="scope.row.status === 0 && scope.row.id != 1" size="mini" @click="enable(scope.row)">启用</el-button>
                    <el-button type="primary" v-if="scope.row.status === 1 && scope.row.id != 1" size="mini" @click="disable(scope.row)">禁用</el-button>
                    <el-button type="primary" v-if="scope.row.id != 1" size="mini" @click="edit(scope.row)">修改</el-button>
                    <el-button type="primary" v-if="scope.row.id != 1" size="mini" @click="bindRole(scope.row)">绑定角色</el-button>
                    <el-button type="danger"  v-if="scope.row.id != 1" size="mini" @click="deleteUser(scope.row)">删除</el-button>
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
            <el-form :rules="rules" ref="dataForm" :model="user" label-position="left" label-width="80px" style='width: 400px; margin-left:50px;'>
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model.trim="user.userName"></el-input>
                </el-form-item>
                <el-form-item label="密码" v-if="dialogStatus=='create'" prop="password">
                    <el-input v-model.trim="user.password"/>
                </el-form-item>
                <el-form-item label="真实姓名"  prop="realName">
                    <el-input v-model.trim="user.realName"/>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthday">
                    <el-date-picker v-model.trim="user.birthday" value-format="yyyy-MM-dd" placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-radio v-model.trim="user.sex" label="1">男</el-radio>
                    <el-radio v-model.trim="user.sex" label="2">女</el-radio>
                </el-form-item>
                <el-form-item label="手机号码" prop="cellPhone">
                    <el-input v-model.trim="user.cellPhone"/>
                </el-form-item>
                <el-form-item label="部门" prop="deptId">
                    <el-select v-model="user.deptId" filterable clearable  placeholder="请选择部门">
                        <el-option
                                v-for="item in deptList"
                                :key="item.id"
                                :label="item.deptName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model.trim="user.email"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button v-if="dialogStatus=='create'" type="primary" @click="saveUserData">确 定</el-button>
                <el-button v-else type="primary" @click="updateUser">确 定</el-button>
            </div>
        </el-dialog>
        <!--用戶綁定角色模态框-->
        <el-dialog title="用戶綁定角色" :visible.sync="bingRoleDialogFormVisible">
            <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <div style="margin: 15px 0;"></div>
            <el-checkbox-group v-model="checkedRoleId" @change="handleCheckedRoleChange">
                <el-checkbox v-for="role in roleList" :label="role.id" :key="role.id">{{role.roleName}}</el-checkbox>
            </el-checkbox-group>
            <div slot="footer" class="dialog-footer">
                <el-button @click="bingRoleDialogFormVisible = false">取 消</el-button>
                <el-button  type="primary" @click="saveUserRoleData">确 定</el-button>
            </div>
        </el-dialog>·
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
            const validateUserName = (rule, value, callback) => {
                let _this = this;
                const usernameRegex = /^[A-Za-z0-9]{5,16}$/;
                if (!usernameRegex.test(value)) {
                    callback(new Error('用户名格式不正确'))
                } else {
                    const param = {};
                    param.userName = value;
                    if (_this.dialogStatus === 'update') {
                        param.userId = _this.user.id
                    }
                    _this.$http({
                        method: 'POST',
                        url: '/user/checkUserName',
                        data: param
                    }).then((res) => {
                        if(res.data.code === 200 && res.data.result === true){
                            callback(new Error('用户名已存在'));
                        }else{
                            callback();
                        }
                    }).catch((err) => {
                        console.log(err);
                        callback(new Error('服务异常,请稍后重试'));
                    });
                }
            };
            const validatePassword = (rule, value, callback) => {
                const pwdRegex = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,14}$/;
                if (!pwdRegex.test(value)) {
                    callback(new Error('密码格式不正确'));
                } else {
                    callback()
                }
            };
            const validatePhone = (rule, value, callback) => {
                const phoneReg = /^1\d{10}$/;
                if (!phoneReg.test(value)) {
                    callback(new Error('手机号码格式不正确'));
                } else {
                    callback()
                }
            };
            const validateEmail = (rule, value, callback) => {
                const emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if (!emailReg.test(value)) {
                    callback(new Error('邮箱格式不正确'));
                } else {
                    callback()
                }
            };
            return {
                checkedRoleId: [],
                bindUserId: [],
                roleList: [],
                deptList: [],
                tableData: null,
                listLoading: true,
                total: 0,
                listQuery: {
                    pageNum: 1,
                    pageSize: 20,
                    userName: '',
                    sex: ''
                },
                sexArray: [
                    {
                        code: '1',
                        value: '男'
                    },
                    {
                        code: '2',
                        value: '女'
                    }
                ],
                isIndeterminate: true,
                checkAll: false,
                bingRoleDialogFormVisible: false,
                dialogFormVisible: false,
                dialogStatus: '',
                textMap: {
                    update: '修改',
                    create: '新增'
                },
                dialogPvVisible: false,
                downloadLoading: false,
                user: {
                    id: '',
                    userName: '',
                    password: '',
                    realName: '',
                    sex: '1',
                    birthday: '',
                    cellPhone: '',
                    deptId: '',
                    email: ''
                },
                rules: {
                    userName: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '用户名长度为2到20个字符', trigger: 'blur' },
                        { validator: validateUserName, trigger: 'blur' }
                    ],
                    realName: [
                        { required: true, message: '真实姓名不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '请正确填写姓名,支持2-20个汉字', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '密码长度为2到20个字符', trigger: 'blur' },
                        { validator: validatePassword, trigger: 'blur' }
                    ],
                    cellPhone: [
                        { required: true, message: '手机号码不能为空', trigger: 'blur' },
                        { length: 11, message: '手机号码长度为11个字符', trigger: 'blur' },
                        { validator: validatePhone, trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '邮箱不能为空', trigger: 'blur' },
                        { validator: validateEmail, trigger: 'blur' }
                    ],
                    deptId: [{ required: true, message: '部门不能为空', trigger: 'blur' }],
                    birthday: [{ required: true, message: '出生日期不能为空', trigger: 'change' }]
                }
            }
        },
        filters: {
            sexFilter(sex) {
                let value = '';
                switch (sex) {
                    case '1':
                        value = '男';
                        break;
                    case '2':
                        value = '女';getDepartmentList;
                        break;
                }
                return value
            },
            statusFilter(status) {
                const statusMap = {
                    1: '启用',
                    0: '禁用'
                };
                return statusMap[status];
            }
        },
        created() {
            this.fetchData();
            this.getDepartmentList();
        },
        methods: {
            getDepartmentList() {
                let _this = this;
                _this.$http({
                    method: 'POST',
                    url: '/user/getDepartmentList'
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.deptList = res.data.result;
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
            fetchData() {
                let _this = this;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/user/listPage',
                    data: _this.user
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
                this.listQuery.pageSize = val;
                this.fetchData();
            },
            handleCurrentChange(val) {
                this.listQuery.pageNum = val;
                this.fetchData()
            },
            save() {
                let _this = this;
                _this.resetUserData();
                _this.dialogStatus = 'create';
                _this.dialogFormVisible = true;
                _this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate();
                })
            },
            saveUserData() {
                let _this = this;
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/user/saveUserInfo',
                            data: _this.user
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.dialogFormVisible = true;
                                _this.resetUserData();
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
            resetUserData() {
                let _this = this;
                _this.user = {
                    userName: '',
                    password: '',
                    realName: '',
                    sex: '1',
                    birthday: '',
                    cellPhone: '',
                    email: ''
                }
            },
            edit(row) {
                let _this = this;
                _this.user = Object.assign({}, row);
                _this.dialogStatus = 'update';
                _this.dialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['dataForm'].clearValidate();
                })
            },
            updateUser() {
                let _this = this;
                _this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/user/modifyUser',
                            data: _this.user
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.dialogFormVisible = false;
                                _this.resetUserData();
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
            deleteUser(row) {
                let _this = this;
                _this.$confirm(`确定删除用户`+row.realName+`吗？`, '删除提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/user/delete',
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
                this.listQuery.pageNum = 1;
                this.fetchData()
            },
            exportUserInfo() {
            },
            resetForm(form) {
                this.$refs[form].resetFields();
                this.fetchData()
            },
            enable(row) {
                let _this = this;
                _this.$confirm(`确定启用用户`+row.realName+`吗？`, '启用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/user/enable',
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
                })
            },
            disable(row) {
                let _this = this;
                _this.$confirm(`确定禁用用户`+row.realName+`吗？`, '禁用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/user/disable',
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
                })
            },
            bindRole(row){
                let _this = this;
                _this.bingRoleDialogFormVisible = true;
                _this.bindUserId = row.id;
                _this.$http({
                    method: 'POST',
                    url: '/user/getRoleList',
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.roleList = res.data.result;
                    }else{
                        _this.$message({
                            type: 'warning',
                            message: res.data.message
                        })
                    }
                }).catch((err) => {
                    console.log(err);
                });
                _this.getBindRoleListByUserId();

            },
            saveUserRoleData(){
                let _this = this;
                let param = {};
                param.userId = _this.bindUserId;
                param.roleIdList = _this.checkedRoleId;
                _this.$http({
                    method: 'POST',
                    url: '/user/bindRole',
                    data: param
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.bingRoleDialogFormVisible = false;
                        _this.$message({
                            type: 'success',
                            message: '绑定成功!'
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
            },
            getBindRoleListByUserId(){
                let _this = this;
                _this.$http({
                    method: 'POST',
                    url: '/user/getBindRoleListByUserId',
                    data: _this.bindUserId
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.checkedRoleId = res.data.result;
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
            handleCheckAllChange(val){
                let _this = this;
                let allRoleIdList = [];
                _this.roleList.forEach((item) => {
                    allRoleIdList.push(item.id);
                });
                _this.checkedRoleId = val ? allRoleIdList : [];
                _this.isIndeterminate = false;
            },
            handleCheckedRoleChange(value){
                let _this = this;
                let checkedCount = value.length;
                _this.checkAll = checkedCount === _this.roleList.length;
                _this.isIndeterminate = checkedCount > 0 && checkedCount < _this.roleList.length
            }
        }
    });
</script>
</body>
</html>