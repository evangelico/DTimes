<%@include file="include/top.jsp"%>
<div class="main_container">
	<div class="logo_big" align="center">
		<img src="<%=WWWstatic%>/img/logo_big.jpg" />
	</div>
	<div class="login_title">
		<p>
			Gestione <br /> Iscrizioni e Corsi
		</p>
	</div>
	<div class="login_form" align="center">
		<s:form action="main/index" method="POST" cssStyle="margin:0;">
			<div class="form">
				<p class="intro">Benvenuti</p>
				<s:textfield name="USERNAME" size="25" />
				<p class="label">
					<s:property value="%{getText('login.username')}" />
				</p>
				<s:password name="PASSWORD" size="25" />
				<p class="label">
					<s:property value="%{getText('login.password')}" />
				</p>
				<s:hidden name="LOGIN_ATTEMPT" value="%{'1'}" />
				<s:submit value="%{getText('login.submit')}" />
				<div class="footer">
					<div class="box_error">
						<s:actionerror />
					</div>
				</div>
			</div>
		</s:form>
	</div>
	<div class="footer_container">
		<div class="footer">
			<p>Developed by Evangelisti Fernando Giuseppe</p>
		</div>
	</div>
</div>
</body>
</html>