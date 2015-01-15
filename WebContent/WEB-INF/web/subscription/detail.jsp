<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Dettaglio</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<i class="icon-inbox"></i>Dettaglio di:
						<s:property value="%{subscription.name +' '+ subscription.surname}" />
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<table class="table table-nomargin dataTable dataTable-tools  table-bordered">
							<thead>
								<tr role="row">
									<th class="sorting" rowspan="1" colspan="1" style="width: 59px;">Pagamento N°</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 143px;">Data pagamento</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 143px;">Pacchetti</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 190px;">Periodo pagato</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 117px;">Totale / Sconto / Pagato</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 115px;">Fattura N°</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 151px;"></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="payments.content" status="paymentStatus" var="payment">
									<s:set var="paymentId" value="id" />
									<tr class="odd">
										<td class=" ">
											<s:property value="id" />
										</td>
										<td class=" ">
											<s:date name="paymentDate" format="dd/MM/yyyy" />
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
											<s:property value="getAmount(#paymentId)" />
											/
											<s:property value="percentageDiscount" />
											/
											<s:property value="amountPaied" />
										</td>
										<td class=" ">
											<s:property value="invoice.id" />
										</td>
										<td class=" ">
											<a href="<s:url action="invoice/stamp"><s:param name="invoiceId"><s:property value="invoice.id" /></s:param></s:url>">
												<div class="btn btn-info" style="width: 112px; height: 30px; margin-bottom: 10px;">Stampa fattura</div>
											</a> <a href="<s:url action="payment/edit"><s:param name="paymentId"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-warning" style="width: 78px; height: 30px; margin-bottom: 10px;">Modifica</div>
											</a> <a href="<s:url action="payment/remove"><s:param name="paymentId"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-danger" style="width: 78px; height: 30px; margin-bottom: 10px;">Elimina</div>
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
								<s:property value="payments.totalPages" />
							</span>
						</div>
						<div class="dataTables_paginate paging_full_numbers">
							<s:if test="!payments.firstPage">
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page" value="0"/></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Prima </a>
							</s:else>
							<s:if test="subscriptions.hasPreviousPage()">
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page-1"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>"> <s:property value="page+1" />
								</a>
							</span>
							<s:if test="subscriptions.hasNextPage()">
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page+1"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!subscriptions.lastPage">
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="subscriptions.totalPages-1"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="subscription/detail"><s:param name="subscriptionIDToShow"><s:property value="subscriptionIDToShow"/></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
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