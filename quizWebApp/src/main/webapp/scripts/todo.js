var deelnemerID = -1;

$(function() {
	loadGebruikerFuncties("td_gebruiker", "/quizWebApp",
			loadIngelogdeFunctionaliteitenTodo, false);

	$.ajax({
		method : "GET",
		url : "todo",
		data : "requestType=start",
		dataType : "json",
		success : toonAllesBijStart
	});
});

function toonAllesBijStart(data, textStatus, jqXHR) {
	var deelnemers = data.deelnemers;

	for (var i = 0; i < deelnemers.length; i++) {
		$("#ul_deelnemers").append($("<li>").text(deelnemers[i].naam).addClass("selectable").data("id", deelnemers[i].id));
	}

	$(".select").selectable({
		unselected : function() {
			$(":not(.ui-selected)", this).each(function() {
				$(this).removeClass('ui-state-highlight');
			});
		},
		selected : function() {
			$(".ui-selected", this).each(function() {
				$(this).addClass('ui-state-highlight');
				deelnemerID = $(this).data("id")
				requestToDoLijstje(deelnemerID);
			});
		}
	});
	
	$(".selectable").hover(
			  function () {
			    $(this).addClass('ui-state-hover');
			    $(this).css("cursor", "pointer");
			  }, 
			  function () {
			    $(this).removeClass('ui-state-hover');
			  }
			);
	
	toonMogelijkeVragenReeksen(data.vragenReeksen);
}

function requestToDoLijstje(deelnemerID) {
	var data = {"requestType" : "todoLoad", "deelnemerID" : deelnemerID};
	$.ajax({
		method : "GET",
		url : "todo",
		data : data,
		dataType : "json",
		success: toonToDoLijstje
	});
}

function toonToDoLijstje(data, textStatus, jqXHR) {
	$("#div_todo").empty();
	$("#div_todo").append($("<h2>").text("To Do"));
	if (data.todo.length === 0) {
		$("#div_todo").append($("<p>").text("Voor deze deelnemer is het to-do lijstje nog leeg"));
	} else {
		var vragRksn = data.todo;
		var table = $("<table>").addClass("todoTabel").attr("id", "tabel_todo").attr("cellspacing", "0px");
		var tr;
		for (var i = 0; i < vragRksn.length; i++) {
			tr = $("<tr>").attr("id", "todo" + vragRksn[i].id);
			tr.append($("<td>").text("[" + vragRksn[i].thema + "]").addClass("left"));
			tr.append($("<td>").text(vragRksn[i].naam).addClass("left"));
			tr.append($("<td>").addClass("left").append($("<img>").data("id", vragRksn[i].id).attr("src", "images/MinB.png").addClass("min")));
			table.append(tr);
		}
		$("#div_todo").append(table);
		
		$("img.min").click(function(e) {
			verwijderVragenReeks($(this).data("id"), deelnemerID);
		});
		
		$("img.min").hover(
			function() {
				$(this).css("cursor", "pointer");
				var id = $(this).data("id");
				$("#todo" + id).addClass("ui-state-hover");
			},
			function() {
				var id = $(this).data("id");
				$("#todo" + id).removeClass("ui-state-hover");
			}
		);
	}
	
	toonMogelijkeVragenReeksen(data.vragenReeksen);
}

function toonMogelijkeVragenReeksen(vragenReeksen) {
	$("#div_vragenreeksen").empty();
	$("#div_vragenreeksen").append($("<h2>").text("Vragenreeksen"));
	
	var table = $("<table>").addClass("todoTabel").attr("cellspacing", "0px");
	$("#div_vragenreeksen").append(table);	
	for (var i = 0; i < vragenReeksen.length; i++) {
		var tr = $("<tr>").attr("id", "vr" + vragenReeksen[i].id);
		tr.append($("<td>").text("[" + vragenReeksen[i].thema + "]").addClass("left"));
		tr.append($("<td>").text(vragenReeksen[i].naam).addClass("left"));
		tr.append($("<td>").append($("<img>").attr("src", "images/PlusB.png").addClass("plus").data("id", vragenReeksen[i].id)));
		table.append(tr);
	}
	
	$("img.plus").click(function(e) {
		if (deelnemerID >= 0) {
			voegVragenReeksToe($(this).data("id"), deelnemerID);
		} else {
			$("#div_selDeeln").dialog({
				modal : true,
				buttons : [{
					text : "OK",
					click : function(e) {
						$(this).dialog("close");
					}
				}],				
				minWidth : 400
			});
		}
	});
	
	$("img.plus").hover(
		function() {
			$(this).css("cursor", "pointer");
			var id = $(this).data("id");
			$("#vr" + id).addClass("ui-state-hover");
		},
		function() {
			var id = $(this).data("id");
			$("#vr" + id).removeClass("ui-state-hover");
		}
	);
}

function voegVragenReeksToe(vragenReeksID, deelnemerID) {
	var data = {"requestType" : "voegToe", "deelnemerID" : deelnemerID, "vragenReeksID" : vragenReeksID};
	$.ajax({
		method : "GET",
		url : "todo",
		data : data,
		success : function(data, textStatus, jqXHR) {
			requestToDoLijstje(deelnemerID);
		}
	});
}

function verwijderVragenReeks(vragenReeksID, deelnemerID) {
	var data = {"requestType" : "verwijder", "deelnemerID" : deelnemerID, "vragenReeksID" : vragenReeksID};
	$.ajax({
		method : "GET",
		url : "todo",
		data : data,
		success : function(data, textStatus, jqXHR) {
			requestToDoLijstje(deelnemerID);
		}
	});
}

function loadIngelogdeFunctionaliteitenTodo(isBeheerder) {
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