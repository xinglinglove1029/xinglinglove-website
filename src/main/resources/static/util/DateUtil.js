//扩展Date的format方法
let DateUtils = {
    common: {
        getWeek: function(str) {
            let dateobj=new Date(str);
            let firstDay = this.getFirstWeekBegDay(dateobj.getFullYear());
            if (dateobj < firstDay) {
                firstDay = this.getFirstWeekBegDay(dateobj.getFullYear() - 1);
            }
            d = Math.floor((dateobj.valueOf() - firstDay.valueOf()) / 86400000);
            let weekNum = Math.floor(d / 7) + 2;
            let strDate = dateobj.getFullYear() + "第" + weekNum +"周";
            return strDate;
        },
        getFirstWeekBegDay: function(year) {
            let tempdate = new Date(year, 0, 1);
            let temp = tempdate.getDay();
            if (temp == 1){
                return tempdate;
            }
            temp = temp == 0 ? 7 : temp;
            tempdate = tempdate.setDate(tempdate.getDate() + (8 - temp));
            return new Date(tempdate);
        },
        getPreDate: function(date,days){
            let d=new Date(date);
            d.setDate(d.getDate()-days);
            let year = d.getFullYear();
            let month=d.getMonth()+1;
            if (month < 10) {
                month = '0' + month;
            }
            let day = d.getDate();
            if (day < 10) {
                day = '0' + day;
            }
            return year+'-'+month+'-'+day;
        },
        format: function (date, format) {
            try {
                // yyyy年MM月dd日 HH小时mm分ss秒
                let o = {
                    'M+': date.getMonth() + 1, // month
                    'd+': date.getDate(), // day
                    'H+': date.getHours(), // hour
                    'm+': date.getMinutes(), // minute
                    's+': date.getSeconds(), // second
                    'q+': Math.floor((date.getMonth() + 3) / 3), // quarter
                    'S': date.getMilliseconds()// millisecond
                };
                if (/(y+)/.test(format)) {
                    format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
                }
                for (let k in o) {
                    if (new RegExp('(' + k + ')').test(format)) {
                        format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
                    }
                }
                return format;
            } catch (e) {
                return '';
            }
        },
        dateFormatAttendanceDate: function (timestamp) {
            var date = new Date(parseInt(timestamp) * 1000);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            if (month < 10) {
                month = '0' + month;
            }
            var day = date.getDate();
            if (day < 10) {
                day = '0' + day;
            }
            return year + "-" + month + "-" + day;
        },
        dateFormatAttendanceMonth: function (timestamp) {
            var date = new Date(parseInt(timestamp) * 1000);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            if (month < 10) {
                month = '0' + month;
            }
            return year + "-" + month;
        },
        dateFormat: function (timestamp) {
            var date = new Date(parseInt(timestamp) * 1000);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            if (month < 10) {
                month = '0' + month;
            }
            var day = date.getDate();
            if (day < 10) {
                day = '0' + day;
            }
            var hours = date.getHours();
            if (hours < 10) {
                hours = '0' + hours + ':';
            } else {
                hours = hours + ':';
            }
            var minutes = date.getMinutes();
            if (minutes < 10) {
                minutes = '0' + minutes + ':';
            } else {
                minutes = minutes + ':';
            }
            var second = date.getSeconds();
            if (second < 10) {
                second = '0' + second;
            }
            return year + "-" + month + "-" + day + " " + hours + minutes + second;
        }
    }
};

