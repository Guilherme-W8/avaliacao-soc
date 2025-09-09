<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title><s:text name="label.titulo.pagina.consulta.compromisso" /></title>
<link rel="stylesheet" href="../templates/header/header.css">
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-secondary">
	<%@ include file="../templates/header/header.jsp"%>
	<div class="container">
		<div class="row mt-5 mb-2">
			<div class="col-sm p-0">
				<s:form action="/filtrarCompromissos.action">
					<div class="input-group">
						<span class="input-group-text"> <strong><s:text
									name="label.buscar.por" /></strong>
						</span>
						<s:select cssClass="form-select" name="filtrar.opcoesCombo"
							list="listaOpcoesCombo" headerKey="" headerValue="Escolha..."
							listKey="%{codigo}" listValueKey="%{descricao}"
							value="filtrar.opcoesCombo.codigo" />

						<s:textfield cssClass="form-control" id="valorBusca"
							cssStyle="display:none" name="filtrar.valorBusca" />
						<button class="btn btn-primary" type="submit">
							<s:text name="label.pesquisar" />
						</button>
					</div>
				</s:form>
			</div>
		</div>

		<div class="row">
			<table class="table table-light table-striped align-middle">
				<thead>
					<tr>
						<th><s:text name="label.id" /></th>
						<th><s:text name="label.funcionario" /></th>
						<th><s:text name="label.agenda" /></th>
						<th><s:text name="label.data" /></th>
						<th><s:text name="label.horario" /></th>
						<th class="text-end mt-5"><s:text name="label.acao" /></th>
					</tr>
				</thead>

				<tbody>
					<s:iterator value="compromissos">
						<tr>
							<td>${rowid}</td>
							<td>${nomeFuncionario}</td>
							<td>${nomeAgenda}</td>
							<td>${data}</td>
							<td>${hora}</td>
							<td class="text-end"><s:url action="inputCompromissos"
									var="editar">
									<s:param name="compromissoVo.rowid" value="rowid"></s:param>
								</s:url> <a href="${editar}" class="btn btn-warning text-white"> <s:text
										name="label.editar" />
							</a> <s:url action="excluirCompromissos" var="excluir">
									<s:param name="compromissoVo.rowid" value="rowid" />
								</s:url> <a href="#" class="btn btn-danger" data-bs-toggle="modal"
								data-bs-target="#confirmarExclusao" data-href="${excluir}">
									<s:text name="label.excluir" />
							</a></td>
						</tr>
					</s:iterator>
				</tbody>

				<tfoot class="table-secondary">
					<tr>
						<td colspan="6"><s:url action="inputCompromissos" var="novo" />

							<a href="${novo}" class="btn btn-success"> <s:text
									name="label.novo" />
						</a></td>
					</tr>
				</tfoot>
			</table>
		</div>

		<div class="row"></div>
	</div>

	<div class="modal fade" id="confirmarExclusao"
		data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<s:text name="label.modal.titulo" />
					</h5>

					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>

				<div class="modal-body">
					<span><s:text name="label.modal.corpo" /></span>
				</div>

				<div class="modal-footer">
					<a class="btn btn-secondary" data-bs-dismiss="modal"
						aria-label="Close"> <s:text name="label.nao" />
					</a>

					<s:a href="" id="excluir" class="btn btn-primary"
						style="width: 75px;">
						<s:text name="label.sim" />
					</s:a>
				</div>
			</div>
		</div>
	</div>

	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>

	<script>
		var confirmarExclusao = document.getElementById('confirmarExclusao');
		confirmarExclusao.addEventListener('show.bs.modal', function(event) {
			var button = event.relatedTarget;
			var href = button.getAttribute('data-href');
			var excluirBtn = document.getElementById('excluir');
			excluirBtn.setAttribute('href', href);
		});

		document.addEventListener('DOMContentLoaded', function() {
			var selectFiltro = document
					.querySelector('select[name="filtrar.opcoesCombo"]');
			var inputBusca = document.getElementById('valorBusca');

			function atualizarTipoCampo() {
				var opcaoSelecionada = selectFiltro.value;

				inputBusca.type = 'text';
				inputBusca.placeholder = '';
				inputBusca.removeAttribute('pattern');
				inputBusca.removeAttribute('title');
				inputBusca.removeAttribute('min');

				switch (opcaoSelecionada) {
				case '1': 
					inputBusca.type = 'number';
					inputBusca.placeholder = 'Digite o ID...';
					inputBusca.setAttribute('min', '1');
					inputBusca.setAttribute('style', 'display: block');
					break;
				case '2': 
					inputBusca.type = 'text';
					inputBusca.placeholder = 'Digite o nome do funcionário...';
					inputBusca.setAttribute('style', 'display: block');
					break;
				case '3': 
					inputBusca.type = 'text';
					inputBusca.placeholder = 'Digite o nome da agenda...';
					inputBusca.setAttribute('style', 'display: block');
					break;
				case '4': 
					inputBusca.type = 'date';
					inputBusca.placeholder = '';
					inputBusca.setAttribute('style', 'display: block');
					break;
				default:
					inputBusca.setAttribute('style', 'display: none');
					break;
				}
			}

			selectFiltro.addEventListener('change', atualizarTipoCampo);
			atualizarTipoCampo(); 
		});
	</script>
</body>
</html>