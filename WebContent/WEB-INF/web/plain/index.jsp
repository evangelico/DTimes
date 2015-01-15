<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Pacchetti corsi</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<i class="icon-inbox"></i> Corsi disponibili
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="dataTables_wrapper">
						<div class="dataTables_filter">
							<s:form action="plain/index" method="POST" cssStyle="margin: 0px;">
							<div class="dataTables_filter">
								<label><span>Nome:</span> <s:textfield name="nameFilter" /></label>
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
								<tr role="row">
									<th class="sorting" rowspan="1" colspan="1" style="width: 160px;">Nome</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 338px;">Descrizione</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 65px;">Giorni di validità</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 65px;">Costo</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 65px;">Percentuale insegnate</th>
									<th class="sorting" rowspan="1" colspan="1" style="width: 129px;"></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="plains.content" status="plainStatus" var="plain">
									<tr class="odd">
										<td class=" ">
											<s:property value="name" />
										</td>
										<td class=" ">
											<s:property value="description" />
										</td>
										<td class=" ">
											<s:property value="expirationType.name()" />
										</td>
										<td class=" ">
											<s:property value="amount" />
										</td>
										<td class=" ">
											<s:property value="teacherPercentage" />
										</td>
										<td class=" ">
											<a href="<s:url action="plain/edit"><s:param name="plainIDToEdit"><s:property value="id" /></s:param></s:url>">
												<div class="btn btn-warning" style="width: 78px; height: 30px; margin-bottom: 10px;">Modifica</div>
											</a>
											<a href="<s:url action="plain/remove"><s:param name="plainIDToDelete" ><s:property value="id" /></s:param><s:param name="page"><s:property value="page"/></s:param></s:url>">
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
								<s:property value="plains.totalPages" />
							</span>
						</div>
						<div class="dataTables_paginate paging_full_numbers">
							<s:if test="!plains.firstPage">
								<a href="<s:url action="plain/index"><s:param name="page" value="0"/><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:if>
							<s:else>
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="first paginate_button paginate_button_disabled"> Inizio </a>
							</s:else>
							<s:if test="plains.hasPreviousPage()">
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="page-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:if>
							<s:else>
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="previous paginate_button paginate_button_disabled"> Precedente </a>
							</s:else>
							<span>
								<a class="paginate_active" href="<s:url action="plain/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>">
									<s:property value="page+1" />
								</a>
							</span>
							<s:if test="plains.hasNextPage()">
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="page+1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:if>
							<s:else>
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="next paginate_button paginate_button_disabled">Successiva</a>
							</s:else>
							<s:if test="!plains.lastPage">
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="plains.totalPages-1"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
							</s:if>
							<s:else>
								<a href="<s:url action="plain/index"><s:param name="page"><s:property value="page"/></s:param><s:param name="nameFilter"><s:property value="nameFilter"/></s:param></s:url>" class="last paginate_button paginate_button_disabled">Ultima</a>
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