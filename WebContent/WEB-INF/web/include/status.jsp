<div class="status">
	<div class="box orange">
		<i class="icon-calendar"></i>
		<div class="details">
			<span class="big" style="text-transform: capitalize;"><s:date name="now" format="MMMM dd, yyyy"/></span>
			<span style="text-transform: capitalize;"><s:date name="now" format="EEEE, HH:mm"/></span>
		</div>
	</div>
	<a href="<s:url action="search/paymentExpired"/>">
		<div class="box red">
			<i class="icon-money"></i>
			<div class="details">
				<span class="big">
					<s:property value="deadlinesExpired" />
				</span>
				<span>Iscritti non paganti</span>
			</div>
		</div>
	</a>
	<a href="<s:url action="search/certificatesExpired"/>">
		<div class="box red">
			<i class="icon-money"></i>
			<div class="details">
				<span class="big">
					<s:property value="certificatesExpired" />
				</span>
				<span>Certificati medici scaduti</span>
			</div>
		</div>
	</a>
</div>