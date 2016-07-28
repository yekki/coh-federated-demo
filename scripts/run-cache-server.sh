#!/bin/bash

. ./setenv.sh

CLUSTER=$1

# set the correct keystore for the cluster
# SSL_CONFIG="-Didentity.manager=file:${CLUSTER}-keystore.jks"

if [ "$CLUSTER" == "ClusterA" ]; then
	CLUSTERPORT=11100
	JMXPORT=17091
	CACHECONFIG=$DEMO_HOME/config/demo-cache-ClusterA.xml
elif [ "$CLUSTER" == "ClusterB" ]; then
	CLUSTERPORT=11200
	JMXPORT=17092
	CACHECONFIG=$DEMO_HOME/config/demo-cache-ClusterB.xml
else
	echo "Please specify the cluster name of federation cluster: ClusterA or ClusterB!"
	exit 1
fi

jmxpids=`ps aux | grep "cluster=$CLUSTER" | grep "port=$JMXPORT" | grep -v grep | awk '{print $2}'`

if [ -z $jmxpids ] ; then
	JMX_OPTS="-Dtangosol.coherence.management=all -Dtangosol.coherence.management.remote=true -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMXPORT -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=127.0.0.1"
fi

COH_OPTS="$COH_OPTS $JMX_OPTS -Dcoherence.clusterport=$CLUSTERPORT -Dcoherence.cluster=$CLUSTER -Dcoherence.cacheconfig=$CACHECONFIG"

$JAVA_HOME/bin/java $COH_OPTS -cp $CLASSPATH -Xms512m -Xmx512m com.tangosol.net.DefaultCacheServer
