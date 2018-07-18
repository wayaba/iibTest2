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
		
		stage('SonarQube analysis') {
			steps {
				script {
					def scannerHome = tool 'sonnar-jenkins'
					withSonarQubeEnv('sonarqube') {
						sh "${scannerHome}/bin/sonar-scanner \
										-Dsonar.projectKey=esqpipeline \
										-Dsonar.projectname=Esqpipeline \
										-Dsonar.projectVersion=1 \
										-Dsonar.sources=. \
										-Dsonar.language=esql"
					}
				}
			}
		}
		
		stage('Compilacion')
			{
				agent {
					docker { image 'ibmcom/iib:latest' 
							args '-u 0:0 -e LICENSE=accept -e NODENAME=DesaDocker1 -e SERVERNAME=MiSERVER1'
					}
				}
				steps{
						echo "EJECUTO ${params.mqsihome}/server/bin/mqsipackagebar -w ${params.workspacesdir} -a ${params.barname} -k ${params.appname}"
						sh "${params.mqsihome}/server/bin/mqsipackagebar -w ${params.workspacesdir} -a ${params.barname} -k ${params.appname}"
					}
					
			}
		
		stage('Deploy')
			{
				agent {
					docker { image 'ibmcom/iib:latest' 
							args '-u 0:0 -e LICENSE=accept -e NODENAME=DesaDocker1 -e SERVERNAME=MiSERVER1'
					}
				}
				steps{
						echo "EJECUTO ${params.mqsihome}/server/bin/mqsideploy -i http://192.168.99.100 -p 4415 -a ${params.barname} -e ungrupo"
						sh "${params.mqsihome}/server/bin/mqsideploy -i 192.168.99.100 -p 4415 -a ${params.barname} -e ungrupo"
					}
					
			}
		
		stage('Test')
			{
				steps{
						script {
							try{
								sh 'docker rm postmantemp'
							}
							finally{
								echo 'borro por las dudas'
							}
						}
						echo "Ejecuto el newman para llamar a la collection de postman"
						//sh 'docker run -t --name postmantemp  postman/newman_ubuntu1404 run https://www.getpostman.com/collections/968a33a4326a6494ede6 --disable-unicode'
						//sh 'docker run -v ~/var/jenkins_home/workspace/prueba2iib:/etc/newman -t --name postmantemp   postman/newman_ubuntu1404 run postman_collection.json --disable-unicode'
						sh 'docker run -v ~/var/jenkins_home/workspace/prueba2iib:/etc/newman -t --name postmantemp postman/newman_ubuntu1404 --collection="postman_collection.json"  --disable-unicode'
						sh 'docker rm postmantemp'
					}
				
			}
	}
}