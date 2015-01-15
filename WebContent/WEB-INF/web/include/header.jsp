<div class="header">
	<div class="logo_small">
		<img alt="" src="<%=WWWstatic%>/img/logo_small.jpg" />
	</div>
	<%@include file="menu.jsp"%>
	<a href="<s:url action="profile/index"/>" style="text-decoration: none; color: #ffffff;">
		<div class="user">
			<p>
				<s:property value="%{userDTO.name+ ' ' + userDTO.surname}" />
			</p>
			<img src="<%=WWWstatic%>/img/user.png" alt="" />
		</div>
	</a>
</div>