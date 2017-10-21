$(document).ready(function() {

	var lista = listarCartorios();

	carregaTabela(lista);

	$('#formulario').validate({
		rules : {
			nome : {
				required : true
			}
		},
		messages : {
			nome : {
				required : "O nome do cartório é obrigatório"
			}
		}
	});
});

function listarCartorios() {
	var listagem;
	$.ajax({
		type : 'GET',
		url : 'cartorios',
		success : function(lista) {
			console.log(lista);
			listagem = lista;
		},
		error : function(error) {
			console.log(error);
		}
	});
	return listagem;
}

function envia(event) {
	event.preventDefault();

	if ($('#formulario').valid()) {
		var cartorio = {}
		cartorio.nome = $('#txt-nome').val();
		cartorio.id = "";
		if ($('#txt-id').val() == '') {
			console.log(cartorio);
			$.ajax({
				type : 'POST',
				url : 'cartorios',
				contentType : 'application/json; charset=utf-8',
				data : JSON.stringify(cartorio),
				success : function(id) {
					$('#txt-id').val(id);
					alert("adicionado com sucesso");
				},
				error : function(error) {
					console.log(error);
				}

			});
		} else {
			console.log(cartorio);
			cartorio.id = $('#txt-id').val()
			$.ajax({
				type : 'PUT',
				url : 'cartorios/' + cartorio.id,
				contentType : 'application/json; charset=utf-8',
				data : JSON.stringify(cartorio),
				success : function(id) {
					$('#txt-id').val(id);
					alert("Alterado com sucesso");
				},
				error : function(error) {
					console.log(error);
				}

			});
		}

	}

}

function excluir(e) {
	var tr = $(e).closest('tr');

	if (confirm("Deseja excluir seguinte cartorio?\n" + "Id: "
			+ $(tr).find("td:eq(0)").text() + "\nNome: "
			+ $(tr).find("td:eq(1)").text())) {

		var cartorio = {
			'id' : $(tr).find("td:eq(0)").text(),
			'nome' : $(tr).find("td:eq(1)").text(),
		}

		$.ajax({
			url : 'cartorios/'+cartorio.id,
			type : 'DELETE',
			success : function(response) {
				alert("Cartorio excluido com sucesso");
			},
			error : function(error) {
				alert("Não foi possivel excluido o cartorio");
				
			}
		});
	}

}

function carregaTabela(lista) {
	$.each(lista, function(index, c) {
		var newRow = $('<tr>');
		var cols = "";

		cols += '<td>' + c.id + '</td>';
		cols += '<td>' + c.nome + '</td>';
		cols += '<td>' + '<input class="btn btn-link"'
				+ 'onclick="alterar(this);"' + 'type="button"'
				+ 'value="Alterar">' + '</td>';
		cols += '<td>' + '<input class="btn btn-danger"'
				+ 'onclick="excluir(this);"' + 'type="button"'
				+ 'value="Excluir">' + '</td>';

		newRow.append(cols);

		$('#tabela-cartorios tbody').append(newRow);
	});
}