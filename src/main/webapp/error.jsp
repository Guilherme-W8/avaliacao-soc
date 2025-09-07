<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title><s:text name="label.titulo.pagina.consulta" /></title>
<link rel='stylesheet'
	href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
</head>
<body class="bg-light">
	<div class="modal fade" id="errorModal" tabindex="-1" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-danger text-white">
					<h5 class="modal-title">Erro</h5>
					<s:form action="%{proximaAction}" method="post" theme="simple">
			          	<s:submit value=" " class="btn-close btn-close-white" aria-label="Fechar" />
			        </s:form>
				</div>
				<div class="modal-body">
					<ul>
						<s:if test="hasActionErrors()">
								<s:actionerror />
						</s:if>
					</ul>
				</div>
				<div class="modal-footer">
					<s:form action="%{proximaAction}" method="post" theme="simple">
			          	<s:submit value="Entendido" class="btn btn-danger" />
			        </s:form>
				</div>
			</div>
		</div>
	</div>
	
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	<s:if test="hasActionErrors()">
		<script>
			document.addEventListener("DOMContentLoaded", function() {
				const modalEl = document.getElementById('errorModal');
				const modal = new bootstrap.Modal(modalEl);
				modal.show();
			});
		</script>
	</s:if>
</body>
</html>