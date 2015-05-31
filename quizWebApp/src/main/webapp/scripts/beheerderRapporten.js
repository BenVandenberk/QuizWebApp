var deelnames = new Array();
var groepeerPerQuiz = false;
var aantalDeelnamesDeelnemer = {};
var aantalDeelnamesVragenReeks = {};

$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp",
			loadIngelogdeFunctionaliteitenBehRap, false);
	
	$("#menu").menu({
		position: { my: "left top", at: "left bottom" },
		icons : {submenu : "ui-icon-triangle-1-s"}
	});
	
	// MenuKnopHandlers
	
	$("#men_new").click(function(e) {
		var data = {requestType : "new"};
		$.ajax({
			method : "GET",
			url : "beheerderRapporten",
			data : data,
			success : function(data, statusText, jqXHR) {
				location.reload();
			}
		});		
	});
	
	$("#men_save").click(function(e) {
		$("#dial_save").dialog({
			modal : true
		});
		$(".btn").button();
		$("#btn_save").click(function(e) {
			var data = {requestType : "save", groepeerPerQuiz : groepeerPerQuiz, naam : $("#txt_naamRap").val(), deelnames : JSON.stringify(deelnames, ["id", "toonDetail"])};
			$.ajax({
				method : "GET",
				url : "beheerderRapporten",
				data : data,
				dataType : "json",
				success : function(data, statusText, jqXHR) {					
					$("#p_save").html(data.message);
					$("#txt_naamRap").hide();
					$("#btn_save").attr("value", "OK");
					$("#btn_save").click(function(e) {
						$("#dial_save").dialog("close");
					});
				}
			});
		});
	});
	
	$("#men_load").click(function(e) {
		var data = {requestType : "kort"};
		$.ajax({
			method : "GET",
			url : "beheerderRapporten",
			data : data,
			dataType : "json",
			success : function (data, statusText, jqXHR) {				
				$("#dial_load").empty(); 
				var ul = $("<ul>").addClass("load");
				 var li;
				 for (var i = 0; i < data.rapporten.length; i++) {
					 li = $("<li>").text(data.rapporten[i].naam + " - " + data.rapporten[i].tijdstip).data("id", data.rapporten[i].id).addClass("load");
					 ul.append(li);
				 }
				 $("#dial_load").append(ul);
				 $("li.load").click(function(e) {
					var data = {requestType : "load", id : $(this).data("id")};
					$.ajax({
						method : "GET",
						url : "beheerderRapporten",
						data : data,
						dataType : "json",
						success : function(data, statusText, jqXHR) {	
							reset();
							for (var i = 0; i < data.deelnames.length; i++) {				
								$(".deel" + data.deelnames[i].id).hide();
							}
							groepeerPerQuiz = data.groepeerPerQuiz;
							deelnames = deelnames.concat(data.deelnames);
							refresh();
							$("#dial_load").dialog("close");
						}
					});
				 });
				 $("#dial_load").dialog({
					 minWidth : 400
				 });
			}
		});
	});
	
	$("#groep_quiz").click(function(e) {
		if (!groepeerPerQuiz) {
			groepeerPerQuiz = true;
			refresh();
		}
	});
	
	$("#groep_deel").click(function(e) {
		if (groepeerPerQuiz) {
			groepeerPerQuiz = false;
			refresh();
		}
	});
	
	var data = {requestType : "start"};
	$.ajax({
		method : "GET",
		url : "beheerderRapporten",
		data : data,
		dataType: "json",
		success : toonAlleDeelnemersEnVragenReeksen
	});
});

// LOAD DE TWEE VENSTERTJES LINKS
function toonAlleDeelnemersEnVragenReeksen(data, statusText, jqXHR) {
	var deelnemers = data.deelnemers;
	var vragenReeksen = data.vragenReeksen;
	
	// Deelnemers
	
	var ul = $("<ul>").addClass("rapUl");
	var li;
	var innerUl;
	var deelname;
	for (var i = 0; i < deelnemers.length; i++) {	
		aantalDeelnamesDeelnemer[deelnemers[i].id] = 0;
		
		li = $("<li>").addClass("deeln" + deelnemers[i].id);
		li.append($("<span>").text(deelnemers[i].naam).addClass("rapDraggable").data("id", deelnemers[i].id).data("type", "deelnemer"));
		innerUl = $("<ul>").addClass("rapUl");
		for (var j = 0; j < deelnemers[i].deelnames.length; j++) {
			aantalDeelnamesDeelnemer[deelnemers[i].id]++;
			deelname = deelnemers[i].deelnames[j];
			innerUl.append($("<li>").text(deelname.vragenReeksNaam + " - " + deelname.tijdstip + " - " + deelname.score + "/" + deelname.aantalVragen).addClass("rapDraggable").data("id", deelname.id).data("type", "deelname").addClass("deel" + deelname.id));
		}
		li.append(innerUl);
		ul.append(li);
	}
	$("#rapport_deelnemers").append(ul);
	
	// Vragenreeksen
	
	var ul = $("<ul>").addClass("rapUl");
	var li;
	var innerUl;
	var deelname;
	for (var i = 0; i < vragenReeksen.length; i++) {
		aantalDeelnamesVragenReeks[vragenReeksen[i].id] = 0;
		
		li = $("<li>").addClass("quiz" + vragenReeksen[i].id);
		li.append($("<span>").text(vragenReeksen[i].naam).addClass("rapDraggable").data("id", vragenReeksen[i].id).data("type", "quiz"));
		innerUl = $("<ul>").addClass("rapUl");
		for (var j = 0; j < vragenReeksen[i].deelnames.length; j++) {
			aantalDeelnamesVragenReeks[vragenReeksen[i].id]++;
			deelname = vragenReeksen[i].deelnames[j];
			innerUl.append($("<li>").text(deelname.deelnemerNaam + " - " + deelname.tijdstip + " - " + deelname.score + "/" + deelname.aantalVragen).addClass("rapDraggable").data("id", deelname.id).data("type", "deelname").addClass("deel" + deelname.id));
		}
		li.append(innerUl);
		ul.append(li);
	}
	$("#rapport_vragenreeksen").append(ul);
	
	addDragAndDropFunctionaliteit();
}

function addDragAndDropFunctionaliteit() {
	
	//DND
	$(".rapDraggable").draggable({
		  helper: "clone",
		  cursor : "pointer"
	});
	
	$("#div_rapport").droppable({
		drop : function(event, ui) {
			var id = ui.draggable.data("id");
			var type = ui.draggable.data("type");
			loadDeelnameDetails(id, type);
		}
	})
	
	
	// LAYOUT
	$(".rapDraggable").hover(
			  function () {
			    $(this).addClass('rapHover');
			    $(this).css("cursor", "pointer");
			  }, 
			  function () {
			    $(this).removeClass('rapHover');
			  }
			);	
}

// VULT GLOBALE VARIABELE 'DEELNAMES' AAN MET GEDROPTE QUIZ/DEELNEMER/DEELNAME
function loadDeelnameDetails(id, type) {
	var data = {requestType : type, id : id};
	$.ajax({
		method : "GET",
		url : "beheerderRapporten",
		dataType : "json",
		data : data,
		success : function(data, textStatus, jqXHR) {
			// HIDE ALLE DEELNAMES (LINKS) DIE GETOOND ZULLEN WORDEN
			for (var i = 0; i < data.deelnames.length; i++) {				
				$(".deel" + data.deelnames[i].id).hide();
				data.deelnames[i]["toonDetail"] = false;
				if (!rapportBevatDeelname(data.deelnames[i].id)) {
					deelnames.push(data.deelnames[i]);
				}
			}			
			refresh();
		}
	});
}

// BOUWT RAPPORT OP BASIS VAN GLOBALE VARIABELE 'DEELNAMES'
function refresh() {
	$("#div_rapport").empty();
	$("#div_rapport").append($("<h1>").text("Rapport").addClass("center"));
	
	if (groepeerPerQuiz) {
		deelnames.sort(function(a, b) {return a.vragenReeksNaam.localeCompare(b.vragenReeksNaam)});
	} else {
		deelnames.sort(function(a, b) {return a.deelnemerNaam.localeCompare(b.deelnemerNaam)});
	}
	
	var huidigeDeelnemer;
	var huidigeVragenReeks;
	for (var i = 0; i < deelnames.length; ) {
		
		if (groepeerPerQuiz) {
			huidigeVragenReeks = deelnames[i].vragenReeksNaam;
			$("#div_rapport").append($("<h2>").text(huidigeVragenReeks).addClass("center"));
			while (i < deelnames.length && deelnames[i].vragenReeksNaam == huidigeVragenReeks) {				
				$("#div_rapport").append(maakDeelname(deelnames[i]));			
				$("#div_rapport").append(maakDeelnameDetail(deelnames[i]));			
				i++;
			}
		} else {
			huidigeDeelnemer = deelnames[i].deelnemerNaam;
			$("#div_rapport").append($("<h2>").text(huidigeDeelnemer).addClass("center"));
			while (i < deelnames.length && deelnames[i].deelnemerNaam == huidigeDeelnemer) {				
				$("#div_rapport").append(maakDeelname(deelnames[i]));			
				$("#div_rapport").append(maakDeelnameDetail(deelnames[i]));			
				i++;
			}
		}
		
	}
	
	// VragenReeksDetails hidden of niet
	for (var i = 0; i < deelnames.length; i++) {
		if (!deelnames[i]["toonDetail"]) {
			$("#det" + deelnames[i].id).hide();
		}
	}	
	
	hideShowDeelnemersQuizzen();
	
	$(".tab_deeln").hover(
			  function () {
				$(this).removeClass('ui-state-active');  
			    $(this).addClass('rapHoverBold');
			    $(this).css("cursor", "pointer");
			  }, 
			  function () {
			    $(this).removeClass('rapHoverBold');
			    $(this).addClass('ui-state-active');
			  }
			);	
	
	$(".verwijderKnop").hover(
			function() {
				$(this).removeClass("verwijderKnopOut");
				$(this).addClass("verwijderKnopHover");
			},
			function() {
				$(this).removeClass("verwijderKnopHover");
				$(this).addClass("verwijderKnopOut");
			}
		);
	
	// VERWIJDER DEELNAME UIT QUIZ
	$(".verwijderKnop").click(function(e) {
		var id = $(this).data("id");		
		
		// VERWIJDEREN UIT 'DEELNAMES'
		for (var i = 0; i < deelnames.length; i++) {
			if (deelnames[i].id == id) {
				deelnames.splice(i, 1);
				break;
			}
		}
		
		// TERUG TONEN IN MENUUTJES LINKS
		$(".deel" + id).show();
		
		refresh();		
	});
	
	// TOON / VERBERG DEELNAMEDETAILS
	$(".tab_deeln").click(function(e) {
		var geklikt = $("#det" + $(this).data("id"));
		var deelname;
		for (var i = 0; i < deelnames.length; i++) {
			if (deelnames[i].id == $(this).data("id")) {
				deelname = deelnames[i];
				break;
			}
		}
		if (geklikt.is(":visible")) {
			geklikt.hide();
			deelname["toonDetail"] = false;
		} else {
			geklikt.show();
			deelname["toonDetail"] = true;
		}
	});
}

function maakDeelname(deelname) {
	var table = $("<table>").addClass("spanTable").addClass("tab_deeln").addClass("ui-state-active").data("id", deelname.id);
	var tr = $("<tr>");			
	if (groepeerPerQuiz) {
		tr.append($("<td>").text("Deelnemer: " + deelname.deelnemerNaam));
	} else {
		tr.append($("<td>").text("Vragenreeks: " + deelname.vragenReeksNaam));
	}
	tr.append($("<td>").text(deelname.score + "/" + deelname.aantalVragen).attr("rowspan", "2").addClass("big"));
	tr.append($("<td>").text("-").attr("rowspan", "2").addClass("verwijderKnop").addClass("verwijderKnopOut").data("id", deelname.id));
	table.append(tr);
	table.append($("<tr>").append(
			$("<td>").text("Tijdstip: " + deelname.tijdstip)));	
	return table;
}

function maakDeelnameDetail(deelname) {
	var details = deelname.antwoorden;
	var table = $("<table>").addClass("tab_details").addClass("spanTable").attr("id", "det" + deelname.id);
	var tr;
	
	// HEADERS
	
	tr = $("<tr>");
	tr.append($("<th>").text("Vraag").addClass("left"));
	tr.append($("<th>").text("Gegeven Antwoord").addClass("left"));
	tr.append($("<th>").text("Verbetering").addClass("left"));
	table.append(tr);
	
	// BODY
	
	var imgSrc;
	for (var i = 0; i < details.length; i++) {
		imgSrc = details[i].isJuist ? "images/JuistGB.png" : "images/FoutGB.png";
		tr = $("<tr>");
		tr.append($("<td>").html(details[i].vraag));
		tr.append($("<td>").html(details[i].antwoord));
		tr.append($("<td>").append($("<img>").attr("src", imgSrc).addClass("verbetering")));
		table.append(tr);
	}
	return table;
}

// HIDE EEN DEELNEMER OF QUIZ (LINKS) ALS ALLE DEELNAMES ERVAN AL IN HET RAPPORT STAAN
function hideShowDeelnemersQuizzen() {
	var deelnamesPerDeelnemer = {};
	var deelnamesPerVragenReeks = {};
	
	// TEL AANTAL GETOONDE DEELNAMES PER DEELNEMER/QUIZ
	for (var i = 0; i < deelnames.length; i++) {
		if (deelnames[i].deelnemerId in deelnamesPerDeelnemer) {
			deelnamesPerDeelnemer[deelnames[i].deelnemerId]++;
		} else {
			deelnamesPerDeelnemer[deelnames[i].deelnemerId] = 1;
		}
		
		if (deelnames[i].vragenReeksId in deelnamesPerVragenReeks) {
			deelnamesPerVragenReeks[deelnames[i].vragenReeksId]++;
		} else {
			deelnamesPerVragenReeks[deelnames[i].vragenReeksId] = 1;
		}
	}
	
	// VERGELIJK MET BEGINSITUATIE EN HIDE/SHOW
	var deelnemerIDs = Object.keys(aantalDeelnamesDeelnemer);
	for (var i = 0; i < deelnemerIDs.length; i++) {
		if (deelnamesPerDeelnemer[deelnemerIDs[i]] >= aantalDeelnamesDeelnemer[deelnemerIDs[i]]) {			
			$("li.deeln" + deelnemerIDs[i]).hide();
		} else {
			$("li.deeln" + deelnemerIDs[i]).show();
		}
	}
	
	var vragenReeksIDs = Object.keys(aantalDeelnamesVragenReeks);
	for (var i = 0; i < vragenReeksIDs.length; i++) {
		if (deelnamesPerVragenReeks[vragenReeksIDs[i]] >= aantalDeelnamesVragenReeks[vragenReeksIDs[i]]) {
			$("li.quiz" + vragenReeksIDs[i]).hide();
		} else {
			$("li.quiz" + vragenReeksIDs[i]).show();
		}
	}
}

function reset() {
	deelnames = new Array();
	
	$("li").each(function() {
		$(this).show();
	});
}

function rapportBevatDeelname(id) {
	var bevat = false;
	for (var i = 0; !bevat && i < deelnames.length; i++) {
		if (deelnames[i].id == id) {
			bevat = true;
		}
	}
	return bevat;
}

function loadIngelogdeFunctionaliteitenBehRap(isBeheerder) {
	if (!isBeheerder) {
		$("#div_accDenied").dialog({
			modal : true,
			buttons : [{
				text : "OK",
				click : function(e) {
					$(this).dialog("close");
				}
			}],
			close : function(event, ui) {
				window.location = "/quizWebApp";
			},
			minWidth : 400
		});
	}
}