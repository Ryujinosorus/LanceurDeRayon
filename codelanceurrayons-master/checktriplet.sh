#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

java -Dapple.awt.UIElement=true -cp $MYPATH/raytracer.jar tools.CheckTriplet "$1"
