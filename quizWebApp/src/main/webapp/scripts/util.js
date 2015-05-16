function loadGebruikerFuncties(plaatsId, redirectTo, loadIngelogdeFunctionaliteiten) {
	$.ajax({
		method : "GET",
		url : "gebruikerInfo",
		dataType: "json",
		success : function(data, statusText, jqXHR) {
			$("#" + plaatsId).html("Ingelogd als: " + data.gebruikersNaam);	
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
			$(".btn").button();
			loadIngelogdeFunctionaliteiten();
		}
	});
	$("#btn_login").click(function(e) {
		$("#div_login").dialog({
			modal : true,
			minWidth : 450,
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
			error: loginMislukt
		});
	});
	$("#btn_registreer").click(function(e) {
		$("#div_registreer").dialog({
			modal: true,
			minWidth : 450,
			close: function(event, ui) {
				window.location = redirectTo;
			}
		});
	});
	$("#btn_registreer_enter").click(function(e) {
		var reg_data = $("#frm_registreer").serialize();
		$.ajax({
			method : "POST",
			url : "registreer",
			data: reg_data,
			success: function(data, statusText, jqXHR) {				
				$("#div_registreer").html(jqXHR.responseText);	
				$.ajax({
					method : "POST",
					url : "login",
					data : reg_data
				});
			}
		});
	});
}

function loginMislukt(jqXHR, statusText, errorThrown) {
	$("#lbl_error").html(jqXHR.responseText);
}