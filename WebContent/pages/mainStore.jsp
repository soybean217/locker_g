<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>后台系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" href="pages/css/basic.css" />
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="pages/js/jquery.js"></script>
</head>
<style>
.shade {
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	filter: alpha(opacity = 50);
	-moz-opacity: 0.5;
	opacity: 0.5;
	-khtml-opacity: 0.5;
	z-index: 1001;
	display: none;
}

.pmydiv {
	position: absolute;
	width: 300;
	height: 130;
	background-color: #ECFFFF;
	z-index: 1003;
	display: none;
	text-align: center;
	padding: 6px 16px;
	border: 12px solid #C4E1FF;
	overflow: auto;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#p_mydiv').click(function(e) {
			e.stopPropagation();
			$('#p_divshade').removeClass('hide');
			$('#p_mydiv').removeClass('hide');
		});

		$('#p_department').click(function(e) {
			e.stopPropagation();
			$('#p_divshade').removeClass('hide');
			$('#p_department').removeClass('hide');
		});

		$(document).click(function() {
			if (!$('#p_divshade').hasClass('hide')) {
				$('#p_divshade').hide();
				$('#p_mydiv').hide();
				$('#p_department').hide();
			}
		});
	});

	function show(dd) {
		// for(var i=1;i<=8;i++)
		//  {
		var list = document.getElementById('list' + dd);
		// if (list != null) {
		// if(i==Number(dd))
		//{
		if (list.style.display == 'none') {
			list.style.display = 'block';
		} else {
			list.style.display = 'none';
		}
		// }
		// }
		// }
	}
	function detailShowSrc(src) {
		detailShow.location.href = src;
	}
	function useview() {
		window
				.open('pages/help/srahelp.htm', 'newwindow',
						'width=980,height=680,top=100,left=260,scrollbars=yes,resizeable=yes');
	}

	function open1() {
		window
				.open(
						'showresult.jsp',
						'newwindow',
						'width=980,height=680,top=100,left=260,toolbar=no,menubar=no,titlebar=no,location=no,resizeable=no');

	}
	function deleteAssess(src) {
		var href = src + $("#p_mydiv_id").val();
		detailShowSrc(href);
		cancel();
	}
	function deleteDepartment(src) {
		var href = src + $("#p_department_id").val();
		detailShowSrc(href);
		cancel();
	}
	function cancel() {
		$('#p_divshade').hide();
		$('#p_mydiv').hide();
		$('#p_department').hide();
	}
</script>
<body bgcolor="#C7E3F3">
	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		height="5%">
		<tr background="">
			<td height="5%" background="pages/images/systemLogo2.JPG"><img
				src="pages/images/systemLogoNew.gif" height="60" /></td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		height="90%">
		<tr>
			<td width="15%"
				style="background-color: #C7E3F3; vertical-align: top;">
				<table border="0" cellpadding="0" cellspacing="0" width="98%"
					id="tab">
					<tr>
						<td><img src="pages/images/base.gif" width="100%"
							height="45px" /></td>
					</tr>
					<!-- 基本信息配置 -->
					<tr>
						<td>
							<table border="0" cellpadding="0" cellspacing="0" width="100%"
								height="100%">
								<tr>
									<td width="2px"></td>
									<td
										style="background-image: url(pages/images/bg.jpg); line-height: 24px; color: #fff; font-weight: bold; cursor: pointer;"
										onclick="show(32);"><img src="pages/images/folder.gif"
										width="20" height="20"
										style="float: left; margin-left: 2px; margin-right: 5px;" />功能菜单</td>
								</tr>
								<tr>
									<td colspan="2">
										<table border="0" cellpadding="0" cellspacing="0" width="100%"
											height="100%">
											<tr>
												<td width="20%"></td>
												<td width="80%">
													<div id="list32">
														<a href="javascript:detailShowSrc('orderList.action')">订单查询</a>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>




					<tr>
						<td
							style="background-image: url(pages/images/bg.jpg); line-height: 24px; color: #0753A7; font-weight: bold;">
							<table border="0" cellpadding="0" cellspacing="0" width="100%"
								height="100%">
								<tr>
									<td width="2px"><img src="pages/images/Logoff.gif"
										width="15" height="15"
										style="float: left; margin-left: 7px; margin-right: 10px;"></td>
									<td><a href="logout.action"
										style="text-decoration: none; font-weight: bold;"><s:text
												name="admin.cancellation"></s:text></a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td width="85%" height="100%" style="background-color: #73ACB5;">
				<iframe src="pages/welcome.jsp" name="detailShow" id="detailShow"
					align="MIDDLE" width="100%" height="100%" marginwidth="1"
					marginheight="1" frameborder="1" scrolling="yes" frameborder="0">
					<s:text name="home.internetexplorer.error"></s:text>
				</iframe>
			</td>
		</tr>
	</table>
	<span class="STYLE3"></span>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		height="5%">
		<tr>
			<td align="center"></td>
		</tr>
	</table>
	<div id="p_mydiv" class="pmydiv">
		<table align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input type="hidden" id="p_mydiv_id"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td><input type="button" class="FixButtonStyle" value="确定"
					onclick="Javascript:deleteAssess('deleteAssess.action?id=')" /></td>
				<td><input type="button" class="FixButtonStyle" value="取消"
					onclick="Javascript:cancel()" /></td>
			</tr>
		</table>
	</div>
	<div id="p_department" class="pmydiv">
		<table align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input type="hidden" id="p_department_id"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td><input type="button" class="FixButtonStyle" value="确定"
					onclick="Javascript:deleteDepartment('deleteDepartment.action?id=')" /></td>
				<td><input type="button" class="FixButtonStyle" value="取消"
					onclick="Javascript:cancel()" /></td>
			</tr>
		</table>
	</div>
	<div id="p_divshade" class="shade"></div>
</body>
</html>