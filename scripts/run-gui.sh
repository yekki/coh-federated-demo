#!/bin/bash

. ./setenv.sh

CLUSTER=$1

if [ "$CLUSTER" == "ClusterA" ]; then
        CACHECONFIG=$DEMO_HOME/config/demo-cache-ClusterA.xml
elif [ "$CLUSTER" == "ClusterB" ]; then
        CACHECONFIG=$DEMO_HOME/config/demo-cache-ClusterB.xml
else
        echo "Please specify the cluster name: ClusterA or ClusterB!"
        exit 1
fi

COH_OPTS="$COH_OPTS -Dcoherence.distributed.localstorage=false -Dcoherence.cacheconfig=$CACHECONFIG"
#COH_OPTS="$COH_OPTS -Dcoherence.log=$DEMO_HOME/logs/federation.log"
#COH_OPTS="$COH_OPTS -Didentity.manager=file:$2-keystore.jks"

$JAVA_HOME/bin/java -Xms64m -Xmx256m $COH_OPTS com.oracle.poc.coherence.GUI.DemoGUI $*
