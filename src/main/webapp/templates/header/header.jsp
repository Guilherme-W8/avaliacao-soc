<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container">
		<s:url action="menu" var="menuUrl" />
		<a class="navbar-brand" href="${menuUrl}">Sistema de Agendamento
		</a>

		<div class="navbar-nav ms-auto">
			<s:url action="todosFuncionarios" var="funcionariosUrl" />
			<a class="nav-link" href="${funcionariosUrl}">Funcionários
			</a>

			<s:url action="todosAgendas" var="agendasUrl" />
			<a class="nav-link" href="${agendasUrl}">Agendas
			</a>

			<s:url action="todosCompromissos" var="compromissosUrl" />
			<a class="nav-link" href="${compromissosUrl}">Compromissos </a>
				
			<s:url action="filtrarRelatorios" var="relatoriosUrl" />
			<a class="nav-link" href="${relatoriosUrl}">Gerar Relatório Compromissos </a>
		</div>
	</div>
</nav>