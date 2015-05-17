<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css" type="text/css" />
<link rel="stylesheet" href="./css/jquery-ui.css" type="text/css" />
<script src="./scripts/jquery.js" type="text/javascript"></script>
<script src="./scripts/jquery-ui.js" type="text/javascript"></script>
<script src="./scripts/util.js" type="text/javascript"></script>
<script src="./scripts/home.js" type="text/javascript"></script>
</head>
<body>

<div class="head">

	<table class="spanTable">
		<tr>
			<td>
				<a href="/quizWebApp">
					<img class="left" src="./images/logo.png" />
				</a>
			</td>
			<td align="right" id="td_gebruiker">				
				<button type="button" id="btn_registreer" class="btn">Registreer</button>
				<button type="button" id="btn_login" class="btn">Login</button>				
			</td>
		</tr>
	</table>
	
	<div id="div_login" class="dialog" title="Login">
		<p>Geef je gebruikersnaam en paswoord in:</p>
	
		<form id="frm_login">
			<table>
				<tr>
					<td>Gebruikersnaam:</td>
					<td><input type="text" name="gebruikersNaam"/></td>
				</tr>
				<tr>
					<td>Paswoord:</td>
					<td><input type="password" name="paswoord"/></td>
				</tr>
				<tr>
					<td></td>
					<td class="right"><input type="button" class="btn" value="Login" id="btn_login_enter"/></td>
				</tr>
			</table>
		</form>
	
		<p id="lbl_error" class="error"></p>
	</div>
	
	<div id="div_registreer" title="Registreer" class="dialog">
		<p>Kies een gebruikersnaam en paswoord:</p>
	
		<form id="frm_registreer">
			<table>
				<tr>
					<td>Gebruikersnaam:</td>
					<td><input type="text" name="gebruikersNaam"/></td>
				</tr>
				<tr>
					<td>Paswoord:</td>
					<td><input type="password" name="paswoord"/></td>
				</tr>
				<tr>
					<td></td>
					<td class="right"><input type="button" class="btn" value="Registreer" id="btn_registreer_enter"/></td>
				</tr>
			</table>
		</form>
	</div>	

</div>

<div class="main">

	<div id="tabs">
		<ul id="ul_tabs">
			<li><a href="#tab1">Welkom</a></li>
			<li><a href="#tab2">Info</a></li>
		</ul>
	
		<div id="tab1" class="tabPanel">
			<h1>Welkom</h1>
			<p>Browse door vragenreeksen, neem deel aan quizzen en bekijk je resultaten</p>
			<br />
			<a href="deelnemen.html" class="btn">Deelnemen</a>
		</div>
		
		<div id="tab2" class="left tabPanel">
			<h1 class="center">Info</h1>
			<p>Als anonieme gebruiker kan je vragenreeksen oplossen. Na elke reeks krijg je een overzicht met je score en de juiste antwoorden. Om van de volledige functionaliteit van QWA gebruik te maken registreer je je eerst.</p>
			<p>Als geregistreerde gebruiker wordt elk antwoord dat je geeft opgeslagen. Later kan je van iedere vorige deelname een overzicht opvragen. Bepaalde vragenreeksen kan je pas oplossen als je een voorgaande vragenreeks hebt opgelost. Log je dus in om je progress te bewaren! </p>
			<p>Inloggen met een beheerdersaccount laat je toe om to-do lijsten op te stellen voor bepaalde deelnemers. Als beheerder heb je ook toegang tot de volledige rapportagefuncties.</p>
			<p>Veel quizplezier!</p>
		</div>
	</div>
	
</div>



</body>
</html>
