package load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Thema;
import model.VragenReeks;
import model.vraag.DragAndDropVraag;
import model.vraag.KlassiekeVraag;
import model.vraag.MultipleChoiceVraag;
import model.vraag.NumeriekeVraag;
import model.vraag.Vraag;
import data.DBFacade;

public class Load {

	public static void main(String[] args) {
		List<Vraag> dieren = new ArrayList<Vraag>();
		dieren.add(new DragAndDropVraag("Sleep de dieren naar het aantal poten", dnd(new String[] { "Hond", "4" }, new String[] {
				"Kat", "4" }, new String[] { "Kever", "6" }, new String[] { "Mus", "2" })));
		dieren.add(new NumeriekeVraag("Hoeveel poten heeft een spin?", 8));
		dieren.add(new NumeriekeVraag("Hoeveel vogelsoorten leven er in België? Je mag er een eindje naast zitten", 430, 300,
				560, 0, 900));
		dieren.add(new MultipleChoiceVraag("Welk dier leeft op de boerderij?", keuzes("Koe", "Olifant", "Worm"),
				new String[] { "Koe" }, true));
		dieren.add(new MultipleChoiceVraag("Welke dieren leven in de bomen?", keuzes("Paard", "Eekhoorn", "Luiaard", "Hond"),
				new String[] { "Eekhoorn", "Luiaard" }, false));
		dieren.add(new KlassiekeVraag("Is de schildpad een zoogdier?", "Nee"));
		dieren.add(new KlassiekeVraag("Kan een kikker zwemmen?", "Ja"));

		List<Vraag> cijfers = new ArrayList<Vraag>();
		cijfers.add(new DragAndDropVraag("Sleep de getallen naar hun kleinste deler", dnd(new String[] { "5", "priem" },
				new String[] { "27", "3" }, new String[] { "64", "2" })));
		cijfers.add(new NumeriekeVraag("Hoeveel is 5 x 9 + 3?", 48));
		cijfers.add(new NumeriekeVraag("Hoeveel positive priemgetallen zijn er, kleiner dan 60? Je mag er 5 naast zitten", 17,
				12, 22, 0, 60));
		cijfers.add(new MultipleChoiceVraag("Vervolledig de reeks: 1, 1, 2, 3, 5, ...", keuzes("6", "7", "8", "9"),
				new String[] { "8" }, true));
		cijfers.add(new MultipleChoiceVraag("Welke getallen zijn deelbaar door 17?", keuzes("52", "340", "68", "186"),
				new String[] { "340", "68" }, false));
		cijfers.add(new KlassiekeVraag("Is de vierkantwortel van 10000 groter dan 100?", "Nee"));
		cijfers.add(new KlassiekeVraag("Is 84 deelbaar door 21?", "Ja"));

		List<Vraag> rekenenGevord = new ArrayList<Vraag>();
		rekenenGevord.add(new KlassiekeVraag("Behoort 88 tot de reeks van Fibonacci?", "Nee"));
		rekenenGevord.add(new KlassiekeVraag("Kan een tweedegraadsvergelijking geen enkele reële wortel hebben?", "Ja"));
		rekenenGevord.add(new MultipleChoiceVraag("Kies wat past. Een parallellogram heeft ...", keuzes(
				"Twee paar gelijke hoeken", "Twee paar evenwijdige zijden", "Vier zijden van gelijke lengte"), new String[] {
			"Twee paar gelijke hoeken", "Twee paar evenwijdige zijden" }, false));
		rekenenGevord.add(new MultipleChoiceVraag("Met hoeveel graden komt &#960; radialen overeen?",
				keuzes("360", "270", "180"), new String[] { "180" }, true));
		rekenenGevord.add(new NumeriekeVraag(
				"Hoeveel mogelijke combinaties zijn er voor een pincode van 5 cijfers (0-9, herhalingen toegestaan)?", 100000));
		rekenenGevord
		.add(new NumeriekeVraag(
				"Op hoeveel manieren kan je 20 maken door gehele, positieve getallen op te tellen? (11 + 9 = 9 + 11). Je mag er een eind naast zitten.",
				626, 400, 826, 0, 1000));
		rekenenGevord.add(new DragAndDropVraag("Sleep de vergelijking naar de juiste grafiek", dnd(new String[] { "x²+2x+5",
		"Parabool" }, new String[] { "sin(x)", "Sinusoïde" }, new String[] { "10x-6", "Rechte" })));

		List<Vraag> fysica = new ArrayList<Vraag>();
		fysica.add(new KlassiekeVraag("Bestaat er zwaartekracht in de ruimte?", "Ja"));
		fysica.add(new KlassiekeVraag("Telt het melkwegstelsel meer dan tien miljard sterren?", "Ja"));
		fysica.add(new MultipleChoiceVraag("F = m * ... ?", keuzes("v", "a", "c"), new String[] { "a" }, true));
		fysica.add(new MultipleChoiceVraag("Wat meet je met een dynamometer?", keuzes("Kracht", "Snelheid", "Rotatiesnelheid"),
				new String[] { "Kracht" }, true));
		fysica.add(new MultipleChoiceVraag(
				"Welke van de onderstaande personen zijn (o.a.) bekend voor hun bijdrage aan de fysica?", keuzes(
						"Louis Gay-Lussac", "Fran&ccedil;ois Englert", "Georg Wilhelm Friedrich Hegel", "Archimedes"),
						new String[] { "Louis Gay-Lussac", "François Englert", "Archimedes" }, false));
		fysica.add(new NumeriekeVraag("De lichtsnelheid is ongeveer 3 * 10^... ?", 8));
		fysica.add(new NumeriekeVraag(
				"Als een wagen vanuit stilstand een constante versnelling aanhoudt van 3 m/s<sup>2</sup>, hoe snel rijdt de wagen dan na 5 seconden? (in m/s)",
				15));
		fysica.add(new NumeriekeVraag("In welk jaar is Newton geboren? Je mag er 50 jaar naast zitten.", 1642, 1592, 1692, 1000,
				2000));
		fysica.add(new DragAndDropVraag("Golven of gassen?", dnd(new String[] { "Doppler-effect", "Golven" }, new String[] {
				"pV=nRT", "Gassen" }, new String[] { "geluid", "Golven" })));

		List<Vraag> chemie = new ArrayList<Vraag>();
		chemie.add(new KlassiekeVraag("Bestaat het element 'Br'?", "Ja"));
		chemie.add(new KlassiekeVraag("Kan je goud oplossen?", "Ja"));
		chemie.add(new MultipleChoiceVraag("Welk element is geen metaal", keuzes("Fluor", "Magnesium", "Kwik"),
				new String[] { "Fluor" }, true));
		chemie.add(new MultipleChoiceVraag("Uit welke elementen bestaan ammoniak?", keuzes("Stikstof", "Zuurstof", "Waterstof"),
				new String[] { "Stikstof", "Waterstof" }, false));
		chemie.add(new NumeriekeVraag(
				"Bij 500 ml van een oplossing met een concentratie van 5 mol/l voegen we 500 ml van eenzelfde soort oplossing toe. Wat moet de concentratie van de toegevoegde 500 ml zijn (in mol/l) opdat de resulterende oplossing een concentratie heeft van 7,5 mol/l?",
				10));
		chemie.add(new NumeriekeVraag("Hoeveel elementen zijn er tot op vandaag ontdekt?", 118, 100, 136, 0, 200));

		VragenReeks dierenVR = new VragenReeks();
		dierenVR.setNaam("Dieren");
		dierenVR.setThema(Thema.Biologie);
		for (Vraag v : dieren) {
			dierenVR.addVraag(v);
		}

		VragenReeks cijfersVR = new VragenReeks();
		cijfersVR.setNaam("Wiskunde 1");
		cijfersVR.setThema(Thema.Wiskunde);
		for (Vraag v : cijfers) {
			cijfersVR.addVraag(v);
		}

		VragenReeks rekenenGevordVR = new VragenReeks();
		rekenenGevordVR.setNaam("Wiskunde 2");
		rekenenGevordVR.setThema(Thema.Wiskunde);
		for (Vraag v : rekenenGevord) {
			rekenenGevordVR.addVraag(v);
		}

		VragenReeks fysicaVR = new VragenReeks();
		fysicaVR.setNaam("Fysica 1");
		fysicaVR.setThema(Thema.Fysica);
		for (Vraag v : fysica) {
			fysicaVR.addVraag(v);
		}

		VragenReeks chemieVR = new VragenReeks();
		chemieVR.setNaam("Chemie 1");
		chemieVR.setThema(Thema.Chemie);
		for (Vraag v : chemie) {
			chemieVR.addVraag(v);
		}

		DBFacade dbFacade = DBFacade.getUniekeInstantie();
		dbFacade.saveVragenReeks(cijfersVR);
		dbFacade.saveVragenReeks(dierenVR);
		dbFacade.saveVragenReeks(rekenenGevordVR);
		dbFacade.saveVragenReeks(fysicaVR);
		dbFacade.saveVragenReeks(chemieVR);
	}

	private static List<String> keuzes(String... keuzes) {
		ArrayList<String> result = new ArrayList<String>();
		for (String keuze : keuzes) {
			result.add(keuze);
		}
		return result;
	}

	private static Map<String, String> dnd(String[]... kvps) {
		HashMap<String, String> result = new HashMap<String, String>();
		for (String[] kvp : kvps) {
			result.put(kvp[0], kvp[1]);
		}
		return result;
	}

}
