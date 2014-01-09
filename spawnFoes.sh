#!/bin/bash
# you must be in team122 for this to work!

spawnFoe() {
    local branch=$1
    local folderName="_team${1//\./_/g}"
    git checkout $branch

    echo $folderName

    # eval "rm -rdf ./$folderName"
    # eval "mkdir ./$folderName"
    # eval "cp -r ./team009/* ./$folderName/"
    # eval "find ./$folderName -name '*.java' -type f -exec sed -i.bak 's/team009/$folderName/g' {} +"
    # eval "rm -rf ./$folderName/**/*.bak"
}

spawnFoe "0.1.1"
git checkout test
