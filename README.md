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

**Første skjerm**
- 3 knapper
  - "Start Game" starter spillet.
  - "Game Controls" er hjelpesiden. Her får brukeren oversikt over alle kontrollmulighetene i spillet.
  - "Quit Game" avslutter spillet.

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
  - H: Åpne hjelpesiden

## Hvordan kjøre koden?
For å kjøre koden, følg disse trinnene:
1. Last ned koden fra Git ved å klone eller laste ned repositoriet til din lokale maskin.
2. Åpne prosjektet i en kodeeditor (f.eks. VSCode, Eclipse, IntelliJ).
3. Kompilér og kjør hovedklassen DesktopLauncher.java.

Man kan også bruke `gradle run` i terminalen.

## Hvordan teste koden?

For å teste koden vår bruker vi JUnit 5 for enhetstesting og Mockito for mock-objekter. Følg disse trinnene for å kjøre testene:

1. Åpne prosjektet i din foretrukne kodeeditor.
2. Naviger til test-fanen i din kodeeditor og kjør testene
3. Eventuelt i terminalen skriv inn kommandoen `gradle test`

## Hvor ressurser er hentet fra

### Grafikk

**Grafikk for blokker**
Blokkene i verden er hentet fra: PiiXL (2023). Textures-16. [Png fil]. itch.io. https://piiixl.itch.io/textures
Blokkene brukt som inventory er hentet fra: Admurin. (2022). BlockyLife [Sprite pack]. itch.io.https://admurin.itch.io/mega-admurins-freebies

**Grafikk for spiller og fiender**
Player: Admurin. (2023). Monster_Pack_Free_Character [Sprite pack]. itch.io. https://admurin.itch.io/mega-admurins-freebies 
Slime: rvros. (2018). slime [Sprite]. itch.io. https://rvros.itch.io/pixel-art-animated-slime
Pink monster: Free Game Assets (GUI, Sprite, Tilesets). (2019). free-pixel-art-tiny-hero-sprites [Sprite pack]. itch.io. https://free-game-assets.itch.io/free-tiny-hero-sprites-pixel-art
Knight: aamatniekss. (2021). FreeKnight_v1 [Sprite]. itch.io. https://aamatniekss.itch.io/fantasy-knight-free-pixelart-animated-character

Vi har hentet bilde til musepekeren fra denne siden: Astropulse. (2023). Pixel Cursors, astro_arrow [Cur fil]. itch.io. https://astropulse.itch.io/familiar-pixel-cursors

**Grafikk for inventory, health, overlay**
Pinne: Admurin. (2022). Admurin's Armory [Sprite pack]. itch.io https://admurin.itch.io/pixel-armory
Sverd: DantePixels (2023) Tools Asset 16x16 [Sprite pack]. itch.io https://dantepixels.itch.io/tools-asset-16x16
Samuel Sjøen (2024) Overlay textures. Lisens: CC0 1.0 Universal

**Annet**
For tekst på MenuScreen.java og HelpScreen.java har denne siden blitt benyttet: Textcraft. (2024). Minecrafter [Font]. Textcraft.net. https://textcraft.net/
Bakgrunnsbilde når man taper: Generated with AI ∙ April 30, 2024 at 5:18 PM. https://www.bing.com/images/create/a-solemn-and-sad-atmosphere-with-27game-over27-text-/1-66310bbf22a94349a09e8e2e9111f254?id=AkidL78M%2fLXYNmcMvLQ1wA%3d%3d&view=detailv2&idpp=genimg&thId=OIG4.N.gQ.Xn3lc4vfJQo4noe&FORM=GCRIDP&ajaxhist=0&ajaxserp=0
Bakgrunnsbilde når man vinner: Generated with AI ∙ April 30, 2024 at 4:58 PM. https://www.bing.com/images/create/minecraft-inspired-game-win-screen-with-the-word-27/1-6631071a452f4e8eb907d46712ce82ca?id=oqqW3oDEaPa99ghhS4G%2fCw%3d%3d&view=detailv2&idpp=genimg&thId=OIG3.koz_AolWtspy1WXgQj6n&FORM=GCRIDP&mode=overlay

### Lyd

**Lyd brukt når man fjerner blokker**
"mineSound.wav": ryanconway. (2014). Pickaxe mining stone [Lydklipp]. Freesound.org.  https://freesound.org/s/240801/. Lisens: Creative Commons Attribution 4.0

### Kode
Brukt denne spillelisten som grunnlag for BodyHelperService.java, GameEntity.java og MinecraftMap.java:  Small Pixel Games. (2021). 2D platformer [Spilleliste]. YouTube. https://www.youtube.com/playlist?list=PLVNiGun9focYT2OVFUzL30wUtOToo6frD
