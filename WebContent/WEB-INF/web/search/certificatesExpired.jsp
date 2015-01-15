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
						<i class="icon-inbox"></i>Certificati scaduti
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<div class="dataTables_filter">
							<s:form action="search/certificatesExpired" method="POST" cssStyle="margin: 0px;">
								<div class="dataTables_filter">
									<label><span>Nominativo:</span> <s:textfield name="nameFilter" /></label> <label>
										<button type="submit" class="btn btn-primary">Filtra</button> <s:hidden name="paymentPeriod" /> <s:hidden name="plainId" />
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
									<th class="sorting" rowspan="1" colspan="1" >Numero Iscrizione</th>
									<th class="sorting" rowspan="1" colspan="1">Nominativo</th>
									<th class="sorting" rowspan="1" colspan="1" >Data scadenza certificato</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 150px;"></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="subscriptions.content" status="subscriptionStatus" var="subscription">
									<tr class="odd">
										<td class=" ">
											<s:property value="id" />
										</td>
										<td class=" ">
											<s:property value="%{name +' '+ surname}" />
										</td>
										<td class=" ">
											<s:date name="medicalCertificateDate" format="dd/MM/yyyy" />
										</td>
										<td class=" ">
											<a href="<s:url action="subscription/edit"><s:param name="subscriptionIDToEdit"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-info" style="width: 112px; height: 30px; margin-bottom: 10px;">Aggiorna certificato</div>
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
								<a href="<s:url action="search/certificatesExpired"><s:param name="page" value="0"/><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:else>
							<s:if test="subscriptions.hasPreviousPage()">
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>"> <s:property value="page+1" />
								</a>
							</span>
							<s:if test="subscriptions.hasNextPage()">
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page+1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!subscriptions.lastPage">
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="subscriptions.totalPages-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/certificatesExpired"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
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