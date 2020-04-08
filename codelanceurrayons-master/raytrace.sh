#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

java -Djava.awt.headless=true -Dapple.awt.UIElement=true -Dnoshow -Dsequential -cp $MYPATH/raytracer.jar tools.CLI $1 2>&1

