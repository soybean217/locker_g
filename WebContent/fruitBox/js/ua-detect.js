/**
 * Created by yiwei on 2015/9/1.
 * @desc UA判断
 *       1.设备判断：
 *          1) IPhone_IPod
 *          2) IPad
 *          3) Android(包括非小米Android判断)
 *          4) MIPhone
 *          5) MIPad
 *          6) PC
 *          7) WinPhone
 *       2. 平台判断
 *          1) 微信
 */
;(function (device, platform) {
    var ua = navigator.userAgent,
        device_reg = [
            /(MI(\s+)|2013022|2013023|HM|2014011|2014501|2014813|2014811|2014812|2014817|2014818|2014819|2014502|2015|xiaomi).*/i,
            /(MI(.*|\s*)PAD).*/i,
            /(iPhone | iPod).*/i,
            /(iPad).*/i,
            /(Android).*/i,
            /(iPhone|iPad|iPod|iOS).*/i,
            /(windows(.*|\s*)phone).*/i
        ],
        platform_reg = [
            /MicroMessenger/i,
            /MiuiBrowser/ig,
            /weibo/gi
        ];

    /*** 判断是否是iphone或者ipod ***/
    device.isIPhone_Pod = function () {
        return device_reg[2].test(ua);
    };

    /*** 判断是否是ipad ***/
    device.isIPad = function () {
        return device_reg[3].test(ua);
    };

    /*** 判断是否是miphone ***/
    device.isMiPhone = function () {
        return device_reg[0].test(ua) && !device.isMiPad();
    };

    /*** 判断是否是MiPad ***/
    device.isMiPad = function () {
        return device_reg[1].test(ua);
    };

    /* 判断是否是Android */
    device.isAndroid = function () {
        return device_reg[4].test(ua);
    };

    /* 判断是否是IOS */
    device.isIOS = function () {
        return device_reg[5].test(ua);
    };

    /*** 判断是否是其它安卓设备 ***/
    device.isOtherAndroid = function () {
        return device.isAndroid() && !device.isMiPad() && !device.isMiPhone();
    };

    /*** 判断是否是pc ***/
    device.isPC = function () {
        return !device.isAndroid() && !device.isIOS() && !device.isWinPhone();
    };

    /*** 判断是否是winphone ***/
    device.isWinPhone = function () {
        return device_reg[6].test(ua);
    };

    /* 判断是否是微信平台 */
    platform.isWeixin = function () {
        return platform_reg[0].test(ua);
    };

    /* 判断是否是小米浏览器 */
    platform.isMiBrowser = function () {
        return platform_reg[1].test(ua);
    };

    /* 判断是否是微博 */
    platform.isWeibo = function () {
        return platform_reg[2].test(ua);
    };

})(window.device || (window.device = {}), window.platform || (window.platform = {}));