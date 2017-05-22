<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <!-- Access the bootstrap Css like this,
                Spring boot will handle the resource mapping automcatically -->
        <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

        <!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
        <c:url value="/css/main.css" var="jstlCss" />
        <link href="${jstlCss}" rel="stylesheet" />
    </head>
    <body>
    <nav class="navbar navbar-inverse">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Wetterdaten</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Generate</a></li>
                    <li><a href="#about">Live</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">

        <div class="starter-template">
            <h1>Wetterdaten-Generator</h1>
            <form:form method="post" modelAttribute="weatherdata" action="/">
                <spring:bind path="cityName">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Cityname</label>
                        <div class="col-sm-10">
                            <form:input path="cityName" type="text" class="form-control" id="cityName" placeholder="Konstanz"/>
                            <form:errors path="cityName" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="plz">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Postal Code</label>
                        <div class="col-sm-10">
                            <form:input path="plz" type="text" class="form-control" id="cityName" placeholder="78467"/>
                            <form:errors path="plz" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="longitude">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Longitude</label>
                        <div class="col-sm-10">
                            <form:input path="longitude" type="text" class="form-control" id="longitude" placeholder="longitude"/>
                            <form:errors path="longitude" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="latitude">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Latitude</label>
                        <div class="col-sm-10">
                            <form:input path="latitude" type="text" class="form-control" id="latitude" placeholder="latitude"/>
                            <form:errors path="latitude" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="weatherIcon">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">WeatherIcon</label>
                        <div class="col-sm-10">
                            <form:input path="weatherIcon" type="text" class="form-control" id="weatherIcon" placeholder="Icon"/>
                            <form:errors path="weatherIcon" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="currentWeather">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Current Weather</label>
                        <div class="col-sm-10">
                            <form:input path="currentWeather" type="text" class="form-control" id="currentWeather" placeholder="15 Grad und Sonnenschein"/>
                            <form:errors path="currentWeather" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="currentWeatherId">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">currentWeatherId</label>
                        <div class="col-sm-10">
                            <form:input path="currentWeatherId" type="text" class="form-control" id="cityName" placeholder="i dont know"/>
                            <form:errors path="currentWeatherId" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="pressure">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Pressure</label>
                        <div class="col-sm-10">
                            <form:input path="pressure" type="text" class="form-control" id="pressure" placeholder="990"/>
                            <form:errors path="pressure" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="humitidy">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Humitidy</label>
                        <div class="col-sm-10">
                            <form:input path="humitidy" type="text" class="form-control" id="humitidy" placeholder="60"/>
                            <form:errors path="humitidy" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="windspeed">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Wind speed</label>
                        <div class="col-sm-10">
                            <form:input path="windspeed" type="text" class="form-control" id="windspeed" placeholder="10"/>
                            <form:errors path="windspeed" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="windDeg">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Wind direction</label>
                        <div class="col-sm-10">
                            <form:input path="windDeg" type="text" class="form-control" id="windDeg" placeholder="270"/>
                            <form:errors path="windDeg" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="temperature">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Actual Temperature</label>
                        <div class="col-sm-10">
                            <form:input path="temperature" type="text" class="form-control" id="temperature" placeholder="25.0"/>
                            <form:errors path="temperature" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="temperatureMax">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Max. Temperature</label>
                        <div class="col-sm-10">
                            <form:input path="temperatureMax" type="text" class="form-control" id="temperatureMax" placeholder="35.0"/>
                            <form:errors path="temperatureMax" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="temperatureMin">
                    <div class="form-group ${status.error ? 'has error' : ''}">
                        <label class="col-sm-2 control-label">Min. Temperature</label>
                        <div class="col-sm-10">
                            <form:input path="temperatureMin" type="text" class="form-control" id="temperatureMin" placeholder="0.0"/>
                            <form:errors path="temperatureMin" class="control-label"/>
                        </div>
                    </div>
                </spring:bind>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                    </div>
                </div>


            </form:form>
        </div>
        <form action="<c:url value="/API-Data"/>"method="get">
            <div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" class="btn-lg btn-primary pull-right" value="Send API-Data"/>
                    </div>
                </div>
            </div>
        </form>


    </div>

    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>