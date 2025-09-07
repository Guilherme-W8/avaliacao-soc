<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Compromisso</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
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
						<s:actionerror />
					</div>
					<button type="button" class="btn-close btn-close-white me-2 m-auto"
						data-bs-dismiss="toast" aria-label="Close"></button>
				</div>
			</div>
		</div>
	</s:if>
	<div class="container">
		<s:form action="%{'/atualizarCompromissos.action'}">
			<div class="card mt-5">
				<div class="card-header d-flex align-items-center">
					<s:url action="todosCompromissos" var="todos" />
					<a href="${todos}" class="btn btn-success ">Voltar</a>

					<h5 class="card-title mb-0 mx-3">
						<s:if test="compromissoVo.rowid == null">Novo Compromisso</s:if>
						<s:else>Editar Compromisso</s:else>
					</h5>
				</div>

				<div class="card-body">
					<div class="row align-items-center">
						<label for="id" class="col-sm-2 col-form-label text-center">
							Código: </label>

						<div class="col-sm-2">
							<s:textfield cssClass="form-control" id="id"
								name="compromissoVo.rowid" readonly="true" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label for="funcionario"
							class="col-sm-2 col-form-label text-center"> Funcionário:
						</label>

						<div class="col-sm-5">
							<s:select cssClass="form-select" id="funcionario"
								name="compromissoVo.idFuncionario" list="listaFuncionarios"
								headerKey="" headerValue="Escolha o funcionário..."
								listKey="rowid" listValue="nome"
								value="%{compromissoVo.idFuncionario}" required="true" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label for="agenda" class="col-sm-2 col-form-label text-center">
							Agenda: </label>

						<div class="col-sm-5">
							<s:select cssClass="form-select" id="agenda"
								onchange="atualizarDisponibilidade()"
								name="compromissoVo.idAgenda" list="listaAgendas" headerKey=""
								headerValue="Escolha a agenda..." listKey="rowid"
								listValue="nome" value="%{compromissoVo.idAgenda}"
								required="true" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label for="data" class="col-sm-2 col-form-label text-center">
							Data: </label>

						<div class="col-sm-3">
							<s:textfield cssClass="form-control" id="data"
								name="compromissoVo.data" type="date" required="true" />
						</div>
					</div>

					<div class="row align-items-center mt-3">
						<label for="hora" class="col-sm-2 col-form-label text-center">
							Horário: </label>

						<div class="col-sm-3">
							<s:textfield cssClass="form-control" id="hora"
								name="compromissoVo.hora" type="text" required="true" />
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
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/pt.js"></script>

	<script>
		function resetFormulario() {
			var selectNomeFuncionario = document.querySelector('select[name="compromissoVo.idFuncionario"]'); 
			var selectNomeAgenda = document.querySelector('select[name="compromissoVo.idAgenda"]');
			var inputData = document.querySelector('input[name="compromissoVo.data"]');
			var inputHora = document.querySelector('input[name="compromissoVo.hora"]');
			
			selectNomeFuncionario.value = "";
			selectNomeAgenda.value = "";
			inputData.value = "";
			inputHora.value = "";
		}
	</script>

	<script>
		var disponibilidade = 3;
		var horarioManha = {
			de: '06:00',
			para: '11:59'
		};
		
		var horarioTarde = {
			de: '12:00',
			para: '17:59'
		};
		
		var horarioAmbos = {
			de: '06:00',
			para: '17:59'
		};
	
		var hora = flatpickr("#hora", {
			enableTime : true,
			noCalendar : true,
			altFormat : "H:i",
			altInput: true,
			displayFormat: "H:i:S",
			time_24hr : true,
			minTime : retornaHorarioDisponivel().de,
			maxTime : retornaHorarioDisponivel().para,
			allowInput: true,
			onOpen: function(selectedDates, dateStr, instance){
				if(!dateStr){
					instance.setDate(retornaHorarioDisponivel().de);
				}
			},
			onChange: function(selectedDates, dateStr, instance) {
			  if (!dateStr) return;

			  const limites = retornaHorarioDisponivel(); // pega limites atualizados
			  const [h, m] = dateStr.split(":").map(Number);
			  const minutos = h * 60 + m;
			  
			  const [inicioH, inicioM] = limites.de.split(":").map(Number);
			  const [fimH, fimM] = limites.para.split(":").map(Number);
			  
			  const inicio = inicioH * 60 + inicioM;
			  const fim = fimH * 60 + fimM;
			  
			  if (minutos < inicio) {
			      instance.setDate(limites.de, true);
			  } else if (minutos > fim) {
			      instance.setDate(limites.para, true);
			  }
		    }
		});
		
		flatpickr("#data", {
		    altFormat: "d/m/Y",
		    altInput: true,
		    allowInput: true,
		    locale: "pt"
		});
		
		function atualizarDisponibilidade(){
			var selectNomeAgenda = document.querySelector('select[name="compromissoVo.idAgenda"]');
			
			var listaAgendas = [
		        <s:iterator value="listaAgendas" var="agenda">
		            {
		            	id: '<s:property value="#agenda.rowid"/>',
		             	codigoPeriodo: '<s:property value="#agenda.codigoPeriodoDisponivel"/>'
		            },
		        </s:iterator>
		    ];
			
			disponibilidade = listaAgendas.find(function(a) {
				return a.id == selectNomeAgenda.value
			}).codigoPeriodo;

			hora.set('minTime', retornaHorarioDisponivel().de);
			hora.set('maxTime', retornaHorarioDisponivel().para);
			hora.clear();
		}
		
		function retornaHorarioDisponivel(){
			switch(disponibilidade){
				case '1':
					return horarioManha;
				break;
				
				case '2':
					return horarioTarde;
				break;
				
				default:
				case '3':
					return horarioAmbos;
				break;
			}
		}
	</script>

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
</body>
</html>