#!/bin/bash
if [ $# -ne 2 ]; then
  echo "name and version not supplied"
  exit 1
fi

cd /e/Documents/Github/Framework/

names=$1
IFS="," read -ra my_array <<< "$names"

for i in "${my_array[@]}"
do
  mvn deploy:deploy-file -DgroupId=me.piggypiglet -DartifactId=framework-$i -Dversion=$2 -Dpackaging=jar -Dfile=$i/build/libs/$i-$2.jar -DpomFile=maven/$i/pom.xml -DrepositoryId=piggypiglet -Durl=https://repo.piggypiglet.me/repository/maven-releases
done