<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Manage Balance</title>
  <script type="text/javascript">
    var balances = "${balances}"
    console.log(balances)
  </script>
</head>
<body>
<h1>Manage Balance</h1>

<h2>Add UAH</h2>
<form action="${pageContext.request.contextPath}/balance/manage" method="post">
  <input type="hidden" name="action" value="addUah">
  <label for="amount">Amount:</label>
  <input type="text" id="amount" name="amount"><br>
  <button type="submit">Add</button>
</form>

<h2>Decrease UAH</h2>
<form action="${pageContext.request.contextPath}/balance/manage" method="post">
  <input type="hidden" name="action" value="decreaseUah">
  <label for="amount">Amount:</label>
  <input type="text" id="amount" name="amount"><br>
  <button type="submit">Decrease</button>
</form>

<h2>Convert Currency</h2>
<form action="${pageContext.request.contextPath}/balance/manage" method="post">
  <input type="hidden" name="action" value="convert">
  <label for="fromCurrency">From Currency:</label>
  <input type="text" id="fromCurrency" name="fromCurrency"><br>
  <label for="toCurrency">To Currency:</label>
  <input type="text" id="toCurrency" name="toCurrency"><br>
  <label for="amount">Amount:</label>
  <input type="text" id="amount" name="amount"><br>
  <button type="submit">Convert</button>
</form>

<h2>Current Balances</h2>
<table border="1">
  <thead>
  <tr>
    <th>Currency</th>
    <th>Amount</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="balance" items="${balances}">
    <tr>
      <td><c:out value="${balance.currency}" /></td>
      <td><c:out value="${balance.amount}" /></td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>