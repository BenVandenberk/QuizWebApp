$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp", loadIngelogdeFunctionaliteiten, true);
	$(".btn").button();
	$("#tabs").tabs();
});

function loadIngelogdeFunctionaliteiten(isBeheerder) {
	$("#tab1").append($("<a>").html("Rapporten").attr("href", "rapporten.html").addClass("btn"));
	$(".btn").button();
	
	if(isBeheerder) {
		loadBeheerderFunctionaliteiten();
	}
}

function loadBeheerderFunctionaliteiten() {
	$("#ul_tabs").append($("<li>").append($("<a>").attr("href", "#tab3").html("Beheerder")));
	$("#tabs").append($("<div>").attr("id", "tab3").addClass("tabPanel"));
	$("#tab3").append($("<h1>").html("Beheerder"));
	$("#tab3").append($("<p>").html("Stel to-do-lijsten op voor individuele deelnemers en genereer tailor-made rapporten"));
	$("#tab3").append($("<br>"));
	$("#tab3").append($("<a>").html("To-do").addClass("btn").addClass("right5").attr("href", "/quizWebApp/todo.html"));
	$("#tab3").append($("<a>").html("Rapporten").addClass("btn"));
	
	$(".btn").button();
	$("#tabs").tabs("refresh");
}

