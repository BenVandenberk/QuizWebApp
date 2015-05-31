function loadGebruikerFuncties(plaatsId, redirectTo,
		loadIngelogdeFunctionaliteiten, vanuitHome) {
	$.ajax({
				method : "GET",
				url : "gebruikerInfo",
				dataType : "json",
				success : function(data, statusText, jqXHR) {
					$("#" + plaatsId).html($("<label>").attr("id", "lbl_todo").text("(" + data.todo.length + ")").data("id", data.id));
					$("#" + plaatsId).append("Ingelogd als: " + data.gebruikersNaam);
					$("#" + plaatsId).append('<button style="margin-left: 20px;" id="btn_logout" type="button" class="btn">Logout</button>');
					$("#btn_logout").click(function(e) {
						$.ajax({
							method : "GET",
							url : "logout",
							success : function(data, statusText, jqXHR) {
								window.location = redirectTo;
							}
						});
					});
					$("#lbl_todo").click(function(e) {
						toonToDoDialog($(this).data("id"), data.todo);
					});
					$("#lbl_todo").hover(
							  function () {
							    $(this).addClass('ui-state-hover');							    
							  }, 
							  function () {
							    $(this).removeClass('ui-state-hover');
							  }
							);
					$(".btn").button();
					if (data.isBeheerder) {
						$("#lbl_todo").hide();
					}
					loadIngelogdeFunctionaliteiten(data.isBeheerder);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					if (!vanuitHome) {
						window.location = "/quizWebApp";
					}
				}
			});	

	$("#btn_login").click(function(e) {
		$("#div_login").dialog({
			modal : true,
			minWidth : 650,
			minHeight : 250
		});
	});
	$("#btn_login_enter").click(function(e) {
		var data = $("#frm_login").serialize();
		$.ajax({
			method : "POST",
			url : "login",
			data : data,
			success : function(data, statusText, jqXHR) {
				window.location = redirectTo;
			},
			error : loginMislukt
		});
	});
	$("#btn_registreer").click(function(e) {
		$("#div_registreer").dialog({
			modal : true,
			minWidth : 650,
			close : function(event, ui) {
				window.location = redirectTo;
			}
		});
		$("#inp_dob").datepicker({
			dateFormat : "dd/mm/yy"
		});
		$("#frm_registreer").validate({
			rules : {
				paswoord: 'required',
		        paswoord2: {
		            equalTo: '#paswoord',
		            required : true
		        },
		        gebruikersNaam : 'required'
			},
			messages : {
				gebruikersNaam : "Gebruikersnaam is verplicht",
				paswoord : "Paswoord is verplicht",
				paswoord2 : {
					required : "Herhaal het paswoord",
					equalTo : "De twee paswoorden zijn niet gelijk"
				}
			},
			errorElement : 'li',
			errorLabelContainer : '#ul_error'
		});
	});
	$("#btn_registreer_enter").click(function(e) {
		if ($("#frm_registreer").valid()) {
			var reg_data = $("#frm_registreer").serialize();
			$.ajax({
				method : "POST",
				url : "registreer",
				data : reg_data,
				success : function(data, statusText, jqXHR) {
					$("#div_registreer").html($("<p>").text(jqXHR.responseText));
					$("#div_registreer").append($("<button>").addClass("btn").text("OK").attr("id", "btn_ok"));
					$("#btn_ok").button();
					$("#btn_ok").click(function(e) {
						$("#div_registreer").dialog("close");
					});
					$.ajax({
						method : "POST",
						url : "login",
						data : reg_data
					});
				}
			});
		}
	});
}

function loginMislukt(jqXHR, statusText, errorThrown) {
	$("#lbl_error").html(jqXHR.responseText);
}

function toonToDoDialog(deelnemerID, todo) {
	$("#dial_todo").html($("<h3>").text("Vragenreeksen - To Do").addClass("left"));
	if(todo.length > 0) {
		var ul = $("<ul>").addClass("vragen");
		for (var i = 0; i < todo.length; i++) {
			ul.append($("<li>").text(todo[i].naam).addClass("vraag").data("id", todo[i].id).data("isEnabled", todo[i].isEnabled));
		}
		$("#dial_todo").append(ul);
		
		$("li.vraag").click(function(e) {
			if ($(this).data("isEnabled")) {
				var id = $(this).data("id");
				$.ajax({
					method : "GET",
					data : "vragenReeksId=" + id,
					url : "startVragenReeks",
					success : function(data, textStatus, jqXHR) {
						window.location = "quiz.html";
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert(textStatus);
					}
				});
			}
		});
	} else {
		$("#dial_todo").append($("<p>").text("To-do lijstje is leeg"));
	}
	
	$("li.vraag").each(function() {
		if (!$(this).data("isEnabled")) {
			$(this).addClass("disabled");
		}
	});
	
	$("#dial_todo").dialog({
		buttons : [{
			text : "OK",
			click : function(e) {
				$(this).dialog("close");
			}
		}],
		minWidth : 450,
		minHeight : 275
	});
}