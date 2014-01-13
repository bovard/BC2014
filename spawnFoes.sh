#!/bin/bash
# you must be in teams for this to work!

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

# deletes bin and current spawned foes.
if [ $# > 0 ]; then

    while [ $# -gt 0 ]; do
        declare action=$1
        shift
        if [ "$action" == "delete" ]; then
            find . -type d -name "_team*" -exec rm -rdf {} \;
            rm -rdf ../bin
        else
            echo "Spawning $action"
            spawnFoe $action
        fi
    done
    rm 0
    git checkout master
    exit
fi


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
rm 0
