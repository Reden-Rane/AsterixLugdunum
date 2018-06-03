![Banner](/images/image4.png)

<h2> Introduction </h2>

Pour le projet de fin d’année 2017-2018, nous avions pour objectif de réaliser un jeu faisant appel à nos connaissances acquises durant l’année (tableau 2D, objets, variables, conditions, boucle...). L’un des objectif vise également à développer notre répartition du travail dans un projet d’équipe : __Charles Javerliat, Loris Leong, Clément Pambrun__.

<h2>Téléchargement</h2>
Releases disponibles ici: https://github.com/Reden-Rane/AsterixLugdunum/releases

<h2>Le principe du jeu  </h2>

Le mini-jeu consiste en une course de rapidité se jouant à un joueur. L’objectif étant d’appuyer sur les touches données à chaque séquence générée aléatoirement, tout en limitant les erreurs afin d’avancer le plus vite possible. Ainsi, plus vite le joueur appuie correctement sur les touches indiquées, plus son temps sera optimal.
Nous avons décidé de réaliser ce jeu avec une interface graphique et des effets sonores pour le rendre plus attractif.

<h2>Analyse technique </h2>

Nous avons décidé d’emprunter une approche orientée objet car elles satisfait au mieux nos besoins sur ce jeu. Le niveau de la course est contenu dans la classe `BoatRaceLevel` qui contient les divers objets nécessaires à son bon fonctionnement.

A l’ouverture du mini-jeu, le niveau est réinitialisé par la méthode `resetLevel()` qui remet donc les valeurs par défaut pour les objets utilisés (comme la position du bateau (objet `playerBoat`), le score, ...)

L’objet `Boat` possède ralentit lorsqu’il est touché par un boulet de canon.

La méthode void `update()` met à jour la logique du niveau et vérifie si celui ci est terminé grâce à la méthode `getPlayerProgress()` (renvoyant une valeur entre 0 pour le départ et 1 à l’arrivée). Lorsque le joueur a atteint la distance fixée par la variable `LEVEL_FINISH_LINE_DISTANCE`), le temps mis pour finir la course (récupéré par la méthode `getDuration()`, est comparé à son record et ce dernier est mis à jour si le joueur a réalisé un meilleur temps (variable `bestTime`).
La méthode parent d’`update()` permet de mettre à jour les positions des entités en additionnant la vélocité à leur position actuelle, on appelle le update() du parent avec `super.update()`. Régulièrement (période spécifique : `long CANNONBALL_SPAWN_PERIOD`), `update()` appelle `randomlyShootCannonball()` qui a la probabilité de faire apparaître un boulet de canon. La trajectoire du boulet est définie dans la méthode `shootCannonball()` et dans la classe objet `Cannonball (float x, float y)`. La méthode `update()` suit alors l’évolution de l’objet et l’enlève du niveau s’il entre en collision avec le bateau ou s’il disparaît de l’écran (tombe à l’eau).


Avant la course, le joueur doit appuyer sur la touche ENTREE qui permet de commencer le niveau grâce à la méthode `keyEvent()`, la variable levelStarted étant fausse avant le départ.

![AppuyerSurEntree](/images/image3.png)

Lorsque le joueur actionne une touche, la méthode `keyEvent()` vérifie que seule une touche est actionnée et que celle-ci correspond bien à la touche générée et renvoyée par la méthode `getKeyToPress()`. Le nombre de touches correctes effectué dans la séquence est alors compté dans la variable `validKeyCounter`.
Si la touche appuyée est correcte, cela se traduit visuellement par la touche qui grossit, autrement elle tremble signifiant que le joueur s’est trompé (Cf. GIF ci-dessous).

![Gameplay](/images/image1.gif)

Au cours du niveau, la méthode `generateRandomKeySequence(int sequenceSize, int[] keys)` permet de générer aléatoirement dans la liste dynamique `keySequence`, les touches (dont le nombre pour chaque séquence est défini par la variable `initialKeySequenceSize`) que le joueur devra suivre pour faire avancer le bateau.

A chaque fin de séquence, la méthode `handleKeySequence()` applique le bonus de vitesse proportionnel au nombre de touches appuyées correctement (variable `validKeyCounter`) par le joueur.

La méthode `applyWaterResistance()` permet de prendre en compte la résistance de l’eau sur le bateau afin de rendre plus réaliste son déplacement et de pénaliser le joueur si il n’appuie pas assez rapidement sur les touches car il freinera.

<h2>Retours d’expérience et commentaires </h2>

Cette expérience fut forte intéressante car elle nous a permis de voir concrètement les possibilités que permet l'algorithmie.
Nous voulions au départ développer différents mini-jeux accessibles depuis le hub, mais nous n’avons finalement réalisé que la course de bateau par manque de temps.

<h2>License </h2>

>The MIT License (MIT)
>Copyright (c) 2018 Charles Javerliat

>Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.```
