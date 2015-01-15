<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Avvisi</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<i class="icon-inbox"></i>Pagamenti nascosti
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<div class="dataTables_filter">
							<s:form action="search/deadlinesHide" method="POST" cssStyle="margin: 0px;">
								<div class="dataTables_filter">
									<label><span>Nominativo:</span> <s:textfield name="nameFilter" /></label>
									<label><span>Corso:</span> <s:select name="plainFilter" list="plainsSelectList" headerKey="" headerValue="Seleziona un pacchetto di corsi" listKey="id" listValue="name" cssStyle="border: 1px solid #ccc;" /></label>
									<label>
										<button type="submit" class="btn btn-primary">Filtra</button>
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
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Pacchetto</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 100px;">Importo da pagare</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 38px;">Scadenza</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 120px;"></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="deadlines.content" status="deadlinesStatus" var="deadline">
									<tr class="odd">
										<td class=" ">
											<s:property value="subscription.id" />
										</td>
										<td class=" ">
											<s:property value="%{subscription.name +' '+ subscription.surname}" />
										</td>
										<td class=" ">
											<s:property value="packageCourses.name" />
										</td>
										<td class=" ">
											<s:property value="packageCourses.amount" />
										</td>
										<td class=" ">
											<s:date name="deadlineDate" format="dd/MM/yyyy" />
										</td>
										<td class=" ">
											<a href="<s:url action="search/enableDeadline"><s:param name="deadlineId"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-warning" style="width: 154px; height: 30px; margin-bottom: 10px;">Attiva scadenza</div>
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
								<s:property value="deadlines.totalPages" />
							</span>
						</div>
						<div class="dataTables_paginate paging_full_numbers">
							<s:if test="!deadlines.firstPage">
								<a href="<s:url action="search/deadlinesHide"><s:param name="page" value="0"/><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:else>
							<s:if test="deadlines.hasPreviousPage()">
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>">
									<s:property value="page+1" />
								</a>
							</span>
							<s:if test="deadlines.hasNextPage()">
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page+1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!deadlines.lastPage">
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="deadlines.totalPages-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="search/deadlinesHide"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param><s:param name="plainFilter"><s:property value="plainFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:else>
						</div>
					</div>
				</div>
				<h3>Totale non pagato : <s:property value="totalUnpaid"/></h3>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</div>
</body>
</html>