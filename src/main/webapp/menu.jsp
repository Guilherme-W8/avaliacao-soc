<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title>Sistema de Agendamento - Menu Principal</title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
<!-- Font Awesome para os ícones -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-light">
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card shadow">
					<div class="card-header bg-primary text-white text-center">
						<h3>Sistema de Agendamento</h3>
						<p class="mb-0">Escolha uma opção abaixo</p>
					</div>
					<div class="card-body">
						<div class="row">
							<!-- Card Funcionários -->
							<div class="col-md-4 mb-3">
								<div class="card h-100 border-success">
									<div class="card-body text-center">
										<i class="fas fa-users fa-3x text-success mb-3"></i>
										<h5 class="card-title">Funcionários</h5>
										<p class="card-text">Gerenciar funcionários do sistema</p>
										<s:url action="todosFuncionarios" var="funcionarios" />
										<s:a action="todosFuncionarios" cssClass="btn btn-success">Acessar</s:a>
									</div>
								</div>
							</div>

							<!-- Card Agendas -->
							<div class="col-md-4 mb-3">
								<div class="card h-100 border-warning">
									<div class="card-body text-center">
										<i class="fas fa-calendar-alt fa-3x text-warning mb-3"></i>
										<h5 class="card-title">Agendas</h5>
										<p class="card-text">Gerenciar agendas disponíveis</p>
										<s:url action="todosAgendas" var="agendas" />
										<a href="${agendas}" class="btn btn-warning text-white">Acessar</a>
									</div>
								</div>
							</div>

							<!-- Card Compromissos -->
							<div class="col-md-4 mb-3">
								<div class="card h-100 border-danger">
									<div class="card-body text-center">
										<i class="fas fa-clock fa-3x text-danger mb-3"></i>
										<h5 class="card-title">Compromissos</h5>
										<p class="card-text">Gerenciar compromissos agendados</p>
										<s:url action="todosCompromissos" var="compromissos" />
										<a href="${compromissos}" class="btn btn-danger">Acessar</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>