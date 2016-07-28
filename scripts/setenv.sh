#!/bin/bash

export JAVA_HOME=/Users/gniu/.jenv/versions/1.8
export COHERENCE_HOME=/Users/gniu/Oracle/mw12c/coherence
export DEMO_HOME=/Users/gniu/Workspaces/ProjectStuff/FederatedCachingDemo
export COH_OPTS="-Djava.net.preferIPv4Stack=true"
export CLASSPATH=$DEMO_HOME/classes:$DEMO_HOME/config:$COHERENCE_HOME/lib/coherence.jar:$COHERENCE_HOME/lib/jline.jar