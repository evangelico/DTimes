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
					<s:form action="search/plains" method="POST">
						<div class="control-group">
							<label for="textfield" class="control-label">Periodo :</label>
							<div class="controls">
								<span id="sprytextfield3">
									<s:select id="paymentPeriod" list="paymentsPeriod" cssClass="input-medium" name="paymentPeriod" headerKey="" headerValue="Selezina periodo" />
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
						<i class="icon-inbox"></i>Situazione corsi
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<div class="box_error">
							<s:actionerror />
						</div>
						<table class="table table-nomargin dataTable dataTable-tools  table-bordered">
							<thead>
								<tr>
									<th class="sorting" rowspan="1" colspan="1" style="width: 50px;">Numero iscritti Generale</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 250px;">Nome</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Totale incassato</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Percentuale di sconto</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Corrispettivo</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="plains.content" status="plainStatus" var="payment">
									<s:if test="%{paymentPeriod == '' || paymentPeriod == null}">
										<tr class="odd">
											<td class=" ">
												<s:property value="getCountSubscriptionByPlain(id)" />
											</td>
											<td class=" ">
												<s:property value="name" />
											</td>
											<td class=" ">
												<s:property value="getTotalAmountByPlain(id,paymentPeriod)" />
											</td>
											<td class=" ">
												<s:property value="teacherPercentage" />
											</td>
											<td class=" ">
												<s:property value="getTotalToTeacherByPlain(id,paymentPeriod)" />
											</td>
										</tr>
									</s:if>
									<s:else>
										<s:if test="%{!subscriptionPlain}">
											<tr class="odd">
												<td class=" ">
													<s:property value="getCountSubscriptionByPlain(id)" />
												</td>
												<td class=" ">
													<s:property value="name" />
												</td>
												<td class=" ">
													<s:property value="getTotalAmountByPlain(id,paymentPeriod)" />
												</td>
												<td class=" ">
													<s:property value="teacherPercentage" />
												</td>
												<td class=" ">
													<s:property value="getTotalToTeacherByPlain(id,paymentPeriod)" />
												</td>
											</tr>
										</s:if>
									</s:else>
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
								<s:property value="plains.totalPages" />
							</span>
						</div>
						<div class="dataTables_paginate paging_full_numbers">
							<s:if test="!plains.firstPage">
								<a href="<s:url action="search/plains"><s:param name="page" value="0"/><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:else>
							<s:if test="plains.hasPreviousPage()">
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="page-1"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="search/plains"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>"> <s:property value="page+1" />
								</a>
							</span>
							<s:if test="plains.hasNextPage()">
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="page+1"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!plains.lastPage">
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="plains.totalPages-1"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/plains"><s:param name="page"><s:property value="page"/></s:param><s:param name="paymentPeriod"><s:property value="paymentPeriod"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:else>
						</div>
					</div>
				</div>
				<h3>
					Incasso :
					<s:property value="totalPaid" />
				</h3>
				<h3>
					Corrispettivi :
					<s:property value="teacherToPaid" />
				</h3>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</div>
</body>
</html>