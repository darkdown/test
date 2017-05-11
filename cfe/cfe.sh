#!/bin/sh 
# 
# recursive check files encoding and converter to new encoding
#
# cfe.sh (path) (file name filter) (to encoding)
# etc. : ./cfe.sh ./ *.txt utf-8
#

replace_encoding(){
	from_encoding=`file -b --mime-encoding $1`
	if [ "$from_encoding" != "$to_encoding" ]
	then 
		echo "[MSG] from "$from_encoding" to "$to_encoding
		iconv -f $from_encoding -t $to_encoding $1 > $1.utf-8
		if [ $? -eq "1" ] 
		then
			rm $1.utf-8
			echo "[MSG] failed."
		else
			diff $1 $1.utf-8
			if [ $? -eq "0" ] 
			then
				rm $1.utf-8
				echo "[MSG] same content." 
			else 
				cat $1.utf-8 > $1
				echo "[MSG] ok." 
			fi
		fi
	fi
}

export -f replace_encoding 
export to_encoding=$3

find $1 -name $2 -exec bash -c 'replace_encoding "$0"' {} \;
