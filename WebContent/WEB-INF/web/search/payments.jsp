<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Ricerche</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<i class="icon-th-list"></i>Filtro
					</h3>
				</div>
				<div class="box-content nopadding">
					<s:form action="search/payments" method="POST">
						<div class="control-group">
							<label for="textfield" class="control-label">Periodo :</label>
							<div class="controls">
								<span id="sprytextfield3">
									<s:select list="paymentsPeriod" cssClass="input-medium" name="paymentPeriod" headerKey="" headerValue="Selezina periodo" />
								</span>
							</div>
							<label for="textfield" class="control-label">Corso :</label>
							<div class="controls">
								<span id="sprytextfield3">
									<s:select list="plains" cssClass="input-medium" name="plainId" headerKey="" headerValue="Selezina un pacchetto di corsi" listKey="id" listValue="name" />
								</span>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">Cerca</button>
						</div>
					</s:form>
				</div>
			</div>
			<div class="clear"></div>
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<i class="icon-inbox"></i>Pagamenti effettuati
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<div class="dataTables_filter">
							<s:form action="search/payments" method="POST" cssStyle="margin: 0px;">
								<div class="dataTables_filter">
									<label><span>Nominativo:</span> <s:textfield name="nameFilter" /></label>
									<label>
										<button type="submit" class="btn btn-primary">Filtra</button>
										<s:hidden name="paymentPeriod"/>
										<s:hidden name="plainId"/>
									</label>
								</div>
							</s:form>
						</div>
						<div class="box_error">
							<s:actionerror />
						</div>
						<table class="table table-nomargin dataTable dataTable-tools  table-bordered">
							<thead>
								<tr>
									<th class="sorting" rowspan="1" colspan="1" style="width: 50px;">Numero Iscrizione</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 250px;">Nominativo</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Pacchetti</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 38px;">Periodo</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Totale pagato</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 121px;">Data pagamento</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 95px;">Numero Fattura</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="payments.content" status="paymentStatus" var="payment">
									<s:set var="paymentId" value="id" />
									<tr class="odd">
										<td class=" ">
											<s:property value="subscription.id" />
										</td>
										<td class=" ">
											<s:property value="%{subscription.name +' '+ subscription.surname}" />
										</td>
										<td class=" ">
											<s:iterator value="packagesCourses" status="plainStatus" var="plain">
												<div>
													<s:property value="name" />
												</div>
											</s:iterator>
										</td>
										<td class=" ">
											<s:iterator value="packagesCourses" status="plainStatus" var="plain">
												<div>
													<s:property value="getPeriodoPagato(getExpiration(#paymentId,id))" />
												</div>
											</s:iterator>
										</td>
										<td class=" ">
											<s:property value="amountPaied" />
										</td>
										<td class=" ">
											<s:date name="paymentDate" format="dd/MM/yyyy" />
										</td>
										<td class=" ">
											<s:property value="invoice.id" />
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
								<s:property value="payments.totalPages" />
							</span>
						</div>
						<div class="dataTables_paginate paging_full_numbers">
							<s:if test="!payments.firstPage">
								<a href="<s:url action="search/payment"><s:param name="page" value="0"/><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:else>
							<s:if test="payments.hasPreviousPage()">
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="page-1"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="search/payment"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>"> <s:property value="page+1" />
								</a>
							</span>
							<s:if test="payments.hasNextPage()">
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="page+1"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!payments.lastPage">
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="payments.totalPages-1"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/payment"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param><s:param name="plainId"><s:property value="plainId"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:else>
						</div>
					</div>
				</div>
				<h3>
					Incasso :
					<s:property value="totalPaid" />
				</h3>
				<s:if test="%{plainId > 0}">
					<h3>
						Corrispettivo insegnate del corso :
						<s:property value="teacherToPaid" />
					</h3>
				</s:if>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</div>
</body>
</html>