# Pathfinding
Visualisation du pathfinding

Created by 
Clément Florant
Matthis Manthe
Theo Coquelin
Abdelkader Bennis

Ce programme a pour vocation d'illustrer le fonctionnement du pathfinding notamment dans le jeu vidéo.

Execution : Le main se situe dans Pathfinding.java

Avertissement : la troisième méthode de recherche de chemin est experimentale et nécessite de nombreuses optimisations, il n'est pas
conseillé de l'utiliser.

Utilisation :

La fenêtre quadrillée représente le terrain. L'autre fenêtre est l'interface qui vous permet de sélectionner ce que vous souhaiter ajouter
sur le terrain. Une fois un composant sélectionné, cliquez sur le quadrillage pour l'y déposer. Pour les composants multiples (obstacles,
ralentissements, ...) il est possible de maintenir la souris enfoncée tout en la déplaçant pour "colorier" rapidement une certaine zone.
Le bouton remove permet de retirer un composant du terrain en cliquant sur ce composant. Pour effacer le contenu du terrain, cliquer sur
clear. Le menu déroulant permet de sélectionner l'algorithme à utiliser pour résoudre le problème. Pour lancer la résolution d'un problème,
cliquez sur go (il est nécessaire d'avoir au moins fixé un point de départ et d'arrivée). Le mode temps vous permet d'estimer le temps
nécessaire à la résolution du problème en fonction de l'algorithme utilisé. Le mode reflexion montre le "raisonnement" de l'ordinateur.
La selection du thème vous permet de choisir des sprites "plus jolis" ce qui permet de faire plus facilement le lien avec le monde du
jeu vidéo. Le bouton Change Sprites permet quand à lui de modifier la topologie des îles du thème pirate.
