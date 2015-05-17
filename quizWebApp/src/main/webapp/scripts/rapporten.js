var geladenDeelnames = new Array();
var deelnameID;

$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp", loadIngelogdeFunctionaliteitenRapp, false);
	
	$.ajax({
		method : "GET",
		url : "rapporten",
		data: "id=-1",
		dataType : "json",
		success : toonDeelnames
	});
	
	$(".btn").button();
	
	deelnameID = location.search.split('id=')[1] ? location.search.split('id=')[1] : -1;	
});

function deelnameAlGeladen(deelnameID) {
	var result = false;
	for (var i = 0; i < geladenDeelnames.length; i++) {
		if (geladenDeelnames[i] === deelnameID) {
			result = true;
		}
	}
	return result;
}

function toonDeelnames(data, textStatus, jqXHR) {
	var deelnames = data.deelnames;
	
	for (var i = 0; i < deelnames.length; i++) {
		$("#accordion_rapporten").append($("<h2>").attr("data-deelname-id", deelnames[i].id).text(deelnames[i].thema + " - " + deelnames[i].quizNaam + " - " + deelnames[i].tijdstip));
		$("#accordion_rapporten").append($("<div>").addClass("rapportUit").attr("data-deelname-id", deelnames[i].id));
	}	
	$("#accordion_rapporten").accordion({
		collapsible : true,
		heightStyle : "content",
		active : false,
		beforeActivate : panelClick		
	});	
	$("div .rapportUit").removeClass("ui-widget-content");
	
	if (deelnameID >= 0) {	
		$("h2[data-deelname-id~='" + deelnameID + "']").bind("click", function() {			  
	          setTimeout(function() {
	        	  var div = $("div[data-deelname-id~='" + deelnameID + "']");
	        	  var offsetTop = div.offset().top + div.height();	        	  
	        	  window.scroll(0, offsetTop);
	          }, 500);			
		});
		$("h2[data-deelname-id~='" + deelnameID + "']").click();		
	}
}

function panelClick(event, ui) {
	if (ui.newHeader.length > 0) { // CHECK OF HET PANEEL GEOPEND WORDT
		var deelnameID = ui.newHeader.data("deelname-id");
		if (!deelnameAlGeladen(deelnameID)) {
			var data = "id=" + deelnameID;
			geladenDeelnames.push(deelnameID);
			$.ajax({
				method : "GET",
				url : "rapporten",
				dataType: "json",
				data : data,
				success : function(data, textStatus, jqXHR) {					
					toonDeelnameDetail(data, textStatus, jqXHR, ui.newPanel);					
				}
			});
		}
	}
}

function toonDeelnameDetail(data, textStatus, jqXHR, activePanel) {
	activePanel.empty();
	activePanel.append($("<h3>").html("Score: " + data.score + "/" + data.aantalVragen));
	
	var table = $("<table style='width:100%'>").addClass("rapportUit");
	var headers = ["Vraag", "Gegeven antwoord", "Juiste antwoord", "Verbetering"];
	activePanel.append(table);
	var tr = $("<tr>");
	table.append(tr);
	
	for (var i = 0; i < headers.length; i++) {
		tr.append($("<th>").html(headers[i]));
	}
	
	var antwoorden = data.antwoorden;
	var imgSrc;
	for (var i = 0; i < antwoorden.length; i++) {
		imgSrc = antwoorden[i].isJuist ? "images/JuistGB.png" : "images/FoutGB.png";
		table.append($("<tr>")
		.append($("<td>").html(antwoorden[i].vraag).addClass("left").addClass("rapportUit"))
		.append($("<td>").html(antwoorden[i].antwoord).addClass("rapportUit"))
		.append($("<td>").html(antwoorden[i].juisteAntwoord).addClass("rapportUit"))
		.append($("<td>").addClass("rapportUit").append($("<img>").attr("src", imgSrc).addClass("verbetering"))));
	}

}

function loadIngelogdeFunctionaliteitenRapp() {
	
}