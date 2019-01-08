<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page contentType = "text/html;charset=utf-8"
         isELIgnored ="false"
         pageEncoding ="utf-8"%>

<fmt:setLocale value="${not empty sessionScope.lang?sessionScope.lang:not empty param.lang?param.lang:not empty requestScope.lang?requestScope.lang:'eng'}"/>
<fmt:bundle basename="pagecontent" prefix ="header.">

<html>
<head>
    <style><jsp:include page = "/css/header-fixed.css"/></style>
</head>
<body>
    <header class="header">
        <nav class="navbar">
            <div class="logo">
                <a href="${pageContext.servletContext.contextPath}/controller?command=home">AudioSpot</a>
            </div>
            <c:if test="${sessionScope.user.role.value eq 'admin'}">
                <a href="${pageContext.servletContext.contextPath}/controller?command=addTrack"><fmt:message key="addTrack"/></a>
                <a href="${pageContext.servletContext.contextPath}/controller?command=showAlbums"><fmt:message key="albums"/></a>
                <a href="${pageContext.servletContext.contextPath}/controller?command=showTracks"><fmt:message key="tracks"/></a>
                <%--<li class="menu_item"><a href="${pageContext.servletContext.contextPath}/controller?command=showPlaylists"><fmt:message key="audioSets"/></a></li>--%>
                <a href="${pageContext.servletContext.contextPath}/controller?command=showClients"><fmt:message key="clients"/></a>
            </c:if>

            <c:if test="${sessionScope.user.role.value eq 'client'}">
                <a href="${pageContext.servletContext.contextPath}/controller?command=showPlaylist"><fmt:message key="myPlaylist"/></a>
                <a href="${pageContext.servletContext.contextPath}/controller?command=showAlbums"><fmt:message key="albums"/></a>
                <a href="${pageContext.servletContext.contextPath}/controller?command=showTracks"><fmt:message key="tracks"/></a>
                <a href="${pageContext.servletContext.contextPath}/controller?command=payOrder"><fmt:message key="PayOrder"/></a>
            </c:if>

            <c:if test="${sessionScope.user.role ne null}">
                <a href="#" class = "selected">${sessionScope.user.login}</a>
            </c:if>
            <div>
                <a href="<c:url value="">
                    <c:forEach items="${param}" var="entry">
                        <c:if test="${entry.key != 'lang'}">
                            <c:param name="${entry.key}" value="${entry.value}" />
                        </c:if>
                    </c:forEach>
                    <c:param name="lang" value="en" />
                </c:url>"><fmt:message key="en"/></a>

                <a href="<c:url value="">
                    <c:forEach items="${param}" var="entry">
                        <c:if test="${entry.key != 'lang'}">
                            <c:param name="${entry.key}" value="${entry.value}" />
                        </c:if>
                    </c:forEach>
                    <c:param name="lang" value="ru" />
                </c:url>"><fmt:message key="ru"/></a>
            </div>
            <c:if test="${sessionScope.user.role ne null}">
                <a href="${pageContext.servletContext.contextPath}/controller?command=logout&lang=${sessionScope.lang}"><fmt:message key="logOut"/></a>
            </c:if>
        </nav>
    </header>
</body>
</html>
</fmt:bundle>