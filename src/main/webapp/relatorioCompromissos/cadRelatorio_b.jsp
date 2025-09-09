<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title><s:text name="label.titulo.pagina.relatorio" /></title>
</head>
<body>

	<table style="width: 100%;">
		<thead>
			<tr>
				<th style="text-align: left;">Funcionário</th>
				<th style="text-align: left;">Agenda</th>
				<th style="text-align: left;">Data/Hora Compromisso</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="compromissosList">
				<tr>
					<td>${idFuncionario} - ${nomeFuncionario}</td>
					<td>${idAgenda} - ${nomeAgenda}</td>
					<td>${data} - ${hora}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

</body>
</html>