<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Iscrizioni</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<i class="icon-inbox"></i> Iscritti
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<s:form action="subscription/index" method="POST" cssStyle="margin: 0px;">
							<div class="dataTables_filter">
								<label><span>Nominativo:</span> <s:textfield name="nameFilter" /></label>
								<label><span>Corso:</span> <s:select name="plainFilter" list="plains" headerKey="" headerValue="Seleziona un pacchetto di corsi" listKey="id" listValue="name" cssStyle="border: 1px solid #ccc;" /></label>
								<label>
									<button type="submit" class="btn btn-primary">Filtra</button>
								</label>
							</div>
						</s:form>
						<div class="box_error">
							<s:actionerror />
						</div>
						<table class="table table-nomargin dataTable dataTable-tools  table-bordered">
							<thead>
								<tr role="row">
									<th class="sorting" rowspan="1" colspan="1">Stato</th>
									<th class="sorting" rowspan="1" colspan="1">Data Iscrizione</th>
									<th class="sorting" rowspan="1" colspan="1">Nome</th>
									<th class="sorting" rowspan="1" colspan="1">Cognome</th>
									<th class="sorting" rowspan="1" colspan="1">Codice fiscale</th>
									<th class="sorting" rowspan="1" colspan="1">Data di nascita</th>
									<th class="sorting" rowspan="1" colspan="1">Indirizzo</th>
									<th class="sorting" rowspan="1" colspan="1">E-mail</th>
									<th class="sorting" rowspan="1" colspan="1">Numero di telefono</th>
									<th class="sorting" rowspan="1" colspan="1">Numero di cellulare</th>
									<th class="sorting" rowspan="1" colspan="1">Scadenza certificato</th>
									<th class="sorting" rowspan="1" colspan="1"></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="subscriptions.content" status="subscriptionStatus" var="subscription">
									<tr class="odd">
										<td class=" ">
											<s:if test="active">
												Attivo
											</s:if>
											<s:else>
												Disattivo
											</s:else>
										</td>
										<td class=" ">
											<s:date name="registrationDate" format="dd/MM/yyyy" />
										</td>
										<td class=" ">
											<s:property value="name" />
										</td>
										<td class=" ">
											<s:property value="surname" />
										</td>
										<td class=" ">
											<s:property value="fiscalCode" />
										</td>
										<td class=" ">
											<s:date name="birthdayDate" format="dd/MM/yyyy" />
										</td>
										<td class=" ">
											<s:property value="address" />
										</td>
										<td class=" ">
											<s:property value="email" />
										</td>
										<td class=" ">
											<s:property value="phoneNumber" />
										</td>
										<td class=" ">
											<s:property value="cellNumber" />
										</td>
										<td class=" ">
										<s:date name="medicalCertificateDate" format="dd/MM/yyyy" />
										</td>
										<td class=" ">
											<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-info" style="width: 78px; height: 30px; margin-bottom: 10px;">Dettaglio</div>
											</a> <a href="<s:url action="subscription/edit"><s:param name="subscriptionIDToEdit"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-warning" style="width: 78px; height: 30px; margin-bottom: 10px;">Modifica</div>
											</a>
											<s:if test="active">
												<a href="<s:url action="subscription/disable"><s:param name="subscriptionIDToDisable" ><s:property value="id" /></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>">
													<div class="btn btn-warning" style="width: 78px; height: 30px; margin-bottom: 10px;">Disattiva</div>
												</a>
											</s:if>
											<s:else>
												<a href="<s:url action="subscription/enable"><s:param name="subscriptionIDToEnable" ><s:property value="id" /></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>">
													<div class="btn btn-info" style="width: 78px; height: 30px; margin-bottom: 10px;">Attiva</div>
												</a>
											</s:else>
											<a href="<s:url action="subscription/remove"><s:param name="subscriptionIDToDelete" ><s:property value="id" /></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>">
												<div class="btn btn-danger" style="width: 78px; height: 30px;">Elimina</div>
											</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<div class="dataTables_info">
							Pagina
							<span>
								<s:property value="page+1" />
							</span>
							di
							<span>
								<s:property value="subscriptions.totalPages" />
							</span>
						</div>
						<div class="dataTables_paginate paging_full_numbers">
							<s:if test="!subscriptions.firstPage">
								<a href="<s:url action="subscription/index"><s:param name="page" value="0"/><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:else>
							<s:if test="subscriptions.hasPreviousPage()">
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="page-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="subscription/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>"> <s:property value="page+1" />
								</a>
							</span>
							<s:if test="subscriptions.hasNextPage()">
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="page+1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!subscriptions.lastPage">
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="subscriptions.totalPages-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:else>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</div>
</body>
</html>