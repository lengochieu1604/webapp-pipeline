def validate() {
    sh "docker version"
} 
def build_docker_image()) {
    sh "docker build -t ${BUILD_IMAGE} ."
} 
def push_docker_image()) {
    withDockerRegistry([credentialsId: "${CREDENTIAL_ID}", url: "http://${ARTIFACT_REPOSITORY}"]) {
        sh "docker push ${BUILD_IMAGE}"
    }
} 
def deploy(){
    sh "kubectl set image deployment/angular-frontend angular-frontend=${BUILD_IMAGE}" 

}
def clean(){
    echo 'Cleaning up workspace'
    sh "docker rmi ${BUILD_IMAGE} || true"
    deleteDir()
}
return this