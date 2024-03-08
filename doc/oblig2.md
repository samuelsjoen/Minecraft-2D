# Rapport – innlevering 2 - del B
**Team:** *Team 6* – *Magnus, Samuel, Nini, Martine*

## Prosjektrapport

### Rollene i teamet
- Teamlead: Alle (vi har alle oversikt over hva som må gjøres, en demokratisk måte)
- Sekretær: Nini og Martine (rollen innebærer å ha hovedansvar for referat og skriving av oblig)
- Utviklere: Alle
- Testere: Alle, alle lager tester til sin egen kode.

**Innhold av ulike roller:**

Vi har ikke fordelt roller innad i teamet - vi har funnet ut at det er noe vi bør gjøre. Slik at vi nå har gitt ansvar til Martine og Nini for å skrive referat/oblig - da de tidligere i prosessen har gjort dette. Vi har også listet opp roller for testere, utviklere - slik at vi har ekstra fokus på testing - noe vi ikke har gjort noe av frem til nå. Fra før hadde alle rollen som teamlead - da vi på andre gruppemøte fant ut at alle skulle være teamlead. 

Det var vanskelig å vite hvilke roller vi skulle dele inn i ved start og hvilke roller vi trengte tidlig i prosjektet. Så vi gikk for en demokratisk variant - der vi alle har noe ansvar - vi kommunsierer godt og fordeler oppgavene som må gjøres. 
Slik at vi alle har delområder vi har ansvar for hele tiden. F.eks. har Magnus hatt ansvar for player/entity, Samuel har hatt kontroll over health og inventory, Nini har hatt ansvar over map, pipeline, gradle og Martine har hatt kontroll over menu-screen og vedlikehold av bugs. Slik at vi ikke har hatt overlappende områder - slik at vår kode-prosess har fungert bra - selv om vi ikke har hatt distinkte roller. 

### Erfaringer og refleksjoner
- Teamet har funnet at å ikke ha roller har vært tilfredsstillende, men vi vurderer å gjøre noen justeringer nå for å få oversikt over alle oppgavene som må gjøres, men de fleste rollene betjens av alle. 

- Da vi alle jobber på samme prosjekt, er det vanskelig å vite hva andre forventer av deg - da vi ikke har satt noe klare mål. Vi har funnet at løsningen på dette kan være mer parprogrammering. Det kan virke overveldende, av og til står man fast og  man bruker lang tid på å finne ut hvordan man skal begrense seg. Dersom man har en annen person man kan dele tanker med - kan denne prossessen bli lettere. 

- Kommunikasjonen har vært effektiv, men vi ønsker å forbedre arbeidet med å få bedre oversikt over hva som må gjøres.
- Vi slet mye i starten med at koden vår ikke ville kjøre, og at koden ikke ville bygges med gradle. Vi brukte mye tid på å finne ut av det administrative angående prosjektet. Om vi skulle gå for gradle eller maven - vi valgte å gå for gradle da dette var lettere med libgdx - dog fikk vi mye problemer med pipeline - da vi måtte sette denne opp på nytt igjen. Og malen vi hadde for prosjektet inkluderte også foldere for "ios", "html" etc. Da vi fjernet disse mappen og tok bort tilhørende i build-filene - fikk vi et problem med at vår "desktop" og "core"-folder ikke eksisterte. Ingenting fungerte - vi hadde mye problemer med å få dette til å fungere igjen. Og brukte en hel gruppetime og resten av dagen etterpå på å få ordnet dette. Dette var mye

### Gruppedynamikk og kommunikasjon
- Gruppedynamikken har vært god, vi har ikke hatt noen uenigheter innad i gruppen underveis, kun trøbbel med git - som ikke spiller på lag. 
- Kommunikasjonen har hovedsakelig vært via discord, og vi har hatt jevnlige møter hver fredag for å sikre god fremdrift.

### Retrospektiv
- Vi har nesten oppnådd et minimum viable product (MVP). Vi har fått til en player som går og hopper i en verden, og enemies som dukker opp i verdenen underveis. Vi har fordelt oppgavene på gruppemøter, de som har lyst til å gjøre en oppgave får lov å gjøre oppgaven. Vi har alle hatt lyst å jobbe med ulike ting - slik at dette har fungert for å fordele oppgavene. Vi har hatt noe problemer med oppgavehåndtering, det er vanskelig å vite hvor stor oppgaven er ved fordeling. Slik at vi fremover skal parprogrammere større oppgaver. 

- Vi forventet ikke at vi skulle ha så mye tekniske problemer, relatert til visual studio code og git - dette er noe vi har brukt mye tid på, men nå omsider fått til. 

- Prosjektstyringen har fungert bra - vi har holdt oss godt innenfor alle tidsfrister og har god oversikt over når oppgaver skal leveres. 


- Forbedringspunkter: 
  1. Forbedre testdekning og kvalitetssikring av koden. Da vi per nå har ingen tester som er relevante for koden.
  2. Bedre oversikt over oppgaver som må gjøres. Vi har satt opp Asana, for å dele opp prosjektet i mindre mer overkommelige biter. Vi må bli flinkere til å ta del i Asana og benytte det parallelt med Discord.

## Krav og spesifikasjon

### Status på kravene
- Vi har prioritert kravene som gjør at vi får en player i en verden, og vi har kommet nesten i mål. Vi har prioritert kravene i rekkefølge.
- Vi har gjort følgende siden forrige levering: laget health, enemies, collision, attacking, plassering av blokker og mining.
- Vi har ikke nådd MVP, men vi nærmer oss målet. Vi mangler kun å få et mål for spillet og få et nytt spill når man avslutter

Dette var MVP-kravene våre fra oblig1: 
1. ~~Vise et spillebrett~~
2. ~~Vise spiller på spillebrett~~
3. ~~Flytte spiller med pil-taster, spesifisert mer i readme~~
4. ~~Spiller interagerer med terreng (vha. mus for å klikke på blokker)~~
5. ~~Vise fiender/monstre; de skal interagere med terreng og spiller~~
6. ~~Spiller kan dø (ved kontakt med fiender og ved å falle utfor skjermen)~~
7. Mål for spillbrett (enten et sted, en mengde poeng, drepe alle fiender e.l.) ENDRING: Vi tenker å ha en portal som man må åpne med en nøkkel. Når man entrer portalen så får man en BOSS man må bekjempe. Når BOSS dør vinner man spillet!
8. Nytt spillbrett når forrige er ferdig ENDRING: Når man bekjemper bossen eller dør så får man en skjerm hvor man kan velge å starte spillet på nytt.
9. ~~Start-skjerm ved oppstart / game over, samt en skjerm som viser ulike tastetrykk etc man kan bruke i spillet~~

### Prioritering av oppgaver
- Vi prioriterer oppgavene basert på hva som er nødvendig for å oppnå MVP. Vi har prioritert å få en player som kan bevege seg. Slik at vi ikke har fokusert på hvordan player interagerer med enemy og verdenen for å hakke brikker etc. - men det er planen videre å få til interaksjonene.
- Vi deler oppgaver hver fredag når vi har gruppemøte. 

### Endringer i kravene
- Vi har gjort noen justeringer i kravene basert på at vi har litt mer oversikt over hvordan spillet skal være. Blant annet når det kommer til målet for spillet og hva som skjer når man er ferdig med spillet.
- Endringene har blitt gjort for å gjøre det lettere for oss å vite hva vi skal gjøre og hva som er målet. 

## Produkt og kode

### Bidrag til kodebasen
- Vi har bidratt jevnt med å kode. Noen comitter ikke like ofte, mens andre comitter små kodebiter ofte. Sammenlagt er det like mye kode som blir utarbeidet av hvert av teammedlemmene - selv om det kanskje ikke er like mange commits per person.

### Dette har vi fikset siden sist
- Vi har fikset følgende siden sist:
  - Vi har fått til å animere player, slik at den nå har et sverd og beveger seg i riktig retning - før så beveget figur-animasjonen mot høyre selv om figuren ble styrt mot venstre. Selve figuren bevegte seg i riktig retning, det var kun animasjonen som var feil retning. 
- Implementert mining og plassering av blokker, ui for health og inventory. Fått lagt til at enemies kan dø og at player kan dø.

### Bygging, testing og kjøring
- Prosessen for å bygge, teste og kjøre koden er dokumentert i [README.md]. [Klikk her for å se README.md](../README.md)
- Vi har testet koden på Windows og OS X for å sikre kompatibilitet, men vi må også teste på Linux til neste gang.

### Klassediagram
- Vi har utarbeidet et klassediagram som viser de ulike klassene våre. Vi har brukt MVC-oppsettet slik at koden vår er delt inn i model-view-controller.

### Kodekvalitet og testdekning
- Vi har ikke jobbet med tester enda. Vi har noen tester i en annen branch, og vi tenker å jobbe med å få flere tester på plass frem mot neste innlevering. 
- Vi har ikke fått på plass automatiske tester for å dekke logikken i koden, vi har kun "testet" kode ved å se på player og verdenen visuelt. Dette er selvsagt noe vi skal jobbe mot å nå frem mot neste innlevering. 
