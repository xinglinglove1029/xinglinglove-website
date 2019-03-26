let CookieUtils = {
    common: {
        /**
         * 写 cookie
         * @param {Object} key     cookie 的名字
         * @param {Object} value   cookie 的值
         * @param {Object} expires  过期时间
         * @param {Object} path     路劲
         * @param {Object} domain   安全域
         */
         setCookie:function(key,value,expires,path,domain){
            //判断 key 是否存在
            if(!key && key != 0){
                return false;
            }
            //如果 value 等于空 就赋值为空字符串
            value ? true : (value = "");
            //如果 path 为空，默认为全局都可以访问
            if(!path){
                path = "/";
            }
            var cks = key + "=" + value + "; path="+path;
            if(expires){
                if(typeof expires == "number"){
                    var d = new Date();
                    d.setTime(d.getTime() + expires);
                    expires = d;
                }
                //把时间格式转换为字符串
                cks += "; expires=" + expires.toGMTString();
            }
            if(domain){
                cks += "; domain="+ domain;
            }
            document.cookie = cks;
        },

        /**
         * 获取 cookie
         * @param {Object} key  cookie 的名字
         */
         getCookie:function(key){
            var cks = document.cookie;
            var list = cks.replace(/;\s/g,";").split(";");

            var value = "";
            for(var i=0, len = list.length; i < len; i++){
                var item = list[i].split("=");
                if(item[0] == key){
                    value = item[1];
                    break;
                }
            }
            return value;
        },


        /**
         * 获取 cookie
         * @param {Object} key  cookie 的名字
         */
         getCookie2:function(key){
            var cks = document.cookie;
            cks = cks.replace(/;\s/g,";");
            var value = "";
            //用下标去判断这个 cookie 是否存在
            var firstIndex = cks.indexOf(key);
            if(firstIndex >= 0){
                //从 key 值开始截取
                var str2 = cks.substring(firstIndex+key.length);
                //从截取后的字符串查找第一个 分号
                var lastIndex = str2.indexOf(";");
                if(lastIndex>0){
                    var str3 = str2.substring(1,lastIndex);
                    value = str3;
                }else{
                    var str3 = str2.substring(1);
                    value = str3;

                }
            }
            return value;
        },
        delCookie:function(name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = getCookie(name);
            if (cval != null)
                document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
        }
    }
};

