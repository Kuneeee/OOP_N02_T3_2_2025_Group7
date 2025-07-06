
filename=$(basename "$1" .java)
javac "$1" || exit 1
java "$filename"
rm "${filename}.class"
