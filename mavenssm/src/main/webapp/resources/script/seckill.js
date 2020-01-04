//存放主要的交互逻辑js代码
//javascript 模块话

// $(function(){
//
//
//     $.get('/mavenssm_war/seckill/time/now',{},function (resullt) {
//         alert("2222222222222");
//         alert();
//         var dat=new Date(resullt.data);
//         alert(dat.getFullYear());
//     },'json');
// });



var seckill = {
    //封装秒杀相关的ajax的url
    URL : {
        now: function () {
            return '/mavenssm_war/seckill/time/now';
        },
        exposer : function(seckillId){
            return '/mavenssm_war/seckill/'+seckillId+'/exposer';
        },
        execution : function (seckillId,md5) {
            return '/mavenssm_war/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },

    //处理秒杀逻辑
    handleSeckillkill: function(seckillId,node){
        node.hide()
            .html('<button class = "btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')//按钮
        $.post(seckill.URL.exposer(seckillId),{},function(result){
            //再回调函数中、执行交互流程
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log("killUrl:"+killUrl);
                    //绑定一次点击事件
                    $("#killBtn").one('click',function () {
                        //执行秒杀请求的操作
                        //1.先禁用按钮
                        $(this).addClass("disabled");

                        //2.执行秒杀请求
                        $.post(killUrl,{},function(result){
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class = "label label-success">'+stateInfo+'</span>');

                            }
                        });
                    });
                    node.show();
                }else{
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log("result"+result);
            }
        });

    },

    //验证手机号
    //isNaN()这个方法就是非数字 那么!isNaN() 就是代表数字的意思

    validatePhone : function(phone){
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },

    countdown:function(seckillId,nowTime,startTime,endTime){
        var seckillBox = $('#seckill-box');

        // alert(typeof(nowTime))
        // alert(typeof(startTime))
        // alert(typeof(endTime))
        //时间判断
        if((nowTime-endTime)>0){
            //秒杀结束
            seckillBox.html('秒杀结束！');
        }else if((nowTime - startTime)<0){

            //秒杀未开始
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                //控制时间的格式
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //时间完成之后回调函数
            }).on('finish.countdown',function () {
                //当倒计时结束时候、那么就获取秒杀地址、也就是会出现一个秒杀的按钮、执行秒杀
                seckill.handleSeckillkill(seckillId,seckillBox);
            });
        }else{
            //秒杀开始
            seckill.handleSeckillkill(seckillId,seckillBox);
        }
    },

    //详情页秒杀逻辑
    detail : {
        //详情页面的初始化
        init : function(params){
            //手机验证和登陆,计时间交互
            //规划我们的交互流程
            //在cookie查找手机号码,
            var killPhone = $.cookie('killPhone');

            //接下来就是验证手机号 因为验证手机号 多个地方都要用到,所有把这个步骤放在上方
            //如果cookie 没有手机号的话 也就是 如果手机号为空的时候 也就是验证手机号 返回结果为false时
            if(!seckill.validatePhone(killPhone)){
                //如果手机号为空的时候 就要绑定手机号
                //控制输出
                var killPhoneModel = $('#killPhoneModal');
                //弹出显示层
                killPhoneModel.modal({
                    show : true, //显示弹出层
                    backdrop : 'static', //禁止位置关闭
                    keyboard : false //关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                   var inputPhone = $('#killPhoneKey').val();
                   console.log('inputPhone='+inputPhone); //todo
                   if(seckill.validatePhone(inputPhone)){
                       //电话写入cookie
                       //expires cookie 有效期7天
                       $.cookie('killPhone',inputPhone,{expires:7,path:'/mavenssm_war/seckill'});
                       //刷新页面
                       window.location.reload();
                   }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                   }
                });
            }
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            //判断过了就是已经登陆了
            $.get(seckill.URL.now(),{},function (result) {

                if(result && result['success']){

                    var nowTime = result['data'];
                    //时间判断 计时交互
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log('result:'+result);
                }
            },'json');

        }

    }
}
