#!/bin/bash

. ./setenv.sh

CLUSTER=$1

if [ -z $CLUSTER ]; then
	echo "Please specify the cluster name: ClusterA or ClusterB!"
	exit 1
fi

pids=`ps aux | grep "cluster=$CLUSTER" | grep -v grep | awk '{print $2}'`

if [ -z $pids ] ; then
	echo "No cache member found in $CLUSTER"
else
	kill -9 $pids
	echo "All members in $CLUSTER were killed!"
fi

exit 0
