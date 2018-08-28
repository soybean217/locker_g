/**
 * Created by chenlei on 2015/6/23.
 */
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