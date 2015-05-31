var type;
var slideWaarde = 0;
var numMetInterval = false;
var dndAntwoorden = {};

$(function() {
	$.ajax({
		method : "GET",
		url : "gebruikerInfo",
		dataType : "json",
		success : toonGebruiker,
		error : anoniemeGebruiker
	});

	$.ajax({
		method : "GET",
		url : "volgendeVraag",
		dataType : "json",
		success : antwoordKlik,
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.responseText);
		}
	});

	$("#btn_antwoord").click(function(e) {
		if (isValidAntwoord()) {
			var data = "antwoord=" + getAntwoord();			
			$.ajax({
				method : "GET",
				url : "volgendeVraag",
				data : data,
				dataType : "json",
				success : antwoordKlik
			});
		}
	});

	$(".btn").button();
});

function toonGebruiker(data, textStatus, jqXHR) {
	$("#td_gebruiker").html("Ingelogd als: " + data.gebruikersNaam);
}

function anoniemeGebruiker(jqXHR, textStatus, errorThrown) {
	if (jqXHR.status === 501) {
		$("#td_gebruiker").html("Anonieme gebruiker");
	}
}

function antwoordKlik(data, textStatus, jqXHR) {
	if (data.afgelopen) {
		if (data.uitgebreidRapport) {
			window.location = "/quizWebApp/rapporten.html?id=" + data.deelnameID;
		} else {
			document.title = data.titel;
			toonBeperktRapport(data);
		}
	} else {
		document.title = data.titel;
		toonVraag(data);
	}
}

function toonVraag(vraag) {	
	type = vraag.vraagType.type;		

	toonGemeenschappelijkDeel(vraag);
	switch (type) {
		case "JaNee" :
			toonJaNeeVraag(vraag);
			break;
		case "MC" :
			toonMultipleChoiceVraag(vraag);
			break;
		case "Numeriek" :
			vraag.isExactAntwoord
					? toonExacteNumeriekeVraag(vraag)
					: toonIntervalNumeriekeVraag(vraag);
			break;
		case "DND" :
			toonDnDVraag(vraag);
			break;
	}
}

function toonGemeenschappelijkDeel(vraag) {
	$("#vraag").html($("<p>").text(vraag.huidigeVraagIndex + "/" + vraag.aantalVragen).addClass("right"));
	$("#vraag").append($("<p>").html(vraag.vraag)).append($("<br>"));
	
	var verbeteringen = vraag.verbetering;
	var imgSrc;
	$("#tr_verbetering").empty();
	for (var i = 0; i < vraag.huidigeVraagIndex - 1; i++) {
		imgSrc = verbeteringen[i] ? "images/JuistGB.png" : "images/FoutGB.png";
		$("#tr_verbetering").prepend($("<td>").append($("<img>").attr("src", imgSrc).addClass("verbetering")));
	}
}

function toonJaNeeVraag(vraag) {
	$("#vraag")
			.append(
					"<input type='radio' id='rd_ja' value='Ja' class='ant' name='antwoorden' /><label for='rd_ja'>Ja</label>");
	$("#vraag")
			.append(
					"<input type='radio' id='rd_nee' value='Nee' class='ant' name='antwoorden' /><label for='rd_nee'>Nee</label>");
	$(".ant").button();
}

function toonMultipleChoiceVraag(vraag) {
	for (var i = 0; i < vraag.keuzes.length; i++) {
		var type = vraag.uniekAntwoord ? "radio" : "checkbox";		
		$("#vraag").append($("<input>").attr("type", type).attr("name", "antwoorden").attr("value", escape(vraag.keuzes[i])).attr("id", i).addClass("ant"));
		$("#vraag").append($("<label>").attr("for", i).html(vraag.keuzes[i]).addClass("mc"));
		$("#vraag").append($("<br>"));
	}
	$(".ant").button();
}

function toonDnDVraag(vraag) {
	var table = $("<table>").addClass("teSlepen").addClass("fixed");
	for (var i = 0; i < vraag.teSlepen.length; i++) {		
		var td = $("<td>").append($("<img>").attr("src", vraag.imagePaths[vraag.teSlepen[i]]).attr("data-id", vraag.teSlepen[i]).addClass("teSlep").addClass("out"));
		var row = $("<tr>").html(td);
		table.append(row);
	}
	$("#vraag").append(table);
	
	table = $("<table>").addClass("antwoordVelden").addClass("fixed");
	for (var i = 0; i < vraag.antwoordVelden.length; i++) {
		var td = $("<td>").append($("<img>").attr("src", vraag.imagePaths[vraag.antwoordVelden[i]]).attr("data-id", vraag.antwoordVelden[i]).addClass("ant").addClass("out"));
		var row = $("<tr>").html(td);
		table.append(row);
	}
	$("#vraag").append(table);
	
	$("img.teSlep").draggable();
	$("img.ant").droppable({
	      drop: function( event, ui ) {
	          $(this)
	            .addClass("ui-state-highlight");
	          ui.draggable.removeClass("out");
	          ui.draggable.addClass("gedropt");
	          dndAntwoorden[ui.draggable.attr("data-id")] = $(this).attr("data-id");
	      },
	      out: function( event, ui ) {
	    	  $(this).removeClass("ui-state-highlight");
	    	  ui.draggable.removeClass("gedropt");
	    	  ui.draggable.addClass("out");
	    	  delete dndAntwoorden[ui.draggable.attr("data-id")];    	  
	      }
	});
}

function toonExacteNumeriekeVraag(vraag) {
	numMetInterval = false;
	
	$("#vraag").append("<input type='text' class='ant' name='num_ant'/>");
	$(".ant").wrap("<form id='frm_num'></form>");
	$("#vraag").append("<div id='error' class='top10'></div>");

	$("#frm_num").validate({
		rules : {
			num_ant : {
				required : true,
				range : [0, 10000000]
			}
		},
		messages : {
			num_ant : {
				required : "Vul een numeriek antwoord in",
				range : "Vul een numeriek antwoord in"
			}
		},
		errorElement : 'div',
		errorLabelContainer : '#error'
	});
}

function toonIntervalNumeriekeVraag(vraag) {
	numMetInterval = true;	

	$("#vraag").append("<div id='num_slider'></div>");
	$("#vraag").append("<br />");
	$("#vraag").append("<label id='lbl_waarde'></label>");
	
	var min = vraag.onderGrensKeuze;
	var max = vraag.bovenGrensKeuze;
	$("#num_slider").slider({min: min, max: max, slide: toonSlideGetal});	
}

function toonSlideGetal(event, ui) {
	$("#lbl_waarde").html(ui.value);
	slideWaarde = ui.value;
}

function getAntwoord() {
	var data = "";

	switch (type) {
		case "JaNee" :
			data = $("input[name=antwoorden]:checked").val();
			break;
		case "MC" :
			$("input[name=antwoorden]:checked").each(function() {
				data += $(this).val() + ";";
			});
			data = data.substr(0, data.length - 1);			
			break;
		case "Numeriek" :
			data = numMetInterval ? slideWaarde : $(".ant").val();			
			break;
		case "DND" :
			data = encodeURIComponent(JSON.stringify(dndAntwoorden));			
	}

	return data;
}

function isValidAntwoord() {
	if (type === "Numeriek" && !numMetInterval) {
		return $("#frm_num").valid();
	}

	return true;
}

function toonBeperktRapport(data) {
	var vragenReeks = data.vragenReeks;
	
	$(".main").empty();
	$(".main").append($("<h1>").text("Rapport"));
	$(".main").append($("<h2>").text("-- " + vragenReeks.naam + " --"));
	$(".main").append($("<h3>").text("Je behaalde een score van: " + data.score + "/" + vragenReeks.aantalVragen));
	$(".main").append($("<div>").addClass("rapport").attr("id", "div_beperkt_rapport"));
	$("#div_beperkt_rapport").append($("<table style='width:100%'>").attr("id", "tab_rapport").addClass("left"));
	
	$("#tab_rapport").append($("<tr>").append(
			$("<th>").addClass("center").text("Vraag")).append(
					$("<th>").addClass("center").text("Juiste antwoord")).append(
							$("<th>").addClass("center").text("Verbetering")));
	
	var imgSrc;
	for (var i = 0; i < vragenReeks.aantalVragen; i++) {
		imgSrc = data.verbetering[i] ? "images/JuistGB.png" : "images/FoutGB.png";
		$("#tab_rapport").append($("<tr>").append(
				$("<td style='width:70%'>").html(vragenReeks.vragen[i].vraag).addClass("rapport")).append(
						$("<td>").addClass("center").html(vragenReeks.vragen[i].juisteAntwoord).addClass("rapport")).append(
								$("<td>").addClass("center").html($("<img>").addClass("rapport").attr("src", imgSrc).addClass("verbetering"))));
	}
	
	$(".main").append($("<br>"));
	$(".main").append($("<div>").addClass("right").addClass("top10").attr("id", "div_return"));
	$("#div_return").append($("<a>").text("Home").attr("href", "/quizWebApp").addClass("btn"));
	$("#div_return").append($("<a>").text("Deelnemen").attr("href", "deelnemen.html").addClass("btn"));
	$(".btn").button();
}