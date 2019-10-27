#!/bin/bash
if [ $# -ne 2 ]; then
  echo "name and/or version not supplied"
  exit 1
fi

gradle clean
gradle jar

cd /e/Documents/Github/Framework/
rm -r maven/tmp/
mkdir maven/tmp/

names=$1

if [[ $names == "*" ]]; then
  nameArray=(core addons/console addons/http addons/jars addons/logback addons/mysql bindings/discord/jda bindings/minecraft/common bindings/minecraft/bukkit bindings/minecraft/sponge bindings/minecraft/bungeecord bindings/minecraft/velocity)
else
  IFS="," read -ra nameArray <<< "$names"
fi

for i in "${nameArray[@]}"
do
  IFS="/" read -ra cutName <<< "$i"

  name=${cutName[-1]}

  if [[ $name == "common" ]]; then
    name="${cutName[-2]}-common"
  fi

  cp maven/${name}/pom.xml maven/tmp/pom.xml
  sed -i "s/@VERSION@/$2/g" maven/tmp/pom.xml
  sed -i "s/@CORE@/$2/g" maven/tmp/pom.xml
  mvn deploy:deploy-file -DgroupId=me.piggypiglet -DartifactId=framework-$name -Dversion=$2 -Dpackaging=jar -Dfile=$i/build/libs/${cutName[-1]}-$2.jar -DpomFile=maven/tmp/pom.xml -DrepositoryId=piggypiglet -Durl=https://repo.piggypiglet.me/repository/maven-releases
  rm maven/tmp/pom.xml
done