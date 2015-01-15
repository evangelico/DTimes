<%@include file="../include/top.jsp"%>
<div class="main_container">
	<%@include file="../include/header.jsp"%>
	<div class="central_container">
		<div class="section_preview">
			<div class="title">Configurazioni</div>
			<%@include file="../include/status.jsp"%>
		</div>
		<div class="main_section">
			<div class="box-bordered box-color">
				<div class="box-title">
					<h3>
						Modifica dati amministratore
						<span class="alert-info">(Uscire ed effettuare la login con le nuove credenziali) </span>
					</h3>
				</div>
				<div class="box-content nopadding">
					<s:form action="profile/saveEdit" method="POST">
						<s:token />
						<div class="control-group">
							<label for="textfield" class="control-label">Username*:</label>
							<div class="controls">
								<s:textfield type="text" name="userNew.username" value="%{userDTO.username}" cssClass="input-xlarge" />
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Nome*:</label>
							<div class="controls">
								<s:textfield type="text" name="userNew.name" value="%{userDTO.name}" cssClass="input-xlarge" maxlength="25" />
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Cognome*:</label>
							<div class="controls">
								<s:textfield type="text" name="userNew.surname" value="%{userDTO.surname}" cssClass="input-xlarge" maxlength="25" />
							</div>
						</div>
						<div class="control-group">
							<label for="textfield" class="control-label">Password*:</label>
							<div class="controls">
								<s:password name="userNew.password" cssClass="input-xlarge" maxlength="25" cssStyle="border: 1px solid #ccc;width: 270px;"/>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">Salva</button>
						</div>
						<div class="box_error">
							<s:actionerror />
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