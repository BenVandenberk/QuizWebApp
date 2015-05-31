$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp/deelnemen.html", loadIngelogdeFunctionaliteitenDeeln, true);
	$(".btn").button();		
	
	$.ajax({
		method : "GET",
		url : "vragenreeksen",
		dataType : "json",
		success : toonVragenReeksen
	});
});

function toonVragenReeksen(data, textStatus, jqXHR) {
	var themas = data.themas;
	var vragenReeksen = data.vragenreeksen;
	
	for(var i = 0; i < themas.length; i++) {
		$("#accordion_thema").append($("<h2>" + themas[i].omschrijving + "</h2>"));
		$("#accordion_thema").append($("<div>").addClass("vragen").append($("<ul>").addClass(themas[i].omschrijving).addClass("vragen")));		
		
		for (var j = 0; j < vragenReeksen.length; j++) {
			if (themas[i].omschrijving === vragenReeksen[j].thema) {
				$("ul." + themas[i].omschrijving)
				.append($("<li>").addClass("vraag").attr("id", vragenReeksen[j].id).html(vragenReeksen[j].naam).data("isEnabled", vragenReeksen[j].isEnabled));
			}
		}			
	}
	
	$("li.vraag").each(function() {
		if (!$(this).data("isEnabled")) {
			$(this).addClass("disabled");
		}
	});
	
	$("#accordion_thema").accordion({
		collapsible : true,
		active : false
	});	
	
	$("div .vragen").removeClass("ui-widget-content");
	
	$("li.vraag").click(function(e) {
		if ($(this).data("isEnabled")) {
			$.ajax({
				method : "GET",
				data : "vragenReeksId=" + $(this).attr("id"),
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
}

function loadIngelogdeFunctionaliteitenDeeln(isBeheerder) {
	
}


