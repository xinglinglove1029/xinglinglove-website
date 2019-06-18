<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">
    .el-tree{
        width:300px
    }
    .el-tree-node__content {
        line-height: 36px;
        height: 36px;
        cursor: pointer
    }

    .el-tree-node__content>.el-checkbox,.el-tree-node__content>.el-tree-node__expand-icon {
        margin-right: 8px
    }

    .el-tree-node__content>.el-checkbox {
        vertical-align: middle
    }

    .el-tree-node__content:hover {
        background: #e4e8f1
    }

    .el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
        background-color: #edf7ff
    }
</style>
<body>
<div id="app">
    <div class="app-container calendar-list-container">
        <div class="filter-container">
            <el-form :inline="true" :model="listQuery" ref="listQuery">
                <el-form-item label=""  prop="roleName">
                    <el-input @keyup.enter.native="searchFilter"  style="width: 200px;" class="filter-item" placeholder="角色名称" v-model="listQuery.roleName">
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
            <el-table-column label="角色名"  align="center" prop="roleName">
                <template slot-scope="scope">
                    <span>{{scope.row.roleName}}</span>
                </template>
            </el-table-column>
            <el-table-column label="角色编码"  align="center" prop="roleCode">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.id === '1'" :type="scope.row.roleCode">{{scope.row.roleCode}}</el-tag>
                    <span v-else>{{scope.row.roleCode}}</span>
                </template>
            </el-table-column>
            <el-table-column class-name="status-col" label="状态"  align="center" prop="status">
                <template slot-scope="scope">
                    <span>{{scope.row.status | statusFilter}}</span>
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
                    <el-button type="primary" v-if="scope.row.status === '0' && scope.row.id !== '1'" size="mini" @click="enable(scope.row)">启用</el-button>
                    <el-button type="primary" v-if="scope.row.status === '1' && scope.row.id !== '1'" size="mini" @click="disable(scope.row)">禁用</el-button>
                    <el-button type="primary" v-if="scope.row.id !== '1'" size="mini" @click="edit(scope.row)">修改</el-button>
                    <el-button type="primary" v-if="scope.row.id !== '1'" size="mini" @click="bindUser(scope.row)">绑定用户</el-button>
                    <el-button type="primary" v-if="scope.row.id !== '1'" size="mini" @click="bindAuthority(scope.row)">绑定权限</el-button>
                    <el-button type="danger" v-if="scope.row.id !== '1'" size="mini" @click="deleteRole(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div v-show="!listLoading" class="pagination-container">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.pageNum"
                           :page-sizes="[10,20,30, 50]" :page-size="listQuery.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <!--新增模态框-->
        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" @click="dialogFormVisible = false">
            <el-form :rules="rules" ref="dataForm" :model="role" label-position="left" label-width="80px" style='width: 400px; margin-left:50px;'>
                <el-form-item label="角色名" prop="roleName">
                    <el-input v-model.trim="role.roleName"></el-input>
                </el-form-item>
                <el-form-item label="角色编码"  prop="roleCode">
                    <el-input v-model.trim="role.roleCode"/>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model.trim="role.status" label="1">启用</el-radio>
                    <el-radio v-model.trim="role.status" label="0">禁用</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button v-if="dialogStatus=='create'" type="primary" @click="saveRoleData">确 定</el-button>
                <el-button v-else type="primary" @click="updateRole">确 定</el-button>
            </div>
        </el-dialog>
        <!--角色绑定用户模态框-->
        <el-dialog :visible.sync="bindUserDialogVisible" @click="bindUserDialogVisible = false" width="55%">
            <template>
                <el-transfer style="margin-left: 20px;"
                        filterable
                        :filter-method="filterMethod"
                        filter-placeholder="请输入用户名"
                        v-model="alreadyBindUser"
                        :props="{key: 'id',label: 'realName',disabled: 'disabled'}"
                        @change="handleChange"
                        :titles="['未绑定用户', '已绑定用户']"
                        :button-texts="['到左边', '到右边']"
                        :data="allUser">
                    <span slot-scope="{ option }">{{ option.realName }} - {{ option.cellPhone }}</span>

                </el-transfer>
            </template>
            <div slot="footer" class="dialog-footer">
                <el-button @click="bindUserDialogVisible = false">取 消</el-button>
                <el-button type="primary" :disabled="btnDisabled"  @click="roleBindUser()">保存</el-button>
            </div>
        </el-dialog>
        <!--角色绑定权限模态框-->
        <el-dialog :visible.sync="bindAuthorityDialogVisible" @click="bindAuthorityDialogVisible = false">
            <div class="tree-container" >
                <el-row :gutter="24">
                    <el-col :span="6" :xs="24" :sm="24" :md="12" :lg="6" style="margin-bottom: 20px;">
                        <el-button type="primary" @click="checkedAll" size="mini">全选</el-button>
                        <el-button type="primary" @click="noCheckedAll" size="mini">全不选</el-button>
                    </el-col>
                </el-row>
                <el-row :gutter="24">
                    <el-col :span="6" :xs="24" :sm="24" :md="6" :lg="6" style="margin-bottom: 20px;">
                        <el-input placeholder="输入关键字进行过滤" v-model="filterText" style="width:300px"> </el-input>
                        <el-tree
                                class="filter-tree"
                                show-checkbox
                                :data="treeData"
                                :props="defaultProps"
                                default-expand-all
                                node-key="id"
                                highlight-current
                                check-strictly
                                :default-checked-keys="checkedKeys"
                                accordion
                                :filter-node-method="filterNode"
                                ref="authorityTree">
                        </el-tree>
                    </el-col>
                </el-row>

            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="bindAuthorityDialogVisible = false">取 消</el-button>
                <el-button type="primary" :disabled="btnDisabled" @click="saveAuthorityInfo">确 定</el-button>
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
            const validateRoleName = (rule, value, callback) => {
                let _this = this;
                const roleNameRegex = /^[\u4e00-\u9faf]+$/;
                if (!roleNameRegex.test(value)) {
                    callback(new Error('角色名格式不正确'));
                } else {
                    const param = {};
                    param.roleName = value;
                    if (_this.dialogStatus === 'update') {
                        param.roleId = _this.role.id
                    }
                    _this.$http({
                        method: 'POST',
                        url: '/role/checkRoleName',
                        data: param
                    }).then((res) => {
                        if(res.data.code === 200 && res.data.result === true){
                            callback(new Error('角色名已存在'));
                        }else{
                            callback();
                        }
                    }).catch((err) => {
                        console.log(err);
                        callback(new Error('服务异常,请稍后重试'));
                    });
                }
            };
            const validateRoleCode = (rule, value, callback) => {
                let _this = this;
                const roleCodeRegex = /^[A-Za-z0-9]{3,16}$/;
                if (!roleCodeRegex.test(value)) {
                    callback(new Error('角色编码格式不合法'));
                } else {
                    const param = {};
                    param.roleCode = value;
                    if (_this.dialogStatus === 'update') {
                        param.roleId = _this.role.id
                    }
                    _this.$http({
                        method: 'POST',
                        url: '/role/checkRoleCode',
                        data: param
                    }).then((res) => {
                        if(res.data.code === 200 && res.data.result === true){
                            callback(new Error('角色编码已存在'));
                        }else{
                            callback();
                        }
                    }).catch((err) => {
                        console.log(err);
                        callback(new Error('服务异常,请稍后重试'));
                    });
                }
            };
            return {
                filterMethod(query, item) {
                    return item.realName.indexOf(query) > -1;
                },
                btnDisabled: false,
                filterText: '',
                treeData: [],
                defaultProps: {
                    children: 'children',
                    label: 'resourceName'
                },
                tableData: [],
                alreadyBindUser: [],
                allUser: [],
                checkedNodeList: [],
                listLoading: true,
                total: 0,
                listQuery: {
                    pageNum: 1,
                    pageSize: 20,
                    roleName: '',
                    status: ''
                },
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
                currentSelectedRoleId: '',
                bindUserIds: [],
                selectedRoleId: '',
                checkedKeys: [],
                checkedAllKeys: [],
                dialogFormVisible: false,
                bindUserDialogVisible: false,
                bindAuthorityDialogVisible: false,
                dialogStatus: '',
                textMap: {
                    update: '修改',
                    create: '新增'
                },
                dialogPvVisible: false,
                downloadLoading: false,
                role: {
                    id: '',
                    roleName: '',
                    roleCode: '',
                    status: '1'
                },
                rules: {
                    roleName: [
                        { required: true, message: '角色名不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '角色名长度为2到20个字符', trigger: 'blur' },
                        { validator: validateRoleName, trigger: 'blur' }
                    ],
                    roleCode: [
                        { required: true, message: '角色编号不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '请正确填写姓名,支持2-20个汉字', trigger: 'blur' },
                        { validator: validateRoleCode, trigger: 'blur' }
                    ]
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.authorityTree.filter(val);
            }
        },
        filters: {
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
        },
        methods: {
            filterNode(value, data) {
                if (!value) return true;
                return data.resourceName.indexOf(value) !== -1;
            },
            fetchData() {
                let _this = this;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/role/listPage',
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
                _this.fetchData()
            },
            handleCurrentChange(val) {
                let _this = this;
                _this.listQuery.pageNum = val;
                _this.fetchData()
            },
            save() {
                let _this = this;
                _this.resetRoleData();
                _this.dialogStatus = 'create';
                _this.dialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['dataForm'].clearValidate()
                })
            },
            saveRoleData() {
                let _this = this;
                _this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/role/saveRoleInfo',
                            data: _this.role
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.dialogFormVisible = false;
                                _this.resetRoleData();
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
            resetRoleData() {
                let _this = this;
                _this.role = {
                    roleName: '',
                    roleCode: '',
                    status: '1'
                }
            },
            resetRoleData() {
                let _this = this;
                _this.role = {
                    roleName: '',
                    roleCode: '',
                    status: '1'
                }
            },
            edit(row) {
                let _this = this;
                _this.role = Object.assign({}, row);
                _this.dialogStatus = 'update';
                _this.dialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['dataForm'].clearValidate()
                })
            },
            updateRole() {
                let _this = this;
                _this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/role/modifyRole',
                            data: _this.role
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.dialogFormVisible = false;
                                _this.resetRoleData();
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
            deleteRole(row) {
                let _this = this;
                _this.$confirm(`确定删除角色`+row.roleName+`吗`, '删除提示',{
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/role/delete',
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
                _this.fetchData()
            },
            enable(row) {
                let _this = this;
                _this.$confirm(`确定启用角色`+row.roleName+`吗`, '启用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/role/enable',
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
                _this.$confirm(`确定禁用角色`+row.roleName+`吗`, '禁用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/role/disable',
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
            bindUser(row) {
                let _this = this;
                _this.currentSelectedRoleId = row.id;
                console.log(row);
                _this.bindUserDialogVisible = true;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/role/getBindUserByRoleId',
                    data: row.id
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.allUser = res.data.result.alllUserList;
                        _this.alreadyBindUser = res.data.result.alreadyBindUserIds;
                        _this.bindUserIds = res.data.result.alreadyBindUserIds;
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
            getAllResourceIdList(){
                let _this = this;
                _this.$http({
                    method: 'POST',
                    url: '/role/getAllResourceIdList'
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.checkedAllKeys = res.data.result;
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
            bindAuthority(row){
                let _this = this;
                _this.selectedRoleId = row.id;
                _this.bindAuthorityDialogVisible = true;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/role/getAllAuthorityInfoList'
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.treeData = res.data.result;
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
                _this.$http({
                    method: 'POST',
                    url: '/role/getBindResourceInfoByRoleId',
                    data: _this.selectedRoleId
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.checkedKeys = res.data.result;
                    }else{
                        _this.$message({
                            type: 'warning',
                            message: res.data.message
                        })
                    }
                }).catch((err) => {
                    console.log(err);
                });
                _this.getAllResourceIdList();
            },
            saveAuthorityInfo(){
                let _this = this;
                _this.btnDisabled = true;
                let checkedNodes = _this.$refs.authorityTree.getCheckedNodes();

                _this.checkedNodeList = [];
                checkedNodes.forEach((item) => {
                    let obj = {};
                    obj.resourceId = item.id;
                    obj.type = item.type;
                    _this.checkedNodeList.push(obj);
                });
                let param = {};
                param.roleId = _this.selectedRoleId;
                param.resourceInfoList = _this.checkedNodeList;
                console.log(param);
                _this.$http({
                    method: 'POST',
                    url: '/role/roleBindResource',
                    data: param
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.bindAuthorityDialogVisible = false;
                        _this.listLoading = false;
                        _this.btnDisabled = false;
                        _this.$message({
                            type: 'success',
                            message: '绑定成功!'
                        })
                    }else{
                        _this.btnDisabled = false;
                        _this.$message({
                            type: 'warning',
                            message: res.data.message
                        })
                    }
                }).catch((err) => {
                    console.log(err);
                });
            },
            handleChange(value, direction, movedKeys) {
                let _this = this;
                console.log(value, direction, movedKeys);
                _this.bindUserIds = value;
            },
            checkedAll(){
                let _this = this;
                _this.checkedKeys = _this.checkedAllKeys;
            },
            noCheckedAll(){
                let _this = this;
                _this.$refs.authorityTree.setCheckedKeys([]);
                _this.checkedKeys = [];
            },
            roleBindUser(){
                let _this = this;
                let param = {};
                param.roleId = _this.currentSelectedRoleId;
                param.userIds = _this.bindUserIds;
                _this.$http({
                    method: 'POST',
                    url: '/role/roleBindUser',
                    data: param
                }).then((res) => {
                    if(res.data.code === 200){
                        _this.bindUserDialogVisible = false;
                        _this.btnDisabled = false;
                        _this.currentSelectedRoleId ='';
                        _this.$message({
                            type: 'success',
                            message: '绑定成功!'
                        })
                    }else{
                        _this.btnDisabled = false;
                        _this.$message({
                            type: 'warning',
                            message: res.data.message
                        })
                    }
                }).catch((err) => {
                    console.log(err);
                });
            }
        }
    });
</script>
</body>
</html>