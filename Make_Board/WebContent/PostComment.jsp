<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.util.Date,java.text.SimpleDateFormat, BoardClass.*" %>

<%
String pid = request.getParameter("id");
int id = Integer.parseInt(pid);

Post post = MemberDAO.findByPostId(id);

List<Comment> list = MemberDAO.findByAllComment();

%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
      body { font-family: 굴림체; }
      table.table { width: 500px; }
      table td:nth-child(1) { background-color: #eee; }
  </style>
</head>
<body>

<div class="container">

<table class="table table-bordered table-condensed">
    <% for (int i = 0; i < list.size(); ++i) { %>
    	<tr>
    		<td>댓글번호</td>
    		<td>내용</td>
    		<td>작성시간</td>
    	</tr>
    	<tr>
    		<td><%= list.get(i).getCommentId()%></td>
    		<td><%= list.get(i).getContent() %></td>
    		<td><%= list.get(i).getCreateDateTime()%></td>
    	</tr>
    <% }%>
</table>
</div>
</body>
</html>