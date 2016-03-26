<%@page import="fr.tbr.iamcore.datamodel.Identity"%>
<%@page import="java.util.List"%>
<%@page import="fr.tbr.iamcore.dao.IdentityJdbcDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
<%
%>
</head>
<body>

<div align="left">
        <table border="1" cellpadding="5">
            <caption><h2>List of users</h2></caption>
            <tr>
                <th>UID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Profession</th>
            </tr>
            <c:forEach var=identity items="${searchresult}">
                <tr>
                    <td><${identity.setUid()} /></td>
                    <td><${identity.getDisplayName()} /></td>
                    <td><${identity.getEmail()} /></td>                    
                    <td><"${identity.getBirthDate()}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>