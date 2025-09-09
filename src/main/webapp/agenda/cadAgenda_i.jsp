<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><s:text name="label.titulo.pagina.cadastro" /></title>
<link rel="stylesheet" href="../templates/header/header.css">
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-secondary">
	<%@ include file="../templates/header/header.jsp"%>
	<s:if test="hasActionErrors()">
		<div class="toast-container position-fixed bottom-0 end-0 p-3">
			<div id="errorToast"
				class="toast align-items-center border-0 text-light bg-danger"
				role="alert" aria-live="assertive" aria-atomic="true">
				<div class="d-flex">
					<div class="toast-body">
						<s:actionerror cssClass="" />
					</div>
					<button type="button" class="btn-close btn-close-white me-2 m-auto"
						data-bs-dismiss="toast" aria-label="Close"></button>
				</div>
			</div>
		</div>
	</s:if>
	<div class="container">
		<s:form action="%{'/atualizarAgendas.action'}">
			<div class="card mt-5">
				<div class="card-header d-flex align-items-center">
					<s:url action="todosAgendas" var="todos" />
					<a href="${todos}" class="btn btn-success ">Voltar</a>

					<h5 class="card-title mb-0 mx-3">
						<s:if test="agendaVo.rowid == null">Nova Agenda</s:if>
						<s:else>Editar Agenda</s:else>
					</h5>
				</div>

				<div class="card-body">
					<div class="row align-items-center">
						<label for="id" class="col-sm-2 col-form-label text-center">
							Código: </label>

						<div class="col-sm-2">
							<s:textfield cssClass="form-control" id="id"
								name="agendaVo.rowid" readonly="true" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label for="nome" class="col-sm-2 col-form-label text-center">
							Nome: </label>

						<div class="col-sm-5">
							<s:textfield cssClass="form-control" id="nome"
								name="agendaVo.nome" required="true" maxlength="100" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label for="periodo" class="col-sm-2 col-form-label text-center">
							Período: </label>

						<div class="col-sm-5">
							<s:select cssClass="form-select" id="periodo"
								name="agendaVo.codigoPeriodoDisponivel"
								list="listaOpcoesPeriodoDisponivel" headerKey=""
								headerValue="Escolha o período..." listKey="codigo"
								listValue="descricao"
								value="%{agendaVo.codigoPeriodoDisponivel}" required="true" />
						</div>
					</div>
				</div>

				<div class="card-footer">
					<div class="form-row">
						<button class="btn btn-primary col-sm-4 offset-sm-1">Salvar</button>
						<button type="button" onclick="resetFormulario()"
							class="btn btn-secondary col-sm-4 offset-sm-2">Limpar
							Formulário</button>
					</div>
				</div>
			</div>
		</s:form>
	</div>

	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	<s:if test="hasActionErrors()">
		<script>
			document.addEventListener("DOMContentLoaded", function() {
				var toastEl = document.getElementById('errorToast');
				var toast = new bootstrap.Toast(toastEl, {
					delay : 0,
					autohide : false
				});
				toast.show();
			});
		</script>
	</s:if>

	<script>
		function resetFormulario() {
			var inputNomeAgenda = document
					.querySelector('input[name="agendaVo.nome"]');
			var selectPeriodo = document
					.querySelector('select[name="agendaVo.codigoPeriodoDisponivel"]');

			inputNomeAgenda.value = "";
			selectPeriodo.value = "";
		}
	</script>
</body>
</html>