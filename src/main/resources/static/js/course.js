//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)

var course = {
    //封装选课相关ajax的url
    URL: {
        now: function () {
            return '/course/time/now';
        },
        exposer: function (courseId) {
            return '/course/' + courseId + '/exposer';
        },
        execution: function (courseId, md5) {
            return '/course/' + courseId + '/' + md5 + '/execution';
        },
        check:function(){
            return "/course/check";
        },
        rollback:function(courseId){
            return '/course/' + courseId + '/rollback';
        }
    },

    //验证登录信息
    validateLogin: function (userName,password) {
        if (userName && !isNaN(userName)&&password) {
            return true;//直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        } else {
            return false;
        }
    },

    //详情页选课逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //规划我们的交互流程
            //在cookie中查找信息
            var studentId = $.cookie('studentId');
            var studentMD5 = $.cookie('studentMD5');
            var stuName = $.cookie('stuName');
            var is_fifteen = $.cookie('is_fifteen');
            if (studentId==null||studentId==""||studentMD5==null||studentMD5=="") {
                var loginModal = $('#loginModal');
                loginModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false//关闭键盘事件
                });

                $('#loginBtn').click(function () {
                    var userName = $('#userName').val();
                    var password = $('#password').val();
                    if (course.validateLogin(userName,password)) {
                        $.post("/student/login",{userName:userName,password:password},function(result){
                            if(result&&result['is_Login']){
                                //写入cookie(关闭浏览器则过期)
                                $.cookie('studentId', result['studentId'], {path: '/course'});
                                $.cookie('studentMD5', result['studentMD5'], {path: '/course'});
                                $.cookie('stuName', result['stuName'], {path: '/course'});
                                $.cookie('is_fifteen', result['is_fifteen'], {path: '/course'});
                                //验证通过　　刷新页面
                                window.location.reload();
                            }else{
                                $('#loginMessage').hide().html('<label class="label label-danger">用户名或密码错误!</label>').show(300);
                            }
                        });

                    } else {
                        //todo 错误文案信息抽取到前端字典里
                        $('#loginMessage').hide().html('<label class="label label-danger">用户名或密码错误!</label>').show(300);
                    }
                });
            }else{
                $("#header-info").html(stuName+"&nbsp;实习("+is_fifteen+"周)");
                $("#logout").show();
                $("#check").show();
                $("#back").show();
            }

            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var courseId = params['courseId'];
            $.get(course.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断 计时交互
                    course.countDown(courseId, nowTime, startTime, endTime);
                } else {
                    alert('result: ' + result);
                }
            });
        }
    },

    handlerSeckill: function (courseId, node) {

        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始选课</button>');
        //获取选课地址,控制显示器,执行选课
        $.get(course.URL.exposer(courseId), {}, function (result) {
            //在回调函数种执行交互流程
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开启选课
                    //获取选课地址
                    var md5 = exposer['md5'];
                    var url = course.URL.execution(courseId, md5);
                    //绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行选课请求
                        //1.先禁用按钮
                        $(this).addClass('disabled');//,<-$(this)===('#killBtn')->
                        //2.发送选课请求执行选课
                        $.post(url, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示选课结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                } else {
                    //未开启选课(浏览器计时偏差)
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    course.countDown(courseId, now, start, end);
                }
            } else {
                console.log('result: ' + result);
            }
        });

    },

    countDown: function (courseId, nowTime, startTime, endTime) {
        var courseBox = $('#course-box');
        if (nowTime > endTime) {
            //选课结束
            courseBox.html('选课结束!');
        } else if (nowTime < startTime) {
            //选课未开始,计时事件绑定
            //var killTime = new Date(startTime + 1000);
            courseBox.html("开选时间: "+formatDate(new Date(startTime))+"(北京时间)");
            /*courseBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('选课倒计时: %D天 %H时 %M分 %S秒 ');
            }).on('finish.countdown', function () {
                //时间完成后回调事件
                //获取选课地址,控制现实逻辑,执行选课
                course.handlerSeckill(courseId, courseBox);
            });*/
        } else {
            //选课开始
            course.handlerSeckill(courseId, courseBox);
        }
    },

    logout:function(){
        $.cookie('studentId',  "",{path:"/course"});
        $.cookie('studentMD5', "",{path:"/course"});
        $.cookie('stuName',  "",{path:"/course"});
        $.cookie('is_fifteen',  "",{path:"/course"});
        //刷新页面
        window.location.reload();
    },
    check:function(){
        var studentId = $.cookie('studentId');
        window.location.href=this.URL.check()+"/"+studentId;
    },

    rollbackSeckill: function (courseId) {
        layer.confirm('你确定要退选？', {
            icon: 0,
            btn: ['确认','取消'] //按钮
        }, function(){
            var url = course.URL.rollback(courseId);
            $.post(url, {}, function (result) {
                if (result && result['success']) {
                    var killResult = result['data'];
                    var state = killResult['state'];
                    var stateInfo = killResult['stateInfo'];
                    //显示退选结果
                    layer.alert(stateInfo, {icon: 6,yes: function(){
                        //刷新页面
                        window.location.reload();
                    }});
                }else{
                    layer.alert(result.msg, {icon: 5,yes: function(){
                        //刷新页面
                        window.location.href="/course/"+courseId+"/detail";
                    }});
                }

            });
        }, function(){

        });
    },
    back:function(){
        window.location.href="/course/list";
    }

};

function formatDate(now) {
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    var date=now.getDate();
    var hour=now.getHours();
    if(hour<10){
        hour="0"+hour;
    }
    var minute=now.getMinutes();
    if(minute<10){
        minute="0"+minute;
    }
    var second=now.getSeconds();
    if(second<10){
        second="0"+second;
    }
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}
