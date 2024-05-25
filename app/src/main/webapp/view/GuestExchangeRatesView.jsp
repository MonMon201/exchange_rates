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
                var currency = cells[0].textContent;
                var date = cells[1].textContent;

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
                var currency = cells[0].textContent;
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
<h1>Current Exchange Rates</h1>

<label for="currencySelect">Select Currency:</label>
<select id="currencySelect" onchange="filterCurrency()">
    <option value="All">All</option>
</select>

<br>

<label for="startDate">Start Date:</label>
<input type="date" id="startDate" name="startDate" onchange="filterCurrency()">
<label for="endDate">End Date:</label>
<input type="date" id="endDate" name="endDate" onchange="filterCurrency()">

<table id="ratesTable" border="1">
    <thead>
    <tr>
        <th>Currency</th>
        <th>Date</th>
        <th>Buying Rate (UAH)</th>
        <th>Selling Rate (UAH)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="rate" items="${exchangeRates}">
        <tr>
            <td><c:out value="${rate.currency}" /></td>
            <td><c:out value="${rate.date}" /></td>
            <td><c:out value="${rate.buyingRate}" /></td>
            <td><c:out value="${rate.sellingRate}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Button to navigate to admin view -->
<button onclick="location.href='${pageContext.request.contextPath}/admin/exchangeRates'">Go to Admin View</button>

</body>
</html>
