$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp", loadIngelogdeFunctionaliteiten);
	$(".btn").button();
	$("#tabs").tabs();
});

function loadIngelogdeFunctionaliteiten() {
	$("#tab1").append($("<a>").html("Rapporten").attr("href", "rapporten.html").addClass("btn"));
	$(".btn").button();
}

