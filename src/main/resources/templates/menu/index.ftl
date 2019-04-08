<!DOCTYPE html>
<html lang="en">
<head>
    <#include "../common/head.ftl">
</head>
<style type="text/css">
   .filter-container{
       margin: 30px 10px 30px 0;
   }
</style>
<body>
<div id="app">
    <div class="app-container calendar-list-container">
        <div class="filter-container">
            <el-button class="filter-item" type="primary"  @click="newAdd" >添加</el-button>
            <el-button class="filter-item" type="primary"  @click="edit" >修改</el-button>
            <el-button class="filter-item" type="primary"  @click="disable" >禁用</el-button>
            <el-button class="filter-item" type="primary"  @click="enable" >启用</el-button>
            <el-button class="filter-item" type="primary"  @click="deleteMenu" >删除</el-button>
        </div>
        <div class="tree-container" style="width:25%;">
            <el-input
                    placeholder="输入关键字进行过滤"
                    v-model="filterText">
            </el-input>
            <el-tree
                    class="filter-tree"
                    :data="treeData"
                    :props="defaultProps"
                    default-expand-all
                    node-key="id"
                    highlight-current
                    accordion
                    :filter-node-method="filterNode"
                    @node-click="getNodeData"
                    ref="menuTree">
            </el-tree>
        </div>
        <!--新增模态框-->
        <el-dialog :visible.sync="addDialogFormVisible" @close="resetForm('ruleForm1')">
            <el-form :rules="addRules" ref="ruleForm1" :model="ruleForm1" label-position="left" label-width="80px" style='width: 400px; margin-left:50px;'>
                <el-form-item label="父菜单" prop="addParentName">
                    <el-input v-model.trim="addParentName" disabled></el-input>
                </el-form-item>
                <el-form-item label="菜单名称" prop="menuName">
                    <el-input v-model.trim="ruleForm1.menuName"></el-input>
                </el-form-item>
                <el-form-item label="菜单编码"  prop="menuCode">
                    <el-input v-model.trim="ruleForm1.menuCode"/>
                </el-form-item>
                <el-form-item label="菜单URL"  prop="url">
                    <el-input v-model.trim="ruleForm1.url"/>
                </el-form-item>
                <el-form-item label="排序"  prop="number">
                    <el-input-number v-model.trim="ruleForm1.number" controls-position="right" :min="1" :max="100"/>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model.trim="ruleForm1.status" label="1">启用</el-radio>
                    <el-radio v-model.trim="ruleForm1.status" label="0">禁用</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitAddForm('ruleForm1')">保存</el-button>
                <el-button @click="resetForm('ruleForm1')">取消</el-button>
            </div>
        </el-dialog>

        <!--新增模态框-->
        <el-dialog :visible.sync="editDialogFormVisible" @close="resetForm('ruleForm2')">
            <el-form :rules="editRules" ref="ruleForm2" :model="ruleForm2" label-position="left" label-width="80px" style='width: 400px; margin-left:50px;'>
                <el-form-item label="父菜单" prop="editParentName">
                    <el-input v-model.trim="editParentName" disabled></el-input>
                </el-form-item>
                <el-form-item label="菜单名称" prop="menuName">
                    <el-input v-model.trim="ruleForm2.menuName"></el-input>
                </el-form-item>
                <el-form-item label="菜单编码"  prop="menuCode">
                    <el-input v-model.trim="ruleForm2.menuCode"/>
                </el-form-item>
                <el-form-item label="菜单URL"  prop="url">
                    <el-input v-model.trim="ruleForm2.url"/>
                </el-form-item>
                <el-form-item label="排序"  prop="number">
                    <el-input-number v-model.trim="ruleForm2.number" controls-position="right" :min="1" :max="100"/>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model.trim="ruleForm2.status" label="1">启用</el-radio>
                    <el-radio v-model.trim="ruleForm2.status" label="0">禁用</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitEditForm('ruleForm2')">保存</el-button>
                <el-button @click="resetForm('ruleForm2')">取消</el-button>
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
            const validateMenuCode = (rule, value, callback) => {
                let _this = this;
                const menuCodeRegex = /^[A-Za-z0-9]{3,16}$/;
                if (!menuCodeRegex.test(value)) {
                    callback(new Error('菜单编码格式不合法'))
                } else {
                    const param = {};
                    param.menuCode = value;
                    if (this.dialogStatus === 'update') {
                        param.menuId = this.ruleForm2.id
                    }
                    _this.$http({
                        method: 'POST',
                        url: '/menu/checkMenuCode',
                        data: param
                    }).then((res) => {
                        if(res.data.code === 200 && res.data.result === true){
                            callback(new Error('菜单编码已存在'));
                        }else{
                            callback();
                        }
                    }).catch((err) => {
                        console.log(err);
                        callback(new Error('服务异常,请稍后重试'));
                    });
                }
            };
            const validateMenuName = (rule, value, callback) => {
                const menuNameRegex = /^[\u4e00-\u9faf]+$/;
                if (!menuNameRegex.test(value)) {
                    callback(new Error('菜单名称格式不合法'))
                } else {
                    callback()
                }
            };
            return {
                filterText: '',
                treeData: [],
                defaultProps: {
                    children: 'children',
                    label: 'menuName'
                },
                labelPosition: 'right',
                listQuery: {
                    menuName: ''
                },
                listLoading: true,
                addDialogFormVisible: false,
                editDialogFormVisible: false,
                dialogStatus: '',
                deleteMenuId: '',
                disabledMenu: '',
                downloadLoading: false,
                addParentName: '',
                ruleForm1: {
                    pid: '',
                    menuName: '',
                    menuCode: '',
                    url: '',
                    status: '1',
                    number: 1
                },
                editParentName: '',
                ruleForm2: {
                    id: '',
                    pid: '',
                    parentName: '',
                    menuName: '',
                    menuCode: '',
                    url: '',
                    status: '1',
                    number: 1
                },
                addRules: {
                    menuName: [
                        { required: true, message: '菜单名不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '菜单名长度为2到20个字符', trigger: 'blur' },
                        { validator: validateMenuName, trigger: 'blur' }
                    ],
                    menuCode: [
                        { required: true, message: '菜单编码不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '请正确填写菜单编码,支持2-20个汉字', trigger: 'blur' },
                        { validator: validateMenuCode, trigger: 'blur' }
                    ],
                    url: [
                        { required: true, message: '菜单URL不能为空', trigger: 'blur' },
                        { min: 2, max: 30, message: '请正确填写菜单URL,支持2-30个字符', trigger: 'blur' }
                    ]
                },
                editRules: {
                    menuName: [
                        { required: true, message: '菜单名不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '菜单名长度为2到20个字符', trigger: 'blur' },
                        { validator: validateMenuName, trigger: 'blur' }
                    ],
                    menuCode: [
                        { required: true, message: '菜单编码不能为空', trigger: 'blur' },
                        { min: 2, max: 20, message: '请正确填写菜单编码,支持2-20个汉字', trigger: 'blur' },
                        { validator: validateMenuCode, trigger: 'blur' }
                    ],
                    url: [
                        { required: true, message: '菜单URL不能为空', trigger: 'blur' },
                        { min: 2, max: 30, message: '请正确填写菜单URL,支持2-30个字符', trigger: 'blur' }
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
        watch: {
            filterText(val) {
                this.$refs.menuTree.filter(val);
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                let _this = this;
                _this.listLoading = true;
                _this.$http({
                    method: 'POST',
                    url: '/menu/getMenuTree',
                    data: '-1'
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
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.menuName.indexOf(value) !== -1;
            },
            getNodeData(data) {
                let _this = this;
                console.log(data);
                // 新增
                _this.ruleForm1.pid = data.id;
                _this.addParentName = data.menuName;
                // 修改
                _this.ruleForm2.id = data.id;
                _this.ruleForm2.pid = data.parentId;
                _this.editParentName = data.parentMenuName;
                _this.ruleForm2.menuCode = data.menuCode;
                _this.ruleForm2.menuName = data.menuName;
                _this.ruleForm2.url = data.url;
                _this.ruleForm2.number = data.number;
                _this.ruleForm2.status = data.status;
                // 删除
                _this.deleteMenuId = data.id;
                // 节点禁用标识
                _this.disabledMenu = data.disabled;
            },
            newAdd() {
                let _this = this;
                if (_this.ruleForm1.pid === '') {
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '请选择父菜单'
                    });
                    return false
                }
                _this.addDialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['ruleForm1'].clearValidate()
                })
            },
            submitAddForm(formName) {
                let _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.listLoading = true;
                        _this.$http({
                            method: 'POST',
                            url: '/menu/saveMenuInfo',
                            data: _this.ruleForm1
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.addDialogFormVisible = false;
                                _this.resetForm(formName);
                                _this.listLoading = false;
                                _this.$notify({
                                    title: '成功',
                                    message: '保存成功',
                                    type: 'success',
                                    duration: 2000
                                })
                            }else{
                                _this.$message.error(res.data.message)
                            }
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                })
            },
            resetForm(formName) {
                let _this = this;
                _this.$refs[formName].resetFields();
                _this.addDialogFormVisible = false;
                _this.editDialogFormVisible = false;
            },
            edit() {
                let _this = this;
                if (_this.ruleForm2.id === '') {
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '请选择修改的菜单'
                    });
                    return false
                }
                if (_this.disabledMenu) {
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '该菜单不允许修改'
                    });
                    return false
                }
                _this.dialogStatus = 'update';
                _this.editDialogFormVisible = true;
                _this.$nextTick(() => {
                    _this.$refs['ruleForm2'].clearValidate()
                })
            },
            submitEditForm(formName) {
                let _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.listLoading = true;
                        _this.$http({
                            method: 'POST',
                            url: '/menu/modifyMenu',
                            data: _this.ruleForm2
                        }).then((res) => {
                            if(res.data.code === 200){
                                _this.listLoading = false;
                                _this.editDialogFormVisible = false;
                                _this.resetForm(formName);
                                _this.fetchData();
                                _this.$notify({
                                    title: '成功',
                                    message: '更新成功',
                                    type: 'success',
                                    duration: 2000
                                })
                            }else{
                                _this.$message.error(res.data.message)
                            }
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                })
            },
            deleteMenu() {
                let _this = this;
                if (_this.disabledMenu) {
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '该菜单不允许删除'
                    });
                    return false
                }
                _this.$confirm(`确定删除该菜单吗？`, '删除提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.listLoading = true;
                    _this.$http({
                        method: 'POST',
                        url: '/menu/delete',
                        data: _this.deleteMenuId
                    }).then((res) => {
                        if(res.data.code === 200){
                            _this.listLoading = false;
                            _this.fetchData();
                            _this.$notify({
                                title: '成功',
                                message: '删除成功',
                                type: 'success'
                            })
                        }else{
                            _this.$message.error(res.data.message)
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
            enable() {
                let _this = this;
                _this.$confirm(`确定启用该菜单吗？`, '启用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.listLoading = true;
                    _this.$http({
                        method: 'POST',
                        url: '/menu/enable',
                        data: _this.deleteMenuId
                    }).then((res) => {
                        if(res.data.code === 200){
                            _this.listLoading = false;
                            _this.fetchData();
                            _this.$notify({
                                title: '成功',
                                message: '启用成功',
                                type: 'success'
                            })
                        }else{
                            _this.$message.error(res.data.message)
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
            disable() {
                let _this = this;
                this.$confirm(`确定禁用该菜单吗？`, '禁用提示', {
                    cancelButtonText: '取消',
                    confirmButtonText: '确定',
                    type: 'warning'
                }).then(() => {
                    _this.listLoading = true;
                    _this.$http({
                        method: 'POST',
                        url: '/menu/disable',
                        data: _this.deleteMenuId
                    }).then((res) => {
                        if(res.data.code === 200){
                            _this.listLoading = false;
                            _this.fetchData();
                            _this.$notify({
                                title: '成功',
                                message: '禁用成功',
                                type: 'success'
                            })
                        }else{
                            _this.$message.error(res.data.message)
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
            }
        }
    });
</script>
</body>
</html>