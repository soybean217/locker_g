
1.首先source 数据库脚本 脚本位于：ROOT\WEB-INF\sql 目录下 wisdom.sql
2. 修改tomcat\ conf 目录下 server.xml 找到配置端口号处，把protocol="HTTP/1.1" 改成 protocol="org.apache.coyote.http11.Http11NioProtocol"
3 创建用户
4 给创建的用户绑定设备
5 考核项目录入新增，目前只支持1000米的项目考核。每次考核结束后，如需再次考核，必须再新增一条考核记录。
6 新增考核项目后，可在实时显示中显示每个人跑步的状态。
7 在考核成绩查询中，可查看最终的成绩。



///-------------------
2016年2月26日10:40:14 gjc
新增sql 语句  
alter table fruit add place varchar(45)
alter table fruit add texture varchar(45)
alter table fruit add imageurl varchar(1024)