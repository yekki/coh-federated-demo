#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home
export COHERENCE_HOME=/Users/gniu/Oracle/mw12c/coherence
export DEMO_HOME=/Users/gniu/Workspaces/coh-federated-demo
export COH_OPTS="-Djava.net.preferIPv4Stack=true"
export CLASSPATH=$DEMO_HOME/classes:$DEMO_HOME/config:$COHERENCE_HOME/lib/coherence.jar:$COHERENCE_HOME/lib/jline.jar