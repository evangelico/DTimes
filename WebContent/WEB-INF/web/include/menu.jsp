<div class="menu">
	<a href="<s:url action="main/index"/>">
		<div class="wrapper-dropdown" style="width: 100px;">
			<span>Dashboard</span>
		</div>
	</a>
	<div class="wrapper-dropdown multi" style="width: 180px;">
		<span>Gestione iscrizioni</span>
		<ul class="dropdown">
			<li><a href="<s:url action="subscription/index"/>">Lista iscrizioni</a></li>
			<li><a href="<s:url action="subscription/insert"/>">Nuova iscrizione</a></li>
		</ul>
	</div>
	<a href="<s:url action="payment/index"/>">
		<div class="wrapper-dropdown" style="width: 94px;">
			<span>Pagamenti</span>
		</div>
	</a>
	<div class="wrapper-dropdown multi" style="width: 210px;">
		<span>Gestione pacchetti corsi</span>
		<ul class="dropdown">
			<li><a href="<s:url action="plain/index"/>">Lista pacchetti corsi</a></li>
			<li><a href="<s:url action="plain/insert"/>">Nuovo pacchetto corso</a></li>
		</ul>
	</div>
	<div class="wrapper-dropdown multi" style="width: 180px;">
		<span>Cerca</span>
		<ul class="dropdown">
			<li><a href="<s:url action="search/paymentExpired"/>">Iscritti non paganti</a></li>
			<li><a href="<s:url action="search/payments"/>">Pagamenti effettuati</a></li>
			<li><a href="<s:url action="search/plains"/>">Dettaglio corsi</a></li>
			<li><a href="<s:url action="search/deadlinesHide"/>">Scadenza nascoste</a></li>
		</ul>
	</div>
	<a href="<s:url action="main/logout"/>">
		<div class="wrapper-dropdown" style="width: 100px;">
			<span>Esci</span>
		</div>
	</a>
</div>