# Oversikt over krav til prosjektet

## Generelt om vurdering av semesterprosjektet
Semesteroppgaven utgjør totalt 40% av sluttkarakteren, hvorav halvparten er design/prosjekt/rapportering (den skriftlige delen av innleveringen), og den andre halvparten er programvaren dere bygger. Dere får tilbakemelding på begge deler underveis, men vi venter til siste innlevering med å sette endelig poeng på programvare-delen. 

Dere blir vurdert som programvareutviklere, ikke spilldesignere, og ting som regeldesign og utvikling av plott og bakgrunn er veldig tidkrevende. Det er viktigere å legge vekt på god utviklingsmetododikk og god teknisk arkitektur og design enn å komme opp med et kreativt konsept.

Hovedvekten av vurderingen på sluttproduktet ligger på spillmekanikken, måten dere jobber sammen og refleksjon over prosessen, ikke det visuelle.

## Vurderingskriterier

### Design/prosjekt/prosess/rapportering (20%)

#### Formalia
- [x] Prosjektets git-repositorium ligger under teamets gitlab-gruppe og har et relevant navn
- [x] README.md-fil, med navn på team-medlemmer, teamet og prosjektet, kort beskrivelse og brukerveiledning for spillet, teknisk info om hvordan koden kjøres, og informasjon om hvor grafikk/lyd er hentet fra (kilde/opphavsrett)
- [ ] doc/obligX.md-fil med oversiktlig svar på oppgaver
- [ ] Alt er oversiktlig og riktig format

#### Team
- [x] Møtereferater (i, eller lenket til fra oblig-filen)
- [x] Teambeskrivelse og rollefordeling
- [ ] Beskrivelse av prosjektmetodikk
- [ ] Retrospektiv – hva var planlagt (av metodikk, etc), hva gjorde dere faktisk og hvorfor, hva vil dere evt. gjøre annerledes
- [ ] Project board, issues etc. er oppdatert
- [x] Gruppedynamikk og kommunikasjon: alle meninger bli hørt, alle bidrar jevnt, tonen er god
- [x] Alle bidrar til normalt, godt arbeidsmiljø

#### Git / versjonskontroll
- [ ] Commits er ryddige
- [x] Ingen filer mangler
- [x] Innlevering er tagget riktig
- [ ] Commit-meldinger er meningsfulle
- [ ] doc/obligX.md har oversikt over evt. større endringer, eller forbedringer dere har blitt bedt om å gjøre
- [x] Jevn fordeling av commits mellom teammedlemmer

### Programvare, produkt og kvalitet (20%)

#### Spesifikasjon
- [ ] Overordnet beskrivelse av konsept, etc.
- [ ] Brukerhistorier
- [ ] Akseptansekriterier og arbeidsoppgaver (kort beskrivelse)
- [ ] Hva inngår i MVP? Hva er evt. stretch goal?

#### Produktleveranse
- [x] Koden sjekker ut og bygger
- [ ] Kan bygges og kjøres på alle operativsystem
- [ ] Kan bygges og testes ikke-interaktivt (dvs. på en server uten tilhørende skjerm/tastatur)
- [ ] Kravene er oppfylt
- [x] Spillet er spillbart (trenger ikke være et bra eller gøy spill, men det må være mulig for at vanlig menneske å bruke det)
- [x] Teknisk dokumentasjon om oppsett – hvordan bygge og kjøre programmet osv. (i README.md, se punkt under Formalia)
- [x] pom.xml / build.gradle er oppdatert med korrekt prosjektnavn, main-klassenavn etc.
- [ ] Teknisk beskrivelse av prosjektet og arkitekturen (inkl. klassediagram)

#### Kodekvalitet
God kodestil (formattering, meningsfulle variabelnavn, unngå code smells), koden er tilstrekkelig dokumentert. Hvis det er kommentarer i koden, skal de være meningsfylte.
- [ ] Følger single responsibility principle; high cohesion, low coupling
- [ ] God bruk av interface, arv, kodegjenbruk – open-closed principle
- [ ] Korrekt (evt.) bruk av arv – Liskov substitution principle
- [ ] Unngå unødvendige avhengigheter i koden – interface segregation principle
- [ ] Referer til abstraksjoner (interfaces), ikke konkrete klasser – dependency inversion principle
- [ ] God navngivning
- [ ] Public metoder dokumentert
- [ ] Don't Repeat Yourself – bruk abstraksjon heller enn copy/paste
- [ ] Unngå død og råtnende kode

#### Testing
- [ ] Test coverage
- [ ] Tester som faktisk kan finne feil
- [ ] Tester er automatiske
- [ ] Automatiske tester kan kjøres «hodeløst»
- [ ] Minimum 75% test coverage

#### Konkrete krav
- [x] Spillet har forside/hjelpeside
- [ ] MVC-design
- [ ] Lyd koblet til hendelse
- [ ] Objektfabrikker
- [ ] Abstrakte objektfabrikker
- [ ] Objekter som modifiserer oppførsel (powerups, etc)

(Andre konkrete krav (se Krav til prosjektet) er fordelt under de andre punktene)

## Krav til prosjektet
Se forøvrig krav til innlevering i hver oppgave. Det kan være vi endrer litt på kravene underveis, men vi skal prøve å være rimelig greie kunder :)

### Krav til spesifikke egenskaper
(Vi går gjennom disse tingene i løpet av semesteret – og det går helt fint å starte uten, og heller refaktorere når man blir kjent med systemet. Kravene er til sluttproduktet, ikke milepælene.)

**Lyd**
- [ ] Det skal være minst én lyd som er koblet til en hendelse i spillet (f.eks. tyggelyder når noen spiser). Koden som håndterer lyd må også være adskilt.

**Objektfabrikker**
- [ ] Det skal være mulig å opprette enheter i spillet ved hjelp av objektfabrikker – f.eks. å tegne kart basert på en streng (# er vegg, | er en søyle, o er et hull i bakken, e.l.)
- [ ] Fabrikkene skal ikke være hardkodet (f.eks. med et stort switch-statement) – dvs. man kan legge til flere ting uten å endre koden til selve fabrikken (→ ha mulighet til å registrere nye type objekter)

**Testing**
- [ ] Koden skal ha tester, med minimum 75% coverage.
- [ ] Automatiske tester skal kunne kjøres uten å kreve interaksjon med brukeren eller bruk av grafikk/lyd.

**Annet**
- [ ] Det skal være mulig å ha ting i spillet som endrer oppførselen til spiller (eller andre) – f.eks. power-ups (spis en sopp, bli kjempestor / plukk en blomst, skyt flammer) eller klær (putt på støvler, gå lange skritt / ta på kappe, bli usynlig). Det bør være mulig å legge til nye power-ups uten å endre koden til spilleren (eller andre som blir påvirket).
- [ ] Public metoder må være dokumentert (som minimum: metoder som brukes på tvers av pakker)
- [x] Spillet skal ha en forside, og en hjelpeside som viser hvordan kontrollene funker (det kan være samme siden).
- [x] Kilder må oppgis for all grafikk og lyd, og dere må ha rett til å bruke det (f.eks. ting som er dekket av Creative Commons-lisens eller andre open source lisenser (minimum «fri ikke-kommersiell bruk»)). For ting dere lager selv må dere legge ved lisens som klargjør hvordan ting (evt) kan gjenbrukes.
- [x] Generell dokumentasjon, referater og svar på oppgaver skrives i Markdown og legges i doc/ undermappen, hovedfilen for hver innlevering skal ha navnet doc/obligX.md. Det går evt. an å lenke til vedlagte bilde/HTML/PDF-ressurser – men all tekst/vedlegg som inngår i en innlevering må være tydelig lenket til fra hovedfilen. Ting skrevet i tekstbehandler må konverteres først.
- [x] Grafikk/brukegrensesnitt skal være mest mulig adskilt¹ fra spill-logikken (MVC e.l.). (Det kan være vanskelig å få dette til med scene graph-systemene i libGDX og JavaFX, men i det minste bør input være adskilt (slik at f.eks. spiller-objektet ikke jobber direkte med tastaturet))

¹ Med «adskilt» tenker vi at implementasjonen av klasse A ikke skal være direkte avhengig av klasse B (spesielt på tvers av pakker), men heller jobber mot et interface I. Dvs. hvis B f.eks. er lydavspilleren, så har A «abstrahert bort» hvordan lyd funker. (Se coupling, separation of concerns, dependency inversion priciple, og single responsibility principle)

### Krav til deltakelse
Alle i teamet må…

- [x] delta i design- og utviklingsarbeidet – men dere kan ha spesialiserte roller,
- [x] møte på gruppe, eller være i kontakt med de andre i løpet av uken (dersom det er grunner til at det ikke går an, må man orientere/avtale det med de andre i teamet),
- [x] bidra i programmerings-, skrive- og testearbeidet og committe ting til Git (evt. nevne om man har parprogrammert) – vi sjekker at Git-loggen er rimelig balansert,
- [ ] ha tilstrekkelig oversikt til å kunne forklare arkitektur og slikt – men det er ikke nødvendig å kjenne alle implementasjonsdetaljer,
- [ ] kunne forklare hva som skjer i kode man har skrevet selv, og i minst én pakke/klasse/etc som noen andre har skrevet,
- [ ] ha skrevet minst én test,
- [x] ha skrevet noe kode som interagerer med noen andres kode (mer enn bare å kalle doit()).
- [x] følge opp arbeidsoppgaver man avtaler i teamet,
- [x] bidra til, følge opp og ta ansvar for at alle i teamet (+gruppelederne!) har et trygt og godt arbeidsmiljø, uavhengig av bakgrunn/kjønn/legning/livssyn/funksjonsnedsettelse/etc.

### Krav fra til README (fra Del A): 
README.md-filen skal (gjennom hele semesteret) holdes oppdatert med:
- [x] navn på team-medlemmene, teamet og prosjektet, samt gruppenummer
- [x] kort beskrivelse av spillet og hvordan det brukes (f.eks. hvilke tastetrykk som gjør hva).
- [x] hvordan koden kjøres.
- [x] hvor evt. grafikk/lyd-ressurser er hentet fra.
- [x] Dere kan forbedre og endre på konseptbeskrivelsen underveis, etterhvert som dere får bedre innsikt i hva dere vil

## Krav til generelle egenskaper
Dere kommer til å lære om disse tingene i løpet av kurset, så ikke få panikk om det høres ukjent ut:

- [ ] Kvalitet: Test grundig, bruk verktøy som SpotBugs for å finne feil, hold koden lesbar med autoformattering osv. Koden skal ha generelt god kvalitet og være dokumentert slik at andre kan legge til utvidelser uten å måtte gå inn og lese alle implementasjonsdetaljene.
- [ ] Abstraksjon/dependency inversion: Tenk interfaces og skjul klassene – det gjør det enklere å bytte ut funksjonalitet og å «mocke» resten av systemet når du tester en enhet. Vi legger vekt på god bruk av abstraksjon (pakke vekk detaljer bak et veldefinert grensesnitt).
- [ ] Decoupling:  Sørg for at forskjellige enheter i programmet er mest mulig uavhengig av hverandre – spesielt med tanke på model (intern representasjon av spillet) og view (grafisk representasjon beregnet på brukeren) – se Model-View-Controller.
- [ ] Koden bør følge vanlige, moderne objektorienterte designprinsipper (SOLID, etc.)
- [ ] Det må være enkelt for oss å følge med på hva dere har gjort, hva som endrer seg, og hvordan vi bruker/tester spillet.


## Krav til de ulike delene:

### Krav for del B - hva som skal inkluderes i oblig-filene
For de neste innleveringene skal dere jobbe videre iterativt – forbedre og tilpasse prosessen og spesifikasjonen; videreutvikle implementasjonen i tråd med kravene; og vurdere/reflektere over arbeidet. Dvs. forbedre Del A iterativt og inkrementelt inntil dere kommer i land med et ferdig produkt.

Lag korte referat fra team-møtene (ha med dato, hvem som var tilstede, hva dere diskuterte, hvilke avgjørelser dere tok, og hva dere ble enige om å gjøre til neste gang)

#### Prosjektrapport
(Leveres i doc/obligX.md.)
Følgende skal med i team/prosjekt-rapporten, enten ved at det går frem fra referatene, og/eller at dere skriver en oppsummering. Sjekk at dere har vært innom alle punktene, selv om det bare er for å si at det fungerer bra.

- [ ] Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
- [ ] Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
- [ ] Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?
- [ ] Hvordan er gruppedynamikken? Er det uenigheter som bør løses?
- [ ] Hvordan fungerer kommunikasjonen for dere?
- [ ] Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om feilretting, men om hvordan man jobber og kommuniserer.
- [ ] Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler)
- [ ] Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som havner i referat).
- [ ] Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.

- [ ] For siste innlevering (Oblig 4): Gjør et retrospektiv hvor dere vurderer hvordan hele prosjektet har gått. Hva har dere gjort bra, hva hadde dere gjort annerledes hvis dere begynte på nytt?

#### Krav og spesifikasjon
(Leveres i doc/obligX.md.)

- [ ] Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.
- [ ] For hvert krav dere jobber med, må dere lage 1) ordentlige brukerhistorier, 2) akseptansekriterier og 3) arbeidsoppgaver. Husk at akseptansekriterier ofte skrives mer eller mindre som tester
- [ ] Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også.
- [ ] Forklar kort hvordan dere har prioritert oppgavene fremover
- [ ] Har dere gjort justeringer på kravene som er med i MVP? Forklar i så fall hvorfor. Hvis det er gjort endringer i rekkefølge utfra hva som er gitt fra kunde, hvorfor er dette gjort?
- [ ] Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang.
- [ ] Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs).
- [ ] Kravlista er lang, men det er ikke nødvendig å levere på alle kravene hvis det ikke er realistisk. Det er viktigere at de oppgavene som er utført holder høy kvalitet. Utførte oppgaver skal være ferdige.

#### Produkt og kode
(Evt. tekst / kommentarer til koden kan dere putte i en egen ## Kode-seksjon i doc/obligX.md.)

- [ ] Utbedring av feil: hvis dere har rettet / forbedret noe som er påpekt tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.
- [x] I README.md: Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes.
- [ ] Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. OBS! Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er case sensitive, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
- [ ] Lag og lever et klassediagram. (Hvis det er veldig mange klasser, lager dere for de viktigste.) Det er ikke nødvendig å ta med alle metoder og feltvariabler med mindre dere anser dem som viktige for helheten. (Eclipse har forskjellige verktøy for dette.)
- [ ] Kodekvalitet og testdekning vektlegges. Dersom dere ikke har automatiske tester for GUI-et, lager dere manuelle tester som gruppelederne kan kjøre basert på akseptansekriteriene.
- [ ] Statiske analyseverktøy som SpotBugs eller SonarQube kan hjelpe med å finne feil dere ikke tenker på. Hvis dere prøver det, skriv en kort oppsummering av hva dere fant / om det var nyttig.
- [ ] Automatiske tester skal dekke forretningslogikken i systemet (unit-tester). Coverage kan hjepe med å se hvor mye av koden som dekkes av testene – i Eclipse kan dette gjøres ved å installere EclEmma gjennom Eclipse Marketplace.
- [ ] Utførte oppgaver skal være ferdige. Slett filer/kode som ikke virker eller ikke er relevant (ennå) for prosjektet. (Så lenge dere har en egen git branch for innlevering, så er det ikke noe stress å fjerne ting fra / rydde den, selv om dere fortsetter utviklingen på en annen gren.)

### Del C
Hold presentasjon hvor teamet presenterer prosjeket for alle:

- [ ] Dere skal ha med demo av spillet, og
- [ ] hva er det viktigste dere har lært om å jobbe i et team?
- [ ] hva er det viktigste dere har lært om å jobbe på et større prosjekt over lengre?
- [ ] hva har dere lært om programmering/programutvikling?
- [ ] hva ville dere gjort noe annerledes om dere hadde gjort det igjen?

## Veldig generelle krav som vi har på plass allerede:

### Oppsett/biblioteker

- [x] Java: Spillet skal skrives i Java (f.eks. Java 17 eller 21)
- [x] JUnit: For testing skal JUnit 5 brukes
- [x] Byggesystem:  Vi anbefaler Maven, men det går også an å bruke Gradle.
- [x] Skjelett: Dere får et skjelettprosjekt dere kan bruke som utgangspunkt (med ferdig oppsatt grafikkbibliotek, teste-verktøy og Mac-støtte), men dere kan også sette opp prosjektet selv.
- [x] Grafikk: Velg selv, men dere får skjeletter for libGDX og JavaFX + Anyas veldig eksperimentelle rammeverk. En annen mulighet er Java 2D (med innebygget Java AWT).
- [x] Fysikk/kollisjon: Velg selv (om det trengs), men Box2D eller jbump er populære muligheter
- [x] Versjonskontroll: Bruk Git til versjonskontroll, og gjør aktiv bruk av kollaborativ funksjonalitet i GitLab. Innlevering skjer ved å gi oss tilgang til prosjektet i git.app.uib.no

### Generelle krav
- [x] Spillet skal ha grafikk og lyd.
- [x] Spillet skal ha både spillkarakterer som styres av en menneske-spiller (med tastatur / mus / gamepad) og som er kontrollert av maskinen.
- [x] Spillet skal programmeres i Java, men dere står fritt til (og anbefales) å bruke ekstra biblioteker. Prosjektet skal kunne bundles som en JAR-fil som kan kjøres på både Windows, Linux og Mac med Java SE 19.
- [x] Spillet kan være en «klone» av et eksisterende spill, men dere må følge lov om opphavsrett, varemerker osv, og vanlige regler om plagiat.
