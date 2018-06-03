![Banner](/images/image4.png)

<h2> Introduction </h2>

Pour le projet de fin d’année 2017-2018, nous avions pour objectif de réaliser un jeu faisant appel à nos connaissances acquises durant l’année (tableau 2D, objets, variables, conditions, boucle...). L’un des objectif vise également à développer notre répartition du travail dans un projet d’équipe : __Charles Javerliat, Loris Leong, Clément Pambrun__.

<h2>Téléchargement</h2>
Releases disponibles ici: https://github.com/Reden-Rane/AsterixLugdunum/releases

<h2>Le principe du jeu  </h2>

Le mini-jeu consiste en une course de rapidité se jouant à un joueur. L’objectif étant d’appuyer sur les touches données à chaque séquence générée aléatoirement, tout en limitant les erreurs afin d’avancer le plus vite possible. Ainsi, plus vite le joueur appuie correctement sur les touches indiquées, plus son temps sera optimal.
Nous avons décidé de réaliser ce jeu avec une interface graphique et des effets sonores pour le rendre plus attractif.

<h2>Analyse technique </h2>

Nous avons décidé d’emprunter une approche orientée objet car elles satisfait au mieux nos besoins sur ce jeu.
Le niveau de la course est contenu dans la classe `BoatRaceLevel`.

<h3>BoatRaceLevel.java</h3>

`resetLevel()`: Appelée à chaque réouverte/redémarrage du niveau, remet à zéro les attributs tels que la position du bateau du joueur, le score, vide la liste des boulets de canons présents dans le niveau, etc. Nous avons préféré opter pour une telle méthode plutôt que de recréer une instance du niveau à chaque fois car celà est moins gourmand (pas besoin de réattribuer de l'espace en mémoire).

`generateTerrain()`: Génère le monde, on place 3 tuiles d'eau dans le bas, avec une génération aléatoire qui place des nénuphars de part et d'autre. Au place une ligne qui est une transition entre l'eau et la terre quand y = 3. Finalement on place de la terre pour créer la rive en plaçant aléatoirement des variantes d'herbe avec/sans fleurs, etc.

`update()`: Met à jour le niveau tous les ticks (1 tick = 0,05 secondes). La méthode update de BoatRaceLevel appelle update de son parent qui met à jour la position de toutes les entités du niveau en leur ajoutant la vélocité à leur position.
La version surchargée pour ce niveau spécifique permet de faire apparaitre aléatoirement des boulets de cannon par l'appel de `randomlyShootCannonball()`. On applique également les décélérations du bateau avec la résistance de l'eau `applyWaterResistance()` et l'accélération dans `handleKeySequence()` qui, lorsqu'une séquence est terminée, accélère le bateau par `MAX_ACCELERATION*ratio`, le ratio étant le quotient du nombre de touches correctes sur le nombre de touches contenues dans la séquence. La dernière partie de cette méthode permet de gérer la fin du niveau, si la progression est supérieure à 1 (ligne d'arrivée atteinte) alors on enregistre le temps afin de faire le calcul de la durée qu'à mis le joueur pour parcourir la distance donnée. Joue également les bruitages "Finish" et "New record" si un nouveau record est atteint dans le cas où la durée est inférieure au `bestTime` stocké de manière statique car indépendante de toute instance.

`updateCamera()`: Centre la caméra sur le bateau

`updateCannonballs()`: On détruit les boulets de canon inutiles (sortis du niveau, déjà écrasés) et on vérifie les collisions avec l'eau ou avec le bateau, auquel cas on appelle `playerBoat.hit()` qui ralentira le bateau. Si le boulet tombe dans l'eau on le détruit et on fait apparaitre un effet de jet d'eau au point d'impact.

`keyEvent()`: Sûrement la méthode la plus importante, elle permet de gérer les entrées claviers. Cette méthode vérifie que seule une touche est actionnée et que celle-ci correspond bien à la première touche de la séquence générée (et donc celle qu'il faut appuyer) et renvoyée par la méthode `getKeyToPress()`. Le nombre de touches correctes effectué dans la séquence est alors compté dans la variable `validKeyCounter`.
Si la touche appuyée est correcte, cela se traduit visuellement par la touche qui grossit, autrement elle tremble signifiant que le joueur s’est trompé (Cf. GIF ci-dessous).

![Gameplay](/images/image1.gif)

Avant la course, le joueur doit appuyer sur la touche ENTREE qui permet de commencer le niveau grâce à la méthode `keyEvent()`, la variable levelStarted étant fausse avant le départ.

![AppuyerSurEntree](/images/image3.png)

`generateRandomKeySequence(int sequenceSize, int[] keys)`: Génère aléatoirement une séquence de touche dans la liste dynamique `keySequence` (on stockera la taille initiale dans `initialKeySequenceSize` pour la calcul du ratio par la suite car la taille d'une liste dynamique n'est pas fixe au fur et à mesure qu'on supprime les objets qu'elle contient).

`getPlayerProgress()`: Retourne un flottant compris entre 0 et 1 inclus. La valeur 0 correspond au joueur positionné au début du niveau, et donc 1 au joueur étant positionné  la fin du niveau. Elle est utile pour afficher le message "Finish" ou bien stopper le compteur de temps  la fin du niveau.

<h3>Boat.java</h3>

Cette classe définit l'architecture du bateau que le joueur incarne, elle contient sutout des attributs nécessaires pour le rendu du bateau (rotation des rames, le dernier instant auquel le bateau a été touché par un boulet de canon, etc)

<h3>Cannonball.java</h3>

La seule méthode surchargée est la méthode `update()`. Ici elle met à jour la vélocité du boulet de cannon pour lui appliquer de la gravité.

![Gameplay](/images/image5.gif)

<h2>Retours d’expérience et commentaires </h2>

Cette expérience fut forte intéressante car elle nous a permis de voir concrètement les possibilités que permet l'algorithmie.
Nous voulions au départ développer différents mini-jeux accessibles depuis le hub, mais nous n’avons finalement réalisé que la course de bateau par manque de temps.

<h2>License </h2>

>The MIT License (MIT)
>Copyright (c) 2018 Charles Javerliat

>Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.```
