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
						<s:if test="%{plain != null && plain.id > 0}">
							<i class="icon-th-list"></i> Modifica pacchetto
						</s:if>
						<s:else>
							<i class="icon-th-list"></i> Nuovo pacchetto
						</s:else>
					</h3>
				</div>
				<div class="box-content nopadding">
					<div class="box_error">
						<s:actionerror />
					</div>
					<s:if test="%{plain != null && plain.id > 0}">
						<s:set name="myenv" value="%{'plain/saveEdit'}" />
					</s:if>
					<s:else>
						<s:set name="myenv" value="%{'plain/saveInsert'}" />
					</s:else>
					<s:form action="%{#myenv}" method="POST">
						<s:token />
						<s:if test="%{plain != null && plain.id > 0}">
							<div class="control-group">
								<label for="textfield" class="control-label">ID pacchetto :</label>
								<div class="controls">
									<s:textfield name="plain.id" cssClass="uneditable-input" readonly="true" />
								</div>
							</div>
						</s:if>
						<div class="control-group">
							<label for="textfield" class="control-label">Nome* :</label>
							<div class="controls">
								<span id="sprytextfield1">
									<s:textfield name="plain.name" id="name" cssClass="input-xlarge" maxlength="100" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="emailfield" class="control-label">Descrizione :</label>
							<div class="controls">
								<s:textfield name="plain.description" id="description" cssClass="input-xlarge" maxlength="999" />
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Giorni validit&agrave;* :</label>
							<div class="controls">
								<span id="spryselect1">
									<s:select name="plain.expirationType" id="numberDaysValidity" cssClass="input-xlarge" list="expirationTypes" headerKey="" headerValue="Seleziona il periodo di validità" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Costo* :</label>
							<div class="controls">
								<span id="sprytextfield4">
									<s:textfield name="plain.amount" id="amount" cssClass="input-xlarge" maxlength="10" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Percentuale insegnante :</label>
							<div class="controls">
								<span id="sprytextfield4">
									<s:textfield name="plain.teacherPercentage" id="teacherPercentage" cssClass="input-xlarge" maxlength="10" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Iscrizione&#63; :</label>
							<div class="controls">
								<span id="sprytextfield4">
									<s:checkbox name="plain.subscriptionPlain" id="subscriptionPlain" />
								</span>
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Entrata singola&#63; :</label>
							<div class="controls">
								<span id="sprytextfield4">
									<s:checkbox name="plain.singleLesson" id="singleLesson" />
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
</body>
</html>