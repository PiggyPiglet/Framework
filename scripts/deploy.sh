#!/bin/bash
if [ $# -ne 2 ]; then
  echo "name and/or version not supplied"
  exit 1
fi

cd /e/Documents/Github/Framework/
rm -r maven/tmp/
mkdir maven/tmp/

gradle clean updateLicenses build

names=$1

if [[ $names == "all" ]]; then
  nameArray=(core addons/console addons/http addons/jars addons/logback addons/mysql bindings/discord/jda bindings/minecraft/common bindings/minecraft/bukkit bindings/minecraft/sponge bindings/minecraft/bungeecord bindings/minecraft/velocity bindings/minecraft/nukkit)
elif [[ $names == "bindings" ]]; then
  nameArray=(core bindings/discord/jda bindings/minecraft/common bindings/minecraft/bukkit bindings/minecraft/sponge bindings/minecraft/bungeecord bindings/minecraft/velocity bindings/minecraft/nukkit)
else
  IFS="," read -ra nameArray <<< "$names"
fi

deploy() {
  IFS="/" read -ra cutName <<< "$1"

  name=${cutName[-1]}

  if [[ $name == "common" ]]; then
    name="${cutName[-2]}-common"
  fi

  pomDir="maven/tmp/${name}"
  pom="${pomDir}/pom.xml"

  mkdir -p $pomDir
  cp maven/${name}/pom.xml $pom
  sed -i "s/@VERSION@/$2/g" $pom
  mvn deploy:deploy-file -DgroupId=me.piggypiglet -DartifactId=framework-$name -Dversion=$2 -Dpackaging=jar -Dfile=$i/build/libs/${cutName[-1]}-$2.jar -Dsources=$i/build/libs/${cutName[-1]}-$2-sources.jar -Djavadoc=$i/build/libs/${cutName[-1]}-$2-javadoc.jar -DpomFile=${pom} -DrepositoryId=piggypiglet -Durl=https://repo.piggypiglet.me/repository/maven-releases
  rm $pom
}

for i in "${nameArray[@]}"
do
  deploy $i $2 &
done

wait