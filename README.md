# INF112 Prosjekt - Minecraft 2D (light versjon)

- **Gruppenavn:** Team 6 (Gruppe 2)
- **Teammedlemmer:** Magnus Yadi Halvorsen, Martine Naustdal Larsen, Nini Øyane Bjørnstad, Samuel Alois Starck Sjøen
- **Prosjekt:** Minecraft 2D (light versjon)
- **Lenke til asana:** https://app.asana.com/0/1206585808458619/1206585820662301
- **Lenke til git:** https://git.app.uib.no/samuel.sjoen/team6

## Om spillet
"Minecraft 2D light" er en todimensjonal versjon inspirert av det ikoniske Minecraft-universet. Målet er å utforske, bygge og mine ved å bevege seg rundt i en todimensjonell verden. 

- skal kunne hakke blokker, for så å kunne plassere dem ut
- lyd på hakke
- inventory
- bevege seg til høyre, venstre, hoppe
- (kanskje: kunne hakke seg nedover/klatre oppover)

## Bruk av spillet
- **Tastetrykk:**
  - A: Beveg player til venstre
  - D: Beveg player til høyre
  - Pil venstre: Bevege seg til venstre i menyen
  - Pil høyre: Bevege seg til høyre i menyen
  - Tab: Sverd for å slå ned enemies
  - Spacebar: Hopp
  - (ikke implementert enda) Esc: Åpne pausemeny

- **Museklikk**
  - Venstreklikk for å fjerne blokker
  - Høyreklikk for å plassere ut blokker
  - Hold inne venstreklikk/høyreklikk for å fjerne/plassere flere blokker av gangen.
  - Ønsker å legge inn funksjonalitet for å highlighte blokker når man tar musen over (ikke implementert enda). 

## Hvordan kjøre koden?
For å kjøre koden, følg disse trinnene:

1. Åpne prosjektet i en kodeeditor (f.eks. VSCode, Eclipse, IntelliJ).
2. Kompilér og kjør hovedklassen DesktopLauncher.java.

## Hvor grafikk og lyd-ressurser er hentet fra

Blokkene i verden er hentet fra: https://piiixl.itch.io/textures
Blokkene brukt i inventory er hentet fra: https://admurin.itch.io/mega-admurins-freebies (BlockyLife.zip)
Brukt denne siden til tekst for tittel og knapper på mainscreen: https://textcraft.net/
Slime(enemy) er hentet fra: https://rvros.itch.io/pixel-art-animated-slime
Pink monster er hentet fra: https://free-game-assets.itch.io/free-tiny-hero-sprites-pixel-art
Portal er hentet fra: https://elthen.itch.io/2d-pixel-art-portal-sprites

Vi har tenkt å bytte disse ut da kilde er ukjent.
Player er hentet fra: ukjent at the moment, skal finne kilde
Knight er hentet fra: ukjent at the moment, skal finne kilde

Player vi tenker å bruke fremover er fra: https://admurin.itch.io/mega-admurins-freebies (Monster_Pack_Free_Character.zip)


Brukt denne videoen som grunnlag for BodyHelperService.java, GameEntity.java og TiledMapHelper.java: https://www.youtube.com/playlist?list=PLVNiGun9focYT2OVFUzL30wUtOToo6frD