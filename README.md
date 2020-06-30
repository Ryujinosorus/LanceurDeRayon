# Qu'est ce qu'un Lanceur de Rayon ? 
Un Lanceur de Rayon est une methode permettant de générer des images de synthèses.
Cette méthode consiste à parcourir le sens inverse la lumière.

# Comment le faire fonctionner 

1) Lancez ./build.sh
2) Fini

Vous pouvez lancer ./raytrace.sh demo1.scene.
"demo1.scene" represente le fichier scene qui sera lu.
Plusieurs fichier scene sont fournit pour vous servir d'exemple.

# Guide utilisateurs

Créons un projet pas à pas, permettant d'afficher une sphère magenta sur un plan.
Créez un fichier exemple.scene et entrez ce qui suit.
```
# nous donne une taille de 1024 sur 768
size 1024 768

# le nom de sortie de notre fichier avec son extension
# le logiciel sortira toujours un png
output exemple.png

# on défini une caméra
# sa position, vers où regarder (attention l'axe Z se dirige vers vous), le dessus de l'oeil, le champ de vision
camera 0 0 0 0 0 -1 0 1 0 45

# activer ou non les ombres
shadows true

# la lumière ambient
ambient .1 .1 .1
# une lumière directionnelle, avec sa direction et sa couleur
directional 0 0 1 .5 .5 .5
# une lumière ponctuelle, avec sa position et sa couleur
point 3 0 -3 .5 .5 .5

# la lumière diffusé par la sphère
# attention la somme des composantes de diffuse et ambient ne doivent pas dépasser 1
diffuse .9 .0 .9
# la sphère, position et rayon
sphere 0 0 -5 1

diffuse .9 .9 .9
# un plan, un point du plan et sa normale
plane 0 -1 0 0 1 0
```
