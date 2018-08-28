/**
 * Created by yiwei on 15/12/11.
 */
window.onload = function () {
    var msg = document.getElementById('msg'),
        img = document.getElementById('img'),
        _msg = '';

    var commonAlert = [
            {
                tag: '',
                className: '',
                parentNodeSelector: '#bg_mask'
            }
        ],
        commonEvent = [
            {
                selector: '#bg_mask',
                eventType: 'touchend',
                listener: function (event) {
                    $('#bg_mask').remove();
                }
            }
        ];

    if (window.platform.isWeixin()) {
        guide && guide();
    } else {
        download && download();
    }
    // 引导
    function guide() {
//        setMsg('微信内不能直接下载哟......');
        if (window.device.isIOS()) {
            commonAlert[0].tag = '<img src="./img/pmt.png">';
            commonAlert[0].className = 'alert-bg';
            mi.util.mask('mask_bg', commonAlert, commonEvent);
        } else if (device.isAndroid()) {
            commonAlert[0].tag = '<img src="./img/download-mask.png">';
            commonAlert[0].className = 'alert_bg';
            mi.util.mask('', commonAlert, commonEvent);
        }
    }

    function download() {
        if (window.device.isAndroid()) {
            window.location = 'Fruit.apk';
        }else{
            window.location = 'https://itunes.apple.com/cn/app/guo-bao/id1111805482?l=zh&ls=1&mt=8';
//            window.location.href = 'https://itunes.apple.com/cn/app/wo-yu-feng-tian-zheng-ban/id1042575152?l=zh&ls=1&mt=8';
        }
    }

    function setTimer(time, callback) {
        setTimeout(callback, time);
    }

    function setMsg() {
        img.src = path + 'reason.png';
        img.className = 'reason';
        msg.innerHTML = arguments[0] ||  _msg;
    }

    function addParam(url, obj) {
        var reg = /(\?\w+)/i;
        if (reg.test(url)) {
            if (mi.util.getQueryVal('channel', url) == "") {
                url += '&' + obj.key + '=' + obj.value;
            }
        } else if (!reg.test(url)) {
            url += '?' + obj.key + '=' + obj.value;
        }
        return url;
    }
};