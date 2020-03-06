<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.util.Date,java.text.SimpleDateFormat, BoardClass.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- PostView.jsp 페이지에서 댓글 내용을 입력하고 "등록" 버튼을 누르면 현재 페이지로 이동해서  댓글을 comment 테이블에 insert 한 후에

댓글을 전부 보여주는 페이지인 PostComment.jsp 로 이동 
 -->

<%

String title = request.getParameter("title");
String pid = request.getParameter("PID");

int ID = Integer.parseInt(pid);

MemberDAO.CommentInsert(title, ID);
response.sendRedirect("PostComment.jsp?id=" + ID);


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>