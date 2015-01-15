<%@include file="include/top.jsp"%>
<div class="main_container">
	<%@include file="include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Dashboard</div>
			<%@include file="include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box_long blue">
				<a href="<s:url action="profile/index"/>"> <span>
						<i class="icon-cogs"></i>
					</span> <span class="name">Impostazioni</span>
				</a>
			</div>
			<div class="box_long green">
				<a href="<s:url action="plain/insert"/>"> <span>
						<i class="icon-shopping-cart"></i>
					</span> <span class="name">Nuovo pacchetto corso</span>
				</a>
			</div>
			<div class="box_short orange">
				<a href="<s:url action="plain/index"/>"> <span>
						<i class="icon-eye-open"></i>
					</span> <span class="name" style="padding-top: 24px; text-align: left;">Lista pacchetti corsi</span>
				</a>
			</div>
			<div class="box_long lime">
				<a href="<s:url action="subscription/insert"/>"> <span>
						<i class="icon-money"></i>
					</span> <span class="name">Nuova iscrizione</span>
				</a>
			</div>
			<div class="box_short blue">
				<a href="<s:url action="subscription/index"/>"> <span>
						<i class="icon-dashboard"></i>
					</span> <span class="name">Lista iscrizioni</span>
				</a>
			</div>
			<div class="box_long brown">
				<a href="<s:url action="payment/index"/>"> <span>
						<i class="icon-bolt"></i>
					</span> <span class="name">Nuovo pagamento</span>
				</a>
			</div>
			<div class="box_short red">
				<a href="<s:url action="main/logout"/>"> <span>
						<i class="icon-star"></i>
					</span> <span class="name">Esci</span>
				</a>
			</div>
		</div>
	</div>
	<%@include file="include/footer.jsp"%>
</div>
</body>
</html>