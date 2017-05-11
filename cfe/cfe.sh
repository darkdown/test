#!/bin/sh 
# 
# recursive check files encoding and converter to new encoding
#
# cfe.sh (to encoding) (path) (file name filter)
# etc. : ./cfe.sh utf-8 ./ *.txt 
#

replace_encoding(){
	from_encoding=`file -b --mime-encoding $1`
	echo "[MSG] "$1 >> $log
	echo "[MSG] from "$from_encoding" to "$to_encoding >> $log
	if [ "$from_encoding" != "$to_encoding" ]
	then
		iconv -f $from_encoding -t $to_encoding $1 > $1.utf-8
		if [ $? -eq "1" ] 
		then
			rm $1.utf-8
			echo "[MSG] failed." >> $log
		else
			diff $1 $1.utf-8
			if [ $? -eq "0" ] 
			then
				rm $1.utf-8
				echo "[MSG] same content." >> $log
			else 
				cat $1.utf-8 > $1
				echo "[MSG] ok." >> $log
			fi
		fi
	fi
}

export -f replace_encoding 
export to_encoding=$1
export log="`pwd`/cfe.log"

echo . > $log
find $2 -name $3 -exec bash -c 'replace_encoding "$0"' {} \;
