#!groovy

def firstTest(){    
    stage('build'){
      echo "Second stage"
    }
    
    stage('Cleanup workspace'){
      echo "Hola stage"
    }
}
return this