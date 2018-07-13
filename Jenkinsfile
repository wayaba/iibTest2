#!/bin/bash
pipeline {

	agent any
	
	parameters {
        string(name: 'mqsihome', defaultValue: '/opt/ibm/iib-10.0.0.11', description: '')
		string(name: 'workspacesdir', defaultValue: '/var/jenkins_home/workspace/Pipelineando', description: '')
		string(name: 'barname', defaultValue: '/var/jenkins_home/workspace/bar/apimascotas2.bar', description: '')
		string(name: 'appname', defaultValue: 'ApiMascotas', description: '')
    }

	stages {
		
		stage('Compilacion')
			{
				agent {
					docker { image 'ibmcom/iib:latest' 
							args '-u 0:0 -e LICENSE=accept -e NODENAME=DesaDocker1 -e SERVERNAME=MiSERVER1'
					}
				}
				steps{
						echo "EJECUTO ${params.mqsihome}/server/bin/mqsipackagebar -w ${params.workspacesdir} -b ${params.barname} -k ${params.appname}"
						sh "${params.mqsihome}/server/bin/mqsipackagebar -w ${params.workspacesdir} -b ${params.barname} -k ${params.appname}"
					}
					
			}
		
	}
}