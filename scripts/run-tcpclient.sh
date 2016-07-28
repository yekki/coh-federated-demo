#!/bin/bash

. ./setenv.sh

COH_OPTS="$COH_OPTS -Dcoherence.cacheconfig=$DEMO_HOME/config/demo-cache-client.xml"

$JAVA_HOME/bin/java -Xms64m -Xmx256m $COH_OPTS com.oracle.poc.coherence.clients.CrossCluster $*
