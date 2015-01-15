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
						<s:if test="%{subscription != null && subscription.id > 0}">
							<i class="icon-th-list"></i> Modifica iscrizione
						</s:if>
						<s:else>
							<i class="icon-th-list"></i> Nuova iscrizione
						</s:else>
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="box_error">
						<s:actionerror />
					</div>
					<s:if test="%{subscription != null && subscription.id > 0}">
						<s:set name="myenv" value="%{'subscription/saveEdit'}" />
					</s:if>
					<s:else>
						<s:set name="myenv" value="%{'subscription/saveInsert'}" />
					</s:else>
					<s:form action="%{#myenv}" method="POST">
						<s:token />
						<s:if test="%{subscription != null && subscription.id > 0}">
							<div class="control-group">
								<label for="textfield" class="control-label">ID iscrizione :</label>
								<div class="controls">
									<s:textfield name="subscription.id" cssClass="uneditable-input" readonly="true" />
								</div>
							</div>
						</s:if>
						<div class="control-group">
							<label for="textfield" class="control-label">Data iscrizione* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="registrationDate" id="textfield21" cssClass="input-medium uneditable-input datepick" readonly="true" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Nome* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.name" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Cognome* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.surname" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Codice fiscale* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.fiscalCode" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Data di nascita* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="birthdayDate" id="textfield22" cssClass="input-medium uneditable-input datepick" readonly="true" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Indirizzo* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.address" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Numero di telefono :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.phoneNumber" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Numero di cellulare :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.cellNumber" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Email :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="subscription.email" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Data scadenza certificato medico :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="medicalCertificateDate" id="textfield23" cssClass="input-medium uneditable-input datepick" readonly="true" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label top">Pacchetti :</label>
							<div class="controls" id="plains">
								<s:if test="%{subscription != null}">
									<s:set var="amountPaid" value="0" />
									<s:iterator value="subscription.packagesCourses" var="plain">
										<span id="spryselect1">
											<s:textfield value="%{name}" cssClass="input-large uneditable-input" readonly="true" cssStyle="width: 220px;" />
											<s:hidden type="hidden" name="plainsSelected" value="%{id}" />
											<s:set var="amountPaid" value="%{#amountPaid+amount}" />
											<div class="btn btn-danger inline middle remove-plain" onclick="removePlain(this);" style="width: 145px;">Rimuovi Pacchetto</div>
											<br />
										</span>
									</s:iterator>
								</s:if>
								<span id="spryselect1">
									<s:select name="plainsSelected" id="plainsSelected" list="plains" headerKey="" headerValue="Seleziona un pacchetto di corsi" listKey="id" listValue="name" onchange="populatePlain(this);" />
									<div class="btn btn-primary inline middle add-plain" onclick="addPlain(this);" style="width: 145px;">Aggiungi Pacchetto</div>
									<div class="btn btn-danger inline middle remove-plain hidden" onclick="removePlain(this);" style="width: 145px;">Rimuovi Pacchetto</div>
									<br />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Importo da pagare :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield id="importToPay" cssClass="input-xlarge" readonly="true" value="%{#amountPaid}" />
								</span>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">Salva</button>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../include/footer.jsp"%>
</div>
<script type="text/javascript">
	var plainFinded;
	
	function populatePlain(element) {
		addPlain(element);
		setImportToPay();
	}
	
	function addPlain(element) {
		newPlain = $(element).parent().clone();
		$("#plains").append(newPlain);
		$(newPlain).find(".remove-plain").removeClass("hidden");
	};

	function removePlain(element) {
		plainToremove = $(element).parent().find("#plainsSelected").val();
		$(element).parent().remove();
		setImportToPay();
	};

	function getPlain(plainSelected) {
		var request = $.ajax({
			type : "GET",
			url : '<s:url action="plain/getPlainFromId" />',
			cache : false,
			dataType : "json",
			async : false,
			data : {
				plainSelected : plainSelected
			},
		});

		request.done(function(plain) {
			console.debug("getImportToPay(" + plainSelected + ") --> Success - " + jsonPrettyPrint(plain));
			plainFinded = plain;
		});

		request.fail(function(jqXHR, textStatus) {
			console.error("getImportToPay(" + plainSelected + ") --> Fail - " + textStatus);
			plainFinded = null;
		});
	}

	function setImportToPay() {
		console.debug("setImportToPay()");
		plains = [];

		$.each($("input[name='plainsSelected']"), function(index, obj) {
			plains.push($(obj).val());
		});

		$.each($("select[name=plainsSelected] option:selected"), function(index, obj) {
			plains.push($(obj).val());
		});

		console.debug("Pachetti selezionati: " + plains);

		importToPay = 0;
		if (plains.length > 0) {
			$.each(plains, function(index, value) {
				getPlain(value);
				if (plainFinded != null) {
					importToPay = importToPay + plainFinded.amount;
				}
			});

			importToPay = parseInt(importToPay)
		}

		$("#importToPay").val(importToPay);
	}
</script>
</body>
</html>