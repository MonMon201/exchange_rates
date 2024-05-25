<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exchange Rates</title>
    <script type="text/javascript">
        function filterCurrency() {
            var selectedCurrency = document.getElementById("currencySelect").value;
            var startDate = document.getElementById("startDate").value;
            var endDate = document.getElementById("endDate").value;
            var rows = document.getElementById("ratesTable").getElementsByTagName("tr");

            for (var i = 1; i < rows.length; i++) {
                var cells = rows[i].getElementsByTagName("td");
                var currency = cells[1].textContent;
                var date = cells[2].textContent;

                if ((currency === selectedCurrency || selectedCurrency === "All") &&
                    (!startDate || date >= startDate) &&
                    (!endDate || date <= endDate)) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }
            }
        }

        window.onload = function() {
            var currencies = new Set();
            var rows = document.getElementById("ratesTable").getElementsByTagName("tr");

            for (var i = 1; i < rows.length; i++) { // Start at 1 to skip the header row
                var cells = rows[i].getElementsByTagName("td");
                var currency = cells[1].textContent;
                currencies.add(currency);
            }

            var select = document.getElementById("currencySelect");
            currencies.forEach(function(currency) {
                var option = document.createElement("option");
                option.value = currency;
                option.textContent = currency;
                select.appendChild(option);
            });

            // Set default date
            document.getElementById("startDate").value = "2024-05-19";
            document.getElementById("endDate").value = "2024-05-19";

            // Filter the table with the default date
            filterCurrency();
        }
    </script>
</head>
<body>
<h1>Exchange Rates</h1>

<c:choose>
    <c:when test="${param.role == 'guest'}">
        <label for="currencySelect">Select Currency:</label>
        <select id="currencySelect" onchange="filterCurrency()">
            <option value="All">All</option>
        </select>

        <br>

        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" onchange="filterCurrency()">
        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate" onchange="filterCurrency()">
    </c:when>
    <c:otherwise>
        <h2>Manage Exchange Rates</h2>

        <!-- Search Form -->
        <form action="${pageContext.request.contextPath}/admin/exchangeRates" method="get">
            <label for="currencySearch">Search by Currency:</label>
            <input type="text" id="currencySearch" name="currency" value="${param.currency}">
            <button type="submit">Search</button>
        </form>
    </c:otherwise>
</c:choose>

<table id="ratesTable" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Currency</th>
        <th>Date</th>
        <th>Buying Rate (UAH)</th>
        <th>Selling Rate (UAH)</th>
        <c:if test="${param.role != 'guest'}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="rate" items="${exchangeRates}">
        <tr>
            <td><c:out value="${rate.id}" /></td>
            <td><c:out value="${rate.currency}" /></td>
            <td><c:out value="${rate.date}" /></td>
            <td><c:out value="${rate.buyingRate}" /></td>
            <td><c:out value="${rate.sellingRate}" /></td>
            <c:if test="${param.role != 'guest'}">
                <td>
                    <form action="${pageContext.request.contextPath}/admin/exchangeRates" method="post">
                        <input type="hidden" name="_method" value="put">
                        <input type="hidden" name="id" value="${rate.id}">
                        <input type="text" name="currency" value="${rate.currency}">
                        <input type="text" name="date" value="${rate.date}">
                        <input type="text" name="buyingRate" value="${rate.buyingRate}">
                        <input type="text" name="sellingRate" value="${rate.sellingRate}">
                        <button type="submit">Update</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/admin/exchangeRates" method="post">
                        <input type="hidden" name="_method" value="delete">
                        <input type="hidden" name="id" value="${rate.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${param.role == 'guest'}">
    <button onclick="location.href='${pageContext.request.contextPath}/admin/exchangeRates?role=admin'">Go to Admin View</button>
</c:if>

<c:if test="${param.role != 'guest'}">
    <h2>Add New Exchange Rate</h2>
    <form action="${pageContext.request.contextPath}/admin/exchangeRates" method="post">
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
    <button onclick="location.href='${pageContext.request.contextPath}/guest/exchangeRates?role=guest'">Go to Guest View</button>
</c:if>

</body>
</html>
