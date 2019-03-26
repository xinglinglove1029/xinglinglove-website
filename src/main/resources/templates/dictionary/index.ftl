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
    <div class="app-container calendar-list-container">
        <div class="filter-container">
            <el-button class="filter-item" type="primary"  @click="newAdd" >添加</el-button>
            <el-button class="filter-item" type="primary"  @click="updateDic" >修改</el-button>
            <el-button class="filter-item" type="primary"  @click="deleteDic" >删除</el-button>
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
                    ref="dicTree">
            </el-tree>
        </div>
        <!--新增模态框-->
        <el-dialog :visible.sync="addDialogFormVisible" @close="resetForm('ruleForm1')">
            <el-form :rules="addRules" ref="ruleForm1" :model="ruleForm1" label-position="left" label-width="80px" style='width: 400px; margin-left:50px;'>
                <el-form-item label="父节点" prop="addParentName">
                    <el-input v-model.trim="addParentName" disabled></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="name">
                    <el-input v-model.trim="ruleForm1.name"></el-input>
                </el-form-item>
                <el-form-item label="编码"  prop="code">
                    <el-input v-model.trim="ruleForm1.code"/>
                </el-form-item>
                <el-form-item label="值"  prop="value">
                    <el-input v-model.trim="ruleForm1.value"/>
                </el-form-item>
                <el-form-item label="排序"  prop="number">
                    <el-input-number v-model.trim="ruleForm1.number" controls-position="right" :min="1" :max="100"/>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input  type="textarea" :autosize="{ minRows: 5, maxRows: 100}"
                               placeholder="请输入内容" v-model.trim="ruleForm1.remark" maxlength="255"></el-input>
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
                <el-form-item label="父节点" prop="editParentName">
                    <el-input v-model.trim="editParentName" disabled></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="name">
                    <el-input v-model.trim="ruleForm2.name"></el-input>
                </el-form-item>
                <el-form-item label="编码"  prop="code">
                    <el-input v-model.trim="ruleForm2.code"/>
                </el-form-item>
                <el-form-item label="值"  prop="value">
                    <el-input v-model.trim="ruleForm2.value"/>
                </el-form-item>
                <el-form-item label="排序"  prop="number">
                    <el-input-number v-model.trim="ruleForm2.number" controls-position="right" :min="1" :max="100"/>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input  type="textarea" :autosize="{ minRows: 5, maxRows: 100}"
                               placeholder="请输入内容" v-model.trim="ruleForm2.remark" maxlength="255"></el-input>
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
            const validateCode = (rule, value, callback) => {
                const valueRegex = /^[0-9]{0,12}$/;
                if (!valueRegex.test(value)) {
                    callback(new Error('数据字典编码格式不合法'))
                } else {
                    _this.$http({
                        method: 'POST',
                        url: '/dictionary/checkDicCode',
                        data: value
                    }).then((res) => {
                        if(res.data.success && res.data.body){
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
            return {
                addDialogFormVisible: false,
                editDialogFormVisible: false,
                ruleForm1: {
                    parentCode: '',
                    value: '',
                    code: '',
                    number: '',
                    name: '',
                    remark: ''
                },
                addParentName: '',
                ruleForm2: {
                    parentCode: '',
                    value: '',
                    code: '',
                    number: '',
                    id: '',
                    name: '',
                    remark: ''
                },
                editParentName: '',
                deleteDictionaryId: '',
                filterText: '',
                treeData: [],
                disabledFlag: false,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                addRules: {
                    value: [
                        { required: true, message: '数据字典值不能为空', trigger: 'blur' },
                        { min: 1, max: 32, message: '数据字典值不能超过32个字符', trigger: 'blur' },
                    ],
                    name: [
                        { required: true, message: '数据字典名称不能为空', trigger: 'blur' },
                        { min: 1, max: 32, message: '数据字典名称不能超过32个字符', trigger: 'blur' },
                    ],
                    code: [
                        { required: true, message: '数据字典编码不能为空', trigger: 'blur' },
                        { min: 1, max: 12, message: '数据字典编码不能超过12个字符', trigger: 'blur' },
                        { validator: validateCode, trigger: 'blur' }
                    ],
                    number: [
                        { required: true, message: '排序数字不能为空', trigger: 'blur' }
                    ]
                },
                editRules: {
                    value: [
                        { required: true, message: '数据字典名称不能为空', trigger: 'blur' },
                        { min: 1, max: 32, message: '数据字典名称不能超过32个字符', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '数据字典名称不能为空', trigger: 'blur' },
                        { min: 1, max: 32, message: '数据字典名称不能超过32个字符', trigger: 'blur' },
                    ],
                    number: [
                        { required: true, message: '排序数字不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.dicTree.filter(val);
            }
        },
        created() {
            this.getDictionaryTree()
        },
        methods: {
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            getDictionaryTree() {
                let _this = this;
                _this.$http({
                    method: 'POST',
                    url: '/dictionary/getDicTree'
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
            getNodeData(data) {
                let _this = this;
                console.log(data);
                // 新增
                _this.ruleForm1.parentCode = data.code;
                _this.addParentName = data.name;
                // 修改
                _this.ruleForm2.parentCode = data.parentCode;
                _this.ruleForm2.code = data.code;
                _this.ruleForm2.name = data.name;
                _this.ruleForm2.value = data.value;
                _this.ruleForm2.id = data.id;
                _this.ruleForm2.remark = data.remark;
                _this.ruleForm2.number = data.number;
                _this.editParentName = data.parentName;
                _this.disabledFlag = data.disabled;
                // 删除
                _this.deleteDictionaryId = data.id;
            },
            newAdd() {
                let _this = this;
                if(_this.ruleForm1.parentCode === ''){
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '请选择父节点'
                    });
                    return false;
                }
                _this.addDialogFormVisible = true;
                _this.$nextTick(function (){
                    _this.$refs['ruleForm1'].clearValidate()
                })
            },
            submitAddForm(formName) {
                let _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/dictionary/saveDicInfo',
                            data: _this.ruleForm1
                        }).then((res) => {
                            if(res.data.success){
                                _this.addDialogFormVisible = false;
                                _this.resetForm(formName);
                                _this.getDictionaryTree();
                                _this.$message({
                                    type: 'success',
                                    message: '新增成功!'
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
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                let _this = this;
                _this.$refs[formName].resetFields();
                _this.addDialogFormVisible = false;
                _this.editDialogFormVisible = false;
            },
            updateDic(){
                let _this = this;
                if(_this.ruleForm2.id === ''){
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '请选择要修改的节点'
                    });
                    return false;
                }
                if(_this.disabledFlag){
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '该节点不允许修改'
                    });
                    return false;
                }
                _this.editDialogFormVisible = true;
                _this.$nextTick(function (){
                    _this.$refs['ruleForm2'].clearValidate()
                })
            },
            submitEditForm(formName){
                let _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.$http({
                            method: 'POST',
                            url: '/dictionary/modifyDic',
                            data: _this.ruleForm2
                        }).then((res) => {
                            if(res.data.success){
                                _this.editDialogFormVisible = false;
                                _this.resetForm(formName);
                                _this.getDictionaryTree();
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
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            deleteDic(){
                let _this = this;
                if(_this.deleteDictionaryId === ''){
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '请选中要删除的节点'
                    });
                    return false;
                }
                if(_this.disabledFlag){
                    _this.$message({
                        showClose: true,
                        duration: 3000,
                        message: '该节点不允许删除'
                    });
                    return false;
                }
                _this.$confirm('此操作将永久删除该节点, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.$http({
                        method: 'POST',
                        url: '/dictionary/delete',
                        data: _this.deleteDictionaryId
                    }).then((res) => {
                        if(res.data.success){
                            _this.getDictionaryTree();
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
                    });
                });
            }
        }
    });
</script>
</body>
</html>