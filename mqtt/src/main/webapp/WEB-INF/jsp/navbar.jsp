<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Wetterdaten</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${pageContext.request.contextPath}/">Weatherdata</a></li>
                <li><a href="${pageContext.request.contextPath}/registration">Create User</a></li>
                <li><a href="${pageContext.request.contextPath}/permission">Create Permission</a></li>
                <li>
                    <security:authorize access="isAuthenticated()">
                    authenticated as <security:authentication property="principal.username" />
                    </security:authorize>
                </li>
                <!--li><a href="/user">Live</a></li-->
            </ul>
        </div>
    </div>
</nav>
