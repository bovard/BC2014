#!/bin/bash
# you must be in teams for this to work!

# deletes bin and current spawned foes.
if [ $# > 0 -a "$1" == "delete" ]; then
    find . -type d -name "_team*" -delete
    rm -rdf ../bin
    exit
fi

spawnFoe() {
    local branch=$1
    local folderName="_team${1//\./_}"
    git checkout $branch

    eval "rm -rdf ./$folderName"
    eval "mkdir ./$folderName"
    eval "cp -r ./team009/* ./$folderName/"
    eval "find ./$folderName -name '*.java' -type f -exec sed -i.bak 's/team009/$folderName/g' {} +"
    eval "find ./$folderName -name '*.bak'* -delete"
}

# Tags
spawnFoe 0.2.4
spawnFoe 0.2.5
spawnFoe 0.3.0

# Bovard

# Michael
spawnFoe superSoundTower

# Brent

# Devin


git checkout master
