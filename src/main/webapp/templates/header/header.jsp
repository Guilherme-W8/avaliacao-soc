<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<s:url action="menu" var="menuUrl" />
		<a class="navbar-brand" href="${menuUrl}"> <i
			class="fas fa-calendar-check"></i> Sistema de Agendamento
		</a>

		<div class="navbar-nav ms-auto">
			<s:url action="todosFuncionarios" var="funcionariosUrl" />
			<a class="nav-link" href="${funcionariosUrl}"> <i
				class="fas fa-users"></i> Funcionários
			</a>

			<s:url action="todosAgendas" var="agendasUrl" />
			<a class="nav-link" href="${agendasUrl}"> <i
				class="fas fa-calendar-alt"></i> Agendas
			</a>

			<s:url action="todosCompromissos" var="compromissosUrl" />
			<a class="nav-link" href="${compromissosUrl}"> <i
				class="fas fa-clock"></i> Compromissos </a>
		</div>
	</div>
</nav>