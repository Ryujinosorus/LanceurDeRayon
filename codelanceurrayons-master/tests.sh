#!/bin/bash

set -e

. assert.sh


assert "./compare.sh TEST1/image1.png TEST1/image1.png" "OK\n0\n"
assert "./compare.sh TEST1/image2.png TEST1/image2.png" "OK\n0\n"
assert "./compare.sh TEST1/image1.png TEST1/image2.png" "OK\n879\n"
assert "cp diff.png localdiff.png"
assert "./compare.sh TEST1/diff.png localdiff.png" "OK\n0\n"

assert_end regression


echo "Tests de la bibliothèque mathématique"

assert "./checktriplet.sh \"P 1 1 1,add,P 2 2 2\"" "Interdit"
assert "./checktriplet.sh \"P 1 1 1,mul,P 2 2 2\"" "Interdit"
assert "./checktriplet.sh \"P 1 1 1,mul,2\"" "P 2.0 2.0 2.0"
assert "./checktriplet.sh \"P 1 1 1,sub,P 2 2 2\"" "V -1.0 -1.0 -1.0"
assert "./checktriplet.sh \"P 1 1 1,add,V 2 2 2\"" "P 3.0 3.0 3.0"
assert "./checktriplet.sh \"P 1 1 1,mul,V 2 2 2\"" "Interdit"
assert "./checktriplet.sh \"V 1 1 1,sub,V 2 2 2\"" "V -1.0 -1.0 -1.0"
assert "./checktriplet.sh \"V 1 1 1,dot,V 2 2 2\"" "6.0"
assert "./checktriplet.sh \"V 1 0 0,cross,V 0 1 0\"" "V 0.0 0.0 1.0"

assert_end regression

