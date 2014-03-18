<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<h2>Doctor Dtls</h2>

	<table>
	<tr>
		<td>First Name</td>
		 
	</tr>
	<tr>
		<td>Last Name</td>
	</tr>
	<tr>
		<td>Email</td>
	</tr>
	<tr>
		<td>Telephone</td>
	</tr>
</table>	
data<br>
     ${resultType} 
	 ${result} 
<a href="<c:url value="/template?language=en "/>">EN</a> | <a href="<c:url value="/template?language=de "/>" >DE</a>	
	<br><br>
	Language : <a href="?language=en">English</a>|<a href="?language=de">Dutch</a>
    
<h3>
welcome.springmvc :  <spring:message code="label.firstname"/>
</h3>
 
Current Locale : ${pageContext.response.locale}
 
