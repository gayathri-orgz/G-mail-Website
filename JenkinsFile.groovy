// Ran on windows machine

pipeline{
    agent any
    
    stages {
        stage ('Fetch'){
            steps {
                echo "Fetching code from GIT"
                git branch: 'main', url: 'https://github.com/gayathri-orgz/G-mail-Website.git'
            }
        }
        
        stage ('Build'){
            steps {
                echo "Building code"
                bat 'docker build -t static-website:latest .'
        
            }
        }
        
        stage ('Deploy'){
            steps {
                echo "Deploying app"
                bat '''
                docker rm -f static-website || echo "No old container"
                docker run -d --name static-website -p 80:80 static-website:latest
                '''
            }
        }
    }
}
