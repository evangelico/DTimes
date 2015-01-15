<%@include file="include/top.jsp"%>
<div class="main_container">
	<%@include file="include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Dashboard</div>
			<%@include file="include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box_error big">
				<span>
					<s:actionerror escape="false" />
				</span>
				<span>
					<s:actionmessage escape="false" />
				</span>
				<br>
				<br>
				<span>
					<s:text name="error.internal"  />
				</span>
			</div>
		</div>
	</div>
	<%@include file="include/footer.jsp"%>
</div>
</body>
</html>