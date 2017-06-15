<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container">
    <c:if test="${not empty message}">
        <div class="alert alert-success">
            ${message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-warning">
            ${error}
        </div>
    </c:if>
    <div class="row">
        <div class="col-md-4">
            <form:form method="POST" action="${pageContext.request.contextPath}/permission" modelAttribute="permissionForm" class="form-signin">
                <h2 class="form-signin-heading">Add Permission</h2>
                <spring:bind path="username">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="username" class="form-control" placeholder="Username"
                                    autofocus="true"></form:input>
                        <form:errors path="username"></form:errors>
                    </div>
                </spring:bind>
                <spring:bind path="vHostName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="vHostName" class="form-control" placeholder="vHostName"
                                    autofocus="true"></form:input>
                        <form:errors path="vHostName"></form:errors>
                    </div>
                </spring:bind>
                <spring:bind path="permissions">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox value="read" path="permissions"/>read
                        <form:errors path="permissions"></form:errors>
                    </div>
                </spring:bind>
                <spring:bind path="permissions">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox value="write" path="permissions"/>write
                        <form:errors path="permissions"></form:errors>
                    </div>
                </spring:bind>
                <spring:bind path="permissions">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox value="configure" path="permissions"/>configure
                        <form:errors path="permissions"></form:errors>
                    </div>
                </spring:bind>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            </form:form>
        </div>
    </div>


</div>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>