#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

java -Dapple.awt.UIElement=true -jar $MYPATH/raytracer.jar $1 2>&1

# echo "Pas encore réalisé"
