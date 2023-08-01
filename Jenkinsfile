pipeline {
	
    agent any
	environment {
   mvnHome = tool 'maven-3.9.1'
   dockerImage=""
dockerImageTag = "devopsexamplenew${env.BUILD_NUMBER}"
		 dockerHome = tool 'MyDocker' 
}
    parameters {
        booleanParam(name: "BUILD_FOR_PRODUCTION", defaultValue: false, description: "Check if it's for prod")
        choice(name: "BUILD_LANGUAGE", choices: ["JAVA", "NET", "PHP"] ,description: "Choose your techno, for dev please set default value in your commited file")
        string(name: "USERNAME", defaultValue: "ebouchebel", trim: true, description: "db")
      	password(name: "PASSWORD", defaultValue: "root", description: "db")
         booleanParam(name: "RUN_SONNAR", defaultValue: false, description: "run sonar or not")
	     string(name: "DOCKER_IMAGE_NAME", defaultValue: "imagetest", trim: true, description: "selectdockerimage")
	     booleanParam(name: "CHECK_TEST", defaultValue: false, description: "Check if test is ok or not")
	    
 	
    }
    stages {
        stage("Build Prod") {
		when {
                expression { 
                   return params.BUILD_FOR_PRODUCTION == true
                }
            }
            steps {
		
		git 'https://github.com/EmilBC/Gouvernance.git'
		
		    
                echo "Build stage Prod."
		    script {
               if (params.BUILD_LANGUAGE==""){
		       if(params.CHECK_TEST==false){
		sh "'${mvnHome}/bin/mvn' -B -DskipTests clean package"
		       }else{
			    sh "'${mvnHome}/bin/mvn' -B  clean package"   
		       }
		       echo "Build stage Prod. java" 
		} else {
		    echo "Build stage Prod. " 
		}
		    }
            }
	}
	      stage("Build Prod Dev") {
	when {
                expression { 
                  return params.BUILD_FOR_PRODUCTION == false
                }
            }
		 steps {
		
		git 'https://github.com/EmilBC/Gouvernance.git'
                echo "Build stage Dev"
         script{      
 if(params.CHECK_TEST==false){
		bat "${mvnHome}\\bin\\mvn.cmd -B -DskipTests clean package"
		       }else{
			 bat "${mvnHome}\\bin\\mvn.cmd -B  clean package"   
		       }
	 }
            }
        }




	    
    

	    
      stage('SCM') {
	      steps{
	  checkout scm
	      }    }
    stage('SonarQube Analysis') {
	   steps{
		       
      script{
	 if (params.RUN_SONNAR ==true){
      withSonarQubeEnv() {
      bat "${mvnHome}\\bin\\mvn clean verify sonar:sonar -Dsonar.projectKey=testoutsidegit -Dsonar.projectName='testoutsidegit'"
      }
      }
      }
	    }
    }	
  
  
    
  // stage('Initialize Docker'){    
	  // steps{
	      //    script{
	 // env.PATH = "${dockerHome}/bin:${env.PATH}"     
		//  }
	 //  }
 //   }
    
  //  stage('Build Docker Image') {
	//    steps{
    // bat "docker -H  tcp://7.tcp.eu.ngrok.io:18288  build -t gouvernance:${env.BUILD_NUMBER} ."
	 //   }
  //  }
    
   // stage('Deploy Docker Image'){
	   // steps{
      	//echo "Docker Image Tag Name: ${dockerImageTag}"
	//bat "docker -H  tcp://7.tcp.eu.ngrok.io:18288  run  --name gouvernance:${env.BUILD_NUMBER} -d -p 2222:2222 gouvernance:${env.BUILD_NUMBER}"
	 //   }
   // }
	  stage ('Deploy') {
      steps {
        script {
          deploy adapters: [tomcat9(credentialsId: 'tomcat_credential', path: '', url: 'http://10.12.1.182:8080/')], contextPath: '/gouvernance', onFailure: false, war: 'webapp/target/*.war' 
        }
      }
    }  



	    
    }
    
}
