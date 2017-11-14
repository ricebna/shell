#!/bin/bash

dir="$( cd "$( dirname "$0"  )" && pwd  )"
dir=$dir/git_tools
mkdir $dir >/dev/null 2>&1

if [ ! -n "$1" ];then
 	project_dir=$(cat $dir/project_dir)
	if [[ $project_dir = '' ]];then
	  echo '请设置总项目路径'
	  exit
	fi
else
	echo $1 > $dir/project_dir
  	echo '设置路径成功'
  	exit
fi

IFS=$'\n'
project=$(ls $project_dir)
for pr in $project
do
	cd $project_dir/$pr
	git branch >/dev/null 2>&1
	if [ $? -ne 0 ];then
		echo -e "\n$pr"
		echo "没有获取到仓库信息!"
		continue
	fi
	branch=$(git branch)
	for br in $branch
	do
		br=${br/\* /''}
		br=${br/  /''}
		if [[ $br != 'pro' && $br != 'test' && $br != 'dev' && $br != 'release' && $br != 'master' ]];then
			echo -e "\n$pr"
			#echo -e "$branch"
			git branch
			break
		fi
	done
done
