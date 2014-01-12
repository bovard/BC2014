#!/bin/bash
# you must be in team122 for this to work!
local myCurrentBranch="master"
if [ $# > 0 ]; then
    myCurrentBranch=$1
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
spawnFoe 0.2.3
spawnFoe 0.2.4
spawnFoe 0.2.5

# Bovard

# Michael

# Brent

# Devin


git checkout $myCurrentBranch
