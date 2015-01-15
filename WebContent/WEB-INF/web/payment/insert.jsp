<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Pagamenti</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						<s:if test="%{payment != null && payment.id > 0}">
							<i class="icon-th-list"></i> Modifica pagamento
						</s:if>
						<s:else>
							<i class="icon-th-list"></i> Nuova pagamento
						</s:else>
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="box_error">
						<s:actionerror />
					</div>
					<s:if test="%{payment != null && payment.id > 0}">
						<s:set name="myenv" value="%{'payment/saveEdit'}" />
					</s:if>
					<s:else>
						<s:set name="myenv" value="%{'payment/saveInsert'}" />
					</s:else>
					<s:form action="%{#myenv}" method="POST">
						<s:token />
						<s:if test="%{payment != null && payment.id > 0}">
							<div class="control-group">
								<label for="textfield" class="control-label">ID pagamento :</label>
								<div class="controls">
									<s:textfield name="payment.id" cssClass="input-small uneditable-input" readonly="true" />
								</div>
							</div>
						</s:if>
						<div class="control-group">
							<label for="textfield" class="control-label">ID iscrizione :</label>
							<div class="controls">
								<s:textfield name="subscription.id" cssClass="input-small uneditable-input" readonly="true" />
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Nominativo:</label>
							<div class="controls">
								<s:textfield value="%{subscription.name +' '+ subscription.surname}" cssClass="input-xlarge uneditable-input" readonly="true" />
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Data pagamento* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="paymentDate" id="textfield22" cssClass="input-medium uneditable-input datepick" readonly="true" placeholder="Data pagamento" />
									<span class="textfieldRequiredMsg">Campo obbligatorio</span>
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label top">Scadenze :</label>
							<div class="controls" id="plains">
								<s:if test="%{payment != null && payment.id > 0}">
									<s:iterator value="expiredPaied" var="deadline">
										<span id="spryselect1">
											<s:textfield value="%{packageCourses.name+' - '+getPeriodoPagato(deadlineDate)}" cssClass="input-large uneditable-input" readonly="true" cssStyle="width: 220px;" />
											<s:hidden type="hidden" name="expiredSelected" value="%{id}" />
											<s:hidden type="hidden" name="plainsSelected" id="plainsSelected" />
											<div class="btn btn-danger inline middle remove-plain" onclick="removeDeadline(this);" style="width: 145px;">Rimuovi Pacchetto</div>
											<br />
										</span>
									</s:iterator>
								</s:if>
								<span id="spryselect1">
									<s:select name="expiredSelected" id="expiredSelected" list="deadlinesExpiredList" headerKey="" headerValue="Seleziona una scadenza" listKey="id" listValue="%{packageCourses.name+' - '+getPeriodoPagato(deadlineDate)}" onchange="populatePlain(this);" />
									<div class="btn btn-primary inline middle add-plain" onclick="addDeadline(this);" style="width: 145px;">Aggiungi scadenza</div>
									<div class="btn btn-danger inline middle remove-plain hidden" onclick="removeDeadline(this);" style="width: 145px;">Rimuovi scadenza</div>
									<s:hidden type="hidden" name="plainsSelected" id="plainsSelected" />
									<br />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label top">Ulteriori pagamenti :</label>
							<div class="controls" id="plainsExtra">
								<s:if test="%{payment != null && payment.id > 0}">
									<s:iterator value="plainAlreadyPaied" var="plain">
										<span id="spryselect1">
											<s:textfield value="%{name}" cssClass="input-large uneditable-input" readonly="true" cssStyle="width: 220px;" />
											<s:hidden type="hidden" name="plainsSelectedExtra" value="%{id}" />
											<div class="btn btn-danger inline middle remove-plain" onclick="removePlain(this);" style="width: 145px;">Rimuovi Pacchetto</div>
											<br />
										</span>
									</s:iterator>
								</s:if>
								<span id="spryselect1">
									<s:select name="plainsSelectedExtra" id="plainsSelectedExtra" list="plains" headerKey="" headerValue="Seleziona un pacchetto di corsi" listKey="id" listValue="name" onchange="populatePlainExtra(this);" />
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
									<s:textfield id="importToPay" cssClass="input-small" readonly="true" value="%{#amountPaid}" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Percentuale di sconto:</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="payment.percentageDiscount" id="percentageDiscount" cssClass="input-small" onblur="discount()" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Importo pagato :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="payment.amountPaied" id="importPaid" cssClass="input-small" />
								</span>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">Salva</button>
							<s:hidden name="addPayment" />
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

	function populatePlainExtra(element) {
		if ($(element).val() != '') {
			addPlain(element);
		}
		setImportToPay();
	}

	function addPlain(element) {
		newPlain = $(element).parent().clone();
		$("#plainsExtra").append(newPlain);
		$(newPlain).find(".remove-plain").removeClass("hidden");
	};

	function removePlain(element) {
		plainToremove = $(element).parent().find("#plainsSelectedExtra").val();
		$(element).parent().remove();
		setImportToPay();
	};

	function populatePlain(element) {
		if ($(element).val() != '') {
			getPlainFromDeadline($(element).val());
			if (plainFinded != null && plainFinded != undefined) {
				addDeadline(element);
				setPlainSelected(element);
				setImportToPay();
			}
		}
	}

	function setPlainSelected(element) {
		newPlainSelected = $(element).parent().find("#plainsSelected");
		if (plainFinded != null && plainFinded != undefined) {
			$(newPlainSelected).val(plainFinded.id);
		}
	};

	function addDeadline(element) {
		newDedaline = $(element).parent().clone();
		$("#plains").append(newDedaline);
		$(newDedaline).find(".remove-plain").removeClass("hidden");
	};

	function removeDeadline(element) {
		$(element).parent().remove();
		setImportToPay();
	};

	function getPlainFromDeadline(deadline) {
		var request = $.ajax({
			type : "GET",
			url : '<s:url action="plain/getPlainFromDeadline" />',
			cache : false,
			dataType : "json",
			async : false,
			data : {
				expiredSelected : deadline
			},
		});

		request.done(function(plain) {
			console.debug("getPlainFromDeadline(" + deadline + ") --> Success - " + jsonPrettyPrint(plain));
			plainFinded = plain;
		});

		request.fail(function(jqXHR, textStatus) {
			console.error("getPlainFromDeadline(" + deadline + ") --> Fail - " + textStatus);
			plainFinded = null;
		});
	}

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
			console.debug("getPlain(" + plainSelected + ") --> Success - " + jsonPrettyPrint(plain));
			plainFinded = plain;
		});

		request.fail(function(jqXHR, textStatus) {
			console.error("getPlain(" + plainSelected + ") --> Fail - " + textStatus);
			plainFinded = null;
		});
	}

	function setImportToPayExtra() {
		console.debug("setImportToPayExtra()");
		plains = [];

		$.each($("input[name='plainsSelectedExtra']"), function(index, obj) {
			plains.push($(obj).val());
		});

		$.each($("select[name='plainsSelectedExtra'] option:selected"), function(index, obj) {
			plains.push($(obj).val());
		});

		console.debug("Pachetti extra selezionati: " + plains);

		importToPay = 0;
		if (plains.length > 0) {
			$.each(plains, function(index, value) {
				getPlain(value);
				if (plainFinded != null) {
					importToPay = importToPay + plainFinded.amount;
				}
			});

			importToPay = parseInt(importToPay);
		}

		$("#importToPay").val(importToPay);
	}

	function setImportToPay() {
		console.debug("setImportToPay()");
		plains = [];
		plainsExtra = [];

		$.each($("input[name='expiredSelected']"), function(index, obj) {
			plains.push($(obj).val());
		});

		$.each($("select[name='expiredSelected'] option:selected"), function(index, obj) {
			plains.push($(obj).val());
		});

		console.debug("Pachetti selezionati: " + plains);

		importToPay = 0;
		if (plains.length > 0) {
			$.each(plains, function(index, value) {
				if (value != '') {
					getPlainFromDeadline(value);
					if (plainFinded != null && plainFinded != undefined) {
						importToPay = importToPay + plainFinded.amount;
					}
				}
			});

		}

		$.each($("input[name='plainsSelectedExtra']"), function(index, obj) {
			plainsExtra.push($(obj).val());
		});

		$.each($("select[name='plainsSelectedExtra'] option:selected"), function(index, obj) {
			plainsExtra.push($(obj).val());
		});

		console.debug("Pachetti extra selezionati: " + plains);

		if (plainsExtra.length > 0) {
			$.each(plainsExtra, function(index, value) {
				getPlain(value);
				if (plainFinded != null) {
					importToPay = importToPay + plainFinded.amount;
				}
			});

		}

		$("#importToPay").val(importToPay);
	}

	function discount() {
		setImportToPay();
		discountedAmount = $("#importToPay").val();
		if ($("#percentageDiscount").val() > 0) {
			discountedAmount = discountedAmount - ((discountedAmount * $("#percentageDiscount").val()) / 100);
		}
		$("#importToPay").val(discountedAmount);
	}

	$(document).ready(function() {
		$.each($("input[name='expiredSelected']"), function(index, obj) {
			if ($(obj).val() != '') {
				getPlainFromDeadline($(obj).val());
				if (plainFinded != null && plainFinded != undefined) {
					setPlainSelected(obj);
					setImportToPay();
				}
			}
		});
		$.each($("input[name='plainsSelectedExtra']"), function(index, obj) {
			if ($(obj).val() != '') {
				setImportToPay();
			}
		});
	})
</script>
</body>
</html>