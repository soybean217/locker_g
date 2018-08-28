<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>Apache Tomcat WebSocket Examples: Echo</title>
    <style type="text/css">
        * {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        #connect-container {
            width: 100%;
        }

        #connect-container div {
            padding: 5px;
        }

        #console-container {
            background-color: black;
            color: #fff;
        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 370px;
            overflow-y: scroll;
            padding: 5px;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
    </style>
    <script type="application/javascript">
        var ws = null;
        var responsed = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('echo').disabled = !connected;
            document.getElementById('borrow').disabled = !connected;
            document.getElementById('giveback').disabled = !connected;
            document.getElementById('queryOrder').disabled = !connected;
            document.getElementById('status').disabled = !connected;
        }

        function connect() {
            var target = document.getElementById('target').value;
            if (target == '') {
                alert('Please select server side connection implementation.');
                return;
            }
            if ('WebSocket' in window) {
                ws = new WebSocket(target);
            } else if ('MozWebSocket' in window) {
                ws = new MozWebSocket(target);
            } else {
                alert('WebSocket is not supported by this browser.');
                return;
            }
            ws.onopen = function () {
                setConnected(true);
                log('Info: WebSocket connection opened.');
            };
            ws.onmessage = function (event) {
                log(event.data);
                responsed = event.data;
            };
            ws.onclose = function (event) {
                setConnected(false);
                log('Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
            };
        }

        function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
            setConnected(false);
        }

        function echo() {
            if (ws != null) {
                var message = document.getElementById('message').value;
//                log('Sent: ' + message);
                ws.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }

        function borrow(){
            if (ws != null) {
                var latticeid = document.getElementById('latticeid').value;
                var data = {
                    lattice: {lockid:latticeid},
                    action: 'borrow'
                };

                var send = { type: 'scan', data: data };

                var message = JSON.stringify(send);
//                log(message);
                ws.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }
        function giveback() {
            if (ws != null) {
                var latticeid = document.getElementById('latticeid').value;
                var data = {
                    lattice: {lockid:latticeid},
                    action: 'giveback'
                };

                var send = {type: 'scan', data: data};

                var message = JSON.stringify(send);
//                log( message);
                ws.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }

        function status() {
            if (ws != null) {
                var data = {
                };

                var send = { type: 'status', data: data };

                var message = JSON.stringify(send);
//                log(message);
                ws.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }

        function queryOrder() {
            if (ws != null) {
                var data = {
                };

                var send = { type: 'queryOrder', data: data };

                var message = JSON.stringify(send);
//                log( message);
                ws.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }

        function updateTarget(target) {
            if (window.location.protocol == 'http:') {
                document.getElementById('target').value = 'ws://' + window.location.host + target;
            } else {
                document.getElementById('target').value = 'wss://' + window.location.host + target;
            }
        }

        function log(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        }

        function clearConsole(){
            var console = document.getElementById('console');
            console.innerHTML = '';
            console.innerText = '';
        }

        document.addEventListener("DOMContentLoaded", function() {
            // Remove elements with "noscript" class - <noscript> is not allowed in XHTML
            var noscripts = document.getElementsByClassName("noscript");
            for (var i = 0; i < noscripts.length; i++) {
                noscripts[i].parentNode.removeChild(noscripts[i]);
            }
        }, false);
    </script>
</head>
<body>
<div style="padding: 10px;">
    <div id="connect-container">
        <div>
            <span>Connect to service implemented using:</span>
            <!-- echo example using new programmatic API on the server side -->
            <input id="radio1" type="radio" name="group1" value="/wxapp/websocket"
                   onclick="updateTarget(this.value);"/> <label for="radio1">programmatic API</label>
        </div>
        <div>
            <input id="target" type="text" size="40" style="width: 350px"/>
        </div>
        <div>
            <button id="connect" onclick="connect();">Connect</button>
            <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
        </div>
        <div>
            <textarea id="message" style="width: 350px">Here is a message!</textarea>
        </div>
        <div>
            <button id="echo" onclick="echo();" disabled="disabled">Echo message</button>
            <button id="borrow" onclick="borrow();" disabled="disabled">扫码借杆</button>
            <button id="giveback" onclick="giveback();" disabled="disabled">扫码还杆</button>
            <button id="queryOrder" onclick="queryOrder();" disabled="disabled">查询订单</button>
            <button id="status" onclick="status();" disabled="disabled">检测状态</button>
            <button id="clear" onclick="clearConsole()">清空</button>
        </div>
        <div>
            <s:select list="lattices" name="latticeid" id="latticeid" listValue="lockid" listKey="lockid"></s:select>
        </div>
    </div>
    <div id="console-container">
        <div id="console"/>
    </div>
</div>
</body>
</html>