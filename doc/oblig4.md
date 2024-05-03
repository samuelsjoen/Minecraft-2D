# Rapport – innlevering 4
**Team:** *Team 6* – *Magnus, Samuel, Nini, Martine*

#### Prosjektrapport

Følgende skal med i team/prosjekt-rapporten, enten ved at det går frem fra referatene, og/eller at dere skriver en oppsummering. Sjekk at dere har vært innom alle punktene, selv om det bare er for å si at det fungerer bra.

##### Roller

Rollene i teamet fungerer nå bra! vi føler ikke på noe behov for å gjøre endringer i rollefordelingen. 

##### Prosjektmetodikk & referat

Vi synes at prosjektmetodikken vår har fungert ganske bra. Vi har benyttet oss av et project board gjennom Asana - men vi har det nå på Issue Boards på Git pga. det ikke var mulig å gjøre Asana prosjektet vårt offentlig. Vi bruker issue boardet til å liste opp oppgaver, og det hjelper oss å ha kontroll. 

I tillegg har vi alltid en ukentlig gjennomgang under ukens gruppetime - da går vi gjennom hva som må prioriteres den neste uken.

Vi har også hatt en del møter utenom gruppetimer for å diskutere og planlegge videre arbeid - dette har vært veldig nyttig for oss.

Noen av gruppemedlemmene har også parprogrammert som har vært veldig nyttig for å løse vanskelige oppgaver.

Kanban tavlen vår består av 5 kolonner: "Gjøremål", "Pågående", "Gjennomgang", "Testes" og "Fullført".
I disse kolonnene legger teammedlemmer til oppgaver de ønsker skal gjøres, og etter hvert som oppgaver blir tildelt og gjennomført, flyttes oppgaven fra kolonne til kolonne. 
Det gjør det mulig for oss å få oversikt over hva som må gjøres, hva som er under arbeid, hva som er ferdig og hvem som har ansvar for hva.
Slik skal man kunne unngå at to eller flere medlemmer implementerer samme funksjonalitet. 

Bruk av KanBan har gjort at prosektet ikke har blitt for overveldende. 
Likevel har vi kanskje ikke tatt i bruk denne prosjektmetodikken på en helt optimal måte, ettersom vi har fokusert mer på kommunikasjon og fordeling av oppgaver under fysiske møter eller via Discord.
Dette har blant annet ført til at vi ikke har hatt like god oversikt som vi burde hatt, bl.a. fordi vi har glemt/vært treige på å skrive ned i "Gjøremål" kolonnen, og det har hendt at noen har implementert samme funksjonalitet. 

Referatene fra alle møtene våre er å finne i referat-mappen vår. [Klikk her for å på siste referat.](referat/Referat-03.05.2024.txt)

![Oversikt KanBan](Kanban.jpg)

##### Gruppedynamikk

Gruppedynamikken er god, og vi har en god kommunikasjon på Discord. Vi har ikke hatt noen store uenigheter som har trengt å løses.

##### Retrospektiv 

Vi innser nå i ettertid at vi burde vært ferdig med spillet 1-2 uker før innleveringsfrist slik at vi kunne brukt tiden på å brukerteste. Den siste uken har vi funnet en del bugs i koden. Vi burde brukertestet koden mer og lengre tidligere. Tidligere har vi ofte bare sjekket om spillet fungerer og kan kjøre, men etter at vi kjørte spillet over en lengre periode fant vi ut at det lagget en del som vi nå har fikset. 

Holdt oss ansvarlig for å ha oversikt over de andre sin kode. Slik at vi hvert møte kunne ha en liten gjennomgang av hva kode vi hadde laget. Vi burde brukt møtene på å diskutere kode og ikke bare kodet for oss selv.

Vi synes at prosjektet har gått bra, kommunikasjon og hvordan vi har jobbet har ikke vært noe problem. Vi har hatt god kommunikasjon og har hatt jevnlige møter. Vi har også hatt en god fordeling av arbeidsoppgaver. Mye av problemene vi har hatt i prosjektet har vært med det tekniske, git, vs code, gradle run. I retrospektiv burde vi kanskje endret på arbeidsmåte - kanskje brukt maven fremfor gradle.

Kunne delt opp klassene mer. Brukt flere interfaces og abstrakte klasser.
Burde ha implementert 'OverlayView' på en bedre måte, slik at objektene slipper å måtte oppdatere posisjon hele tiden fordi at kameraet flytter på seg. For å kunne ha fått til det, måtte vi hatt større kunnskap om Libgdx.

Vi kom på forrige uke at vi burde ha en score. Vi har nå lagt det til, men det resettes ikke alltid når man restarter spillet. Vi ser nå i ettertid, på innleveringsdagen, at vi ikke burde prioritert å legge til en ny funksjon i spillet.

1. Vi burde ha ordentlig implementert en DEBUG-mode. Det hadde gjort det lettere for oss å sjekke om ting så riktig ut i GUI.
2. Vi burde ha planlagt prosjektstrukturen bedre før vi begynte å kode, det hadde ført til at vi hadde sluppet å brukt mye tid på å endre på koden slik at den ble mer MVC-aktig.
3. Kunne tatt i bruk maven fremfor gradle ettersom foreleser anbefalte det. I tillegg fikk vi innføring i maven under forelesning, mens det ikke ble lagt noe som helst fokus på gradle. Derfor måtte vi sette oss inn i gradle på egenhånd, noe vi har syntes vært vanskelig. Vi har brukt alt for lang tid på gradle. Det har vært mye problemer i forhold til å få 'gradle run' til å fungere. 

##### Fordeling av bidrag til kodebasen

Vi deler jevnt på arbeidsoppgavene og alle bidrar til kodebasen.

#### Krav og spesifikasjon

##### MVP-Krav

Vi nådde MVP-kravet i forrige innlevering. Vi har nå jobbet med å legge til flere funksjoner og forbedre brukeropplevelsen. Vi har også jobbet med å fikse bugs og forbedre koden vår. For oversikt over kravene vi har jobbet med se oblig 3 [her](oblig3.md). 

Vårt stretch goal er:
- [ ] Legge til flere ting man kan crafte
- [ ] Flere typer blokker og items
- [ ] Gjøre det lettere for spilleren å se hvilken blokk som er markert når man skal fjerne/plassere en blokk (f.eks. ha en tydelig rød boks rundt kantene på blokken)
- [ ] Lagre spillet og laste det inn igjen
- [ ] Ha mulighet til at player kan få mer liv igjen, f. eks. ved å crafte potions. Nå kan player kun crafte rustning, for å få mer liv. 
- [ ] Endre slik at treblokker kan bli kolliderbare når man plasserer dem ut - slik at man kan lage hus etc. uten å "gå gjennom veggene".
- [ ] Legge til mer ores og mengden av dem

###### Brukerhistorier, akseptansekriterier & prioritering av arbeidsoppgaver
Se mer i tidligere oblig for å få en oversikt over brukerhistorier, akseptansekriterier og arbeidsoppgaver, [her](oblig3.md). 

###### Bugs
- Spilleren blir "stuck" når den beveger seg langt til høyre på kartet, men når man "hopper" eller går en annen vei, beveger den seg igjen.
- Spillet krasjer noen ganger når man prøver å lukke det.
- Noen få ganger så fjernes ikke blokken når man klikker på den. Hvis man tar musen og klikker på nytt på blokken - så fungerer det som det skal. 
- Score resettes ikke alltid når man restarter spillet.

#### Produkt og kode

##### Hvordan bygge, teste og kjøre prosjektet
- [x] I README.md: Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes. [Klikk her for å se README.md](../README.md)
- [x] Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. OBS! Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er case sensitive, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
- [ ] Utførte oppgaver skal være ferdige. Slett filer/kode som ikke virker eller ikke er relevant (ennå) for prosjektet. (Så lenge dere har en egen git branch for innlevering, så er det ikke noe stress å fjerne ting fra / rydde den, selv om dere fortsetter utviklingen på en annen gren.)

For instrukser ift. bygging, testing og kjøring av kode [se README.md.](../README.md)
Vi har testet at prosjektet fungerer på Windows og OS X og har sjekket Linux gjennom en VM. 

##### Dette har vi fikset siden sist:
- Vi har nå tatt i bruk flere interfaces.
- Vi har kommentert koden der nødvendig og har nå javadocs på alle public-metoder.
- Vi har nå laget en abstrakt objektfabrikk som vi benytter for å opprette enemies.
- Vi har også gjort det tydligere i Readme.md hvordan man kan nå hjelpesiden. 
- Vi har fikset build.gradle slik at det nå er mulig å kjøre prosjektet fra en jar-fil. Og oppdatert README.md med instrukser for hvordan man kjører prosjektet fra en jar-fil.

##### Utbedring av feil & testing

Vi har nå ca. 86% testdekning - noe vi er fornøyde med! Vi har funnet ut gjennom testing at det har vært en del feil i koden vår - som vi nå har rettet opp i underveis.

##### Manuelle tester:

1. **Spillerbevegelse og interaksjon**:
   - Test at spilleren kan bevege seg (venstre, høyre). Gå til venstre med 'A' og høyre med 'D'.
   - Test at spilleren kan hoppe og angripe fiender. Hopp med 'SPACE' og angrip med 'TAB'.

2. **Fiender**:
   - Sjekk at fiender beveger seg på en forutsigbar måte og angriper spilleren når de er i nærheten.
   - Sjekk at fiender kan bli drept eller skadet av spilleren ved bruk av våpen eller andre midler.

3. **Inventar**:
   - Test at spilleren kan legge til, fjerne og bruke gjenstander fra inventaret.
   - Test at spilleren kan samhandle med miljøet (ved å hakke inn blokker eller plassere blokker). Bruk venstreklikk for å fjerne en blokk når musen er over blokken, og høyreklikk for å plassere en blokk når musen er over en tom rute.

4. **Crafting**:
   - Test at spilleren kan åpne crafting-vinduet ved å trykke 'E' og 'E' for å lukke det.
   - Test at crafting-oppskrifter vises riktig.
   - Test at spilleren kan bruke crafting til å lage nye gjenstander ved å trykke 'ENTER'.

Disse manuelle testene vil bidra til å sikre at spillet fungerer som forventet.

##### Klassediagram

Se nedenfor for klassediagram av koden vår:

![Klassediagramsoversikt](klassediagram/oblig4/classdiagram_overview.png)