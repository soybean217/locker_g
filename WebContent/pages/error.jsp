<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <meta charset="UTF-8">
    <title>水果管理</title>
    <script type="text/javascript" src="./js/jquery.min.js"></script>
    <style type="text/css">
        input,textarea {
            -moz-border-radius: 0px;
            -webkit-border-radius: 0px;
            border-radius: 0px;
            -webkit-appearance: none;
            background: none;
            -webkit-appearance: none;
        }
        input.input-text, select, textarea {
            background: #fff;
            border: 1px solid #ddd;
        }
        /*** 弹框 ***/
        .dialog {
            width: 300px;
            background-color: rgba(255, 255, 255, .9);
            border-radius: 5px;
            position: fixed;
            left: 50%;
            margin-left: -150px;
            margin-bottom: 200px;
            z-index: 1001;
            box-shadow: 0 0 6px rgba(0, 0, 0, .4);
        }

        .dialog-tips {
            background-color: rgba(0, 0, 0, .8);
            border: 0 none;
            color: #fff;
            font-size: 18px;
            border-radius: 5px;
        }

        .dialog-tips-icon {
            display: block;
            margin: 0 auto 10px;
            width: 50px;
            height: 50px;
            background: url(./img/tips-icon.png) no-repeat 0 0;
            background-size: 100px 50px;
        }

        .dialog-tips-icon.error {
            background-position: -50px 0;
        }

        .dialog .content {
            padding: 20px 16px;
            text-align: center;
            font-size: 14px;
        }

        .dialog-tips .content {
            padding: 16px;
        }

        .dialog-caption {
            color: #000;
            overflow: hidden;
        }

        .dialog-caption b { /* display: block; */
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            text-align: center;
            font-size: 14px;
            line-height: 30px;
            font-weight: 400;
            display: none;
        }

        .dialog-icon {
            opacity: .75;
            position: absolute;
            top: -15px;
            right: -15px;
            z-index: 1;
            color: white;
            width: 30px;
            height: 30px;
            line-height: 30px;
            cursor: pointer;
            font-size: 28px;
            background: #000;
            font-family: arial;
            border-radius: 15px;
            text-align: center;
        }

        .dialog-overlay {
            background-color: #000;
            position: fixed;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            opacity: .25;
            z-index: 1000;
        }

        .dialog.cool {
            background-color: black;
            font-size: 120%;
            font-weight: bold;
            color: white;
            text-align: center;
        }

        .dialog input {
            height: 36px;
            padding: 6px;
            line-height: 24px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }

        .dialog-prompt-content {
            margin-bottom: 16px;
        }

        .pc-btn {
            overflow: hidden;
            border-top: 1px solid #dbd8d8;
            border-radius: 0 0 5px 5px;
        }

        .pc-btn a {
            display: block;
            float: left;
            width: 50%;
            line-height: 30px;
            font-size: 14px;
            text-align: center;
            text-decoration: none;
            color: #008db8;
        }

        .pc-btn a:active {
            background: #e3f8ff;
        }

        .pc-btn a.bor1 {
            border-right: 1px solid #dbd8d8;
            margin-left: -1px;
        }
        body{
            font-family: arial, 'Microsoft Yahei', 'Hiragino Sans GB', sans-serif !important;
            color: #333;
        }
        .box{
            margin: 0 10px;
        }
        ul, ol ,p{
            list-style: none;
            margin: 0; padding: 0;
        }
        .box li{
            margin-bottom: 20px;
        }
        .input-text {
            height: 40px;
            box-sizing: border-box;
            width: 100%;
            padding-left: 10px;
            font-size: 16px;

        }
        .btn{
            width: 100%;
            background: #019875;
            height: 40px;
            border: 0;
            color: #fff;
            font-size: 16px;
        }
        .fail{
            width: 100%;
            height: 48px;
            background: url(./img/fail.png) no-repeat;
            padding-left: 60px;
            line-height: 48px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .product{
            border: 2px solid #f5f5f5;
            overflow: hidden;
        }
        img{
            display: block;
            width: 100%;
        }
        .desc{
            padding: 20px 0;
            text-align: left;
            font-size: 16px;
            color: #333;
        }
        .prompt{
            font-size: 12px;
            text-align: left;
            color: #666;
        }
        .fail-prompt{
            padding-top: 30px;
        }
    </style>
    <script type="text/javascript">
 
    </script>
</head>
<body style=" background:url(./img/suc-bg.jpg) no-repeat; background-size:cover;" id=“top">
<form action="./addWxUser.html" method='GET' id="open">
    <section class="box hg">
        <div class="fail">验证用户信息失败！</div>
        <p class="prompt fail-prompt">
            请核实微信号等信息，重新输入
        </p>
        <div class="cl">
            <input class="btn" type="button" value="确定" onclick="checksubmit();"/>
        </div>
    </section>
</form>
<script type="text/javascript">

function sureClick(){
	 alert(1);
	// history.go(-1);
}

    var Modal = {
        el: '<div class="dialog">',
        caption: '<div class="dialog-caption"><b></b><div class="dialog-icon" data-dialog-cancel>×</div></div>',
        content: '<div class="content">',
        btns: '<div class="pc-btn"><a href="#" class="bor1" data-dialog-cancel>取消</a> <a href="#" data-dialog-ok>确定</a></div>',
        mask: '<div class="dialog-overlay">',
        create: function(options) {
            options = options || {}

            var modal = {
                options: options
            }

            modal.el = $(this.el);
            modal.mask = $(this.mask);

            if (options.title) {
                modal.caption = $(this.caption)
                modal.caption.find('b').text(options.title)
                modal.el.append(modal.caption)
            }

            modal.content = $(this.content)
            options.content && modal.content.html(options.content)
            modal.el.append(modal.content)

            if (options.ok || options.cancel) {
                modal.btns = $(this.btns)
                modal.el.append(modal.btns)
            }

            if($('.dialog').length){
                $('.dialog').after(modal.el);
            } else {
                $('body').prepend(modal.el);
            }

            $('body').append(modal.mask)

            modal.close = function() {
                modal.el.remove()
                modal.mask.fadeOut(320, function(){
                    modal.mask.remove()
                })
            }
            modal.center = function() {
                modal.el.css({
                    top: ($(window).height() - modal.el.outerHeight()) / 2
                })
            }

            modal.el.on('tap', '[data-dialog-cancel]', function(e) {
                e.preventDefault()
                if (options.cancel && options.cancel(modal) === false) return
                modal.close()
            })

            modal.el.on('tap', '[data-dialog-ok]', function(e) {
                e.preventDefault()
                if (options.ok && options.ok(modal) === false) return
                modal.close()
            })


            modal.center();

            modal.el.find('input, textarea').eq(0).focus();

            $(window).resize(function(){
                modal.center();
            });

            return modal
        },
        alert: function(str, title) {
            return this.create({
                title: title || ' ',
                content: str
            })
        },
        confirm: function(str, fn, fn2, btn1, btn2) {
            var modal = this.create({
                content: str,
                ok: function(modal) {
                    modal.close();
                    fn && fn();
                },
                cancel: fn2 || function() {}
            })
            if (btn1) {
                modal.el.find('[data-dialog-ok]').text(btn1)
            }
            if (btn2) {
                modal.el.find('[data-dialog-cancel]').text(btn2)
            }
            return modal
        },
        prompt: function(str, fn){
            var modal = this.create({
                content: '<div class="dialog-prompt-content">' + str + '</div><input type="text" name="prompt" autocomplete="off">',
                ok: function(modal) {
                    return fn && fn(modal.el.find('input').val())
                },
                cancel: function() {}
            });


            return modal
        },
        tips: function(str/*, time, type, fn*/) {
            var time = 2000,
                    type = '',
                    fn = $.noop

            $(arguments).each(function(i){
                if (i){
                    var atype = $.type(this);
                    if(atype == 'function'){
                        fn = this;
                    } else if (atype == 'string'){
                        type = this;
                        str = '<i class="dialog-tips-icon '+ type +'"></i>' + str;
                    } else if (atype == 'number'){
                        time = this;
                    };
                };
            })

            var modal = this.create({
                content: str
            })

            modal.el.addClass('dialog-tips').css({
                top: 'auto',
                bottom: '25%'
            })

            setTimeout(function() {
                modal.close()
                fn(modal)
            }, time)

            return modal
        }
    }
    function sureClick() {
        $('#open').submit();
    }
</script>
</body>
</html>
