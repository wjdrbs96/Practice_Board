<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.util.Date,java.text.SimpleDateFormat, BoardClass.*" %>
<%
if (request.getMethod().equals("POST")) {
	Post post = new Post();
	request.setCharacterEncoding("UTF-8");
	
	String pid = request.getParameter("id");
	int id = Integer.parseInt(pid);
	post.setPostId(id);
	
	String title = request.getParameter("title");
	post.setTitle(title);
	
	String content = request.getParameter("content");
	post.setContent(content);
	
	String name = request.getParameter("name");
	post.setName(name);
	
	
	//String date = request.getParameter("time");
	//SimpleDateFormat transformat = new SimpleDateFormat("HH:mm:ss");
	//java.util.Date time = transformat.parse(date);
	//post.setCreateDateTime(time);
	
	MemberDAO.Update(post);
	
	response.sendRedirect("PostMain.jsp");
    return;
		
}



String pid = request.getParameter("id");
int id = Integer.parseInt(pid);

Post post = MemberDAO.findByPostId(id);

MemberDAO.Countup(post.getPostId(), post.getCount());


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

<h1>게시글 수정</h1>

<form method="post">
  <table class="table table-bordered table-condensed">
    <tr>
      <td>게시글 번호</td>
      <td>
          <%= post.getPostId() %>
 
      </td>
    </tr>
    <tr>
      <td>글 제목</td>
      <td>
          <textarea class="form-control" name="title" rows="1"><%= post.getTitle() %></textarea>
      </td>
    </tr>
    <tr>
      <td>글 내용</td>
      <td>
          <textarea class="form-control" name="content" rows="10"><%= post.getContent() %></textarea>
      </td>
    </tr>    
    
    <tr>
      <td>작성자</td>
      <td>
          <input type="text" name="name" class="form-control"  value=<%= post.getName() %>>
      </td>
    </tr>
     <tr>
      <td>작성시간</td>
      <td>
          <input type="text" name="time" class="form-control" value=<%= new SimpleDateFormat("HH:mm:ss").format(new Date()) %>>
      </td>
    </tr>
  </table>
  
  <button type="submit" class="btn btn-primary">저장</button>
  <a href="PostComment.jsp?id=<%= post.getPostId() %>" class="btn btn-warning">댓글</a>
  <a href="PostDelete.jsp?id=<%= post.getPostId() %>" class="btn btn-warning" onclick="confirm('삭제하시겠습니까?')">삭제</a>
  <a href="javascript:window.history.back()" class="btn btn-info">돌아가기</a>
</form>


<h1>댓글 등록</h1>
<form action="InsertComment.jsp" method="get">
  <table class="table table-bordered table-condensed">
    <tr>
      <td class="mid" width="100">댓글</td>
      <td width="700">
          <textarea class="form-control" name="title" rows="2"></textarea>
      </td>
       <td><input type="text" name="PID" value="<%= post.getPostId() %>"></td>
    </tr>
  </table>
  
  <button type="submit" class="btn btn-primary">등록</button>
  
</form>

</div>
</body>
</html>