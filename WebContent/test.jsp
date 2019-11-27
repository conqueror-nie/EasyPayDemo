<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	</head>
	<body>
		<form id = "pay_form" action="http://localhost:8080/api_gateway.do" method="post">
			<input type='hidden' name='sign' id='sign' value='cWRpWgGF+9c5XOM14czJMJIFql2EX6ZC6U418gRfm4qNn0zuf/MgDnXbhSDRFelNa6ois5ddE1Y3f9QU7+shAjc1t6pW636d0uH0byezjKIUrFeTgPpMNddDjDsWO1COWPlKaqC4a3jPenCtcSAKOBU0Lu1XEZS3MONnKKvD00o='/>
			<input type='hidden' name='charset' id='charset' value='UTF-8'/>
			<input type='hidden' name='biz_content' id='biz_content' value='{"merchant_id":"900029000000354","out_trade_no":"201910171564553000835","acc":"6228480018553605470","notify_url":"https://www.baidu.com","front_url":"https://www.baidu.com"}'/>
			<input type='hidden' name='partner' id='partner' value='900029000000354'/>
			<input type='hidden' name='sign_type' id='sign_type' value='RSA'/>
			<input type='hidden' name='service' id='service' value='easypay.pay.nopages.openFront'/>
		</form>
	</body>
	<script type="text/javascript">document.all.pay_form.submit();</script>
</html>