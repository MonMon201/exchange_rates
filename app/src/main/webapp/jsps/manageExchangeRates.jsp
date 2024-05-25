<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Exchange Rates</title>
</head>
<body>
<h1>Manage Exchange Rates</h1>
<table border="1">
    <thead>
    <tr>
        <th>Currency</th>
        <th>Date</th>
        <th>Buying Rate</th>
        <th>Selling Rate</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="rate" items="${exchangeRates}">
        <tr>
            <td><c:out value="${rate.id.currency}" /></td>
            <td><c:out value="${rate.id.date}" /></td>
            <td><c:out value="${rate.buyingRate}" /></td>
            <td><c:out value="${rate.sellingRate}" /></td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/manage-new-rates" method="post">
                    <input type="hidden" name="_method" value="put">
                    <input type="hidden" name="currency" value="${rate.id.currency}">
                    <input type="hidden" name="date" value="${rate.id.date}">
                    <input type="text" name="buyingRate" value="${rate.buyingRate}">
                    <input type="text" name="sellingRate" value="${rate.sellingRate}">
                    <button type="submit">Update</button>
                </form>
                <form action="${pageContext.request.contextPath}/admin/manage-new-rates" method="post">
                    <input type="hidden" name="_method" value="delete">
                    <input type="hidden" name="currency" value="${rate.id.currency}">
                    <input type="hidden" name="date" value="${rate.id.date}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Add New Exchange Rate</h2>
<form action="${pageContext.request.contextPath}/admin/manage-new-rates" method="post">
    <label for="currency">Currency:</label>
    <input type="text" id="currency" name="currency"><br>
    <label for="date">Date:</label>
    <input type="text" id="date" name="date"><br>
    <label for="buyingRate">Buying Rate:</label>
    <input type="text" id="buyingRate" name="buyingRate"><br>
    <label for="sellingRate">Selling Rate:</label>
    <input type="text" id="sellingRate" name="sellingRate"><br>
    <button type="submit">Add</button>
</form>
</body>
</html>