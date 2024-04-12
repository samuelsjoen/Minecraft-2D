# INF112 Prosjekt - Minecraft 2D (light versjon)

**Gruppenavn:** Team 6 (Gruppe 2)
**Teammedlemmer:** Magnus Yadi Halvorsen, Martine Naustdal Larsen, Nini Øyane Bjørnstad, Samuel Alois Starck Sjøen

**Prosjekt:** Minecraft 2D (light versjon)
**Lenke til project board:** https://app.asana.com/0/1206585808458619/1206585820662301
**Lenke til git:** https://git.app.uib.no/samuel.sjoen/team6

==Krav til Readme: Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes.==

## Om spillet
"Minecraft 2D light" er en todimensjonal versjon inspirert av det ikoniske Minecraft-universet. Målet er å utforske, bygge og mine ved å bevege seg rundt i en todimensjonell verden. 

## Målet med spillet

Målet med "Minecraft 2D" er å overleve et visst antall netter. Spilleren må håndtere utfordringer som fiender som angriper. For å overleve må spillerne utvikle strategier for å bekjempe fiender, samle nødvendige ressurser for å bygge beskyttende strukturer og utforske miljøet for å finne verdifulle skatter og hemmeligheter.

Gjennom utforskning og kreativitet kan spillerne tilpasse sin opplevelse ved å bygge sitt eget hjem, lage våpen og verktøy for å opprettholde spillerens helse.

Utforskning av den todimensjonale verdenen åpner opp for nye muligheter og utfordringer, og gir spilleren en spennende opplevelse som utfordrer deres evne til å tilpasse seg og overvinne hindringer i et pikselert landskap.

## Brukerveiledning

**Museklikk**
  - Venstreklikk: Fjerner blokker der musepekeren er
  - Høyreklikk: Plasserer ut blokken som er markert i inventory der musen er
  - Hold inne venstreklikk/høyreklikk for å fjerne/plassere flere blokker av gangen

**Tastetrykk:**
- Bevege spilleren
  - A: Beveg spilleren til venstre
  - D: Beveg spilleren til høyre
  - Tab: Angripe 
  - Spacebar: Hopp

- Inventory-funksjoner
  - E: Åpne/lukke crafting table
  - Q: Dropper inventory item som er markert
  - Pil venstre: Bevege seg til venstre i inventory
  - Pil høyre: Bevege seg til høyre i inventory

- Generelle funksjoner
  - F: Skru av/på fullskjerm modus
  - P: Åpne pausemeny
  - S: Starter spillet når man er inne på hjelpmenyen
  - Esc: Avslutte spillet

## Hvordan kjøre koden?
For å kjøre koden, følg disse trinnene:
1. Last ned koden fra Git ved å klone eller laste ned repositoriet til din lokale maskin.
2. Åpne prosjektet i en kodeeditor (f.eks. VSCode, Eclipse, IntelliJ).
3. Kompilér og kjør hovedklassen DesktopLauncher.java.

Man kan også bruke `gradle build` og `gradle run` i terminalen. ==Per nå er det noen problemer med 'gradle run' pga. file path, men det er noe vi tenker å få oppdatert==

## Hvordan teste koden?

For å teste koden vår bruker vi JUnit 5 for enhetstesting og Mockito for mock-objekter. Følg disse trinnene for å kjøre testene:

1. Åpne prosjektet i din foretrukne kodeeditor.
2. Naviger til test-fanen i din kodeeditor og kjør testene
3. Eventuelt i terminalen skriv inn kommandoen `gradle test`

## Hvor ressurser er hentet fra

=="For ting dere lager selv må dere legge ved lisens som klargjør hvordan ting (evt) kan gjenbrukes."==

### Grafikk

**Grafikk for blokker**
Blokkene i verden er hentet fra: https://piiixl.itch.io/textures
Blokkene brukt som inventory er hentet fra: https://admurin.itch.io/mega-admurins-freebies (BlockyLife.zip)

**Grafikk for spiller og fiender**
Player: https://admurin.itch.io/mega-admurins-freebie (Monster_Pack_Free_Character.zip)
Slime: https://rvros.itch.io/pixel-art-animated-slime
Pink monster: https://free-game-assets.itch.io/free-tiny-hero-sprites-pixel-art

Vi har hentet bilde til musepekeren fra denne siden: https://astropulse.itch.io/familiar-pixel-cursors

**Annet**
For tekst på MenuScreen.java og HelpScreen.java har denne siden blitt benyttet: https://textcraft.net/

### Lyd

**Lyd brukt når man fjerner blokker**
Lyd på hakke er hentet fra: https://www.youtube.com/watch?v=MXibe4aNdGg
"mineSound.wav": https://freesound.org/people/ryanconway/sounds/240801/

### Kode
Brukt denne videoen som grunnlag for BodyHelperService.java, GameEntity.java og MinecraftMap.java: https://www.youtube.com/playlist?list=PLVNiGun9focYT2OVFUzL30wUtOToo6frD
