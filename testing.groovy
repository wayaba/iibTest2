#!groovy

def firstTest(){

  node('test-node'){
    
    stage('build'){
      echo "Second stage"
    }
    
    stage('Cleanup workspace'){
      echo "Hola stage"
    }
  }
}
return this