<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.util.Date,java.text.SimpleDateFormat, BoardClass.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 댓글을 삭제하는 페이지 -->

<%

String id = request.getParameter("id");
int CommentId = Integer.parseInt(id);
List<Comment> list = MemberDAO.findByCommentId(CommentId);
MemberDAO.DeleteComment(CommentId);

response.sendRedirect("PostMain.jsp");
// PostComment.jsp 로 가야하는데 PostID를 모르겠어서 불가능 + comment 테이블에 commentid 가 pk 인 것이 잘못된듯

%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>




</body>
</html>