<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
   var msg="${error}";
   var url="<c:out value = '${url}'/>";
   alert(msg);
   location.href = "/phonebook/login";
</script>
<script>
   var msg="${error}";
   var url="<c:out value = '${url}'/>";
   alert(msg);
   location.href = "redirect:/phonebook/login";
</script>
<title>Insert title here</title>
</head>
<body>

</body>
</html>