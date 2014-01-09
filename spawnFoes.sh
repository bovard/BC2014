#!/bin/bash
# you must be in team122 for this to work!

declare -a versions=("0.1.1", "0.2.0", "0.2.1", "master")

spawnFoe() {
    local branch=$1
    local folderName="_team$1"
    git checkout $branch

    echo "rm -rdf ./$folderName"
    eval "rm -rdf ./$folderName"
    # eval "mkdir ./$folderName"
    # eval "cp -r ./team009 ./$folderName/"
    # eval "find ./$folderName -name '*.java' -type f -exec sed -i.bak 's/team009/$folderName/g' {} +"
    # eval "rm -f ./$folderName/*.bak"
}

for foe in versions
do
    spawnFoe $foe
done
git checkout master
