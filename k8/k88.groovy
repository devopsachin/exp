import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.json.JsonBuilder


node {
        def rootdir="/home/ubuntu"
        def hostname="${hostname}"
	def Instanceid

        stage ('Logining to AWS account'}{
                common.loginaws()
}
        stage ('Loading pipelines scripts '){
                checkout scm
                common=load "${rootdir}/exp/Common.groovy"
}

	stage ('creat vm in aws '){
		common.createInstance(Instanceid)
}
}