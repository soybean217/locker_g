<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.io.*"%>
<%@page import="com.highguard.Wisdom.util.FileAndFolderUtil"%>
<%
	//project_key=v32xEAKsGTIEQxtqgwCldp5aPlcnPs3K&imei=868575021881489&device_key=4PvXDLsMAIYNHY0n&firmware_name=LUG_POPBOT_CN_LOCKER_Luat_V0027_8955&version=1.0.0
	Logger logger = Logger.getLogger(this.getClass());
	String dir = application.getRealPath("/") + "/update/";
	FileAndFolderUtil fileAndFolderUtil = new FileAndFolderUtil();
	List<File> files = fileAndFolderUtil.getFilesFromFolder(dir);
	Collections.sort(files);
	if (request.getParameter("firmware_name") != null && request.getParameter("firmware_name").length() > 0
			&& request.getParameter("version") != null && request.getParameter("version").length() > 0) {
		String firmware_name = request.getParameter("firmware_name");
		String version = request.getParameter("version");
		for (int i = files.size() - 1; i >= 0; i--) {
			File f = files.get(i);
			String filename = f.getName();
			if (f.isFile() && filename.startsWith(request.getParameter("firmware_name"))) {
				String fileVersion = f.getName().substring(f.getName().lastIndexOf("_") + 1, f.getName().lastIndexOf("."));
				if (fileVersion.compareTo(version) > 0) {
					OutputStream outputStream = response.getOutputStream();
					byte b[] = new byte[600];
					//10         response.setHeader("Content-disposition", "attachment;filename="+filename);
					//12         response.setContentType("application/msword");
					long fileLength = f.length();
					String length = String.valueOf(fileLength);
					response.setHeader("Content_length", length);
					FileInputStream inputStream = new FileInputStream(f);
					int n = 0;
					while ((n = inputStream.read(b)) != -1) {
						outputStream.write(b, 0, n);
					}
					out.clear();
					out = pageContext.pushBody();
					return;
				}
			}
		}
	}
	out.println("no available update");
%>