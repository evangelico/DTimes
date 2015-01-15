<%@include file="../include/top_invoice.jsp"%>
<div class="header">
	<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="invoice.subscription.id" /></s:param></s:url>">
		<h1>
			Pagamento del
			<s:date name="invoice.payment.paymentDate" format="dd/MM/yyyy" />
		</h1>
	</a>
	<div class="address">
		<p>D.TIME'S DANCE FITNESS ACCADEMY A.S.D.</p>
		<p>Via Falerina, 33 - 01033 Civita Castellana (VT)</p>
		<p>C.F. 90113390562</p>
		<p>Tel: 0761-514926</p>
	</div>
	<span>
		<img alt="" src="<%=WWWstatic%>/img/logo_big.jpg"/>
	</span>
</div>
<div class="article">
	<img alt="" src="<%=WWWstatic%>/img/logo_small.jpg"/>
	<table class="meta">
		<tbody>
			<tr>
				<th><span>Ricevuta n°</span></th>
				<td>
					<span>
						<s:property value="invoice.id" />
					</span>
				</td>
			</tr>
			<tr>
				<th><span>Data emissione</span></th>
				<td>
					<span>
						<s:date name="invoice.creationDate" format="dd/MM/yyyy" />
					</span>
				</td>
			</tr>
			<tr>
				<th><span>Codice fiscale</span></th>
				<td>
					<s:property value="invoice.subscription.fiscalCode" />
				</td>
			</tr>
		</tbody>
	</table>
	<table class="meta">
		<tbody>
			<tr>
				<th><span>Nominativo</span></th>
				<td>
					<span>
						<s:property value="%{invoice.subscription.name +' '+ invoice.subscription.surname}" />
					</span>
				</td>
			</tr>
			<tr>
				<th><span>Data di Nascita</span></th>
				<td>
					<span>
						<s:date name="invoice.subscription.birthdayDate" format="dd/MM/yyyy" />
					</span>
				</td>
			</tr>
			<tr>
				<th><span>Indirizzo</span></th>
				<td>
					<s:property value="invoice.subscription.address" />
				</td>
			</tr>
		</tbody>
	</table>
	<s:set var="paymentId" value="payment.id" />
	<table class="inventory">
		<thead>
			<tr>
				<th><span>Per attivit&agrave; sportiva:</span></th>
				<th><span>Somma*</span></th>
				<th><span>Periodo</span></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="payment.deadlines" var="deadline">
				<tr>
					<td>
						<span>
							<s:property value="packageCourses.name" />
						</span>
					</td>
					<td>
						<span>
							<s:property value="packageCourses.amount" />
						</span>
					</td>
					<td>
						<span>
							<s:property value="getPeriodoPagato(deadlineDate)" />
						</span>
					</td>
				</tr>
			</s:iterator>
			<s:iterator value="payment.packagesCourses" var="plain">
				<s:if test="%{singleLesson}">
					<tr>
						<td>
							<span>
								<s:property value="name" />
							</span>
						</td>
						<td>
							<span>
								<s:property value="description" />
							</span>
						</td>
						<td>
							<span>
								<s:property value="amount" />
							</span>
						</td>
						<td>
							<span> &nbsp; </span>
						</td>
					</tr>
				</s:if>
			</s:iterator>
		</tbody>
	</table>
	<div class="marca_bollo">
		Soggetta ad imposta vigente
	</div>
	<table class="balance">
		<tbody>
			<tr>
				<th><span>Totale</span></th>
				<td>
					<span>
						<s:property value="getAmount(#paymentId)" />
					</span>
				</td>
			</tr>
			<tr>
				<th><span>Sconto</span></th>
				<td>
					<span>
						<s:property value="payment.percentageDiscount" />
					</span>
				</td>
			</tr>
			<tr>
				<th><span>Pagato</span></th>
				<td>
					<span>
						<s:property value="payment.amountPaied" />
					</span>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="firma">
	<h2>
		<span>Firma incaricato</span>
	</h2>
	<div>
		<p>*Somma non soggetta ad IVA ai sensi del quarto comma dell'art 4 D.P.R. 633/72 e succ. modifiche e integrazioni</p>
	</div>
</div>

<!-- 
<div class="note">
	<h1>
		<span>Note</span>
	</h1>
	<div>
		<p>1). Tutti gli iscritti devono saldare la quota associativa entro la prima settimana del mese</p>
	</div>
</div>
 -->
</body>
</html>