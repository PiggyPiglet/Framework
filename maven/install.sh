#!/bin/bash
if [ $# -ne 2 ]; then
  echo "name and version not supplied"
  exit 1
fi

cd /e/Documents/Github/Framework/
mkdir maven/tmp/

names=$1
IFS="," read -ra my_array <<< "$names"

for i in "${my_array[@]}"
do
  IFS="/" read -ra cutName <<< "$i"
  cp maven/$cutName[-1]/pom.xml maven/tmp/pom.xml
  sed -i "s/@VERSION@/$2/g" maven/tmp/pom.xml
  mvn deploy:deploy-file -DgroupId=me.piggypiglet -DartifactId=framework-$cutName[-1] -Dversion=$2 -Dpackaging=jar -Dfile=$cutName[-1]/build/libs/$i-$2.jar -DpomFile=maven/tmp/pom.xml -DrepositoryId=piggypiglet -Durl=https://repo.piggypiglet.me/repository/maven-releases
  rm maven/tmp/pom.xml
done