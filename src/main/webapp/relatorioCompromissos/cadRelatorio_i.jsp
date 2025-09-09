<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title><s:text name="label.titulo.pagina.relatorio" /></title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
</head>
<body class="bg-secondary">
	<%@ include file="../templates/header/header.jsp"%>
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
	<div class="container">
		<s:form action="/gerarRelatoriosExcel.action" method="POST" id="gerar-relatorio">
			<div class="card mt-3 row col-12 col-md-6 mx-auto">
				<div class="card-header bg-white d-flex align-items-center justify-content-between">
					<h5 class="card-title">Filtrar Relatório Compromissos</h5>
					<div class="mx-2" id="lado-direito">
						<button class="btn btn-sm btn-outline-dark" name="tipoImpressao" onclick="abrirNovaJanela(); return false;" type="button">Imprimir HTML</button>
						<button class="btn btn-sm btn-success" name="tipoImpressao" onclick="gerarRelatorio(); return false;" type="button">Imprimir XLS/XLSX</button>		
					</div>
				</div>
				
				<div class="card-body">
					<div class="row form-group px-3">	
						<div class="col-6 p-2">
							<label for="dataInicial">Data Inicial</label>
							<input class="form-control" placeholder="Selecione..." id="dataInicial" name="filter.dataInicial" required />
						</div>
						
						<div class="col-6 p-2">
							<label for="dataFinal">Data Final</label>
							<input class="form-control" placeholder="Selecione..." id="dataFinal" name="filter.dataFinal" type="date" required />
						</div>
					</div>
				</div>
			</div>
		</s:form>
	</div>
	
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/pt.js"></script>
	
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			flatpickr("#dataInicial", {
				altFormat : "d/m/Y",
				dateFormat: 'Y-m-d',
				altInput : true,
				allowInput : true,
				locale : "pt"
			});
			
			flatpickr("#dataFinal", {
				altFormat : "d/m/Y",
				dateFormat: 'Y-m-d',
				altInput : true,
				allowInput : true,
				locale : "pt"
			});
		});
		
		function abrirNovaJanela() {
			const formData = document.getElementById('gerar-relatorio');
			const dataInicial = formData.dataInicial.value;
			const dataFinal = formData.dataFinal.value;
			const tipoImpressao = 'html';
			
			if (dataInicial != '' && dataInicial != null) {
				const url = 'gerarHTMLRelatorios.action?filter.dataInicial='+dataInicial+'&filter.dataFinal='+dataFinal+'&filter.tipoImpressao='+tipoImpressao;
			    window.open(
			      url,
			      'Impressao Compromissos',
			      'width=800,height=600,scrollbars=yes,resizable=yes'
			    );
			} else {
				mostrarToastSelecioneADataInicial()
			}
		}
		
		function gerarRelatorio() {
			const form = document.getElementById("gerar-relatorio");
			if (form.dataInicial.value == '' || form.dataInicial.value == null) {
				mostrarToastSelecioneADataInicial();
			} else {
		    	tipoInput = document.createElement("input");
		    	tipoInput.type = "hidden";
		    	tipoInput.name = "filter.tipoImpressao";
		    	
		    	form.appendChild(tipoInput);
			  	tipoInput.value = 'xlsx';
				form.submit();
			}
		}
		
		function mostrarToastSelecioneADataInicial() {
			let errorToast = document.getElementById('errorToast')
			let body = document.querySelector('.toast-body');
			body.textContent = "Selecione uma data inicial para o filtro";
			var toast = new bootstrap.Toast(errorToast, {
				delay : 0,
				autohide : false
			});
			toast.show();
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