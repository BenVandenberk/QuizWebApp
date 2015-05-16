$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp/deelnemen.html", loadIngelogdeFunctionaliteitenDeeln);
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
		$("#accordion_thema").append($("<div>").append($("<ul>").addClass(themas[i].omschrijving).addClass("vragen")));
		
		for (var j = 0; j < vragenReeksen.length; j++) {
			if (themas[i].omschrijving === vragenReeksen[j].thema.omschrijving) {
				$("ul." + themas[i].omschrijving).append($("<li>").addClass("vraag").attr("id", vragenReeksen[j].vragenReeksId).html(vragenReeksen[j].naam)); 
			}
		}			
	}
	
	$("#accordion_thema").accordion({
		collapsible : true,
		active : false
	});	
	
	$("li.vraag").click(function(e) {
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
	});		
}

function loadIngelogdeFunctionaliteitenDeeln() {
	
}


