# Rapport – innlevering 1
**Team:** *Team 6* – *Magnus, Samuel, Nini, Martine*

## Oppgave A0

Gruppenavn: Team 6
INF112 - Gruppe 2

Kompetanse: 
Samuel & Magnus - datateknologi
Nini - datasikkerhet
Martine - data science

Da vi har ulike studieretninger i teamet har  vi også noe ulik kompetanse som vi tror er en fordel når vi skal utvilke spillet.

## Oppgave A1

[Klikk her for å se README.md](../README.md)

## Oppgave A2 - konsept

* Spillfigur som kan styres – gå til høyre/venstre, hoppe oppover, åpne inventory
* Todimensjonal verden:
   * Plattform – horisontal flate spilleren kan gå på
   * Spilleren beveger seg oppover ved å hoppe, og nedover ved å hakke seg nedover/fjerne blokker
* Fiender(zombier) som beveger seg og er skadelige ved berøring
* Spilleren kan samle poeng ved å plukke opp ting
* Utfordringen i spillet er: å bevege seg gjennom terrenget, å samle blokker/gjenstander, å bekjempe fiendene, å nå frem til og bekjempe en final boss» 

* Verden er bygget opp av blokker med fast størrelse (felter i et 2D-rutenett)
* Verden er større enn skjermen og scroller horisontalt og vertikalt ettersom personen forflytter seg
* Spilleren kan drepe fiendene ved å banke de med arm/verktøy/sverd
* «Power-ups» som gir spilleren spesielle krefter (drikke ting, ny øks etc)

## Oppgave A3 - spesifikasjon

Målet for vårt spill, Minecraft 2D, er å ha en spiller som kan bevege seg gjennom en verden og samle blokker og kunne bygge ting. 

##### Krav til Minimum Viable Product:
1. Vise et spillebrett
2. Vise spiller på spillebrett
3. Flytte spiller med pil-taster, spesifisert mer i readme
4. Spiller interagerer med terreng (vha. mus for å klikke på blokker)
5. Vise fiender/monstre; de skal interagere med terreng og spiller
6. Spiller kan dø (ved kontakt med fiender og ved å falle utfor skjermen)
7. Mål for spillbrett (enten et sted, en mengde poeng, drepe alle fiender e.l.)
8. Nytt spillbrett når forrige er ferdig
9. Start-skjerm ved oppstart / game over, samt en skjerm som viser ulike tastetrykk etc man kan bruke i spillet

### Brukerhistorier

##### Historie 1:
Som spiller trenger jeg å kunne samle ressurser som tre, stein, og mineraler, slik at jeg kan bygge og oppgradere verktøy, våpen og strukturer i spillet som jeg kan bruke i spillet for å bekjempe fiender og bygge ting.
* Akseptansekriterier
   1. Spilleren kan samle tre, stein og mineraler ved å klikke på dem i spillet.
   2. Ressursene vises i spillerens inventar etter å ha blitt samlet inn.
   3. Spilleren kan bruke de innsamlede ressursene til å bygge og oppgradere verktøy, våpen og strukturer.

* Arbeidsoppgaver
   1. Implementere klikkfunksjonalitet for å samle ressurser.
   2. Utvikle inventarsystem for å lagre og administrere ressurser.
   3. Opprette funksjonalitet for bygging og oppgradering av gjenstander basert på ressursene.

##### Historie 2:
Som spiller trenger jeg å kunne skille stein-blokker, jord-blokker etc. fra bakgrunnselementer slik at jeg avgjøre hvordan jeg skal styre spillfiguren og hvilke blokker jeg ønsker å knuse/samle inn.

* Akseptansekriterier:
   1. Spilleren kan skille mellom ulike typer blokker som stein, jord osv. visuelt.
   2. Muligheten til å ha en form form mus over blokker slik at spiller kan se hva hen knuser/samler inn av blokker.

* Arbeidsoppgaver:
   1. Implementere forskjellige blokktyper som ser ulike ut visuelt.
   2. Utvikle mekanisme for å knuse og samle inn blokker.
   3. Opprette grensesnitt der spilleren kan velge ønsket blokk å knuse, feks. en musehånd som er synlig på skjermen.

##### Historie 3:
Som programmør trenger jeg å kunne skille bakgrunnselementer og spill-blokker fra hverandre, slik at jeg kan avgjøre hvor spillfiguren kan bevege seg.

* Arbeidsoppgaver:
   1. Identifisere og definere bakgrunnselementer og spillblokker, samt forskjellen mellom dem.
   2. Implementere kollisjonsdeteksjon mellom spillfiguren og bakgrunnselementene.
   3. Utvikle bevegelsesmekanismer for spilleren basert på skillet mellom spillfigur og bakgrunnen.

##### Historie 4:
Som spiller trenger jeg å kunne utforske forskjellige steder, som skoger, ørkener og fjellområder, slik at jeg kan finne sjeldne ressurser og oppleve et variert spill.

* Akseptansekriterier:
   1. Minecraft 2D må ha varierte spill-miljøer slik at man kan bevege seg gjennom skog, ørken og fjell bla. 
   2. Sjeldne ressurser må være tilgjengelig i spillet

* Arbeidsoppgaver:
   1. Designe ulike spill-miljøer som har sjeldne ressurser
   2. Lage et system som generer terreng og plasserer sjeldne ressurser tilfeldig, samtidig som plassering av objekter gir mening feks. ikke et tre i ørkenen. 

##### Historie 5:
Som spiller trenger jeg å kunne bekjempe fiender og monstre som angriper om natten og mørke steder som f.eks. huler, slik at jeg kan beskytte meg selv og mine byggverk.

* Akseptansekriterier:
   1. Fiender angriper om natten og i mørke områder.
   2. Spilleren må kunne bekjempe fiender ved bruk av våpen.

* Arbeidsoppgaver:
   1. Designe og animere fiendetyper.
   2. Implementere AI for fiender og hvordan de angriper spilleren/oppfører seg.
   3. Utvikle mekanikk for spilleren og hvordan den bruker våpen for å bekjempe zombier etc.

## Oppgave A3 - prosess for teamet
* Møter og hyppighet av dem
Møtes minst én gang i uken på fredager i gruppetimen, har også mulighet til å møtes torsdager da alle har fri. Så vi har flere møter ved behov.

* Kommunikasjon mellom møter
Kommuniserer gjennom discord, stiller spørsmål og sier ifra når vi jobber med kode etc. 

* Arbeidsfordeling
Lager en liste over hva som må gjøres og fordeler den innad i gruppen.

* Oppfølging av arbeid
Tar en liten gjennomgang av hva som har sjedd gjennom uken hver gruppetime. 

* Deling og oppbevaring av felles dokumenter, diagram og kodebase
Bruker git til kode og dokumenter under .doc-mappen til obliger. Tar også og lagrer diagram her når vi har noe mer oversikt om spillet med tiden.

## Oppgave A4

Se koden her i repoet vårt.

## Oppgave A5

Vi har lært at det er ofte vanskelig å ha kontroll når vi ikke har en konkret plan - vi tenker å bli bedre på dette og skal nå drive prosjektplanlegging gjennom asana.com og vi følger kanban-metodikken. Vi skal skrive hvilke oppgaver som må gjøres etc. og har mer kontroll over prosjektet på denne måten. Derfor blir prosjektet noe mer overkommelig. 

Vi er fornøyd med oppmøte på gruppemøter og selvorganiserte møter. 

Vi er fornøyd med innsatsen i oblig 1/del A - vi har fått gjort alt vi skulle og innen tidsfristen. Vi er fornøyde!
