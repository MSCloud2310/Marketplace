#!/bin/sh
find . -name "pom.xml" | while read -r file; do
	folder=$(dirname "$file")
	cd "$folder" || exit
	echo "Initializing $folder ..."
	mvn clean && mvn install -DskipTests
	cd - >/dev/null || exit
done
